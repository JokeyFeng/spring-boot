/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019/1/19</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.bingo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo
 * @ClassName MapDemo
 * @Author JokeyZheng
 * @Date 2019/1/19
 * @Version 1.0
 */
public class MapDemo {
    public static void main(String[] args) {
        int size = 5;
        Map<String, String> map = new LinkedHashMap<String, String>(size, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                boolean bb = size() > size;
                if (bb) {
                    System.out.println("最近很少用到的key >>" + eldest.getKey());
                }
                return bb;
            }
        };

        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");
        System.out.println(map.toString());
        map.put("6", "6");
        System.out.println(map.toString());
        map.get("2");
        System.out.println(map.toString());
        map.put("7", "7");
        System.out.println(map.toString());

    }

}