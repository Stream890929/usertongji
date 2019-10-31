package com.qfedu.dto;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Stream
 * @date: 2019/10/31 19:24
 * @version: 1.0
 * @description: 基于POI操作Excel
 */
public class POIExcel {
    /**
     * 生成Excel表格
     *
     * @param filePath 文件名
     * @throws IOException
     */
    public static void createExcel(String filePath) throws IOException {
        // 1.创建文档对象
        XSSFWorkbook workbook = new XSSFWorkbook ();
        // 2.创建表单
        XSSFSheet sheet = workbook.createSheet ();
        // 3.创建行
        XSSFRow row = sheet.createRow (0);
        // 4.操作列
        XSSFCellStyle cellStyle = workbook.createCellStyle ();
        cellStyle.setAlignment (HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont ();
        font.setBold (true);
        font.setFontHeight (30);
        cellStyle.setFont (font);

        XSSFCell cell = row.createCell (0);
        cell.setCellValue ("序号");
        cell.setCellStyle (cellStyle);
        XSSFCell cell1 = row.createCell (1);
        cell1.setCellValue ("姓名");
        cell1.setCellStyle (cellStyle);
        XSSFCell cell2 = row.createCell (2);
        cell2.setCellValue ("成绩");
        cell2.setCellStyle (cellStyle);

        // 5.输出内容
        for (int i = 1; i < 101; i++) {
            XSSFRow xssfRow = sheet.createRow (i);
            xssfRow.createCell (0).setCellValue (i);
            xssfRow.createCell (1).setCellValue ("Stream" + i);
            xssfRow.createCell (2).setCellValue (i * 14);
        }

        // 6. 文档保存
        workbook.write (new FileOutputStream (filePath));
    }

    /**
     * 读取Excel
     *
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> readExcel(String fileName) throws IOException {
        // 1.创建文档对象
        XSSFWorkbook workbook = new XSSFWorkbook (fileName);
        // 2.读取表单
        XSSFSheet sheet = workbook.getSheetAt (0);
        List<Map<String, Object>> mapList = new ArrayList<> ();
        // 获取行数
        int lastRowNum = sheet.getLastRowNum ();
        // 获取第一行
        XSSFRow firstRow = sheet.getRow (0);
        // 获取列数
        int lastCellNum = firstRow.getLastCellNum ();

        // 循环遍历数据
        for (int i = 1; i < lastRowNum; i++) {
            XSSFRow row = sheet.getRow (i);
            Map<String, Object> map = new HashMap<> ();
            for (int j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell (j);
                String cellType = cell.getCellType ().name ();

                Object val = null;
                switch (cellType) {
                    case "STRING":
                        val = cell.getStringCellValue ();
                        break;
                    case "NUMBER":
                        val = cell.getNumericCellValue ();
                        break;
                    case "BOOLEAN":
                        val = cell.getBooleanCellValue ();
                        break;
                    default:
                        break;
                }

                if (val != null) {
                    map.put (firstRow.getCell (j).getStringCellValue (), val);
                }
            }
            mapList.add (map);
        }
        workbook.close ();
        return mapList;
    }

    public static void main(String[] args) throws IOException {
        //createExcel ("成绩单.xlsx");
        System.out.println ("读取内容：" + readExcel ("成绩单.xlsx"));
    }
}
