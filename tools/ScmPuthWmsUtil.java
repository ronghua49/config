package com.winway.purchase.util;

import com.alibaba.fastjson.JSON;
import com.hotent.base.util.UniqueIdUtil;
import com.winway.purchase.model.ScmSysLog;
import com.winway.purchase.persistence.dao.ScmSysLogDao;
import com.winway.purchase.tool.ScmWmsConfiguration;
import com.winway.purchase.vo.ScmWmsReturnData;
import com.winway.purchase.vo.ScmWmsReturnDataNoList;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ScmPuthWmsUtil {

    @Resource
    ScmSysLogDao scmSysLogDao;

    private static ScmSysLogDao scmSysLogDaoStatic;

    @PostConstruct
    public void init() {
        scmSysLogDaoStatic = this.scmSysLogDao;
    }

    public static boolean puth(String json, String url, String operation) {
        ScmSysLog scmSysLog = new ScmSysLog();
        try {
            scmSysLog.setCreateDate(new Date());
            scmSysLog.setId(UniqueIdUtil.getSuid());
            scmSysLog.setOperation(operation);
            scmSysLog.setMethod("endApply");
            scmSysLog.setParams(json);
            Map<String, String> header = new HashMap<String, String>();
            header.put("accept", "application/json;charset=UTF-8");
            header.put("Authorization", ScmWmsConfiguration.token);
            header.put("Content-Type", "application/json");
            long l = System.currentTimeMillis();
            String doPostJson = HttpClientUtil.doPostJson(ScmWmsConfiguration.url + url, json, header);
            long l1 = System.currentTimeMillis();
            scmSysLog.setTotalTime(l1 - l);
            ScmWmsReturnData parseObject = JSON.parseObject(doPostJson, ScmWmsReturnData.class);
            System.out.println("wms-----------------------------------------");
            System.out.println("推送json*********************:");
            System.out.println(json);
            System.out.println("接受json*********************:");
            System.out.println(doPostJson);
            System.out.println("wms-----------------------------------------");
            if ("200".equals(parseObject.getStute())) {
                scmSysLog.setResponseState("请求成功");
                scmSysLog.setResult(doPostJson);
                return true;
            } else {
                scmSysLog.setResponseState("请求失败");
                return false;
            }
        } catch (Exception e) {
            scmSysLog.setExceptionMes(e.getMessage());
            return false;
        } finally {
            try {
                scmSysLogDaoStatic.create(scmSysLog);
            } catch (Exception e) {
                System.out.println("wms 日志保存失败");
            }
        }
    }

    public static ScmWmsReturnData getDatPputh(String json, String url) {
        ScmSysLog scmSysLog = new ScmSysLog();
        try {
            scmSysLog.setCreateDate(new Date());
            scmSysLog.setId(UniqueIdUtil.getSuid());
            scmSysLog.setOperation("wms查询：" + url);
            scmSysLog.setParams(json);
            Map<String, String> header = new HashMap<String, String>();
            header.put("accept", "application/json;charset=UTF-8");
            header.put("Authorization", ScmWmsConfiguration.token);
            header.put("Content-Type", "application/json");
            long l = System.currentTimeMillis();
            String doPostJson = HttpClientUtil.doPostJson(ScmWmsConfiguration.url + url, json, header);
            long l1 = System.currentTimeMillis();
            scmSysLog.setTotalTime(l1 - l);
            ScmWmsReturnData parseObject = JSON.parseObject(doPostJson, ScmWmsReturnData.class);
            System.out.println("wms-----------------------------------------");
            System.out.println("推送json*********************:");
            System.out.println(json);
            System.out.println("接受json*********************:");
            System.out.println(doPostJson);
            System.out.println("wms-----------------------------------------");
            if ("200".equals(parseObject.getStute())) {
                scmSysLog.setResponseState("请求成功");
                scmSysLog.setResult(doPostJson);
                return parseObject;
            } else {
                return null;
            }
        } catch (Exception e) {
            scmSysLog.setResponseState("请求失败");
            scmSysLog.setExceptionMes(e.getMessage());
            return null;
        } finally {
            try {
                scmSysLogDaoStatic.create(scmSysLog);
            } catch (Exception e) {
                System.out.println("wms 日志保存失败");
            }
        }
    }

    public static ScmWmsReturnDataNoList getDatPputhNoList(String json, String url) {
        try {
            Map<String, String> header = new HashMap<String, String>();
            header.put("accept", "application/json;charset=UTF-8");
            header.put("Authorization", ScmWmsConfiguration.token);
            header.put("Content-Type", "application/json");
            String doPostJson = HttpClientUtil.doPostJson(ScmWmsConfiguration.url + url, json, header);
            ScmWmsReturnDataNoList parseObject = JSON.parseObject(doPostJson, ScmWmsReturnDataNoList.class);
            System.out.println("wms-----------------------------------------");
            System.out.println("推送json*********************:");
            System.out.println(json);
            System.out.println("接受json*********************:");
            System.out.println(doPostJson);
            System.out.println("wms-----------------------------------------");
            if ("200".equals(parseObject.getStute())) {
                return parseObject;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
