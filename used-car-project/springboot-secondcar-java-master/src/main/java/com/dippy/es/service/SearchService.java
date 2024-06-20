package com.dippy.es.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dippy.entity.CarInfo;
import com.dippy.entity.Region;
import com.dippy.es.model.CarInfoModel;
import com.dippy.es.vo.SearchParamVo;
import com.dippy.es.vo.SearchResult;
import com.dippy.mq.MQIndexMessage;

import java.util.List;

public interface SearchService {

    SearchResult searchListPage(SearchParamVo searchParamVo);

    /**
     * 搜索
     * @param page
     * @param keyword
     * @return
     */
    IPage<CarInfoModel> search(Page page, String keyword);

    /**
     * 初始化es-汽车信息
     *
     * @param records
     * @return
     */
    int initEsData(List<CarInfo> records);

    /**
     * 初始化es - 地址信息
     *
     * @param records
     * @return
     */
    int initRegionEsData(List<Region> records);

    // /**
    //  * 初始化所有car信息
    //  * @return 返回初始化条数
    //  */
    // int initEsData();

    boolean deleteAllESCarInfoIndex();

    void createOrUpdateCarInfoIndex(MQIndexMessage message);

    void removeCarInfoIndex(MQIndexMessage message);

}
