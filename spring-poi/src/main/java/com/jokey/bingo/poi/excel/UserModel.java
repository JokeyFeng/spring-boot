package com.jokey.bingo.poi.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Zhengjingfeng
 * @created 2020/4/8 9:32
 * @comment 用户模式：也就是poi下的usermodel有关包，它对用户友好，有统一的接口在ss包下，但是它是把整个文件读取到内存中的，
 * 对于大量数据很容易内存溢出，所以只能用来处理相对较小量的数据；
 */
public class UserModel {

    public static void main(String[] args) throws InterruptedException {
        try {
            Thread.sleep(5000);
            System.out.println("start read");
            for (int i = 0; i < 100; i++) {
                try {
                    Workbook wb = null;
                    File file = new File("D:/test.xlsx");
                    InputStream fis = new FileInputStream(file);
                    wb = new XSSFWorkbook(fis);
                    Sheet sheet = wb.getSheetAt(0);
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            System.out.println("row:" + row.getRowNum() + ",cell:" + cell.toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
