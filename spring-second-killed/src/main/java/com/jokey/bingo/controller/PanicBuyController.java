package com.jokey.bingo.controller;

import com.jokey.bingo.dto.Exposer;
import com.jokey.bingo.dto.SecKillExecution;
import com.jokey.bingo.dto.PanicBuyResult;
import com.jokey.bingo.entity.PanicBuyEntity;
import com.jokey.bingo.entity.PanicBuyStateEnum;
import com.jokey.bingo.exception.RepeatBuyException;
import com.jokey.bingo.exception.PanicBuyCloseException;
import com.jokey.bingo.service.PanicBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/3/18
 * project:spring-boot
 * package:com.jokey.bingo.controller
 * comment:
 */
@Controller
@RequestMapping("/panic/buy")
public class PanicBuyController {
	
	@Autowired
	private PanicBuyService panicBuyService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<PanicBuyEntity> list = panicBuyService.getSecKillList();
		model.addAttribute("list", list);
		return "list";
	}
	
	@GetMapping("/{buyId}/detail")
	public String detail(@PathVariable("buyId") Long buyId, Model model) {
		if (buyId == null) {
			return "redirect:/secKill/list";
		}
		PanicBuyEntity panicBuyEntity = panicBuyService.getById(buyId);
		if (panicBuyEntity == null) {
			return "forward:/panic/buy/list";
		}
		
		model.addAttribute("panicBuy", panicBuyEntity);
		return "detail";
	}
	
	@ResponseBody
	@PostMapping(value = "/{buyId}/exposer", produces = {"application/json;charset=UTF-8"})
	public PanicBuyResult<Exposer> exposer(@PathVariable Long buyId) {
		PanicBuyResult<Exposer> result;
		try {
			Exposer exposer = panicBuyService.exportSecKillUrl(buyId);
			result = new PanicBuyResult<>(true, exposer);
		} catch (Exception e) {
			result = new PanicBuyResult<>(false, e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@PostMapping(value = "/{buyId}/{md5}/execution", produces = {"application/json;charset=UTF-8"})
	public PanicBuyResult<SecKillExecution> execute(@PathVariable("buyId") Long buyId,
													@PathVariable("md5") String md5,
													@RequestParam(required = false) Long userPhone) {
		if (userPhone == null) {
			return new PanicBuyResult<>(false, "未注册");
		}
		
		PanicBuyResult<SecKillExecution> result;
		try {
			SecKillExecution execution = panicBuyService.executeSecKill(buyId, userPhone, md5);
			result = new PanicBuyResult<>(true, execution);
		} catch (RepeatBuyException r) {
			SecKillExecution repeat = new SecKillExecution(buyId, PanicBuyStateEnum.REPEAT_KILL);
			return new PanicBuyResult<>(false, repeat);
		} catch (PanicBuyCloseException c) {
			SecKillExecution close = new SecKillExecution(buyId, PanicBuyStateEnum.END);
			return new PanicBuyResult<>(false, close);
		} catch (Exception e) {
			result = new PanicBuyResult<>(false, e.getMessage());
		}
		
		return result;
	}
	
	@GetMapping("/time/now")
	public PanicBuyResult<Long> time() {
		return new PanicBuyResult<>(true, Instant.now().toEpochMilli());
	}
}
