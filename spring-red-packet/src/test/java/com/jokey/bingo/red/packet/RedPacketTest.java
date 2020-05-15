package com.jokey.bingo.red.packet;

import com.jokey.bingo.red.packet.base.RedPacketUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class RedPacketTest {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
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
	
	@Test
	void test() {
		redisTemplate.opsForHash().put("ctm:movie:321566", "aaa", "bbbb");
		redisTemplate.opsForValue().set("ctm:plan:321566", "124243");
		Set<String> keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
			Set<String> keysTmp = new HashSet<>();
			Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match("\"ctm" + "*").build());
			while (cursor.hasNext()) {
				keysTmp.add(new String(cursor.next()));
			}
			return keysTmp;
		});
		assert keys != null;
		keys.forEach(System.out::println);
		redisTemplate.delete(keys);
	}
	
}
