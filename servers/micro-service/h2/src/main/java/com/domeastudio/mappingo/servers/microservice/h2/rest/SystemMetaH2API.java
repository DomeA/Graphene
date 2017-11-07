package com.domeastudio.mappingo.servers.microservice.h2.rest;

import com.domeastudio.mappingo.servers.microservice.h2.dto.Message2Client;
import com.domeastudio.mappingo.servers.microservice.h2.pojo.FieldTypeEnum;
import com.domeastudio.mappingo.servers.microservice.h2.pojo.SystemMetaEntity;
import com.domeastudio.mappingo.servers.microservice.h2.service.SystemMetaService;
import com.domeastudio.mappingo.servers.microservice.h2.utils.ErrorCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(value = "通过H2数据库，构建整个数据系统的数据结构描述", protocols = "http")
@RestController
@RequestMapping(value = "/system")
public class SystemMetaH2API {
    @Autowired
    private SystemMetaService systemMetaService;
    @Autowired
    private Message2Client message2Client;

    @ApiOperation(value = "获取系统元数据列表", notes = "",
            httpMethod = "GET", code = 200, protocols = "http")
    @ApiResponse(code = 200, message = "系统元数据JSONObject列表", response = Message2Client.class)
    @RequestMapping(value = "/meta/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client getSystemMetaEntities() {
        if (systemMetaService.findAll().size() > 0) {
            message2Client.setCode("200");
            message2Client.setMessage(systemMetaService.findAll());
        } else {
            message2Client.setCode(ErrorCode.dataBaseSearchFaile.getKey());
            message2Client.setMessage(ErrorCode.dataBaseSearchFaile.getValue());
        }
        return message2Client;
    }

    @ApiOperation(value = "获取一个系统元数据记录", notes = "根据id获取元数据记录", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "元数据的UUID", required = true, dataType = "String")
    @ApiResponse(code = 200, message = "系统元数据JSONObject", response = Message2Client.class)
    @RequestMapping(value = "/meta/get/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client getSystemMetaEntity(@PathVariable("id") String id) {
        if (systemMetaService.findOne(id) != null) {
            message2Client.setCode("200");
            message2Client.setMessage(systemMetaService.findOne(id));
        } else {
            message2Client.setCode(ErrorCode.dataBaseSearchFaile.getKey());
            message2Client.setMessage(ErrorCode.dataBaseSearchFaile.getValue());
        }
        return message2Client;
    }

    @RequestMapping(value = "/meta/fieldType/get/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client getFieldTypeEnum() {
        message2Client.setCode("200");
        message2Client.setMessage(FieldTypeEnum.values());
        return message2Client;
    }

    @RequestMapping(value = "/meta/add/{tableName}/{fieldName}/{fieldType}/{referenceTable}/{foreignKey}/{foreignKeyType}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client addSystemMetaEntity(@PathVariable("tableName") String tableName,
                                              @PathVariable("fieldName") String fieldName,
                                              @PathVariable("fieldType") FieldTypeEnum fieldType,
                                              @PathVariable("referenceTable") String referenceTable,
                                              @PathVariable("foreignKey") String foreignKey,
                                              @PathVariable("foreignKeyType") FieldTypeEnum foreignKeyType) {
        if (!foreignKeyType.equals(FieldTypeEnum.NULL) && !foreignKeyType.equals(fieldType)) {
            message2Client.setCode(ErrorCode.dataTypeInequality.getKey());
            message2Client.setMessage(ErrorCode.dataTypeInequality.getValue());
            return message2Client;
        }
        SystemMetaEntity systemMetaEntity = new SystemMetaEntity();
        systemMetaEntity.setFieldName(fieldName);
        systemMetaEntity.setFieldType(fieldType);
        systemMetaEntity.setTableName(tableName);
        systemMetaEntity.setForeignKey(foreignKey);
        systemMetaEntity.setForeignKeyType(foreignKeyType);
        systemMetaEntity.setReferenceTable(referenceTable);
        systemMetaEntity.setAlias(null);
        if (systemMetaService.addSystemMetaEntity(systemMetaEntity)) {
            message2Client.setCode("200");
            message2Client.setMessage("添加成功");
        } else {
            message2Client.setCode(ErrorCode.dataBaseAddFaile.getKey());
            message2Client.setMessage(ErrorCode.dataBaseAddFaile.getValue());
        }
        return message2Client;
    }

    @RequestMapping(value = "/meta/delete/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client deletSystemMetaEntity(@PathVariable("id") String id) {
        try {
            systemMetaService.deleteSysetemMetaEntity(id);
            message2Client.setCode("200");
            message2Client.setMessage("id=" + id + "的记录删除成功");
        } catch (Exception e) {
            message2Client.setCode(ErrorCode.dataBaseDeleteFaile.getKey());
            message2Client.setMessage(ErrorCode.dataBaseDeleteFaile.getValue());
            e.printStackTrace();
        }
        return message2Client;
    }

    @RequestMapping(value = "/meta/delete",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client deletSystemMetaEntities() {
        try {
            systemMetaService.deleteSystemMetaEntities();
            message2Client.setCode("200");
            message2Client.setMessage("批量删除成功");
        } catch (Exception e) {
            message2Client.setCode(ErrorCode.dataBaseDeleteFaile.getKey());
            message2Client.setMessage(ErrorCode.dataBaseDeleteFaile.getValue());
            e.printStackTrace();
        }
        return message2Client;
    }
}
