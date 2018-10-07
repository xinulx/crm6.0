/**
 * 文件名：ExcelUtil.java
 * 版权：TYDIC Tech. Co. Ltd. All Rights Reserved.
 * 描述：Excel辅助工具类
 * 作者：wangshibao 
 * 创建时间：Nov 12, 2010
 */
package com.mine.App.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel辅助工具类
 * @version  [V2.0, 2011 12 03]
 * @since  [MRM V2.0]
 */
public class ExcelUtil{
	public static String getFileType(String fileName)
	{
		String ret = "";
		int len = fileName.length();
		String ext = "";
		for (int i = len - 1; i > 0; i--)
		{
			char c = fileName.charAt(i);
			if (c == '.')
			{
				ext = fileName.substring(i + 1, len);
				break;
			}
		}
		if (ext.equalsIgnoreCase("doc"))
		{
			ret = "application/msword";
		}
		else if (ext.equalsIgnoreCase("xls"))
		{
			ret = "application/vnd.ms-excel";
		}
		else if (ext.equalsIgnoreCase("pdf"))
		{
			ret = "application/pdf";
		}
		else if (ext.equalsIgnoreCase("txt"))
		{
			ret = "text/plain";
		}
		else if (ext.equalsIgnoreCase("ppt"))
		{
			ret = "application/vnd.ms-powerpoint";
		}
		else if (ext.equalsIgnoreCase("pps"))
		{
			ret = "application/vnd.ms-powerpoint";
		}
		else if (ext.equalsIgnoreCase("gif"))
		{
			ret = "image/gif";
		}
		else if (ext.equalsIgnoreCase("jpg"))
		{
			ret = "image/jpeg";
		}
		return ret;
	}

	/** keys为空时也可调用此方法
	 * @param title
	 * @param keys
	 * @param data
	 * @param out
	 * @throws IOException
	 */
	public void exportExcel(String title, String[] keys, List<Map<String, String>> data, OutputStream out)
			throws IOException
	{
		exportExcel(title, null, keys, data, out);
	}

	/**
	 * title headers均为空
	 * @param keys
	 * @param data
	 * @param out
	 * @throws IOException
	 */
	public void exportExcel(String[] keys, List<Map<String, String>> data, OutputStream out) throws IOException
	{
		exportExcel(null, null, keys, data, out);
	}

	/**
	 * title为空
	 * @param headers
	 * @param keys
	 * @param data
	 * @param out
	 * @throws IOException
	 */
	public void exportExcel(String[] headers, String[] keys, List<Map<String, String>> data, OutputStream out)
			throws IOException
	{
		exportExcel(null, headers, keys, data, out);
	}

	/**
	 * @param title 工作表名称
	 * @param headers 工作表头,可选，如果为null，则取keys为主键。
	 * @param keys 必选，建议取数据库表字段名(为避免数据库为空的字段取值不全，主键字段必选)。
	 * @param data 格式为List<Map<String, String>>
	 * @param out 输出流
	 * @throws IOException
	 */
	public void exportExcel(String title, String[] headers, String[] keys, List<Map<String, String>> data,
			OutputStream out) throws IOException
	{
		// 后期可在此处添加版本检查
		exportExcel_2003(title, headers, keys, data, out);
	}

	/**
	 * 导出2003版本excel文件
	 * @param title
	 * @param headers
	 * @param keys
	 * @param data
	 * @param out
	 * @throws IOException
	 */
	public void exportExcel_2003(String title, String[] headers, String[] keys, List<Map<String, String>> data,
			OutputStream out) throws IOException
	{
		// 创建一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 创建一个表格
		String t = (null != title && !"".equals(title) ? title : "DATA");
		HSSFSheet sheet = workbook.createSheet(t);

		// 设置表格默认列宽度为30个字节
		final int defaultWidth = 20;
		final float defaultHeight = 20;
		sheet.setDefaultColumnWidth(defaultWidth);
		sheet.setDefaultRowHeightInPoints(defaultHeight);

		// 创建并设置样式 表头样式
		HSSFCellStyle headStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		headStyle.setFont(font);
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headStyle.setFillBackgroundColor(HSSFColor.WHITE.index);;//设置背景色
		headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置前景色
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置水平居中  
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);    //设置垂直居中  
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框  
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框  
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框  
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框 


