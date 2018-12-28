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

package com.jokey.base.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Project spring-boot
 * @Package com.jokey.base.mapper
 * @ClassName BaseMapper
 * @Author JokeyZheng
 * @Date 2018/12/28
 * @Version 1.0
 */

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
