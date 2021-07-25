package kklaczek.interview.altimetrik.service;

import kklaczek.interview.altimetrik.dto.PaymentDto;
import kklaczek.interview.altimetrik.entity.Payment;
import kklaczek.interview.altimetrik.repository.PaymentRepository;
import kklaczek.interview.altimetrik.validator.PaymentValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final PaymentValidator paymentValidator;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, ModelMapper modelMapper, PaymentValidator paymentValidator) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
        this.paymentValidator = paymentValidator;
    }

    @Override
    public List<PaymentDto> findAll() {
        return paymentRepository.findAll()
                                .stream()
                                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentDto> findById(final Long id) {
        return paymentRepository.findById(id)
                                .map(payment -> modelMapper.map(payment, PaymentDto.class));
    }

    @Override
    public Optional<PaymentDto> create(final PaymentDto paymentDto) {
        final Payment payment = paymentRepository.save(modelMapper.map(paymentDto, Payment.class));
        return Optional.of(modelMapper.map(payment, PaymentDto.class));
    }

    @Override
    public void delete(final Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Optional<PaymentDto> update(final PaymentDto paymentDto) {
        Optional<Payment> payment = Optional.empty();

        if(paymentValidator.isPaymentDtoValid(paymentDto)) {
            payment = paymentRepository.findById(paymentDto.getId()).map(p -> updateData(p, paymentDto));
        }

        return payment.map(value -> modelMapper.map(paymentRepository.save(value), PaymentDto.class));
    }

    private Payment updateData(final Payment payment, final PaymentDto updater) {
        return payment.amount(updater.getAmount() != null ? updater.getAmount() : payment.getAmount())
                      .currency(updater.getCurrency() != null ? updater.getCurrency() : payment.getCurrency())
                      .userId(updater.getUserId() != null ? updater.getUserId() : payment.getUserId())
                      .targetAcctNumber(updater.getTargetAcctNumber() != null ? updater.getTargetAcctNumber() : payment.getTargetAcctNumber());
    }
}
