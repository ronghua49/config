package com.shuyue.inventory_server.controller.printPDF;

import com.alibaba.fastjson.JSON;
import com.shuyue.inventory_server.controller.BaseController;
import com.shuyue.inventory_server.entity.base.TemplatePrint;
import com.shuyue.inventory_server.repository.TemplatePrintRepository;
import com.shuyue.inventory_server.service.printPDF.PrintPDFService;
import com.shuyue.inventory_server.util.Constants;
import com.shuyue.inventory_server.util.HtmlGenerator;
import com.shuyue.inventory_server.util.PrintPDFContants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jieh
 * @version 1.0
 * @date 2020/2/3
 */
@RestController
@Api(
    value = "PDF打印功能",
    tags = {"PDF打印功能"})
@RequestMapping("/inventory-server/printPDF")
@Slf4j
public class PrintPDFController extends BaseController {
  @Autowired private TemplatePrintRepository templatePrintRepository;
  @Autowired private PrintPDFService printPDFService;

  @GetMapping(value = "/printPDF")
  public String printPDFOptional(HttpServletRequest request, HttpServletResponse response,@Param("templeafKey") String templeafKey) {
    // 返回对象初始化
    Map<String, Object> resultMap = null;
    TemplatePrint templatePrint = templatePrintRepository.getByTemplateKeyAndDelFlgAndStoreId(templeafKey, Constants.DEL_FLG, BaseController.getUserInfo().getStoreId());
    if (templatePrint == null) {
      // storeId为0的模板为通用模板
      templatePrint = templatePrintRepository.getByTemplateKeyAndDelFlgAndStoreId(templeafKey, Constants.DEL_FLG, "0");
    }
    if(templatePrint == null || StringUtils.isEmpty(templatePrint.getTemplateContent())) {
      log.error("模板信息为空");
      return null;
    }
    String templateContent = templatePrint.getTemplateContent();
    OutputStream os = null;
    String htmlStr = "打印失败";
    try {
      Map<String,Object> variables = new HashMap<String,Object>();
      /** 处理variables，获取需要填到模板的数据 **/
      variablesHandle(templeafKey,variables,request);
      //通过freemaker模板生成html
//            ITextRenderer renderer = new ITextRenderer();
      htmlStr = HtmlGenerator.generate(templateContent, variables,request);
//            renderer.setDocumentFromString(htmlStr);
//            ITextFontResolver fontResolver = renderer.getFontResolver();
//            fontResolver.addFont("/font/simsun.ttc",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//            response.setContentType("application/pdf");
//            os = response.getOutputStream();
//            renderer.layout();
//            renderer.createPDF(os, true);
//            os.flush();
    }catch (Exception e){
      log.error("printPDFOptional Exception",e);
    }
    // 返回状态和提示信息设置
    resultMap = getMapSuccess();
    // 返回数据设置
    resultMap.put("data", htmlStr);
    // 返回信息转换成Json格式
    return JSON.toJSONString(resultMap);
  }

  /**
   * 处理variables,获取打印模板中需要填入的信息
   * @param templeafKey
   * @param variables
   * @throws Exception
   */
  private void variablesHandle(String templeafKey,Map<String,Object> variables,HttpServletRequest request) throws Exception{
    log.info("variablesHandle start..." + "templeafKey is ===>" + templeafKey);
    //整车采购
    if(PrintPDFContants.VEHICLEPROCUREMENT.equals(templeafKey)) {
      // 采购单号
      String orderNo=request.getParameter("orderNo");
      variables.putAll(printPDFService.getVehiclePropurement(orderNo));
    }
    // 商品采购
    if(PrintPDFContants.PRODUCTPROCUREMENT.equals(templeafKey)) {
      // 采购单号
      String orderNo=request.getParameter("orderNo");
      variables.putAll(printPDFService.getProductPropurement(orderNo));
    }
    // 商品采购对账
    if(PrintPDFContants.PURCHASESTATEMENT.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String orderNo=request.getParameter("orderNo");
      variables.putAll(printPDFService.getByOrderNo(orderNo));
    }
    // 精品销售
    if(PrintPDFContants.GOODSSALES.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String id=request.getParameter("orderNo");
      variables.putAll(printPDFService.getBoutiqueSalesById(id));
    }
    // 配件销售
    if(PrintPDFContants.GOODSSALE.equals(templeafKey)) {
      String id=request.getParameter("orderNo");
      variables.putAll(printPDFService.getPartSalesById(id));
    }
    // 商品寄存
    if(PrintPDFContants.MERCHANDISEDEPOSIT.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String orderNo=request.getParameter("orderNo");
      variables.putAll(printPDFService.getMerchandiseDepositByOrderNo(orderNo));
    }
    //  商品销售对账
    if(PrintPDFContants.SALESRECONCILIATION.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String orderNo=request.getParameter("orderNo");
      variables.putAll(printPDFService.getSalesByOrderNo(orderNo));
    }
    // 商品收货
    if (PrintPDFContants.RECEIPTOFMERCHANDISE.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String id = request.getParameter("orderNo");
      variables.putAll(printPDFService.getProductReceivingById(id));
    }
    // 盘点报溢
    if (PrintPDFContants.INVENTORYOVERFLOW.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String orderNo = request.getParameter("orderNo");
      variables.putAll(printPDFService.getOverFlowByOrderNo(orderNo));
    }
    // 盘点报损
    if (PrintPDFContants.INVENTORYOVERLOSS.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String orderNo = request.getParameter("orderNo");
      variables.putAll(printPDFService.getOverLossByOrderNo(orderNo));
    }
    // 车辆入库
    if (PrintPDFContants.INVEHICLE.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String receiptNo = request.getParameter("receiptNo");
      variables.putAll(printPDFService.getInVehicleByOrderNo(receiptNo));
    }
    // 车辆出库
    if (PrintPDFContants.OUTVEHICLE.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String owrReceiptNo = request.getParameter("owrReceiptNo");
      variables.putAll(printPDFService.getOutVehicleByOrderNo(owrReceiptNo));
    }
//  商品退货
    if(PrintPDFContants.MEREDCHANDISERETURN.equals(templeafKey)) {
      //获取请求参数并转换
      Enumeration<String> enu = request.getParameterNames();
      String orderNo=request.getParameter("orderNo");
      variables.putAll(printPDFService.getMerReturnByOrderNo(orderNo));
    }
    //  盘点清单管理
    if (PrintPDFContants.CHECKINVENTORY.equals(templeafKey)) {
      String orderNo = request.getParameter("orderNo");
      variables.putAll(printPDFService.getCheckInventoryByOrderNo(orderNo));
    }
    //  精品发料
    if (PrintPDFContants.DELIVERY.equals(templeafKey)) {
      String orderNo = request.getParameter("orderNo");
      variables.putAll(printPDFService.getDeliveryByOrderNo(orderNo));
    }
    //  维修发料
    if (PrintPDFContants.MAINTENANCEISSUE.equals(templeafKey)) {
      String orderNo = request.getParameter("orderNo");
      variables.putAll(printPDFService.getMaintenanceByOrderNo(orderNo));
    }

    //  商品销售退回
    if (PrintPDFContants.MERCHANDISERETURN.equals(templeafKey)) {
      String orderNo = request.getParameter("orderNo");
      variables.putAll(printPDFService.getProductSalesReturnedPDF(orderNo));
    }
    if(PrintPDFContants.TEST.equals(templeafKey)) {
      variables.put("test","中文支持");
    }
    log.info("variablesHandle end..." + "variables is ===>" + JSON.toJSONString(variables));
  }
}
