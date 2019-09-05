package com.winway.scm.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotent.base.controller.BaseController;
import com.hotent.base.util.Base64;
import com.hotent.base.util.JsonUtil;
import com.winway.scm.model.ScmXsBigContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Api(tags = "Test 测试")
public class Test extends BaseController {


    @GetMapping("/test2")
    @ApiOperation(value = "集中发货商品表数据列表", httpMethod = "GET", notes = "获取集中发货商品表列表")
    public ScmXsBigContract test2(@RequestParam String name) {
        ScmXsBigContract contract = new ScmXsBigContract();
        contract.setInvalid("1");
        contract.setEntryPeople(name);
        return contract;
    }

    @PostMapping("/test3")
    @ApiOperation(value = "集中发货商品表数据列表", httpMethod = "POST", notes = "获取集中发货商品表列表")
    public ScmXsBigContract test3(@RequestBody ScmXsBigContract scmXsBigContract) {
        scmXsBigContract.setEntryPeople(scmXsBigContract.getEntryPeople()+"test3-->");
        return scmXsBigContract;
    }


    @PostMapping("/test4")
    @ApiOperation(value = "集中发货商品表数据列表", httpMethod = "POST", notes = "获取集中发货商品表列表")
    public ScmXsBigContract test4(HttpServletRequest request) {
        //获取审批流中的 该对象id 和审批id
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String token = request.getHeader("Authorization");
        headers.add("Authorization", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String url = "http://localhost:8095/test2";
        ResponseEntity<ScmXsBigContract> entity = restTemplate.exchange(
                url+"?name="+"53y35",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                ScmXsBigContract.class
                );
        ScmXsBigContract body = entity.getBody();
        return body;
    }

    @PostMapping("/test5")
    @ApiOperation(value = "集中发货商品表数据列表", httpMethod = "POST", notes = "获取集中发货商品表列表")
    public ScmXsBigContract test5(HttpServletRequest request) {
        //获取审批流中的 该对象id 和审批id
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String token = request.getHeader("Authorization");
        headers.add("Authorization", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String url = "http://localhost:8095/test3";
        ScmXsBigContract contract = new ScmXsBigContract();
        contract.setInvalid("2");
        contract.setEntryPeople("test5");
        String s = restTemplate.postForObject(url, new HttpEntity<String>(JSON.toJSONString(contract), headers), String.class);
        ScmXsBigContract bigContract = JSON.parseObject(s, ScmXsBigContract.class);
        return bigContract;
    }


}
