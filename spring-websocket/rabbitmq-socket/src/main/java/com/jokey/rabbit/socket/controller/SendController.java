package com.jokey.rabbit.socket.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JokeyFeng
 * @date: 2020/8/18
 * @project: spring-boot
 * @package: com.jokey.rabbit.socket.controller
 * @comment:
 */
@RestController
@RequestMapping("/web-socket")
public class SendController {
	
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	/**
	 * 通知消息
	 */
	@GetMapping("/notice")
	public void notice() {
		messagingTemplate.convertAndSend("/topic/notice", JSON.toJSONString("这是通知消息！！"));
	}
	
	/**
	 * 具体用户消息
	 */
	@GetMapping("/user/{name}")
	public void user(@PathVariable("name") String name, String who) {
		messagingTemplate.convertAndSendToUser(name, "/topic/reply", JSON.toJSONString("这是发送给" + who + "用户的消息！！"));
	}
}
