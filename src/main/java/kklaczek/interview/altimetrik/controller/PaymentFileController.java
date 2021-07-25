package kklaczek.interview.altimetrik.controller;

import kklaczek.interview.altimetrik.service.PaymentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class PaymentFileController {

    private final PaymentFileService paymentFileService;

    @Autowired
    public PaymentFileController(final PaymentFileService paymentFileService) {
        this.paymentFileService = paymentFileService;
    }

    @GetMapping(value = "/payment/report")
    public void generateReport(HttpServletResponse response) {
        paymentFileService.generatePaymentsReport(response);
    }
}