		HSSFCellStyle columnStyle = workbook.createCellStyle();
		columnStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框  
		columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框  
		columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框  
		columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框 


		// 设置表格标题行,若未指定标题时，默认取key，即数据库表格对应字段名
		HSSFRow row = sheet.createRow(0);
		String[] tmpHeaders = null;
		if (null != headers && headers.length > 0)
		{
			tmpHeaders = headers;
		}
		else if (null != keys && keys.length > 0)
		{
			tmpHeaders = keys;
		}
		else
		{
			tmpHeaders = new String[] {};
		}
        //设置头部
		for (int i = 0; i < tmpHeaders.length; i++)
		{
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(headStyle);
			HSSFRichTextString text = new HSSFRichTextString(tmpHeaders[i]);
			cell.setCellValue(text);
		}

		// 设置内容
		if (null != data && !data.isEmpty())
		{
			if (null != keys && keys.length > 0)
			{
				int index = 1;
				for (Map<String, String> item : data)
				{
					row = sheet.createRow(index);
					HSSFCell cell;
					for (int i = 0; i < keys.length; i++)
					{
						cell = row.createCell(i);
						cell.setCellStyle(columnStyle);
						Object o = item.get(keys[i]);
						String value = null;
						if(o instanceof java.math.BigDecimal){
							java.math.BigDecimal b = (BigDecimal) o;
							value = b.toString();
						} else {
							value = o == null?"":o.toString();
						}
						
						cell.setCellValue(value);
					}
					index++;
				}
			}
		}
		else
		{
			row = sheet.createRow(1);
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(columnStyle);
			cell.setCellValue("--没有数据!--");
		}
//		for (int i = 0; i < tmpHeaders.length; i++)
//		{
//			sheet.autoSizeColumn(i);
//		}
		workbook.write(out);
	}

	/**
	 * 导出2007+版本excel文件
	 * @param title
	 * @param headers
	 * @param keys
	 * @param data
	 * @param out
	 * @throws IOException
	 */
	public void exportExcel_2007(String title, String[] headers, String[] keys, List<Map<String, String>> data,
			OutputStream out) throws IOException
	{
		// 创建一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();

		// 创建一个表格
		String t = (null != title && !"".equals(title) ? title : "DATA");
		XSSFSheet sheet = workbook.createSheet(t);

		// 设置表格默认列宽度为30个字节
		final int defaultWidth = 40;
		sheet.setDefaultColumnWidth(defaultWidth);

		// 创建并设置样式
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(new XSSFColor(java.awt.Color.lightGray));

		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style2.setFillForegroundColor(new XSSFColor(java.awt.Color.lightGray));

		// 设置表格标题行,若未指定标题时，默认取key，即数据库表格对应字段名
		XSSFRow row = sheet.createRow(0);
		String[] tmpHeaders = null;
		if (null != headers && headers.length > 0)
		{
			tmpHeaders = headers;
		}
		else if (null != keys && keys.length > 0)
		{
			tmpHeaders = keys;
		}
		else
		{
			tmpHeaders = new String[] {};
		}

		for (int i = 0; i < tmpHeaders.length; i++)
		{
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(tmpHeaders[i]);
			cell.setCellValue(text);
		}

		// 设置内容
		if (null != data && !data.isEmpty())
		{
			if (null != keys && keys.length > 0)
			{
				int index = 1;
				for (Map<String, String> item : data)
				{
					row = sheet.createRow(index);
					XSSFCell cell;
					for (int i = 0; i < keys.length; i++)
					{
						cell = row.createCell(i);
						cell.setCellStyle(style2);
						cell.setCellValue(null != item.get(keys[i]) ? item.get(keys[i]) : "");
					}
					index++;
				}
			}
		}
		else
		{
			row = sheet.createRow(1);
			XSSFCell cell;
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("--没有数据!--");
		}
		for (int i = 0; i < tmpHeaders.length; i++)
		{
			sheet.autoSizeColumn(i);
		}
		workbook.write(out);
	}

	/**
	 * 检查excel版本
	 * @param fileExName
	 * @return
	 */
	public int checkExcelVersion(String fileExName)
	{
		int tmp = -1;
		if (fileExName.endsWith(Constant.EXCEL_EX_2003))
		{
			tmp = 0;
		}
		else if (fileExName.endsWith(Constant.EXCEL_EX_2007))
		{
			tmp = 1;
		}
		return tmp;
	}

	

}