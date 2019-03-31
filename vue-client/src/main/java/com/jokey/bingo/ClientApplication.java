package com.jokey.bingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author JokeyFeng
 * date:2019/3/20
 * project:spring-boot
 * package:com.jokey.bingo
 * comment:
 */
@Controller
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("user")
    public String user() {
        return "user";
    }

    @GetMapping("demo")
    public String demo() {
        return "demo";
    }

    @GetMapping("aa")
    public String aa() {
        return "aa";
    }

}
