package kklaczek.interview.altimetrik.service;

import kklaczek.interview.altimetrik.dto.PaymentDto;
import kklaczek.interview.altimetrik.entity.Payment;
import kklaczek.interview.altimetrik.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PaymentServiceImpl(final PaymentRepository paymentRepository, final ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
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
        return checkIfPaymentExists(paymentDto.getId())
                ? updatePayment(paymentDto)
                : Optional.empty();
    }

    private boolean checkIfPaymentExists(final Long id) {
        return id != null
                && paymentRepository.findById(id)
                                    .isPresent();
    }

    private Optional<PaymentDto> updatePayment(final PaymentDto paymentDto) {
        final Payment payment = modelMapper.map(paymentDto, Payment.class);
        return Optional.of(modelMapper.map(paymentRepository.save(payment), PaymentDto.class));
    }
}
