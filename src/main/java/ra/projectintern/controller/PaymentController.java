package ra.projectintern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.request.ConvenientRequest;
import ra.projectintern.model.dto.request.PaymentMethodRequest;
import ra.projectintern.model.dto.response.PaymentMethodResponse;
import ra.projectintern.service.impl.ConvenientService;
import ra.projectintern.service.impl.PaymentMethodService;

import java.util.List;

@RestController
@RequestMapping("/api/public/payment")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentMethodResponse>> getAll() {
        return new ResponseEntity<>(paymentMethodService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<PaymentMethodResponse> addPayment(@RequestBody @ModelAttribute PaymentMethodRequest request) throws CustomException {
        return new ResponseEntity<>(paymentMethodService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPayment(@PathVariable Long id) throws CustomException {
        try {
            return new ResponseEntity<>(paymentMethodService.findById(id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Payment not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentMethodResponse> updatePayment(@PathVariable Long id, @RequestBody @ModelAttribute PaymentMethodRequest request) throws CustomException {
        return new ResponseEntity<>(paymentMethodService.update(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(paymentMethodService.delete(id), HttpStatus.OK);

    }
}
