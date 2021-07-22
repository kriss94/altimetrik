package kklaczek.interview.altimetrik.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PaymentDto {

    private Long id;

    @Positive
    private BigDecimal amount;

    @NotNull
    @Size(min = 3, max = 3)
    private String currency;

    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 26, max = 26) //TODO ask the analyst about validation
    private Long targetAcctNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTargetAcctNumber() {
        return targetAcctNumber;
    }

    public void setTargetAcctNumber(Long targetAcctNumber) {
        this.targetAcctNumber = targetAcctNumber;
    }

    public PaymentDto id(final Long id) {
        this.id = id;
        return this;
    }

    public PaymentDto amount(final BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public PaymentDto currency(final String currency) {
        this.currency = currency;
        return this;
    }

    public PaymentDto userId(final Long userId) {
        this.userId = userId;
        return this;
    }

    public PaymentDto targetAcctNumber(final Long targetAcctNumber) {
        this.targetAcctNumber = targetAcctNumber;
        return this;
    }
}
