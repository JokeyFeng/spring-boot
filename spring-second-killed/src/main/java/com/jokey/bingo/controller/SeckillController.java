package com.jokey.bingo.controller;

import com.jokey.bingo.dto.Exposer;
import com.jokey.bingo.dto.SeckillExecution;
import com.jokey.bingo.dto.SeckillResult;
import com.jokey.bingo.entity.Seckill;
import com.jokey.bingo.entity.SeckillStateEnum;
import com.jokey.bingo.exception.RepeatKillException;
import com.jokey.bingo.exception.SeckillCloseException;
import com.jokey.bingo.service.SeckillService;
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
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;


    @GetMapping("/list")
    public String list(Model model) {
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

    @GetMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }

        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @ResponseBody
    @PostMapping(value = "/{seckillId}/exposer", produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<>(true, exposer);
        } catch (Exception e) {
            result = new SeckillResult<>(false, e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping(value = "/{seckillId}/{md5}/execution", produces = {"application/json;charset=UTF-8"})
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId, @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long phone) {

        if (phone == null) {
            return new SeckillResult<>(false, "未注册");
        }

        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            result = new SeckillResult<>(true, execution);
        } catch (RepeatKillException r) {
            SeckillExecution repeat = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<>(false, repeat);
        } catch (SeckillCloseException c) {
            SeckillExecution close = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<>(false, close);
        } catch (Exception e) {
            result = new SeckillResult<>(false, e.getMessage());
        }

        return result;
    }

    @GetMapping("/time/now")
    public SeckillResult<Long> time() {
        return new SeckillResult<>(true, Instant.now().toEpochMilli());
    }
}
