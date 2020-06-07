package io.mycat.myweb.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        // 允许跨域的域名，可以用*表示允许任何域名使用
        .allowedOrigins("*")
        // 允许任何方法（post、get等）
        .allowedMethods("*")
        // 允许任何请求头
        .allowedHeaders("*")
        // 带上cookie信息
        .allowCredentials(true)
        .exposedHeaders(HttpHeaders.SET_COOKIE)
        // maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
        .maxAge(3600L);
  }
}