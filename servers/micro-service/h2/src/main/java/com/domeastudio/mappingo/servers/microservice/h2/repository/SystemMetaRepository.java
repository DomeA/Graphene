package com.domeastudio.mappingo.servers.microservice.h2.repository;

import com.domeastudio.mappingo.servers.microservice.h2.pojo.SystemMetaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemMetaRepository extends CrudRepository<SystemMetaEntity, String> {
}
