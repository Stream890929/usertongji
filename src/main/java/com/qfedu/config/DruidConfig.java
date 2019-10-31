package com.qfedu.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Stream
 * @version 1.0
 * @date 2019/10/21 18:31
 */
@Configuration
public class DruidConfig {
    /**
     * 创建对象 调用方法
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> createFR() {
        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<> ();
        //设置过滤器
        registrationBean.setFilter (new WebStatFilter ());
        //设置过滤路径
        registrationBean.addUrlPatterns ("/*");
        return registrationBean;
    }

    /**
     * 注册Servlet 实现可视化页面的访问
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> createSRB() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<StatViewServlet> ();
        //设置Servlet对象
        registrationBean.setServlet (new StatViewServlet ());
        //设置触发路径
        registrationBean.addUrlMappings ("/druid/*");
        return registrationBean;
    }

}
