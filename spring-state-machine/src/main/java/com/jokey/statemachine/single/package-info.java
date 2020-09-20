package com.jokey.statemachine.single;
/**
 *
 *1）整个项目只有一种状态机流程，我要是想在一个项目里面又有订单流程，又有公文审批流程怎么办，难道和老板讲我的状态机demo告诉我，状态机的流程只能选一个？
 * 2）整个项目只有一个状态机流程，你没有看眼花，这是另外一个问题。哪怕是只有一种流程，比如订单流程，其实也是有很多订单的流程在同时跑，而不是像这个例子，全部订单共用一个流程，一个订单到WAITING_FOR_RECEIVE状态了，其他订单就不能是UNPAY状态了。
 * 3）参数问题，我们做项目，不是为了看日志打出“---订单创建，待支付---”给我们玩的，而是要处理具体业务的，拿订单流程来说吧，订单号怎么传，状态改变时间怎么回数据库，等等问题其实都需要解决。
 * 4）存储问题，状态机如果有多个，需要的时候从哪去，暂时不需要了放哪去，这都是问题，所以存储状态机也是需要解决的。
 *
 * 作者：张晨辉Allen
 * 链接：https://www.jianshu.com/p/efccf6d8e293
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */