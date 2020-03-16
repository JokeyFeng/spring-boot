package com.jokey.bingo.event;

/**
 * @author JokeyFeng
 * @date: 2020/3/16
 * @project: spring-boot
 * @package: com.jokey.bingo.event
 * @comment: 生产或消费对象 生产者传递一个long类型的值给消费者，而消费者消费这个数据的方式仅仅是把它打印出来。首先声明一个Event来包含需要传递的数据
 */
public class LongEvent {

    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
