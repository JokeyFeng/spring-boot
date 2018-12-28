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

package com.jokey.bingo.third;

import com.jokey.base.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.third
 * @ClassName SpringPayFeign
 * @Author JokeyZheng
 * @Date 2018/12/28
 * @Version 1.0
 */
@FeignClient(value = "Spring-Pay")
public interface SpringPayFeign {

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/insert")
    int insertUser(@RequestBody User user);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/update")
    int updateUser(@RequestBody User user);

}
