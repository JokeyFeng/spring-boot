package com.jokey.bingo.red.packet;

import com.jokey.bingo.red.packet.base.RedPacketUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class RedPacketTest {
	
	@Test
	void divide() {
		//定义总金额,1000分
		int amount = 100;
		//定义总人数即红包的总个数
		int total = 100;
		//调用二倍均值法工具类产生随机金额列表
		List<Integer> redPacketList = RedPacketUtil.divideRedPacket(amount, total);
		System.out.println("总金额：" + amount + "分，总个数：" + total + "个");
		
		int sum = 0;
		for (Integer red : redPacketList) {
			System.out.println("随机金额：" + red + "分，即" + new BigDecimal(red.toString()).divide(new BigDecimal(100)) + "元");
			sum += red;
		}
		System.out.println("所有随机金额叠加之和=" + sum + "分");
		
	}
	
}
