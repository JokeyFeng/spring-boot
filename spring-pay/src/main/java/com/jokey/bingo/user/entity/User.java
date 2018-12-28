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

package com.jokey.bingo.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.entity
 * @ClassName User
 * @Author JokeyZheng
 * @Date 2018/12/28
 * @Version 1.0
 */
@Data
public class User implements Serializable {

    @Id
    private String id;

    @Column
    private String name;
}
