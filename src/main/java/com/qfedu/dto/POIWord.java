package com.qfedu.dto;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: Stream
 * @date: 2019/10/31 15:37
 * @version: 1.0
 * @description: 基于POI操作Word
 */
public class POIWord {
    /**
     * 生成一个荣誉证书Word文档
     *
     * @param name     获奖人
     * @param fileName 文件名
     */
    public static void createWord(String name, String fileName) throws IOException {
        //1、实例化文档对象
        XWPFDocument document = new XWPFDocument ();
        //2、创建段落
        XWPFParagraph paragraph = document.createParagraph ();
        //3、设置信息
        //设置文本对齐方式
        paragraph.setAlignment (ParagraphAlignment.CENTER);
        // 4. 创建文本对象
        XWPFRun run = paragraph.createRun ();
        run.setBold (true);
        run.setColor ("C2475B");
        run.setFontSize (36);
        run.setText ("荣誉证书");

        // 正文 姓名
        XWPFParagraph paragraph2 = document.createParagraph ();
        paragraph2.setAlignment (ParagraphAlignment.LEFT);
        XWPFRun run2 = paragraph2.createRun ();
        run2.setColor ("051C2D");
        run2.setFontFamily ("楷体");
        run2.setFontSize (30);
        run2.setText (name + ":\r\n");

        // 正文 内容
        XWPFParagraph paragraph3=document.createParagraph();
        paragraph3.setAlignment(ParagraphAlignment.LEFT);
        paragraph3.setSpacingBefore(2);
        paragraph3.setFirstLineIndent(2);
        XWPFRun run3=paragraph3.createRun();
        run3.setFontSize(20);
        run3.setText("入职千锋以来，表现妖孽，特此奖励，荣获千锋Java-高级工程师,以资鼓励，再接再厉！！\r\n\r\n\r\n\r\n");

        // 结尾
        XWPFParagraph paragraph4=document.createParagraph();
        paragraph4.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun run4=paragraph4.createRun();
        run4.setFontSize(12);
        run4.setText("千锋教育Java学院 \r\n");
        XWPFRun run42=paragraph4.createRun();
        run42.setText(new SimpleDateFormat ("yyyy年MM月dd日").format(new Date ()));
        run42.setFontSize(12);

        // 5.写出
        document.write (new FileOutputStream (fileName));
        document.close ();
    }

    /**
     * 生成面试登记表
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void createWord(String filePath) throws IOException {
        //1、实例化文档对象
        XWPFDocument document=new XWPFDocument();
        //2、实例化段落
        XWPFParagraph paragraph=document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        //3、设置文本
        XWPFRun run= paragraph.createRun();
        run.setFontSize(40);
        run.setText("千锋教育面试登记表");

        //4、绘制表格
        //4.1 创建表格对象
        XWPFTable table=document.createTable(3,4);
        table.setWidth(5000);

        //4.2 创建行
        XWPFTableRow tableRow1=table.getRow(0);
        table.setWidthType(TableWidthType.DXA);
        XWPFTableCell cell11=tableRow1.getCell(0);
        cell11.setText("语言能力");
        tableRow1.getCell(2).setText("计算机能力");
        XWPFTableRow row2=table.getRow(1);
        row2.getCell(0).setText("其他技能");
        row2.getCell(2).setText("特长爱好");
        XWPFTableRow row3=table.getRow(2);
        row3.getCell(0).setText("求职意向");

        row3.getCell(1).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);

        // 5.写出
        document.write (new FileOutputStream (filePath));
        document.close ();
    }

    public static String readWord(String filePath) throws IOException {
        StringBuffer buffer=new StringBuffer();
        //1、创建文档对象--设置读取的内容
        XWPFDocument document=new XWPFDocument(new FileInputStream (filePath));
        //2、获取文档中所有的段落
        List<XWPFParagraph> paragraphList=document.getParagraphs();
        //3、遍历读取
        for (XWPFParagraph p :paragraphList){
            buffer.append (p.getParagraphText ()+"\r\n");
        }

        document.close ();
        return buffer.toString ();
    }

    public static void main(String[] args) throws IOException {
        //创建文件
        //createWord ("Stream","stream.doc");
        // 读取文档
        //System.out.println ("读取："+readWord ("stream.doc"));
        // 文档插入表格
        createWord("面试登记表.doc");
    }

}
