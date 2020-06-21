package com.jokey.sharding.config;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author JokeyFeng
 * @date: 2020/6/21
 * @project: spring-boot
 * @package: com.jokey.sharding.config
 * @comment:
 */
public class HintSharding implements HintShardingAlgorithm<Long> {
	/**
	 * 分片规则
	 *
	 * @param availableTargetNames 分片表名的集
	 * @param hintShardingValue    分片键集合
	 * @return
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<Long> hintShardingValue) {
		Collection<String> result = new ArrayList<>();
		availableTargetNames.forEach(tableName -> {
			hintShardingValue.getValues().forEach(shardValue -> {
				if (tableName.endsWith(String.valueOf(shardValue.intValue() % 2))) {
					System.out.println("*********************");
					result.add(tableName);
				}
			});
		});
		return result;
	}
}
