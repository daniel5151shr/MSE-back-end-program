package com.qst.mes.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动程序
 *
 * @author QST
 */
@EnableAsync
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MESApplication {

    private static Logger logger = LoggerFactory.getLogger(MESApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MESApplication.class, args);
        logger.info("################MES服务端启动成功################");
    }
}
