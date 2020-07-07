package com.jokey.bingo.poi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zhengjingfeng
 * @created 2020/6/24 17:31
 * @comment
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @RequestMapping("/export")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {


    }

    @RequestMapping("/import")
    public void importExcel(HttpServletRequest request, HttpServletResponse response) {


    }
}
