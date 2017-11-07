package com.domeastudio.mappingo.servers.microservice.h2.service.impl;

import com.domeastudio.mappingo.servers.microservice.h2.pojo.SystemMetaEntity;
import com.domeastudio.mappingo.servers.microservice.h2.repository.SystemMetaRepository;
import com.domeastudio.mappingo.servers.microservice.h2.service.SystemMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemMetaServiceImpl implements SystemMetaService {

    @Autowired
    SystemMetaRepository systemMetaRepository;

    @Override
    public List<SystemMetaEntity> findAll() {
        return (List<SystemMetaEntity>) systemMetaRepository.findAll();
    }

    @Override
    public SystemMetaEntity findOne(String id) {
        return systemMetaRepository.findOne(id);
    }

    @Override
    public Boolean addSystemMetaEntity(SystemMetaEntity systemMetaEntity) {
        SystemMetaEntity entity = systemMetaRepository.save(systemMetaEntity);
        if (entity != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteSysetemMetaEntity(String id) {
        systemMetaRepository.delete(id);
    }

    @Override
    public void deleteSystemMetaEntities() {
        systemMetaRepository.deleteAll();
    }
}
