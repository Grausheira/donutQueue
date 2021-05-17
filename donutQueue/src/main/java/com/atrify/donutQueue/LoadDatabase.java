package com.atrify.donutQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atrify.donutQueue.model.Client;
import com.atrify.donutQueue.model.ClientRepository;


@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initClientDatabase(ClientRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Client(100l, "Premium PLS")));
      log.info("Preloading " + repository.save(new Client(101l, "Premium ++")));
      log.info("Preloading " + repository.save(new Client(102l, "Premium 4eva")));
      log.info("Preloading " + repository.save(new Client(10000l, "Peon eXTRA")));
      log.info("Preloading " + repository.save(new Client(10001l, "Peon Mundane")));
      log.info("Preloading " + repository.save(new Client(10002l, "Peon Workforce")));
    };
  }
}
