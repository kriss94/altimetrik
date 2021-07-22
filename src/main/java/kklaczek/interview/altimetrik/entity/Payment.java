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
}
