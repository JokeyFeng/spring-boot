package com.jokey.bingo.interceptor;


import com.jokey.bingo.entity.EquipmentRepair;
import com.jokey.bingo.entity.WebSocketMsgEntity;
import com.jokey.bingo.service.MessageQueueService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yiheni
 */
@Aspect
@Component
public class DataInterceptor {

    @Autowired
    private MessageQueueService queueService;


    /**
     * 维修工单切点
     */
    @Pointcut("execution(* com.jokey.bingo.service(..))")
    private void repairMsg() {

    }

    /**
     * 返回通知，方法执行正常返回时触发
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "repairMsg()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        //此处可以获得切点方法名
        //String methodName = joinPoint.getSignature().getName()
        EquipmentRepair equipmentRepair = (EquipmentRepair) result;
        WebSocketMsgEntity webSocketMsgEntity = this.generateRepairMsgEntity(equipmentRepair);
        if (webSocketMsgEntity == null) {
            return;
        }
        queueService.send(webSocketMsgEntity);
    }

    /**
     * 生成发送到MQ的维修消息
     *
     * @param equipmentRepair
     * @return
     */
    private WebSocketMsgEntity generateRepairMsgEntity(EquipmentRepair equipmentRepair) {
        WebSocketMsgEntity webSocketMsgEntity = generateRepairMsgFromTasks(equipmentRepair);
        return webSocketMsgEntity;
    }

    /**
     * 从任务中生成消息
     *
     * @param equipmentRepair
     * @return
     */
    private WebSocketMsgEntity generateRepairMsgFromTasks(EquipmentRepair equipmentRepair) {
        //todo 业务代码略
        return null;
    }
}
