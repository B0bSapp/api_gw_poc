package com.example.demo;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PostFilter extends ZuulFilter {
  @Autowired private AuthorizationComponent authorizationComponent;

  @Override
  public String filterType() {
    return "route";
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
    var routesConfiguration = authorizationComponent.getRoutes();
    var currentContext = RequestContext.getCurrentContext();
    var route = currentContext.getOrDefault("proxy", "").toString();
    var authorization = routesConfiguration.get(route).get("authorization");
    currentContext.getZuulRequestHeaders().put("authorization", authorization);

    return null;
  }
}
