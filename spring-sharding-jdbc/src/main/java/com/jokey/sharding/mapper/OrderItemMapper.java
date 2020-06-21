package com.jokey.sharding.mapper;

import com.jokey.sharding.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author JokeyFeng
 * @date: 2020/6/21
 * @project: spring-boot
 * @package: com.jokey.sharding.mapper
 * @comment:
 */
@Mapper
@Component
public interface OrderItemMapper {
	
	/**
	 * 保存
	 *
	 * @param orderItem
	 * @return
	 */
	@Insert("insert into t_order_item(order_id,remark) values(#{orderId},#{remark})")
	Integer save(OrderItem orderItem);
}
