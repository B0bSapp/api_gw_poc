package com.example.demo;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@Slf4j
@EnableZuulProxy
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public ZuulFilter simpleFilter() {
    return new ZuulFilter() {
      @Override
      public String filterType() {
        return "pre";
      }

      @Override
      public int filterOrder() {
        return 1;
      }

      @Override
      public boolean shouldFilter() {
        return true;
      }

      @Override
      public Object run() throws ZuulException {
        var currentContext = RequestContext.getCurrentContext();
        currentContext.getZuulRequestHeaders().put("Nord-User-Id", "DZNQ");
        currentContext.getZuulRequestHeaders().put("Nord-Request-Id", UUID.randomUUID().toString());
        log.info("Calling this guy {}", currentContext.getRequest().getRequestURL());

        return null;
      }
    };
  }

  @Bean
  public ZuulFilter postFilter() {
    return new PostFilter();
  }
}
