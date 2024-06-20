package com.dippy.es.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dippy.entity.CarInfo;
import com.dippy.entity.CarPicture;
import com.dippy.entity.Region;
import com.dippy.es.constant.ESConstant;
import com.dippy.es.model.CarInfoModel;
import com.dippy.es.model.RegionModel;
import com.dippy.es.repository.CarInfoRepository;
import com.dippy.es.repository.RegionRepository;
import com.dippy.es.service.SearchService;
import com.dippy.es.vo.*;
import com.dippy.mq.MQIndexMessage;
import com.dippy.service.CarInfoService;
import com.dippy.service.CarPictureService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    public CarInfoRepository carInfoRepository;

    @Autowired
    public RegionRepository regionRepository;

    @Autowired
    public CarInfoService carInfoService;

    @Autowired
    public CarPictureService carPictureService;

    @Autowired
    public RestHighLevelClient client;


    @Override
    public SearchResult searchListPage(SearchParamVo searchParamVo) {

        // 0. 构建DSL语句
        SearchResult result = null;

        // 1. 准备检索请求
        SearchRequest searchRequest = buildSearchRequest(searchParamVo);


        try {
            // 2. 执行检索请求
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

            // 3.分析响应数据、封装需要的格式

            result = buildSearchResult(response, searchParamVo);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 1. 准备检索请求
     *
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParamVo paramVo) {
        // 构建DSL语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        /**
         * 查询：模糊查询，过滤（分类、按品牌）
         *      term :精确值用
         *      must :文本用
         *
         *      term：文本用全文检索（match）、非文本字段（即数值之类的）、精确值用term
         *          全文匹配条件写在match里，而剩余字段写在filter
         */

        // 1. 构建bool -query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 1.1 must-模糊匹配 -  carBrand 按照品牌名
        // if (!StrUtil.isEmpty(paramVo.getKeyword())) {
        //     boolQuery.must(QueryBuilders.matchQuery("carBrand",paramVo.getCarBrand()));
        // }
        // 1.2.0 bool -must -
        // 实现搜索关键词多字段匹配
        if (!StrUtil.isEmpty(paramVo.getKeyword())) {

            // 多字段匹配
            boolQuery.must(QueryBuilders.multiMatchQuery(paramVo.getKeyword(),
                    "carBrand", "carSeries", "carType", "carGearBox", "carTitle", "carColor", "carConfiguration"));
            // boolQuery.must(QueryBuilders.matchQuery("carBrand", paramVo.getKeyword()));
            // boolQuery.must(QueryBuilders.matchQuery("carTitle", paramVo.getKeyword()));
            // boolQuery.must(QueryBuilders.matchQuery("carColor.keyword", paramVo.getKeyword()));
            // boolQuery.must(QueryBuilders.matchQuery("carSeries.keyword", paramVo.getKeyword()));
            // boolQuery.must(QueryBuilders.matchQuery("carType.keyword", paramVo.getKeyword()));
            // boolQuery.must(QueryBuilders.matchQuery("carConfiguration.keyword", paramVo.getKeyword()));
        }
        // 1.2  ----- 查品牌
        if (!StrUtil.isEmpty(paramVo.getCarBrand())) {
            boolQuery.must(QueryBuilders.matchQuery("carBrand", paramVo.getCarBrand()));
        }
        // 1.2.1  bool - filter - carSeries 按照车系
        if (!StrUtil.isEmpty(paramVo.getCarSeries())) {
            boolQuery.must(QueryBuilders.matchQuery("carSeries.keyword", paramVo.getCarSeries()));
        }
        // 1.2.2  bool - filter - carType 类型
        if (!StrUtil.isEmpty(paramVo.getCarType())) {
            boolQuery.must(QueryBuilders.matchQuery("carType.keyword", paramVo.getCarType()));
        }
        // carUserTime 使用年限
        if (!ObjectUtil.isEmpty(paramVo.getCarUserTime())) {
            boolQuery.filter(QueryBuilders.termQuery("carUserTime", paramVo.getCarUserTime()));
        }
        // transferCount 过户次数
        if (!ObjectUtil.isEmpty(paramVo.getTransferCount())) {
            boolQuery.filter(QueryBuilders.termQuery("transferCount", paramVo.getTransferCount()));
        }
        // carCarMileage 里程(公里)
        if (!ObjectUtil.isEmpty(paramVo.getCarCarMileage())) {
            boolQuery.filter(QueryBuilders.termQuery("carCarMileage", paramVo.getCarCarMileage()));
        }
        //         carColor 颜色
        if (!StrUtil.isEmpty(paramVo.getCarColor())) {
            boolQuery.must(QueryBuilders.matchQuery("carColor.keyword", paramVo.getCarColor()));
        }
        //         carGearBox 变数箱类型
        if (!StrUtil.isEmpty(paramVo.getCarGearBox())) {
            boolQuery.must(QueryBuilders.matchQuery("carGearBox.keyword", paramVo.getCarGearBox()));
        }
        // carDisplacement 排量
        if (!ObjectUtil.isEmpty(paramVo.getCarDisplacement())) {
            boolQuery.filter(QueryBuilders.termQuery("carDisplacement", paramVo.getCarDisplacement()));
        }
        // carSeat 座位数
        if (!ObjectUtil.isEmpty(paramVo.getCarSeat())) {
            boolQuery.filter(QueryBuilders.termQuery("carSeat", paramVo.getCarSeat()));
        }
        // carFuelType 燃料类型
        if (!StrUtil.isEmpty(paramVo.getCarFuelType())) {
            boolQuery.must(QueryBuilders.matchQuery("carFuelType.keyword", paramVo.getCarFuelType()));
        }
        // carQualityTime 质保时间
        if (!ObjectUtil.isEmpty(paramVo.getCarQualityTime())) {
            boolQuery.filter(QueryBuilders.termQuery("carQualityTime", paramVo.getCarQualityTime()));
        }
        // carConfiguration 其他配置
        if (!StrUtil.isEmpty(paramVo.getCarConfiguration())) {
            boolQuery.must(QueryBuilders.matchQuery("carConfiguration.keyword", paramVo.getCarConfiguration()));
        }

        // carPrice 价格区间
        if (ObjectUtil.isNotEmpty(paramVo.getCarPriceMin()) || ObjectUtil.isNotEmpty(paramVo.getCarPriceMax())) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("carPrice");

            // Double carPriceMin = paramVo.getCarPriceMin();
            // Double carPriceMax = paramVo.getCarPriceMax();
            //
            // // 传进来的是null
            // if (ObjectUtil.isNull(carPriceMin)) carPriceMin = 0.0;
            //
            // // 传的价格最大是null
            // if (ObjectUtil.isNull(carPriceMax)) carPriceMax = 1000000.0;

            rangeQuery.gte(paramVo.getCarPriceMin());
            rangeQuery.lte(paramVo.getCarPriceMax());
            boolQuery.filter(rangeQuery);
        }


        // 把条件封装好了
        sourceBuilder.query(boolQuery);

        /**
         * 排序、分页、高亮
         */
        // 排序
        if (!StrUtil.isEmpty(paramVo.getSort())) {
            String sort = paramVo.getSort();
            String[] s = sort.split("_");

            SortOrder order = "asc".equalsIgnoreCase(s[1]) ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(s[0], order);
        }

        //总数。response.getHits().getTotalHits()

        // 分页   from = (pageNum - 1) * size;
        sourceBuilder.from((paramVo.getCurrPage() - 1) * paramVo.getPageSize());
        sourceBuilder.size(paramVo.getPageSize());

        // 高亮
        if (!StrUtil.isEmpty(paramVo.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            highlightBuilder.field("carTitle")
                    .preTags("<b style = \"color:red;\">")
                    .postTags("</b>");

            sourceBuilder.highlighter(highlightBuilder);
        }

        log.info("构建的DSL语句为：{}", sourceBuilder.toString());


        /**
         * 聚合分析
         */
        // 聚合
        // 1. 品牌聚合
        TermsAggregationBuilder carBrandAgg = AggregationBuilders.terms("car_brand_agg");
        carBrandAgg.field("carBrand.keyword").size(50);
        // 2. 品牌聚合的子聚合（也可以不放在子聚合里）
        // 品牌ID
        TermsAggregationBuilder carBrandIdAgg = AggregationBuilders.terms("car_brand_id").field("carId").size(1);
        carBrandAgg.subAggregation(carBrandIdAgg);

        // 汽车车系
        TermsAggregationBuilder carSeriesAgg = AggregationBuilders.terms("car_series").field("carSeries.keyword").size(50);

        // 使用年限
        TermsAggregationBuilder carUserTimeAgg = AggregationBuilders.terms("car_user_time").field("carUserTime").size(10);

        // 汽车类型 SUV 轿车
        TermsAggregationBuilder carTypeAgg = AggregationBuilders.terms("car_type").field("carType.keyword").size(10);

        // 过户次数
        TermsAggregationBuilder transferCountAgg = AggregationBuilders.terms("transfer_count").field("transferCount").size(10);
        // 公里数
        TermsAggregationBuilder carCarMileageAgg = AggregationBuilders.terms("car_car_mileage").field("carCarMileage").size(10);

        // 变数箱类型
        TermsAggregationBuilder carGearBoxAgg = AggregationBuilders.terms("car_gear_box").field("carGearBox.keyword").size(5);
        // 座位数
        TermsAggregationBuilder carSeatAgg = AggregationBuilders.terms("car_seat").field("carSeat").size(5);
        // 燃料类型
        TermsAggregationBuilder carFuelTypeAgg = AggregationBuilders.terms("car_fuel_type").field("carFuelType.keyword").size(5);
        // 价格
        TermsAggregationBuilder CarPriceAgg = AggregationBuilders.terms("car_price").field("carPrice").size(10);
        // 汽车颜色
        TermsAggregationBuilder carColorAgg = AggregationBuilders.terms("car_color").field("carColor.keyword").size(10);
        // carBrandAgg.subAggregation(carColorAgg);


        // 3. 属性聚合
        sourceBuilder.aggregation(carBrandAgg).aggregation(carSeriesAgg).aggregation(carUserTimeAgg)
                .aggregation(carTypeAgg).aggregation(transferCountAgg).aggregation(carCarMileageAgg)
                .aggregation(carGearBoxAgg).aggregation(carSeatAgg).aggregation(carFuelTypeAgg)
                .aggregation(CarPriceAgg).aggregation(carColorAgg);


        log.info("构建的聚合DSL语句为：{}", sourceBuilder.toString());

        // 检索结果。
        SearchRequest searchRequest = new SearchRequest(new String[]{ESConstant.ES_NAME}, sourceBuilder);

        // 返回检索结果
        return searchRequest;
    }

    /**
     * 3. 构建结果数据
     *
     * @param response
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse response, SearchParamVo searchParamVo) {

        SearchResult searchResult = new SearchResult();

        SearchHits hits = response.getHits();

        // ========以下聚合信息得到=========

        // 1. 返回查询到的所有车辆
        List<CarInfoModel> carInfoModels = new ArrayList<>();
        if (hits.getHits() != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                // 当前命中的记录
                CarInfoModel carInfoModel = JSONUtil.toBean(sourceAsString, CarInfoModel.class);
                // 判断高亮
                if (StrUtil.isNotEmpty(searchParamVo.getKeyword())) {

                    // System.out.println(hit.getHighlightFields().get("carTitle"));
                    HighlightField carTitle = hit.getHighlightFields().get("carTitle");// 高亮属性
                    if (carTitle == null) {
                        continue;
                    }
                    Text[] fragments = carTitle.getFragments();
                    String s = carTitle.getFragments()[0].string();// 高亮内容
                    carInfoModel.setCarTitle(s);// 设置高亮的标题，让页面解析
                }
                carInfoModels.add(carInfoModel);
            }
        }


        searchResult.setCarInfoModel(carInfoModels);

        // =========一：以下代码 聚合的第一种方式============

        // 2. 当前车辆的信息分类。用来做筛选的属性
        // 汽车的所有聚合信息
        CarAggVo carAggVo = new CarAggVo();

        // 2.1 拿到品牌聚合
        ParsedStringTerms carBrandAgg = response.getAggregations().get("car_brand_agg");

        List<CarBrandVo> carBrands = carBrandAgg.getBuckets().stream().map(bucket -> {
            // 品牌聚合
            String carBrand = bucket.getKeyAsString();
            // 品牌ID
            ParsedLongTerms carBrandIdAgg = bucket.getAggregations().get("car_brand_id");
            int carBrandId = carBrandIdAgg.getBuckets().get(0).getKeyAsNumber().intValue();

            CarBrandVo carBrandVo = new CarBrandVo();
            carBrandVo.setCarBrand(carBrand);
            carBrandVo.setCarId(carBrandId);

            return carBrandVo;
        }).collect(Collectors.toList());

        carAggVo.setCarBrandVoList(carBrands);
        // searchResult.setCarBrandVoList(carBrands);


        // 2.2 拿到汽车颜色聚合
        ParsedStringTerms carColorAgg = response.getAggregations().get("car_color");
        List<CarColorVo> carColors = carColorAgg.getBuckets().stream().map(bucket -> {
            // 拿到颜色
            String carColor = bucket.getKeyAsString();
            CarColorVo carColorVo = new CarColorVo();
            carColorVo.setCarColor(carColor);
            return carColorVo;
        }).collect(Collectors.toList());
        carAggVo.setCarColorVoList(carColors);


        // 2.3 变数箱类型聚合
        ParsedStringTerms carGearBoxAgg = response.getAggregations().get("car_gear_box");
        List<CarGearBoxVo> carGearBoxs = carGearBoxAgg.getBuckets().stream().map(bucket -> {
            // 拿到变数箱类型
            String carGearBox = bucket.getKeyAsString();
            CarGearBoxVo carGearBoxVo = new CarGearBoxVo();
            carGearBoxVo.setCarGearBox(carGearBox);
            return carGearBoxVo;
        }).collect(Collectors.toList());
        carAggVo.setCarGearBoxVoList(carGearBoxs);
        // searchResult.setCarGearBoxVoList(carGearBoxs);


        // 2.4 燃料类型聚合
        ParsedStringTerms carFuelTypeAgg = response.getAggregations().get("car_fuel_type");
        List<CarFuelTypeVo> carFuelTypeVos = carFuelTypeAgg.getBuckets().stream().map(bucket -> {
            // 拿到燃料类型
            String carFuelType = bucket.getKeyAsString();
            CarFuelTypeVo carFuelTypeVo = new CarFuelTypeVo();
            carFuelTypeVo.setCarFuelType(carFuelType);
            return carFuelTypeVo;
        }).collect(Collectors.toList());
        carAggVo.setCarFuelTypeVoList(carFuelTypeVos);
        // searchResult.setCarFuelTypeVoList(carFuelTypeVos);


        // 2.5 汽车车系聚合
        ParsedStringTerms carSeriesAgg = response.getAggregations().get("car_series");
        List<CarSeriesVo> carSeriesVos = carSeriesAgg.getBuckets().stream().map(bucket -> {
            // 拿到汽车车系
            String carSeries = bucket.getKeyAsString();
            CarSeriesVo carSeriesVo = new CarSeriesVo();
            carSeriesVo.setCarSeries(carSeries);
            return carSeriesVo;
        }).collect(Collectors.toList());
        carAggVo.setCarSeriesVoList(carSeriesVos);
        // searchResult.setCarSeriesVoList(carSeriesVos);


        // 2.6 汽车类型聚合
        ParsedStringTerms carTypeAgg = response.getAggregations().get("car_type");
        List<CarTypeVo> carTypeVos = carTypeAgg.getBuckets().stream().map(bucket -> {
            // 汽车类型
            String carType = bucket.getKeyAsString();
            CarTypeVo carTypeVo = new CarTypeVo();
            carTypeVo.setCarType(carType);
            return carTypeVo;
        }).collect(Collectors.toList());
        carAggVo.setCarTypeVoList(carTypeVos);
        // searchResult.setCarTypeVoList(carTypeVos);


        // 把聚合对应的标题也封装上
        List<String> title = new ArrayList<>();
        title.add("品牌");
        title.add("车系");
        title.add("类型");
        title.add("颜色");
        title.add("变数箱类型");
        title.add("燃料类型");
        searchResult.setArrTitleList(title);

        // 将汽车所有聚合信息封装到结果集中
        searchResult.setCarAggVoList(carAggVo);
        // =========一：以上代码 聚合的第一种方式============


//=================================================================================================


        // =========二：以下代码 聚合的第二种方式本次用以下这种============
        // 2. 封装方式二
        AggVo aggVo = new AggVo();
        List<AggVoList> aggVoListList = new LinkedList<>();
        // 2.1
        // 封装方式二
        AggVoList aggVoList = new AggVoList();
        ParsedStringTerms carBrandAgg2 = response.getAggregations().get("car_brand_agg");
        List<AggChildren> carBrandList = carBrandAgg2.getBuckets().stream().map(bucket -> {
            // 品牌聚合
            String carBrand = bucket.getKeyAsString();
            // 品牌ID
            ParsedLongTerms carBrandIdAgg = bucket.getAggregations().get("car_brand_id");
            int carBrandId = carBrandIdAgg.getBuckets().get(0).getKeyAsNumber().intValue();

            AggChildren aggChildren = new AggChildren();
            aggChildren.setValue(carBrand);
            return aggChildren;
        }).collect(Collectors.toList());

        // 聚合有结果才加入
        if (carBrandList.size() > 0) {
            aggVoList.setTitle("品牌");
            aggVoList.setAggChildrenList(carBrandList);
            aggVoListList.add(aggVoList);
        }


        // 2.2


        // 封装方式二
/*        AggVoList aggVoListSeries = new AggVoList();
        ParsedStringTerms carSeriesAgg2 = response.getAggregations().get("car_series");
        List<AggChildren> carSeriesList = carSeriesAgg2.getBuckets().stream().map(bucket -> {
            // 拿到汽车车系
            String carSeries = bucket.getKeyAsString();
            AggChildren aggChildren = new AggChildren();
            aggChildren.setValue(carSeries);
            return aggChildren;
        }).collect(Collectors.toList());
        aggVoListSeries.setTitle("车系");
        aggVoListSeries.setAggChildrenList(carSeriesList);
        aggVoListList.add(aggVoListSeries);*/


        aggVo.setAggVoListList(getAgg(response, "car_series", aggVoListList, "车系"));


        // 2.3
        // 封装方式二
/*
        AggVoList aggVoListType = new AggVoList();
        ParsedStringTerms carTypeAgg2 = response.getAggregations().get("car_type");
        List<AggChildren> carTypeList = carTypeAgg2.getBuckets().stream().map(bucket -> {
            // 汽车类型
            String carType = bucket.getKeyAsString();
            AggChildren aggChildren = new AggChildren();
            aggChildren.setValue(carType);
            return aggChildren;
        }).collect(Collectors.toList());
        aggVoListType.setTitle("类型");
        aggVoListType.setAggChildrenList(carTypeList);
        aggVoListList.add(aggVoListType);
        aggVo.setAggVoListList(aggVoListList);
*/


        aggVo.setAggVoListList(getAgg(response, "car_type", aggVoListList, "类型"));


        // 2.4
        // 封装方式二
        /*  AggVoList aggVoListFuelType = new AggVoList();
        ParsedStringTerms carFuelTypeAgg2 = response.getAggregations().get("car_fuel_type");
        List<AggChildren> carFuelTypeList = carFuelTypeAgg2.getBuckets().stream().map(bucket -> {
            // 拿到燃料类型
            String carFuelType = bucket.getKeyAsString();
            AggChildren aggChildren = new AggChildren();
            aggChildren.setValue(carFuelType);
            return aggChildren;
        }).collect(Collectors.toList());
        aggVoListFuelType.setTitle("燃料类型");
        aggVoListFuelType.setAggChildrenList(carFuelTypeList);
        aggVoListList.add(aggVoListFuelType);
        aggVo.setAggVoListList(aggVoListList);*/


        aggVo.setAggVoListList(getAgg(response, "car_fuel_type", aggVoListList, "燃料类型"));


        // 2.5
        // 封装方式二
        /* AggVoList aggVoListColor = new AggVoList();
        // //  拿到汽车颜色聚合
        ParsedStringTerms carColorAgg2 = response.getAggregations().get("car_color");
        List<AggChildren> carColorList = carColorAgg2.getBuckets().stream().map(bucket -> {
            // 拿到颜色
            String carColor = bucket.getKeyAsString();
            AggChildren aggChildren = new AggChildren();
            aggChildren.setValue(carColor);
            return aggChildren;
        }).collect(Collectors.toList());
        aggVoListColor.setTitle("颜色");
        aggVoListColor.setAggChildrenList(carColorList);
        aggVoListList.add(aggVoListColor);
        aggVo.setAggVoListList(aggVoListList);
*/
        aggVo.setAggVoListList(getAgg(response, "car_color", aggVoListList, "颜色"));


        // 2.6
        // 封装方式二
        /*    AggVoList aggVoListGearBox = new AggVoList();
        ParsedStringTerms carGearBoxAgg2 = response.getAggregations().get("car_gear_box");
        List<AggChildren> carGearBoxVoList = carGearBoxAgg2.getBuckets().stream().map(bucket -> {
            // 拿到变数箱类型
            String carGearBox = bucket.getKeyAsString();
            AggChildren aggChildren = new AggChildren();
            aggChildren.setValue(carGearBox);
            return aggChildren;
        }).collect(Collectors.toList());
        aggVoListGearBox.setTitle("变数箱类型");
        aggVoListGearBox.setAggChildrenList(carGearBoxVoList);
        aggVoListList.add(aggVoListGearBox);
        aggVo.setAggVoListList(aggVoListList);
*/

        aggVo.setAggVoListList(getAgg(response, "car_gear_box", aggVoListList, "变数箱类型"));


        // 2.7 将汽车所有聚合信息封装到结果集中
        searchResult.setAggVo(aggVo);
        // List<AggVo> aggVoList1 = new ArrayList<>();
        // aggVoList1.add(aggVo);
        // searchResult.setAggVo(aggVoList1);


        // =========二：以上代码是 聚合的第二种方式 ============


        // ========以上聚合信息得到=========


        // 3. 分页信息 - 页码
        searchResult.setCurrPage(searchParamVo.getCurrPage());
        // searchResult.set

        // 4. 分页信息 - 总记录数
        long totalCount = hits.getTotalHits().value;
        searchResult.setTotalCount(totalCount);


        // 5. 分页信息 - 总页码  11/2 = 5 ... 1
        long totalPages = (totalCount + searchParamVo.getPageSize() - 1) / searchParamVo.getPageSize();
        // int totalPages = (int)total % searchParamVo.getPageSize() == 0 ? (int)total / searchParamVo.getPageSize() : (int)total / searchParamVo.getPageSize() + 1;
        searchResult.setTotalPage(totalPages);

        // 6. 分页信息 - 每页显示几条
        searchResult.setPageSize(searchParamVo.getPageSize());


        return searchResult;
    }

    /*    // 封装方式二
        AggVoList aggVoListSeries = new AggVoList();
        ParsedStringTerms carSeriesAgg2 = response.getAggregations().get("car_series");
        List<AggChildren> carSeriesList = carSeriesAgg2.getBuckets().stream().map(bucket -> {
            // 拿到汽车车系
            String carSeries = bucket.getKeyAsString();
            AggChildren aggChildren = new AggChildren();
            aggChildren.setValue(carSeries);
            return aggChildren;
        }).collect(Collectors.toList());
            aggVoListSeries.setTitle("车系");
            aggVoListSeries.setAggChildrenList(carSeriesList);
            aggVoListList.add(aggVoListSeries);

            aggVo.setAggVoListList(aggVoListList);*/


    /**
     * 构建agg结果的辅助函数
     * TODO 虽然抽取方法了 但还是有问题：NULL的没有排除。不过一般不会有NULL值。还有没有属性值的应该不选择属性
     *
     * @param response SearchResponse
     * @param title    车系。车型。等
     * @return
     */
    private List<AggVoList> getAgg(SearchResponse response, String type, List<AggVoList> aggVoListList, String title) {
        AggVoList aggVoListSeries = new AggVoList();
        ParsedStringTerms agg = response.getAggregations().get(type);

        // filter(Objects::nonNull)为过滤null
        List<AggChildren> list = agg.getBuckets().stream().map(bucket -> {
            // 拿到title的类型
            String str = bucket.getKeyAsString();

            AggChildren aggChildren = new AggChildren();
            aggChildren.setValue(str);
            return aggChildren;
        }).collect(Collectors.toList());

        if (list.size() > 0) {
            aggVoListSeries.setTitle(title);
            aggVoListSeries.setAggChildrenList(list);
            aggVoListList.add(aggVoListSeries);
        }
        return aggVoListList;
    }


    /**
     * ES：关键字搜索
     *
     * @param page
     * @param keyword
     * @return
     */
    @Override
    public IPage<CarInfoModel> search(Page page, String keyword) {
        // 分页信息 mybatis plus的page 转成 jpa的page
        Long current = page.getCurrent() - 1;
        Long size = page.getSize();
        Pageable pageable = PageRequest.of(current.intValue(), size.intValue());

        // 搜索es得到pageData
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword,
                "carBrand", "carSeries", "carType", "carGearBox");

        org.springframework.data.domain.Page<CarInfoModel> docments = carInfoRepository.search(multiMatchQueryBuilder, pageable);

        // 结果信息 jpa的pageData转成mybatis plus的pageData
        IPage<CarInfoModel> pageData = new Page(page.getCurrent(), page.getSize(), docments.getTotalElements());
        pageData.setRecords(docments.getContent());

        return pageData;
    }

    /**
     * ES：初始化es 初始化汽车信息
     *
     * @param records 记录数
     * @return
     */
    @Override
    public int initEsData(List<CarInfo> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }

        List<CarInfoModel> documents = new ArrayList<>();
        // List<CarPicture> carPictures = new ArrayList<>();
        for (CarInfo carInfo : records) {
            CarInfoModel carInfoModel = BeanUtil.copyProperties(carInfo, CarInfoModel.class);
            // 2. 查询汽车图片信息
            // carPictureService.listByIds(carPictures);

            List<CarPicture> carPictureList = carPictureService.list(new QueryWrapper<CarPicture>().eq("car_id", carInfo.getCarId()));
            // CarPicture picture = carPictureService.getOne(new QueryWrapper<CarPicture>().eq("car_id", carInfo.getCarId()));

            // carPictures.add(picture);
            // carInfoModel.setCarPictures(carPictures);
            carInfoModel.setCarPictures(carPictureList);

            // 映射转换 ,添加到文档
            documents.add(carInfoModel);
        }

        carInfoRepository.saveAll(documents);

        return documents.size();
    }

    /**
     * es 初始化地址信息
     *
     * @param records
     * @return
     */
    @Override
    public int initRegionEsData(List<Region> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }

        List<RegionModel> documents = new ArrayList<>();
        // List<CarPicture> carPictures = new ArrayList<>();
        for (Region record : records) {
            RegionModel regionModel = BeanUtil.copyProperties(record, RegionModel.class);
            documents.add(regionModel);
        }

        regionRepository.saveAll(documents);

        return documents.size();
    }

    /**
     * ES删全部文档
     *
     * @return
     */
    @Override
    public boolean deleteAllESCarInfoIndex() {

        try {
            carInfoRepository.deleteAll();
            log.info("ES 全部删除了！！！！");

        } catch (Exception e) {
            log.error("ES 全部删除过程中出现异常");
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * 更新添加es、通知MQ
     *
     * @param message
     */
    @Override
    public void createOrUpdateCarInfoIndex(MQIndexMessage message) {

        Integer carId = message.getCarId();
        CarInfo carInfo = carInfoService.selectOneCarInfo(new QueryWrapper<CarInfo>().eq("car_id", carId));

        CarInfoModel carInfoModel = new CarInfoModel();
        BeanUtil.copyProperties(carInfo, carInfoModel);
        carInfoRepository.save(carInfoModel);

        log.info("es 索引更新成功！ ---> {}", carInfoModel.toString());

    }


    /**
     * 删除、通知MQ
     *
     * @param message
     */
    @Override
    public void removeCarInfoIndex(MQIndexMessage message) {
        Integer carId = message.getCarId();

        // es删除索引
        carInfoRepository.deleteById(carId.longValue());
        log.info("es 索引删除成功！ ---> {}", message.toString());
    }

}
