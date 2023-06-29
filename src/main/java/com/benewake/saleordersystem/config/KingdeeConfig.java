package com.benewake.saleordersystem.config;

import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lcs
 * 金蝶bean配置
 * @since 2023年6月27日 17:26
 */
@Configuration
public class KingdeeConfig {

    @Bean
    public K3CloudApi K3CloudApi(){
        return new K3CloudApi();
    }
}
