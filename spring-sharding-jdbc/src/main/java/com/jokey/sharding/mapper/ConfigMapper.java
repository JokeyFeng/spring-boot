package com.jokey.sharding.mapper;

import com.jokey.sharding.entity.Config;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
public interface ConfigMapper {
	/**
	 * 保存
	 *
	 * @param config
	 * @return
	 */
	@Insert("insert into t_config(id,remark,create_time) values(#{id},#{remark},#{createTime})")
	Integer save(Config config);
	
	/**
	 * 根据ID查询
	 *
	 * @param id
	 * @return
	 */
	@Select("select * from t_config where id = #{id}")
	Config selectById(Integer id);
}
