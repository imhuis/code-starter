package com.imhuis.code.example.multidatasource.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author: imhuis
 * @date: 2021/12/21
 * @description:
 */
@Component
@Slf4j
public class CustomizeRunner implements ApplicationRunner {

    @Autowired
    private DataSource oneDataSource;

    @Autowired
    private DataSource twoDataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("oneDataSource: {}", oneDataSource.getConnection().getClientInfo());
        log.info("twoDataSource: {}", twoDataSource.getConnection().getClientInfo());
    }
}
