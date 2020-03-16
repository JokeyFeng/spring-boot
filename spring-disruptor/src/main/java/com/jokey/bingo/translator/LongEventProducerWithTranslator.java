package com.jokey.bingo.translator;

import com.jokey.bingo.event.LongEvent;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author JokeyFeng
 * @date: 2020/3/16
 * @project: spring-boot
 * @package: com.jokey.bingo.translator
 * @comment: Disruptor 3.0提供了lambda式的API。这样可以把一些复杂的操作放在Ring Buffer，
 * 所以在Disruptor3.0以后的版本最好使用Event Publisher或者Event Translator来发布事件。
 * Translator可以分离出来并且更加容易单元测试。Disruptor提供了不同的接口(EventTranslator, EventTranslatorOneArg, EventTranslatorTwoArg, 等等)去产生一个Translator对象。
 * 很明显，Translator中方法的参数是通过RingBuffer来传递的。
 */
public class LongEventProducerWithTranslator {

    private final RingBuffer<LongEvent> ringBuffer;

    /**
     * 一个translator可以看做一个事件初始化器，publicEvent方法会调用它
     * 填充Event
     *
     * @author JokeyZheng
     * @date 2020/3/16 21:05
     * @param null
     * @return null
     */
    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
            (event, sequence, bb) -> event.setValue(bb.getLong(0));

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
