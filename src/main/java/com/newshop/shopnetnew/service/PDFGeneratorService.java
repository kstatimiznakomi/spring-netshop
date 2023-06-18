package com.newshop.shopnetnew.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.dto.OrderDTO;
import com.sun.tools.xjc.Language;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PDFGeneratorService {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public PDFGeneratorService(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    public void export(HttpServletResponse response, Long orderId) throws IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Check", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Order order = orderRepository.getOrderById(orderId);
        OrderDTO orderDetailsDTOS = orderService.getOrderByUser(order.getId());

        for (OrderDTO.OrderDetailsDTO dtos : orderDetailsDTOS.getDetails()){
            Paragraph paragraph2 = new Paragraph(
                    "Product: " + dtos.getProduct() + "\n" +
                          "Amount: " + dtos.getAmount() + "\n" +
                          "Price: " + dtos.getPrice() + " rubles" + "\n" +
                          "Sum: " + dtos.getSum() + "\n" +
                          "\n",
                    fontParagraph);
            paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paragraph2);
        }
        double totalPrice = orderDetailsDTOS.getDetails().stream()
                .map(OrderDTO.OrderDetailsDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();

        Paragraph total = new Paragraph("Total: " + totalPrice + " rubles", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(total);
        document.close();
    }
}
