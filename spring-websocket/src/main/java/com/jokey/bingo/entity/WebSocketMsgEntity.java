package com.jokey.bingo.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author yiheni
 */
@Data
public class WebSocketMsgEntity implements Serializable {
    
    private static final long serialVersionUID = 8449498325906665407L;
    
    public enum OrderType {
        /**
         * 维修
         */
        repair("维修"),
        /**
         * 保养
         */
        maintain("保养"),
        /**
         * 计量
         */
        measure("计量");

        OrderType(String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }

    /**
     * 设备名称
     */
    private String equName;
    /**
     * 设备编号
     */
    private String equId;
    /**
     * 工单类型
     */
    private OrderType orderType;
    /**
     * 工单单号
     */
    private String orderId;
    /**
     * 工单状态
     */
    private String orderStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 消息接收人ID
     */
    private List<String> toUserIds;

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
