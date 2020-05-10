package com.jokey.bingo.controller;

import com.jokey.bingo.vo.LoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author JokeyFeng
 * @date: 2020/4/23
 * @project: spring-boot
 * @package: com.jokey.bingo.controller
 * @comment:
 */
@Controller
public class LoginController {
	
	@GetMapping("/index")
	public String login(Model model) {
		LoginVO loginVO = new LoginVO();
		model.addAttribute("loginVO", loginVO);
		return "login";
	}
	
	@PostMapping("/login")
	public String login(Model model, @ModelAttribute LoginVO loginVO) {
		System.out.println(loginVO.toString());
		model.addAttribute("loginVO", loginVO);
		return "home";
	}
}
