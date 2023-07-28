package com.benewake.saleordersystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
/**
 * @author Lcs
 */
@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = "com.benewake.saleordersystem.mapper")
public class SaleOrderSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaleOrderSystemApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));  // Set your frontend's url here
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-Type")); // You don't need to add access-control-allow-origin here
        configuration.setExposedHeaders(Arrays.asList("Content-Type", "Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
            new CorsFilter(source));

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
    




