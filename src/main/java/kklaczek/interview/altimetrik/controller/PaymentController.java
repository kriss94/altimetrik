package kklaczek.interview.altimetrik.controller;

import kklaczek.interview.altimetrik.dto.PaymentDto;
import kklaczek.interview.altimetrik.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> findAll() {
        return new ResponseEntity<>(paymentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> findById(@PathVariable final Long id) {
        return paymentService.findById(id)
                             .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                             .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PaymentDto> create(@Valid @RequestBody final PaymentDto paymentDto) {
        return paymentService.create(paymentDto)
                             .map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                             .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        paymentService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PaymentDto> update(@RequestBody final PaymentDto paymentDto) {
        return paymentService.update(paymentDto)
                             .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                             .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
