package com.gechev.discoverbulgaria.config;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.util.FileUtil;
import com.gechev.discoverbulgaria.util.impl.FileUtilImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
public class ApplicationBeanConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public Gson gson() {
    return new GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .setPrettyPrinting()
      .create();
  }

  @Bean
  public FileUtil fileUtil() {
    return new FileUtilImpl();
  }

  @Value("${cloudinary.cloud-name}")
  private String cloudName;

  @Value("${cloudinary.api-key}")
  private String apiKey;

  @Value("${cloudinary.api-secret}")
  private String apiSecret;

  @Bean
  public Cloudinary cloudinary() {
    HashMap<String, String> config = new HashMap<>();

    config.put("cloud_name", this.cloudName);
    config.put("api_key", this.apiKey);
    config.put("api_secret", this.apiSecret);

    return new Cloudinary(config);
  }
}
