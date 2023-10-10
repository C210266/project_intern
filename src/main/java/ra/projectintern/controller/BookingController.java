package ra.projectintern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.projectintern.exception.CustomException;

import ra.projectintern.model.dto.request.BookingRequest;
import ra.projectintern.service.impl.BookingService;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/public/booking")
@CrossOrigin("*")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getCart() throws CustomException {
        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<?> addCart(@ModelAttribute BookingRequest bookingRequest, @PathVariable Long id, HttpSession session) throws CustomException {
        session.setAttribute("cart", bookingService.addCart(bookingRequest,id));
        return new ResponseEntity<>("Add Location To Cart Success", HttpStatus.CREATED);
    }

    @DeleteMapping("/removeLocation/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id ) {
        try {
            bookingService.deleteById(id);
            return new ResponseEntity<>("Delete Location Success", HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Failed to delete location: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
