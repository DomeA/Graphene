package com.domeastudio.mappingo.servers.microservice.h2.rest;

import com.domeastudio.mappingo.servers.microservice.h2.dto.Message2Client;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/manager")
public class DataBaseManagerAPI {

    @RequestMapping(value = "/upload/files/{folderName}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client scanFolder2UploadFiles(@PathVariable("folderName") String folderName) {
        return null;
    }

    @RequestMapping(value = "/upload/file/{fileName}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client uploadFile(@PathVariable("fileName") String fileName) {
        return null;
    }

    @RequestMapping(value = "/upload/string/{sql}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client uploadString(@PathVariable("sql") String sql) {
        return null;
    }

    @RequestMapping(value = "/select/{table}/{fields}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client selectDataBySql(@PathVariable("table") String table,
                                          @PathVariable("fields") String fields) {
        return null;
    }

    @RequestMapping(value = "/select/nosql/{table}/{fields}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message2Client selectDataByNoSql(@PathVariable("table") String table,
                                            @PathVariable("fields") String fields) {
        return null;
    }
}
