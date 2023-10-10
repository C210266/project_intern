package ra.projectintern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.request.BookingRequest;
import ra.projectintern.model.dto.request.ReviewRequest;
import ra.projectintern.model.dto.response.ReviewResponse;
import ra.projectintern.service.impl.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/review")
@CrossOrigin("*")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ReviewResponse>> getALl() {
        return new ResponseEntity<>(reviewService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ReviewResponse> addReview(@Valid @RequestBody ReviewRequest request) throws CustomException {
        return new ResponseEntity<>(reviewService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getReview(@PathVariable Long id) throws CustomException {
        try {
            return new ResponseEntity<>(reviewService.findById(id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Review not found", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/locationId/{loca_id}")
    public ResponseEntity<?> getByLocationId(@PathVariable Long loca_id) throws CustomException {
        try {
            return new ResponseEntity<>(reviewService.findByLocationId(loca_id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Location not found", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody @ModelAttribute ReviewRequest request) throws CustomException {
        return new ResponseEntity<>(reviewService.update(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(reviewService.delete(id), HttpStatus.OK);

    }
}
