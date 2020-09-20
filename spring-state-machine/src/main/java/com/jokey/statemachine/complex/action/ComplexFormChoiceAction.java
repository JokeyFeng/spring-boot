package com.jokey.statemachine.complex.action;

import com.jokey.statemachine.dto.FormReq;
import com.jokey.statemachine.event.ComplexFormEvents;
import com.jokey.statemachine.state.ComplexFormStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * @author JokeyFeng
 * @date: 2020/9/14
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.action
 * @comment:
 * 如果用户从CHECK_CHOICE状态如果判断后变成CONFIRM_FORM，执行check2confirm方法，
 * 但可惜，状态的确变化了，但这个方法不会执行，只有在做判断的时候会执行ComplexFormCheckChoiceGuard的evaluate()。
 * 这就有个问题，在实际运行中，我们的确会需要在choice做出判断状态改变的时候要做些业务处理，比如表单check成功后需要通知后续人员来处理什么的，这该怎么办呢？
 *
 * 简单除暴第一招，直接在gurad里面处理。这种方法其实没有任何问题，因为既然判断的业务代码在这里面，
 * 我们把判断完成后需要做的事也在这里写完不就行了。但是，本着无关的业务能解耦就解耦的原则，我们还有一个办法
 *
 * 漂亮解耦第二招，写个action。下面介绍下这个action。
 * action，看这个名字就知道是要搞事情的，之前我们把业务代码都是写到eventConfig的方法里面，但其实可以不怎么干，
 * 我们完全可以在每个状态变化的时候独立写一个action，这样的话就能做到业务的互不打扰。
 * 我们需要的参数通过StateContext传递，其实还是message，这样的话，不同业务用不同的action就行了.
 * action可以多个插入，也就是有多少单独的业务需要在这里面处理都行.
 * 其实回过头来，不止在withChoice()里面可以，之前的withExternal()也是可以的
 *
 */
@Slf4j
public class ComplexFormChoiceAction implements Action<ComplexFormStates, ComplexFormEvents> {
	
	@Override
	public void execute(StateContext<ComplexFormStates, ComplexFormEvents> stateContext) {
		System.out.println("into ComplexFormChoiceAction");
		FormReq form = stateContext.getMessage().getHeaders().get("complexForm", FormReq.class);
		System.out.println("Header内容："+form);
		System.out.println("状态机内容："+stateContext.getStateMachine().getState());
	}
}
