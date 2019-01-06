package com.jokey.base.util;

import com.jokey.base.DataBlock;
import com.jokey.base.DbConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.base.util
 * comment:
 */
@Slf4j
public abstract class AddressUtils {

    public static String getCityInfo(String ip) {
        try {
            Resource resource = new ClassPathResource("ip2region/ip2region.db");
            File file = resource.getFile();
            if (!file.exists()) {
                log.error("Error: Invalid ip2region.db file");
            }
            int algorithm = DbSearcher.BTREE_ALGORITHM;
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, file.getPath());
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            DataBlock dataBlock;
            if (!Util.isIpAddress(ip)) {
                log.error("Error: Invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            log.error("获取地址信息异常：{}", e);
        }
        return "";
    }
}
