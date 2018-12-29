/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2018/12/29</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.base.util;

import java.util.UUID;

/**
 * @Project spring-boot
 * @Package com.jokey.base.util
 * @ClassName IdGenertor
 * @Author JokeyZheng
 * @Date 2018/12/29
 * @Version 1.0
 */
public abstract class IdGenertor {

    /**
     * 获取一个UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
