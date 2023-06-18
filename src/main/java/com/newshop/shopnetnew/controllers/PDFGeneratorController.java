package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.service.PDFGeneratorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PDFGeneratorController {
    private final PDFGeneratorService pdfGeneratorService;

    public PDFGeneratorController(PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

}
