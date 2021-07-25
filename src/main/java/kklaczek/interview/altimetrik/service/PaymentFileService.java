package kklaczek.interview.altimetrik.service;

import javax.servlet.http.HttpServletResponse;

public interface PaymentFileService {

    void generatePaymentsReport(HttpServletResponse response);
}
