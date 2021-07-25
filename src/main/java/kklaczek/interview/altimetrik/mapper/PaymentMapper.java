package kklaczek.interview.altimetrik.mapper;

import kklaczek.interview.altimetrik.dto.PaymentDto;
import kklaczek.interview.altimetrik.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public final class PaymentMapper {

    public PaymentDto mapToCsvPaymentDto(Payment payment) {
        return new PaymentDto().id(payment.getId())
                               .amount(payment.getAmount())
                               .currency(payment.getCurrency())
                               .userId(payment.getUserId())
                               .targetAcctNumber("\t" + payment.getTargetAcctNumber());
    }
}
