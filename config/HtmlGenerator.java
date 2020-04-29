package com.shuyue.inventory_server.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
public class HtmlGenerator {
  public static String generate(
      String templateContent, Map<String, Object> variables, HttpServletRequest request)
      throws Exception {
    log.info("HtmlGenerator generate start");
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
    cfg.setDefaultEncoding("UTF-8");
    StringTemplateLoader templateLoader = new StringTemplateLoader();
    templateLoader.putTemplate("templateName", templateContent);
    cfg.setTemplateLoader(templateLoader);
    try {
      Template template = cfg.getTemplate("templateName");
      StringWriter stringWriter = new StringWriter();
      BufferedWriter writer = new BufferedWriter(stringWriter);
      template.setOutputEncoding("UTF-8");
      template.process(variables, writer);
      String htmlStr = stringWriter.toString();
      writer.flush();
      writer.close();
      log.info("HtmlGenerator generate end");
      return htmlStr;
    } catch (Exception e) {
      log.error("HtmlGenerator generate error", e);
      return null;
    }
  }
}
