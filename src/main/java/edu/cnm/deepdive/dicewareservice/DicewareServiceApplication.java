package edu.cnm.deepdive.dicewareservice;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DicewareServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(DicewareServiceApplication.class, args);
  }

  @Bean
  public ResourceBundle bundle(){
    return ResourceBundle.getBundle("wordlist");
  }

@Bean
  public Random random(){
    return new SecureRandom();
}
}
