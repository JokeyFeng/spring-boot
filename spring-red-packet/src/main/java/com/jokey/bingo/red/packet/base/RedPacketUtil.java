package com.jokey.bingo.red.packet.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author JokeyFeng
 * @date: 2020/5/9
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.base
 * @comment: 二倍均值法生成红包随机金额
 * 二倍均值算法的核心思想是根据每次剩余的总金额 M 和剩余人数 N，执行M/N再乘以2的操作得到一个边界值E，
 * 然后制定一个从0到E的随机区间，在这个随机区间内将产生一个随机金额R，此时总金额M将更新为M-R，剩余人数N更新为N-1。
 * 再继续重复上述执行流程，以此类推，直至最终剩余人数N-1为0，即代表随机数已经产生完毕
 */
@Slf4j
public class RedPacketUtil {
	
	/**
	 * 生成红包随机金额算法
	 *
	 * @param totalAmount 红包总金额(单位:分)
	 * @param totalPeople 抢红包总人数，即总数量
	 * @return
	 */
	public static List<Integer> divideRedPacket(Integer totalAmount, Integer totalPeople) {
		StopWatch sw = new StopWatch("二倍均值法产生随机金额");
		sw.start();
		//用于存储每次生成的小红包随机金额
		List<Integer> amountList = new ArrayList<>();
		if (totalAmount > 0 && totalPeople > 0) {
			//记录剩余的总金额，初始化时金额即为红包的总金额
			Integer restAmount = totalAmount;
			//记录剩余的总人数，初始化时即为指定的总人数
			Integer restPeople = totalPeople;
			//定义生成随机数的实例对象
			ThreadLocalRandom localRandom = ThreadLocalRandom.current();
			//不断循环遍历、迭代更新地生成随机金额R，直到N-1>0
			for (int i = 0; i < totalPeople - 1; i++) {
				//随机范围：[1,剩余人均金额的两倍)，左开有笔-amount即为产生的随机金额
				int amount = localRandom.nextInt((restAmount / restPeople << 1) - 1) + 1;
				//更新剩余的总金额M=M-R
				restAmount -= amount;
				//更新剩余的总人数N=N-1
				restPeople--;
				//将产生的随机金额添加到集合中
				amountList.add(amount);
			}
			//循环完毕，剩余的金额即为最后一个随机金额，也需要添加到集合中
			amountList.add(restAmount);
		}
		sw.stop();
		log.info(sw.prettyPrint());
		return amountList;
	}
}
