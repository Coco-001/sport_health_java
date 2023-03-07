package com.coco.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    /**
     * 创建接口文档
     * @return
     */
    @Bean
    public Docket createApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ajie.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * 设置文档信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("个人健康管理平台")
                .version("1.0.0")
                .contact(new Contact("Coco", "http://localhost:8000/doc.html", "cocospace.top"))
                .description("个人健康管理平台接口文档")
                .build();
    }

    /**
     * 设置请求的信息
     * @return
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> list = new ArrayList<>();
        ApiKey key = new ApiKey("Authorization", "Authorization", "Header");
        list.add(key);
        return list;
    }

    /**
     * 配置security对swagger测试的权限
     * @return
     */
    public List<SecurityContext> securityContexts() {
        List<SecurityContext> list = new ArrayList<>();
        list.add(getSecurityContext());
        return list;
    }

    /**
     * 得到授权路径
     * @return
     */
    private SecurityContext getSecurityContext() {
        return SecurityContext
                .builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("hello/.*"))
                .build();
    }

    /**
     * 给授权swagger，可以进行接口测试
     * @return
     */
    private List<SecurityReference> securityReferences() {
        List<SecurityReference> list = new ArrayList<>();
        //授权范围 全局
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = scope;
        list.add(new SecurityReference("Authorization", scopes));
        return list;
    }

}
