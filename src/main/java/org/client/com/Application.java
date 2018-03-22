package org.client.com;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.client.com.api.AccountInterface;
import org.client.com.api.TokenInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Spring Boot 应用启动类
 * <p>
 * Created by bysocket on 16/4/26.
 */
@SpringBootApplication
public class Application {
//        extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AccountInterface anInterface() {
        AccountInterface accountInterface = Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(AccountInterface.class, "http://39.106.33.113:9002/account");
        return accountInterface;
    }

    @Bean
    public TokenInterface tokenInterface() {
        TokenInterface tkInterface = Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(TokenInterface.class, "http://39.106.33.113:9002/account");
        return tkInterface;
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    // war
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder
//                                                         builder) {
//        // TODO Auto-generated method stub
//        return builder.sources(Application.class);
//    }

}
