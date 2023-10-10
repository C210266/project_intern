package ra.projectintern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.request.HostRequest;
import ra.projectintern.model.dto.request.RoomRequest;
import ra.projectintern.model.dto.response.RoomResponse;
import ra.projectintern.service.impl.HostService;
import ra.projectintern.service.impl.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/public/room")
@CrossOrigin("*")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/getAll")
    public ResponseEntity<List<RoomResponse>> getAll() {
        return new ResponseEntity<>(roomService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<RoomResponse> addHost(@RequestBody @ModelAttribute RoomRequest roomRequest) throws CustomException {
        return new ResponseEntity<>(roomService.save(roomRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getHost(@PathVariable Long id) throws CustomException {
        try {
            return new ResponseEntity<>(roomService.findById(id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Room not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomResponse> updateHost(@PathVariable Long id, @RequestBody @ModelAttribute RoomRequest roomRequest) throws CustomException {
        return new ResponseEntity<>(roomService.update(roomRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHost(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(roomService.delete(id), HttpStatus.OK);

    }
}
