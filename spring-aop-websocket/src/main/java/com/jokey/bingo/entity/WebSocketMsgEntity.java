package com.jokey.bingo.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WebSocketMsgEntity implements Serializable {
    public enum OrderType {
        repair("维修"),
        maintain("保养"),
        measure("计量");

        OrderType(String value) {
            this.value = value;
        }

        String value;

        public String getValue() {
            return value;
        }
    }

    //设备名称
    private String EquName;
    //设备编号
    private String EquId;
    //工单类型
    private OrderType orderType;
    //工单单号
    private String orderId;
    //工单状态
    private String orderStatus;
    //创建时间
    private Date createTime;
    //消息接收人ID
    private List<String> toUserIds;

    public String getEquName() {
        return EquName;
    }

    public void setEquName(String equName) {
        EquName = equName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEquId() {
        return EquId;
    }

    public void setEquId(String equId) {
        EquId = equId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(List<String> toUserIds) {
        this.toUserIds = toUserIds;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
