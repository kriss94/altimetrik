package kklaczek.interview.altimetrik.service;

import kklaczek.interview.altimetrik.dto.PaymentDto;
import kklaczek.interview.altimetrik.mapper.PaymentMapper;
import kklaczek.interview.altimetrik.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentFileServiceImpl implements PaymentFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentFileServiceImpl.class);

    private static final String CONTENT_TYPE = "text/csv";
    private static final String HEADER_KEY = "Content-Disposition";
    private static final String FILE_NAME = "Payments.csv";
    private static final String[] HEADER = {"id", "amount", "currency", "userId", "targetAcctNumber"};

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentFileServiceImpl(final PaymentRepository paymentRepository, final PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public void generatePaymentsReport(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HEADER_KEY, String.format("attachment; filename=\"%s\"", FILE_NAME));


        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE)) {
            csvWriter.writeHeader(HEADER);
            final List<PaymentDto> payments = paymentRepository.findAll()
                                                               .stream()
                                                               .map(paymentMapper::mapToCsvPaymentDto)
                                                               .collect(Collectors.toList());

            for (PaymentDto payment : payments) {
                csvWriter.write(payment, HEADER);
            }
        } catch (IOException exception) {
            LOGGER.warn(String.format("Error when try to generate payments report. Message: %s", exception.getMessage()));
        }


    }
}
