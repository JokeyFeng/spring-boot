package com.jokey.bingo.red.packet.controller;

import com.jokey.bingo.red.packet.base.BaseResponse;
import com.jokey.bingo.red.packet.dto.RedPacketDTO;
import com.jokey.bingo.red.packet.service.IRedPacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author JokeyFeng
 * @date: 2020/5/10
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.controller
 * @comment:
 */
@Slf4j
@RestController
@RequestMapping(value = "/red/packet", produces = MediaType.APPLICATION_JSON_VALUE)
public class RedPacketController {
	
	@Resource
	private IRedPacketService redPacketService;
	
	/**
	 * 发红包请求
	 *
	 * @param dto
	 * @param result
	 * @return
	 */
	@PostMapping(value = "/hand/out", consumes = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<String> handOut(@Validated @RequestBody RedPacketDTO dto, BindingResult result) {
		if (result.hasErrors()) {
			return BaseResponse.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		try {
			return BaseResponse.success(redPacketService.handOut(dto));
		} catch (Exception e) {
			log.error("", e);
			return BaseResponse.fail(e.getMessage());
		}
	}
	
	/**
	 * 抢红包
	 *
	 * @param userId
	 * @param redId
	 * @return
	 */
	@GetMapping(value = "/rob")
	public BaseResponse robPacket(@RequestParam Integer userId, @RequestParam String redId) {
		try {
			return BaseResponse.success(redPacketService.rob(userId, redId));
		} catch (Exception e) {
			//log.error("", e);
			return BaseResponse.fail(e.getMessage());
		}
	}
}
