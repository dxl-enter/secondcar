package com.dippy.controller.es;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dippy.common.result.Result;
import com.dippy.common.result.ResultCode;
import com.dippy.controller.BaseController;
import com.dippy.es.model.CarInfoModel;
import com.dippy.es.vo.SearchParamVo;
import com.dippy.es.vo.SearchResult;
import com.dippy.util.URLUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/carInfo")
@Api(value = "Elasticsearch模块", tags = "Elasticsearch")
public class SearchController extends BaseController {

    @GetMapping("/search")
    public Result search(String keyword) {
        IPage<CarInfoModel> searchPageData = searchService.search(getPage(), keyword);
        return Result.succ(searchPageData);
    }


    /**
     * 页面提交的参数封装成searchParamVo查询
     *
     * @param searchParamVo
     * @return
     */
    @GetMapping("/searchListPage")
    public Result searchListPage(SearchParamVo searchParamVo, HttpServletRequest request) {

        // 获取请求参数

        // Map<String, String[]> parameterMap = request.getParameterMap();

        String queryParameter = request.getQueryString();
        if (!StrUtil.isEmpty(queryParameter)) {
            try {
                // url编码转成UTF8
                // carBrand=大众&carSeries=5系&carType=轿车&carFuelType=机油&carColor=灰色
                queryParameter = URLDecoder.decode(queryParameter, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            log.info("筛选的属性：值 为 {}", queryParameter);
        }

        // carBrand=大众&carSeries=5系&carType=轿车&carFuelType=机油&carColor=灰色
        // 解析出查询参数
        Map<String, String> stringStringMap = URLUtils.urlSplit(queryParameter);
        // 封装成searchParamVo
        BeanUtil.copyProperties(searchParamVo, stringStringMap);


        // 页面传来的参数数据，去es中检索
        SearchResult searchResult = searchService.searchListPage(searchParamVo);

        if (searchResult == null) {
            return Result.succ(ResultCode.YES_CAR_OF_PERSONAL.getCode(), ResultCode.YES_CAR_OF_PERSONAL.getMessage(), null);
        }

        // 没有查到符合的车
        if (searchResult.getCarInfoModel().size() == 0) {
            return Result.fail(ResultCode.No_SEARCH_CAR_INFO.getCode(), ResultCode.No_SEARCH_CAR_INFO.getMessage(), searchResult);
        }

        // 查询成功有自定义信息提示
        return Result.succ(ResultCode.YES_SEARCH_CAR_INFO.getCode(), ResultCode.YES_SEARCH_CAR_INFO.getMessage(), searchResult);
        // 默认信息提示
        // return Result.succ(searchResult);
    }


    /*

    @GetMapping("/carInfoIndex")
    public Result carInfoIndexSearchListPage(SearchParamVo searchParamVo, HttpServletRequest request) {

        // 获取请求参数

        // Map<String, String[]> parameterMap = request.getParameterMap();

        // String queryParameter = request.getQueryString();
        // if (!StrUtil.isEmpty(queryParameter)) {
        //     try {
        //         // url编码转成UTF8
        //         // carBrand=大众&carSeries=5系&carType=轿车&carFuelType=机油&carColor=灰色
        //         queryParameter = URLDecoder.decode(queryParameter, "UTF-8");
        //     } catch (UnsupportedEncodingException e) {
        //         e.printStackTrace();
        //     }
        //     log.info("筛选的属性：值 为 {}",queryParameter);
        // }
        //
        // // carBrand=大众&carSeries=5系&carType=轿车&carFuelType=机油&carColor=灰色
        // // 解析出查询参数
        // Map<String, String> stringStringMap = URLUtils.urlSplit(queryParameter);
        // // 封装成searchParamVo
        // BeanUtil.copyProperties(searchParamVo, stringStringMap);


        // 页面传来的参数数据，去es中检索
        SearchResult searchResult = searchService.searchListPage(searchParamVo);

        // 没有查到符合的车
        if (searchResult.getCarInfoModel().size() == 0)
            return Result.fail(ResultCode.No_SEARCH_CAR_INFO.getCode(),ResultCode.No_SEARCH_CAR_INFO.getMessage(),searchResult);
        // 查询成功有自定义信息提示
        return Result.succ(ResultCode.YES_SEARCH_CAR_INFO.getCode(),ResultCode.YES_SEARCH_CAR_INFO.getMessage(),searchResult);
        // 默认信息提示
        // return Result.succ(searchResult);
    }
*/


}
