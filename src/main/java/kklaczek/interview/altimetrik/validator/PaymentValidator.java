package kklaczek.interview.altimetrik.validator;

import kklaczek.interview.altimetrik.dto.PaymentDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentValidator {

    public boolean isPaymentDtoValid(final PaymentDto paymentDto) {
        return paymentDto.getId() != null
                && validAmount(paymentDto.getAmount())
                && validCurrency(paymentDto.getCurrency())
                && validUserId(paymentDto.getUserId())
                && validTargetAcct(paymentDto.getTargetAcctNumber());
    }

    private boolean validAmount(final BigDecimal amount) {
        return amount == null || amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    private boolean validCurrency(final String currency) {
        return currency == null || currency.matches("[A-Z]{3}");
    }

    private boolean validUserId(final Long userId) {
        return userId == null || userId > 0;
    }

    private boolean validTargetAcct(final String targetAcct) {
        return targetAcct == null || targetAcct.matches("\\d{26}");
    }
}
