package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ConfigurationProperties(prefix = "zuul")
public class AuthorizationComponent {
  Map<String, Map<String, String>> innerRoutes;

  public Map<String, Map<String, String>> getRoutes() {
    return innerRoutes;
  }

  public void setRoutes(Map<String, String> outerRoutes) {
    innerRoutes = new HashMap<>();
    outerRoutes.forEach(
        (key, value) -> {
          var splittedKey = Arrays.asList(key.split("\\."));
          innerRoutes
              .computeIfAbsent(splittedKey.get(0), s -> new HashMap<>())
              .put(splittedKey.get(1), value);
        });
  }
}
