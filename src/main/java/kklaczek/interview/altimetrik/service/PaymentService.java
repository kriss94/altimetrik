package kklaczek.interview.altimetrik.service;

import kklaczek.interview.altimetrik.dto.PaymentDto;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentDto> findAll();

    Optional<PaymentDto> findById(final Long id);

    Optional<PaymentDto> create(final PaymentDto paymentDto);

    void delete(final Long id);

    Optional<PaymentDto> update(final PaymentDto paymentDto);
}
