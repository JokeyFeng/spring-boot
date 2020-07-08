package com.jokey.sharding.mapper;

import com.jokey.sharding.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JokeyFeng
 * @date: 2020/6/21
 * @project: spring-boot
 * @package: com.jokey.sharding.mapper
 * @comment:
 */
@Mapper
@Component
public interface OrderMapper {
	
	/**
	 * 保存
	 *
	 * @param order
	 * @return
	 */
	@Insert("insert into t_order(order_id,user_id,config_id,remark) values(#{orderId},#{userId},#{configId},#{remark})")
	Integer save(Order order);
	
	/**
	 * 查询
	 *
	 * @param userId
	 * @return
	 */
	@Select("select order_id orderId, user_id userId, config_id configId, remark, " +
			"create_time createTime, last_modify_time lastModifyTime from t_order  where user_id = #{userId}")
	Order selectByUserId(Long userId);
	
	/**
	 * 关联item明细表查询
	 *
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@Select("select o.order_id orderId, o.user_id userId, o.config_id configId, o.remark, " +
			"o.create_time createTime, o.last_modify_time lastModifyTime from " +
			"t_order o inner join t_order_item i on o.order_id = i.order_id " +
			"where o.user_id =#{userId} and o.order_id =#{orderId}")
	List<Order> selectOrderJoinOrderItem(@Param("userId") Long userId, @Param("orderId") Long orderId);
	
	/**
	 * 查询
	 *
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@Select("select  o.order_id orderId, o.user_id userId, o.config_id configId, o.remark " +
			"from t_order o inner join t_config c on o.config_id = c.id " +
			"where o.user_id =#{userId} and o.order_id =#{orderId}")
	List<Order> selectOrderJoinConfig(@Param("userId") Long userId, @Param("orderId") Long orderId);
	
	/**
	 * userId范围查询
	 *
	 * @param userList
	 * @return
	 */
	@Select({"<script>" +
			"select order_id orderId, user_id userId, config_id configId, remark," +
			"create_time createTime, last_modify_time lastModifyTime from t_order  where user_id in" +
			"<foreach collection='userList' item='userId' open='(' separator=',' close=')'> #{userId} </foreach>" +
			"</script>"})
	List<Order> selectOrderByUserList(@Param("userList") List<Long> userList);
}
