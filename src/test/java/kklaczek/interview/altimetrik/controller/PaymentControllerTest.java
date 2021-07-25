package kklaczek.interview.altimetrik.controller;

import kklaczek.interview.altimetrik.dto.PaymentDto;
import kklaczek.interview.altimetrik.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        paymentController = new PaymentController(paymentService);
    }

    @Test
    void findAllMethodShouldReturnListOfPayments() {
        when(paymentService.findAll()).thenReturn(Collections.nCopies(25, new PaymentDto()));

        final ResponseEntity<List<PaymentDto>> result = paymentController.findAll();

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new ResponseEntity<>(Collections.nCopies(25, new PaymentDto()), HttpStatus.OK));
    }

    @Test
    void findAllMethodShouldCallOnce() {
        paymentController.findAll();

        verify(paymentService).findAll();
    }


    @Test
    void findByIdMethodShouldReturnNotFoundStatus() {
        when(paymentService.findById(any())).thenReturn(Optional.empty());

        final ResponseEntity<PaymentDto> result = paymentController.findById(1L);

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Test
    void findByIdServiceMethodShouldCallWithCorrectParam() {
        final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(paymentService.findById(idCaptor.capture())).thenReturn(Optional.empty());

        paymentController.findById(12L);

        assertThat(idCaptor.getValue()).isEqualTo(12L);
    }

    @Test
    void findByIdServiceMethodShouldCallOnce() {
        paymentController.findById(1L);

        verify(paymentService).findById(1L);
    }

    @Test
    void findByIdMethodShouldReturnOkStatus() {
        when(paymentService.findById(any())).thenReturn(Optional.of(new PaymentDto().id(1L)
                                                                                    .amount(BigDecimal.valueOf(50))
                                                                                    .currency("PLN")
                                                                                    .userId(2L)
                                                                                    .targetAcctNumber("12345678901234567890123456")));

        final ResponseEntity<PaymentDto> result = paymentController.findById(1L);

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new ResponseEntity<>(new PaymentDto().id(1L)
                                                                          .amount(BigDecimal.valueOf(50))
                                                                          .currency("PLN")
                                                                          .userId(2L)
                                                                          .targetAcctNumber("12345678901234567890123456"),
                                                          HttpStatus.OK));
    }

    @Test
    void createMethodShouldReturnNotFoundStatus() {
        when(paymentService.create(any())).thenReturn(Optional.empty());

        final ResponseEntity<PaymentDto> result = paymentController.create(new PaymentDto());

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Test
    void createMethodShouldCallWithCorrectParam() {
        final ArgumentCaptor<PaymentDto> captor = ArgumentCaptor.forClass(PaymentDto.class);

        when(paymentService.create(captor.capture())).thenReturn(Optional.empty());

        paymentController.create(new PaymentDto().amount(BigDecimal.valueOf(50))
                                                 .currency("PLN")
                                                 .userId(2L)
                                                 .targetAcctNumber("12345678901234567890123456"));

        assertThat(captor.getValue()).usingRecursiveComparison()
                                     .isEqualTo(new PaymentDto().amount(BigDecimal.valueOf(50))
                                                                .currency("PLN")
                                                                .userId(2L)
                                                                .targetAcctNumber("12345678901234567890123456"));
    }

    @Test
    void createServiceMethodShouldCallOnce() {
        paymentController.create(new PaymentDto());

        verify(paymentService).create(any());
    }

    @Test
    void createMethodShouldReturnOkStatus() {
        when(paymentService.create(any())).thenReturn(Optional.of(new PaymentDto().id(1L)
                                                                                  .amount(BigDecimal.valueOf(50))
                                                                                  .currency("PLN")
                                                                                  .userId(2L)
                                                                                  .targetAcctNumber("12345678901234567890123456")));

        final ResponseEntity<PaymentDto> result = paymentController.create(new PaymentDto().amount(BigDecimal.valueOf(50))
                                                                                           .currency("PLN")
                                                                                           .userId(2L)
                                                                                           .targetAcctNumber("12345678901234567890123456"));

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new ResponseEntity<>(new PaymentDto().id(1L)
                                                                          .amount(BigDecimal.valueOf(50))
                                                                          .currency("PLN")
                                                                          .userId(2L)
                                                                          .targetAcctNumber("12345678901234567890123456"),
                                                          HttpStatus.CREATED));
    }

    @Test
    void deleteMethodShouldShouldReturnOkStatus() {
        final ResponseEntity<Void> result = paymentController.delete(1L);

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new ResponseEntity<>(HttpStatus.OK));
    }

    @Test
    void deleteServiceMethodShouldCallOnce() {
        paymentController.delete(1L);

        verify(paymentService).delete(1L);
    }

    @Test
    void updateMethodShouldReturnNotFoundStatus() {
        when(paymentService.update(any())).thenReturn(Optional.empty());

        final ResponseEntity<PaymentDto> result = paymentController.update(new PaymentDto());

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Test
    void updateServiceMethodShouldCallWithCorrectParam() {
        final ArgumentCaptor<PaymentDto> paymentCaptor = ArgumentCaptor.forClass(PaymentDto.class);

        when(paymentService.update(paymentCaptor.capture())).thenReturn(Optional.empty());

        paymentController.update(new PaymentDto().amount(BigDecimal.valueOf(50))
                                                 .currency("PLN")
                                                 .userId(2L)
                                                 .targetAcctNumber("12345678901234567890123456"));

        assertThat(paymentCaptor.getValue()).usingRecursiveComparison()
                                            .isEqualTo(new PaymentDto().amount(BigDecimal.valueOf(50))
                                                                       .currency("PLN")
                                                                       .userId(2L)
                                                                       .targetAcctNumber("12345678901234567890123456"));
    }

    @Test
    void updateServiceMethodShouldCallOnce() {
        paymentController.update(new PaymentDto());

        verify(paymentService).update(any());
    }

    @Test
    void updateMethodShouldReturnOkStatus() {
        when(paymentService.update(any())).thenReturn(Optional.of(new PaymentDto().id(1L)
                                                                                  .amount(BigDecimal.valueOf(50))
                                                                                  .currency("PLN")
                                                                                  .userId(2L)
                                                                                  .targetAcctNumber("12345678901234567890123456")));

        final ResponseEntity<PaymentDto> result = paymentController.update(new PaymentDto().amount(BigDecimal.valueOf(50))
                                                                                           .currency("PLN")
                                                                                           .userId(2L)
                                                                                           .targetAcctNumber("12345678901234567890123456"));

        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new ResponseEntity<>(new PaymentDto().id(1L)
                                                                          .amount(BigDecimal.valueOf(50))
                                                                          .currency("PLN")
                                                                          .userId(2L)
                                                                          .targetAcctNumber("12345678901234567890123456"),
                                                          HttpStatus.OK));
    }

}
