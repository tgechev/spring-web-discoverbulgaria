package com.gechev.discoverbulgaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class DiscoverBulgariaApplication implements ErrorController {

  public static void main(String[] args) {
    SpringApplication.run(DiscoverBulgariaApplication.class, args);
  }

  private static final String PATH = "/error";

  @RequestMapping(value = PATH)
  public String error() {
    return "forward:/index.html";
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
