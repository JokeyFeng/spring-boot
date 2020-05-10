package com.jokey.bingo.poi;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;

/**
 * @author Zhengjingfeng
 * @created 2020/4/8 9:33
 * @comment 事件模式：在poi下的eventusermodel包下，相对来说实现比较复杂，但是它处理速度快，占用内存少，可以用来处理海量的Excel数据。
 */
public class EventModel {

    private void processOneSheet(String fileName) throws Exception {
        OPCPackage pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);
        InputStream sheet = r.getSheet("rId1");
        InputSource sheetSource = new InputSource(sheet);
        parser.parse(sheetSource);
        sheet.close();
    }

    private XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }

    private static class SheetHandler extends DefaultHandler {
        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        @Override
        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
            if ("c".equals(name)) {
                System.out.print(attributes.getValue("r") + " - ");
                String cellType = attributes.getValue("t");
                nextIsString = "s".equals(cellType);
            }
            lastContents = "";
        }

        @Override
        public void endElement(String uri, String localName, String name) throws SAXException {
            if (nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsString = false;
            }

            if ("v".equals(name)) {
                System.out.println(lastContents);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            lastContents += new String(ch, start, length);
        }
    }

    public static void main(String[] args) throws Exception {
        Thread.sleep(5000);
        System.out.println("start read");
        for (int i = 0; i < 100; i++) {
            EventModel example = new EventModel();
            example.processOneSheet("D:/test.xlsx");
            Thread.sleep(1000);
        }
    }
}
