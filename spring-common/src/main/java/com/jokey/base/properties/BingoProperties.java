package com.jokey.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.base.properties
 * comment:
 */
@Configuration
@ConfigurationProperties(prefix = "Bingo")
public class BingoProperties {
    private boolean openAopLog = true;

    public boolean isOpenAopLog() {
        return this.openAopLog;
    }

    public void setOpenAopLog(boolean openAopLog) {
        this.openAopLog = openAopLog;
    }
}
