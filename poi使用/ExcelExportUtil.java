package com.bosideng.bsdyun.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ExcelExportUtil
 * @Description: TODO excel 导出工具类
 * @author Chen Xinjie chenxinjie@bosideng.com
 * @date 2016年3月23日 上午10:05:27
 * 
 */
@Slf4j
public class ExcelExportUtil {

	/**
	 * 导出 Excel
	 * 
	 * @param response
	 * @param fileName
	 * @param sheetName
	 * @param tableName
	 * @param data
	 */
	public static void download(HttpServletResponse response, String fileName, String sheetName, String[] tableName, List<List<String>> data) {
	  OutputStream ouputStream = null;
		try {
//			log.error("size:" + data.size());
			HSSFWorkbook workbook = ExcelExportUtil.generateExcel(data, sheetName, tableName);
//			log.error("size:" + data.size());
			response.setContentType("application/vnd.ms-excel");
      response.addHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"),"ISO8859-1") + ".xls");
      ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
		} catch (Exception e) {
			String fullStackTrace = ExceptionUtils.getStackTrace(e);
			log.error(fullStackTrace);
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.flush();
				} catch (IOException e) {
					String fullStackTrace = ExceptionUtils.getStackTrace(e);
					log.error(fullStackTrace);
				}
				try {
					ouputStream.close();
				} catch (IOException e) {
					String fullStackTrace = ExceptionUtils.getStackTrace(e);
					log.error(fullStackTrace);
				}
			}
		}
	}

	/**
	 * 组装 Excel 对象
	 * 
	 * @param datas
	 * @param sheetName
	 * @param tableName
	 * @return
	 */
	private static HSSFWorkbook generateExcel(List<List<String>> datas, String sheetName, String[] tableName) {
		HSSFWorkbook book = new HSSFWorkbook();
		try {
			HSSFSheet sheet = book.createSheet(sheetName);
			sheet.autoSizeColumn(1, true);// 自适应列宽度

			// 填充表头标题
			HSSFRow firstRow = sheet.createRow(0);// 第几行（从0开始）
			for (int i = 0; i < tableName.length; i++) {
				firstRow.createCell(i).setCellValue(tableName[i]);
			}

			// 填充表格内容
			if (datas != null && !datas.isEmpty()) {
			  for (int i = 0; i < datas.size(); i++) {
	        HSSFRow row2 = sheet.createRow(i + 1);// index：第几行
	        List<String> data = datas.get(i);
	        for (int j = 0; j < data.size(); j++) {
	          HSSFCell cell = row2.createCell(j);// 第几列：从0开始
	          //设置单元格内容为字符串型
	          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	          cell.setCellValue(data.get(j));
	        }
	      }
			}
			// book.write(fos);
			// fos.close();
		} catch (Exception e) {
			String fullStackTrace = ExceptionUtils.getStackTrace(e);
			log.error(fullStackTrace);
		}
		return book;
	}

}
