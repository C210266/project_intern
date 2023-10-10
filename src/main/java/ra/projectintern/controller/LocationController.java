package ra.projectintern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.request.LocationRequest;
import ra.projectintern.model.dto.request.LocationUpdateRequest;
import ra.projectintern.model.dto.response.ImageLocationResponse;
import ra.projectintern.model.dto.response.LocationResponse;
import ra.projectintern.service.impl.ImageLocationService;
import ra.projectintern.service.impl.LocationService;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LocationController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private ImageLocationService imageLocationService;



    @GetMapping("/public/location/getAll")
    public ResponseEntity<List<LocationResponse>> getAll() {
        return new ResponseEntity<>(locationService.findAllInUser(), HttpStatus.OK);
    }

    @GetMapping("/admin/location/getAll")
    public ResponseEntity<List<LocationResponse>> getLocationAdmin() {
        return new ResponseEntity<>(locationService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/admin/location/add")
    public ResponseEntity<?> addLocation(@Valid @ModelAttribute LocationRequest locationRequest, BindingResult bindingResult) throws CustomException {

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            locationService.save(locationRequest); // Gọi phương thức lưu sản phẩm
            return new ResponseEntity<>("Location added successfully", HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
//            Xử lý ngoại lệ với DB
            // Xử lý ngoại lệ và trích xuất thông báo lỗi từ ràng buộc kiểm tra hợp lệ
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<?> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errorMessages);
        }
    }


//    Update location ko anh
    @PutMapping("/admin/location/update/{locationId}")
    public ResponseEntity<LocationResponse> updateProduct(
            @PathVariable Long locationId, @Valid
    @RequestBody @ModelAttribute LocationUpdateRequest locationUpdateRequest) throws CustomException {

        return new ResponseEntity<>(locationService.updateProduct(locationUpdateRequest, locationId), HttpStatus.OK);
    }

    @GetMapping("/admin/location/get/{id}")
    public ResponseEntity<LocationResponse> getLocation(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(locationService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/location/delete/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) throws CustomException {
        locationService.delete(id);
        return new ResponseEntity<>("Delete success", HttpStatus.OK);
    }

    @GetMapping("/admin/changeStatus/{id}")
    public ResponseEntity<LocationResponse> handleChangeStatusProduct(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(locationService.changeStatus(id), HttpStatus.OK);
    }


//    Add them anh vao Location
    @PutMapping("/admin/addImage/toLocation/{id}")
    public ResponseEntity<?> handleAddImageToLocation(@RequestParam("image") MultipartFile multipartFile, @PathVariable Long id) throws CustomException {
        if (multipartFile.isEmpty()) {
            return new ResponseEntity<>("File not null!!!", HttpStatus.BAD_REQUEST);

        }
        try {
            return new ResponseEntity<>(locationService.addImageToLocation(multipartFile, id), HttpStatus.CREATED);
        } catch (CustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    thay doi anh cua Location
    @PutMapping("/admin/changeImage/toLocation/{id}")
    public ResponseEntity<LocationResponse> handleChangeImage(@RequestParam("image") MultipartFile multipartFile, @PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(locationService.changeImgLocation(multipartFile, id), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/admin/deleteImage/{idImage}/inLocation/{idLocation}")
    public ResponseEntity<LocationResponse> handleDeleteImageInLocation(@PathVariable Long idImage, @PathVariable Long idLocation) throws CustomException {
        return new ResponseEntity<>(locationService.deleteImageInProduct(idImage, idLocation), HttpStatus.OK);
    }

    @GetMapping("/admin/findImageByIdLocation/{id}")
    public ResponseEntity<List<ImageLocationResponse>> findImageByIdLocate(@PathVariable Long id) throws CustomException {
        List<ImageLocationResponse> imageProducts = locationService.findAllImageByIdLocation(id);

        if (imageProducts.isEmpty()) {
            throw new CustomException("No images found for location with ID " + id);
        }

        return new ResponseEntity<>(imageProducts, HttpStatus.OK);
    }
}
