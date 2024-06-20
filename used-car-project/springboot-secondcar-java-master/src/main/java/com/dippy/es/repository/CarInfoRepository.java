package com.dippy.es.repository;

import com.dippy.es.model.CarInfoModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

// 符合JPa命名规范的接口
@Repository
public interface CarInfoRepository extends ElasticsearchRepository<CarInfoModel, Long> {


}
