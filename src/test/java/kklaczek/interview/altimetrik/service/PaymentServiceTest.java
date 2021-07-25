package kklaczek.interview.altimetrik.service;

import kklaczek.interview.altimetrik.dto.PaymentDto;
import kklaczek.interview.altimetrik.entity.Payment;
import kklaczek.interview.altimetrik.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ModelMapper modelMapper;

    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentServiceImpl(paymentRepository, modelMapper);
    }

    @Test
    void findAllMethodShouldReturnList() {
        when(paymentRepository.findAll()).thenReturn(Collections.nCopies(2, new Payment().id(1L)
                                                                                         .amount(BigDecimal.valueOf(50))
                                                                                         .currency("PLN")
                                                                                         .userId(2L)
                                                                                         .targetAcctNumber("12345678901234567890123456")));
        when(modelMapper.map(any(), any())).thenReturn(new PaymentDto().id(1L)
                                                                       .amount(BigDecimal.valueOf(50))
                                                                       .currency("PLN")
                                                                       .userId(2L)
                                                                       .targetAcctNumber("12345678901234567890123456"));

        List<PaymentDto> result = paymentService.findAll();

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(Collections.nCopies(2, new PaymentDto().id(1L)
                                                                            .amount(BigDecimal.valueOf(50))
                                                                            .currency("PLN")
                                                                            .userId(2L)
                                                                            .targetAcctNumber("12345678901234567890123456")));
    }

    @Test
    void findAllMethodShouldReturnEmptyList() {
        List<PaymentDto> result = paymentService.findAll();

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(Collections.emptyList());
    }

    @Test
    void findByIdMethodShouldReturnElement() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(new Payment().id(1L)
                                                                                    .amount(BigDecimal.valueOf(50))
                                                                                    .currency("PLN")
                                                                                    .userId(2L)
                                                                                    .targetAcctNumber("12345678901234567890123456")));
        when(modelMapper.map(any(), any())).thenReturn(new PaymentDto().id(1L)
                                                                       .amount(BigDecimal.valueOf(50))
                                                                       .currency("PLN")
                                                                       .userId(2L)
                                                                       .targetAcctNumber("12345678901234567890123456"));

        Optional<PaymentDto> paymentDto = paymentService.findById(1L);

        assertThat(paymentDto).isPresent()
                              .get()
                              .usingRecursiveComparison()
                              .isEqualTo(new PaymentDto().id(1L)
                                                         .amount(BigDecimal.valueOf(50))
                                                         .currency("PLN")
                                                         .userId(2L)
                                                         .targetAcctNumber("12345678901234567890123456"));
    }

    @Test
    void findByIdRepositoryMethodShouldCallOnceWithCorrectParam() {
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        when(paymentRepository.findById(captor.capture())).thenReturn(Optional.empty());

        paymentService.findById(9L);

        verify(paymentRepository).findById(any());
        assertThat(captor.getValue()).isEqualTo(9L);
    }


    @Test
    void findByIdMethodShouldReturnEmptyOptional() {
        when(paymentRepository.findById(any())).thenReturn(Optional.empty());

        Optional<PaymentDto> result = paymentService.findById(9L);


        assertThat(result).isEmpty();
    }

    @Test
    void createMethodShouldSaveAndReturnCorrectObject() {
        final Payment payment = new Payment().amount(BigDecimal.valueOf(50))
                                             .currency("PLN")
                                             .userId(2L)
                                             .targetAcctNumber("12345678901234567890123456");
        final PaymentDto paymentDto = new PaymentDto().amount(BigDecimal.valueOf(50))
                                                      .currency("PLN")
                                                      .userId(2L)
                                                      .targetAcctNumber("12345678901234567890123456");

        when(paymentRepository.save(any())).thenReturn(payment.id(1L));
        when(modelMapper.map(any(), eq(Payment.class))).thenReturn(payment);
        when(modelMapper.map(any(), eq(PaymentDto.class))).thenReturn(paymentDto.id(1L));

        Optional<PaymentDto> result = paymentService.create(paymentDto);

        assertThat(result).isPresent()
                          .get()
                          .usingRecursiveComparison()
                          .isEqualTo(paymentDto.id(1L));
    }


    @Test
    void saveRepositoryMethodShouldCallOnceWithCorrectParam() {
        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);

        final Payment payment = new Payment().amount(BigDecimal.valueOf(50))
                                             .currency("PLN")
                                             .userId(2L)
                                             .targetAcctNumber("12345678901234567890123456");
        final PaymentDto paymentDto = new PaymentDto().amount(BigDecimal.valueOf(50))
                                                      .currency("PLN")
                                                      .userId(2L)
                                                      .targetAcctNumber("12345678901234567890123456");

        when(paymentRepository.save(captor.capture())).thenReturn(payment.id(5L));
        when(modelMapper.map(any(), eq(Payment.class))).thenReturn(payment);
        when(modelMapper.map(any(), eq(PaymentDto.class))).thenReturn(paymentDto.id(5L));

        paymentService.create(paymentDto);

        assertThat(captor.getValue()).usingRecursiveComparison()
                                     .isEqualTo(payment.id(5L));
        verify(paymentRepository).save(payment);
    }

    @Test
    void deleteMethodShouldCallOnceWithCorrectParam() {
        paymentService.delete(1L);

        verify(paymentRepository).deleteById(1L);
    }

    @Test
    void updateMethodShouldReturnEmptyOptional() {
        when(paymentRepository.findById(any())).thenReturn(Optional.empty());

        Optional<PaymentDto> result = paymentService.update(new PaymentDto().id(5L));

        assertThat(result).isEmpty();
    }

    @Test
    void updateMethodShouldReturnCorrectObject() {
        final Payment payment = new Payment().amount(BigDecimal.valueOf(50))
                                             .currency("PLN")
                                             .userId(2L)
                                             .targetAcctNumber("12345678901234567890123456");
        final PaymentDto paymentDto = new PaymentDto().amount(BigDecimal.valueOf(50))
                                                      .currency("PLN")
                                                      .userId(2L)
                                                      .targetAcctNumber("12345678901234567890123456");

        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment.id(5L)));
        when(modelMapper.map(any(), eq(Payment.class))).thenReturn(payment);
        when(modelMapper.map(any(), eq(PaymentDto.class))).thenReturn(paymentDto.id(5L));

        Optional<PaymentDto> result = paymentService.update(paymentDto);

        assertThat(result).isPresent()
                          .get()
                          .usingRecursiveComparison()
                          .isEqualTo(paymentDto.id(5L));
    }
}