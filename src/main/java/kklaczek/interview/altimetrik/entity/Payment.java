package kklaczek.interview.altimetrik.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;
    private String currency;
    private Long userId; //TODO relation Many Payment to One User
    private String targetAcctNumber;

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

    public String getTargetAcctNumber() {
        return targetAcctNumber;
    }

    public void setTargetAcctNumber(String targetAcctNumber) {
        this.targetAcctNumber = targetAcctNumber;
    }

    public Payment id(final Long id) {
        this.id = id;
        return this;
    }

    public Payment amount(final BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Payment currency(final String currency) {
        this.currency = currency;
        return this;
    }

    public Payment userId(final Long userId) {
        this.userId = userId;
        return this;
    }

    public Payment targetAcctNumber(final String targetAcctNumber) {
        this.targetAcctNumber = targetAcctNumber;
        return this;
    }
}
