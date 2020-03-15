package com.jokey.bingo.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author JokeyFeng
 * @date: 2020/3/15
 * @project: spring-boot
 * @package: com.jokey.bingo.dto
 * @comment:
 */
public class DelayedTaskDTO implements Serializable {

    private static final long serialVersionUID = -5847579689260923843L;

    private String id;

    private String message;

    private Long expireTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DelayedTaskDTO that = (DelayedTaskDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(message, that.message) &&
                Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, expireTime);
    }

    @Override
    public String toString() {
        return "DelayedTaskDTO{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
