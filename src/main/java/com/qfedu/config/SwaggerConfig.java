package com.qfedu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    /**
     * 创建文档说明
     * @return
     */
    private ApiInfo createAI() {
        return new ApiInfoBuilder ().title ("用户来源统计").description ("项目概述")
                .contact (new Contact ("Stream", "http://www.17feri.top", "13781150893@163.com")).build ();
    }

    /**
     * 创建Swagger扫描信息
     * @return
     */
    @Bean
    public Docket createD() {
        return new Docket (DocumentationType.SWAGGER_2).apiInfo (createAI ()).select ().
                apis (RequestHandlerSelectors.basePackage ("com.qfedu.controller")).build ();
    }

}
