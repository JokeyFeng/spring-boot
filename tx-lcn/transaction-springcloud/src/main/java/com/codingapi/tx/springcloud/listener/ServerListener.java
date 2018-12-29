package com.codingapi.tx.springcloud.listener;

import com.codingapi.tx.listener.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author yiheni
 */
@Component
public class ServerListener implements ApplicationListener<ServletWebServerInitializedEvent> {

    private Logger logger = LoggerFactory.getLogger(ServerListener.class);

    private int serverPort;

    @Autowired
    private InitService initService;

    public int getPort() {
        return this.serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        logger.info("onApplicationEvent -> onApplicationEvent. " + event.getApplicationContext());
        this.serverPort = event.getApplicationContext().getWebServer().getPort();

        Thread thread = new Thread(() -> {
            // 若连接不上txmanager start()方法将阻塞
            initService.start();
        });
        thread.setName("TxInit-thread");
        thread.start();
    }
}
