package com.example.demo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class WordDocumentExample {
    public static void main(String[] args) throws Exception {
        method1();
    }

    public static void method1() throws IOException, InvalidFormatException {
        try {
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(new File("report.docx"));
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setFontSize(14);
            run.setFontFamily("Times New Roman");
            run.setText("Тут типо отчёт будет");
            document.write(out);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Document created");
    }

}
