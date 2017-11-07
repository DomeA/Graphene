package com.domeastudio.mappingo.servers.microservice.h2.service;

import com.domeastudio.mappingo.servers.microservice.h2.pojo.SystemMetaEntity;

import java.util.List;

public interface SystemMetaService {
    List<SystemMetaEntity> findAll();

    SystemMetaEntity findOne(String id);

    Boolean addSystemMetaEntity(SystemMetaEntity systemMetaEntity);

    void deleteSysetemMetaEntity(String id) throws Exception;

    void deleteSystemMetaEntities() throws Exception;
}
