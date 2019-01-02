/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019/1/2</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Project spring-boot
 * @Package com.jokey.base.util
 * @ClassName CronTimeUtils
 * @Author JokeyZheng
 * @Date 2019/1/2
 * @Version 1.0
 */
public abstract class CronTimeUtils {

    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

    public static void main(String[] args) {
        System.out.println(CronTimeUtils.getCron(new Date()));
    }
}
