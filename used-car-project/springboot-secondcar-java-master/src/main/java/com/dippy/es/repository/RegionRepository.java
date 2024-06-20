package com.dippy.es.repository;

import com.dippy.es.model.RegionModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegionRepository extends ElasticsearchRepository<RegionModel, Integer> {

}
