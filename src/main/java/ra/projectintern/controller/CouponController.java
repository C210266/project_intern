package ra.projectintern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.request.BookingRequest;
import ra.projectintern.model.dto.request.CouponRequest;
import ra.projectintern.model.dto.response.CouponResponse;
import ra.projectintern.service.impl.CouponService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/coupon")
@CrossOrigin("*")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CouponResponse>> getALl() {
        return new ResponseEntity<>(couponService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CouponResponse> addCoupon(@Valid @RequestBody @ModelAttribute CouponRequest request) throws CustomException {
        return new ResponseEntity<>(couponService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCoupon(@PathVariable Long id) throws CustomException {
        try {
            return new ResponseEntity<>(couponService.findById(id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Coupon not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CouponResponse> updateCoupon(@PathVariable Long id, @RequestBody @ModelAttribute CouponRequest request) throws CustomException {
        return new ResponseEntity<>(couponService.update(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(couponService.delete(id), HttpStatus.OK);

    }
}
