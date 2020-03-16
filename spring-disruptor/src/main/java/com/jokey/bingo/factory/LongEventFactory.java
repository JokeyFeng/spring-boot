package com.jokey.bingo.factory;

import com.jokey.bingo.event.LongEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author JokeyFeng
 * @date: 2020/3/16
 * @project: spring-boot
 * @package: com.jokey.bingo.event
 * @comment: 需要让Disruptor为我们创建事件，我们同时还声明了一个EventFactory来实例化Event对象
 */
public class LongEventFactory implements EventFactory {

    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
