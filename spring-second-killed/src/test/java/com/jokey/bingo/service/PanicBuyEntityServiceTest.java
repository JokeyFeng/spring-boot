package com.jokey.bingo.service;


import com.jokey.bingo.PanicBuyApplication;
import com.jokey.bingo.dto.Exposer;
import com.jokey.bingo.dto.SecKillExecution;
import com.jokey.bingo.entity.PanicBuyEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PanicBuyApplication.class)
public class PanicBuyEntityServiceTest {
	
	@Autowired
	private PanicBuyService panicBuyService;
	
	private static String SALT = "asq42Z{P{()(#$^&**asdc";
	
	@Test
	public void getSecKillList() {
		List<PanicBuyEntity> list = panicBuyService.getSecKillList();
		System.out.println(list);
	}
	
	@Test
	public void getById() {
		PanicBuyEntity panicBuyEntity = panicBuyService.getById(2);
		System.out.println(panicBuyEntity);
	}
	
	@Test
	public void exportSecKillUrl() {
		Exposer exposer = panicBuyService.exportSecKillUrl(1);
		System.out.println(exposer);
	}
	
	@Test
	public void executeSecKill() {
		long id = 1000;
		Exposer exposer = panicBuyService.exportSecKillUrl(id);
		//开始秒杀
		if (exposer.getExposed()) {
			long phone = 15626152363L;
			SecKillExecution seckill = panicBuyService.executeSecKill(id, phone, exposer.getMd5());
		} else {
			System.out.println("秒杀还没开始->" + exposer);
		}
	}
}
