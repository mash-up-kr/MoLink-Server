package com.mashup.molinkfirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MolinkFirstApplication {

  public static void main(String[] args) {
    SpringApplication.run(MolinkFirstApplication.class, args);
  }
}
