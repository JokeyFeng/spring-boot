package com.jokey.bingo.mapper;

import com.jokey.bingo.entity.PanicBuyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/3/11
 * project:spring-boot
 * package:com.jokey.bingo.mapper
 * comment:
 */
@Mapper
@Component
public interface PanicBuyMapper {
	
	/**
	 * 扣减库存
	 *
	 * @param buyId
	 * @param killTime
	 * @return
	 */
	int reduceNumber(@Param("buyId") long buyId, @Param("killTime") Date killTime);
	
	/**
	 * 根据ID查询秒杀商品
	 *
	 * @param buyId
	 * @return
	 */
	PanicBuyEntity queryById(long buyId);
	
	/**
	 * 根据偏移量查询秒杀商品列表
	 *
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<PanicBuyEntity> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	
}
