package com.epam.training.ticketservice.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.epam.training.ticketservice.repository")
@EntityScan("com.epam.training.ticketservice.model")
@ComponentScan({"com.epam.training.ticketservice.service"})
@EnableTransactionManagement
public class JpaConfig {
}
