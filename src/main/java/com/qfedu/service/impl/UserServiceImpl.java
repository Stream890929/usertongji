package com.qfedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfedu.dao.UserMapper;
import com.qfedu.entity.User;
import com.qfedu.service.UserService;
import com.qfedu.vo.Result;
import com.qfedu.vo.TongJiDayVo;
import com.qfedu.vo.TongjiBean;
import com.qfedu.vo.TongjiVo;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Stream
 * @version 1.0
 * @date 2019/10/22 14:01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result uploadExcel(MultipartFile file) throws IOException {
        // 文件上传
        if (!file.isEmpty ()) {
            // 获取上传的内容并且用Excel读取
            List<User> userList = readExcel (file.getBytes ());
            // 数据批量新增
            userMapper.insertBatch (userList);
            return Result.setOk ("上传成功");
        }
        return Result.setError ();
    }

    @Override
    public Result queryAll() {
        return Result.setOk (userMapper.selectAll ());
    }

    @Override
    public void downdload(HttpServletResponse response) throws IOException {
        // 1.设置下载信息设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.setContentType ("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader ("content-disposition", "attachment;filename=" + getTime () + "_注册用户渠道.xlsx");
        // 2. 获取数据
        List<User> userList = userMapper.selectAll ();
        // 3. 生成Excel
        createExcel (userList, response.getOutputStream ());
    }

    @Override
    public Result queryTongJi(int type) {
        TongjiVo vo = new TongjiVo ();
        vo.setTypes (userMapper.selectOrigins ());
        switch (type) {
            case 1://天
                vo.setYdata (userMapper.selectDay (getDay (-1)));
                break;
            case 2://月
                vo.setYdata (userMapper.selectMonth (getMonth (-1)));
                break;
            default:
                break;
        }
        return Result.setOk (vo);
    }

    @Override
    public Result queryTongJiDay() {
        TongJiDayVo dayVo = new TongJiDayVo ();
        dayVo.setTypes (new ArrayList<> ());
        dayVo.setYdata (new ArrayList<> ());
        List<TongjiBean> list = userMapper.selectDay (getDay (-1));

        // 添加拥有数据
        for (TongjiBean b : list) {
            dayVo.getTypes ().add (b.getName ());
            dayVo.getYdata ().add (Integer.parseInt (b.getValue ()));
        }

        // 添加没有的数据
        List<String> origins = userMapper.selectOrigins ();
        for (String origin : origins) {
            if (dayVo.getTypes ().indexOf (origin)==-1){
                dayVo.getTypes ().add (origin);
                dayVo.getYdata ().add (0);
            }
        }
        return Result.setOk (dayVo);
    }

    /**
     * 读取表格
     *
     * @param data
     * @return
     */
    private List<User> readExcel(byte[] data) {
        List<User> list = new ArrayList<> ();
        try {
            //1、创建文档对象
            XSSFWorkbook workbook = new XSSFWorkbook (new ByteArrayInputStream (data));
            //2、读取表单
            XSSFSheet sheet = workbook.getSheetAt (0);
            //获取行数
            int last = sheet.getLastRowNum ();
            //获取第一行
            XSSFRow firstRow = sheet.getRow (0);
            //获取列数
            int lastCell = firstRow.getLastCellNum ();
            //循环遍历数据
            for (int i = 1; i < last; i++) {
                XSSFRow row = sheet.getRow (i);
                User user = new User ();
                user.setId ((int) row.getCell (0).getNumericCellValue ());
                user.setUsername (row.getCell (1).getStringCellValue ());
                user.setOrigin (row.getCell (2).getStringCellValue ());
                user.setRtime (parseTime (row.getCell (3).getStringCellValue ()));
                list.add (user);
            }
            workbook.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return list;
    }

    /**
     * 字符串转时间格式
     */
    private Date parseTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        try {
            return sdf.parse (time);
        } catch (ParseException e) {
            e.printStackTrace ();
            return new Date ();
        }
    }

    /**
     * 生成Excel
     */
    private void createExcel(List<User> list, OutputStream os) throws IOException {
        //1、创建文档和表单
        XSSFWorkbook workbook = new XSSFWorkbook ();
        XSSFSheet sheet = workbook.createSheet ("自动生成数据");
        //2、设置首行 标题  标记每一列是啥
        XSSFRow row = sheet.createRow (0);
        row.createCell (0).setCellValue ("序号");
        row.createCell (1).setCellValue ("注册名");
        row.createCell (2).setCellValue ("渠道");
        row.createCell (3).setCellValue ("注册时间");
        row.createCell (4).setCellValue ("创建时间");
        //3、遍历数据写出到每一行
        for (int i = 1; i <= list.size (); i++) {
            User user = list.get (i - 1);
            XSSFRow r1 = sheet.createRow (i);
            r1.createCell (0).setCellValue (user.getId ());
            r1.createCell (1).setCellValue (user.getUsername ());
            r1.createCell (2).setCellValue (user.getOrigin ());
            r1.createCell (3).setCellValue (formatTime (user.getRtime ()));
            r1.createCell (4).setCellValue (formatTime (user.getCtime ()));
        }
        //4、写出
        workbook.write (os);
        //5、关闭
        workbook.close ();
    }

    /**
     * 获取当前日期，转换存储数据库的时间格式
     */
    private String getTime() {
        return new SimpleDateFormat ("yyyy-MM-dd").format (new Date ());
    }

    /**
     * 时间格式转换
     */
    private String formatTime(Date date) {
        return new SimpleDateFormat ("yyyy-MM-dd ").format (date);
    }

    /**
     * 获取某天的
     */
    private String getDay(int days) {
        Calendar calendar = Calendar.getInstance ();
        if (days != 0) {
            calendar.add (Calendar.DAY_OF_MONTH, days);
        }
        return new SimpleDateFormat ("yyyy-MM-dd").format (calendar.getTime ());
    }

    /**
     * 获取某月的
     */
    private String getMonth(int month) {
        Calendar calendar = Calendar.getInstance ();
        if (month != 0) {
            calendar.add (Calendar.DAY_OF_MONTH, month);
        }
        return new SimpleDateFormat ("yyyy-MM").format (calendar.getTime ());
    }

}
