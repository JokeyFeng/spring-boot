/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2018/12/28</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.bingo.tx.mapper;

import com.jokey.base.entity.User;
import com.jokey.base.db.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.repository
 * @ClassName UserMapper
 * @Author JokeyZheng
 * @Date 2018/12/28
 * @Version 1.0
 */
@Component
@Mapper
public interface UserMapper extends MyMapper<User> {

}
