package com.example.declarativetransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(mode = AdviceMode.PROXY)
@Slf4j
@SpringBootApplication
public class DeclarativeTransactionDemoApplication implements CommandLineRunner {
    @Autowired
    private FooService fooService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DeclarativeTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fooService.insertRecord();
        log.info("AAA {}", jdbcTemplate.queryForObject("select count(*) from foo where bar ='AAA'", Long.class));
        try {
            fooService.insertThenRollback();
        } catch (Exception e) {
            log.info("BBB {}",
                    jdbcTemplate.queryForObject("select count(*) from foo where bar='BBB'", Long.class));
        }
        try {
            fooService.invokeInsertThenRollback();
        } catch (Exception e) {
            log.info("BBB {}",
                    jdbcTemplate.queryForObject("select count(*) from foo where bar='BBB'", Long.class));
        }
    }
}
