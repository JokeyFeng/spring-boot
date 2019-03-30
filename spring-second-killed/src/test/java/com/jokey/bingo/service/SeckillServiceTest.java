package com.jokey.bingo.service;


import com.jokey.bingo.SecondKillApplication;
import com.jokey.bingo.dto.Exposer;
import com.jokey.bingo.dto.SeckillExecution;
import com.jokey.bingo.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SecondKillApplication.class)
public class SeckillServiceTest {

    @Autowired
    SeckillService seckillService;

    private static String SALT = "asq42Z{P{()(#$^&**asdc";

    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        System.out.println(list);
    }

    @Test
    public void getById() {
        Seckill seckill = seckillService.getById(2);
        System.out.println(seckill);
    }

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(1);
        System.out.println(exposer);
    }

    @Test
    public void executeSeckill() {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        //开始秒杀
        if (exposer.getExposed()) {
            long phone = 15626152363L;
            SeckillExecution seckill = seckillService.executeSeckill(id, phone, exposer.getMd5());
        } else {
            System.out.println("秒杀还没开始->" + exposer);
        }
    }
}
