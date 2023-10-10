package ra.projectintern.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.request.ConvenientRequest;
import ra.projectintern.model.dto.request.HostRequest;
import ra.projectintern.model.dto.response.ConvenientResponse;
import ra.projectintern.service.impl.ConvenientService;
import ra.projectintern.service.impl.HostService;

import java.util.List;

@RestController
@RequestMapping("/api/public/convenient")
@CrossOrigin("*")

public class ConvenientController {
    @Autowired
    private ConvenientService convenientService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ConvenientResponse>> getAll() {
        return new ResponseEntity<>(convenientService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ConvenientResponse> addConvenient(@RequestBody @ModelAttribute ConvenientRequest convenientRequest) throws CustomException {
        return new ResponseEntity<>(convenientService.save(convenientRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getConvenient(@PathVariable Long id) throws CustomException {
        try {
            return new ResponseEntity<>(convenientService.findById(id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Host not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ConvenientResponse> updateHost(@PathVariable Long id, @RequestBody @ModelAttribute ConvenientRequest convenientRequest) throws CustomException {
        return new ResponseEntity<>(convenientService.update(convenientRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHost(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(convenientService.delete(id), HttpStatus.OK);

    }
}
