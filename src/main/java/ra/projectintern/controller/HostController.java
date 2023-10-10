package ra.projectintern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.request.BookingRequest;
import ra.projectintern.model.dto.request.HostRequest;
import ra.projectintern.model.dto.response.HostResponse;
import ra.projectintern.service.impl.BookingService;
import ra.projectintern.service.impl.HostService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/host")
@CrossOrigin("*")
public class HostController {
    @Autowired
    private HostService hostService;

    @GetMapping("/getAll")
    public ResponseEntity<List<HostResponse>> getAll() {
        return new ResponseEntity<>(hostService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HostResponse> addHost(@RequestBody @ModelAttribute HostRequest hostRequest) throws CustomException {
        return new ResponseEntity<>(hostService.save(hostRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getHost(@PathVariable Long id) throws CustomException {
        try {
            return new ResponseEntity<>(hostService.findById(id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Host not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HostResponse> updateHost(@PathVariable Long id, @RequestBody @ModelAttribute HostRequest hostRequest) throws CustomException {
        return new ResponseEntity<>(hostService.update(hostRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHost(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(hostService.delete(id), HttpStatus.OK);

    }
}
