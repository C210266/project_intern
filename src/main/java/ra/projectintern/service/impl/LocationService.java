package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.ImageLocation;
import ra.projectintern.model.domain.Location;
import ra.projectintern.model.domain.Review;
import ra.projectintern.model.dto.request.LocationRequest;
import ra.projectintern.model.dto.request.LocationUpdateRequest;
import ra.projectintern.model.dto.response.ImageLocationResponse;
import ra.projectintern.model.dto.response.LocationResponse;
import ra.projectintern.repository.IHostRepository;
import ra.projectintern.repository.IImageLocationRepository;
import ra.projectintern.repository.ILocationRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.ImageLocationMapper;
import ra.projectintern.service.mapper.LocationMapper;
import ra.projectintern.service.upload_aws.StorageService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService implements IGenericService<LocationResponse, LocationRequest, Long> {

    @Autowired
    private ILocationRepository locationRepository;
    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private IImageLocationRepository imageLocationRepository;
    @Autowired
    private ImageLocationService imageLocationService;
    @Autowired
    private ImageLocationMapper imageLocationMapper;
    @Autowired
    private IHostRepository hostRepository;
    @Autowired
    private StorageService storageService;

    //    Lay ra tat ca Location
    @Override
    public List<LocationResponse> findAll() {
        return locationRepository.findAll().stream()
                .map(p -> locationMapper.toResponse(p)).collect(Collectors.toList());
    }

    //    Lay ra Location voi status true
    public List<LocationResponse> findAllInUser() {
        return locationRepository.findAll().stream()
                .filter(p -> p.isStatus()) // Lọc các sản phẩm có trạng thái là true
                .map(p -> locationMapper.toResponse(p))
                .collect(Collectors.toList());
    }

    @Override
    public LocationResponse findById(Long aLong) throws CustomException {
        Optional<Location> p = locationRepository.findById(aLong);

        if (p.isPresent()) {
            return locationMapper.toResponse(p.get());
        }
        throw new CustomException("Product not found");
    }


    @Override
    public LocationResponse save(LocationRequest locationRequest) throws CustomException {

//        hostRepository.findById(locationRequest.getHost_id()).orElseThrow(() -> new CustomException("Cant found Host"));

        Location location = locationMapper.toEntity(locationRequest);
        List<String> listUrl = new ArrayList<>();
        for (MultipartFile m : locationRequest.getImage()) {
            listUrl.add(storageService.uploadFile(m));
        }
        // setMain_image() vào cái ảnh đầu tiên
        location.setMain_image(listUrl.get(0));

//        List anh phu
        List<ImageLocation> imageLocations = new ArrayList<>();
        for (String url : listUrl) {
            imageLocations.add(ImageLocation.builder()
                    .image(url)
                    .location(location)
                    .build());
        }
        location.setImages(imageLocations);
        return locationMapper.toResponse(locationRepository.save(location));
    }

    @Override
    public LocationResponse update(LocationRequest locationRequest, Long id) throws CustomException {
        Location p = locationMapper.toEntity(locationRequest);
        p.setId(id);
        return locationMapper.toResponse(locationRepository.save(p));
    }

    //    Update ko thay doi anh
    public LocationResponse updateProduct(LocationUpdateRequest locationUpdateRequest, Long id) throws CustomException {
        Location location = locationMapper.toEntity(locationUpdateRequest);
        location.setId(id);

        LocationResponse locationResponse = LocationResponse.builder()
                .id(location.getId())
//                .reviews_name(location.getReviews().stream().map(Review::getDescription).collect(Collectors.toList()))
                .host_name(location.getHost().getName())
                .rooms_name(location.getRooms().stream().map(room -> room.getName()).collect(Collectors.toList()))
                .status(location.isStatus())
                .type(location.getType())
                .address(location.getAddress())
                .title(location.getTitle())
                .description(location.getDescription())
                .convenient_names(location.getConvenients().stream().map(service -> service.getName()).collect(Collectors.toList()))
                .price(location.getPrice())
                .build();
        locationRepository.save(location);
        return locationResponse;
    }

    @Override
    public LocationResponse delete(Long aLong) throws CustomException {
        Optional<Location> location = locationRepository.findById(aLong);
        if (location.isPresent()) {
            locationRepository.deleteById(aLong);
        } else {
            throw new CustomException("Location not found");
        }
        return null;
    }

    //    Giong findById nhưng khác kiểu trả ve
    public Location findLocationById(Long id) throws CustomException {
        Optional<Location> optionalProduct = locationRepository.findById(id);
        return optionalProduct.orElseThrow(() -> new CustomException("product not found"));
    }

    //    Add List anh phu
    public LocationResponse addImageToLocation(MultipartFile multipartFile, Long id) throws CustomException {
        Location location = findLocationById(id);
        String url = storageService.uploadFile(multipartFile);

        location.getImages().add(
                ImageLocation.builder()
                        .image(url)
                        .location(location)
                        .build());
        return locationMapper.toResponse(locationRepository.save(location));
    }

    //    Change main_image
    public LocationResponse changeImgLocation(MultipartFile multipartFile, Long id) throws CustomException {
        Location location = findLocationById(id);
        String url = storageService.uploadFile(multipartFile);
        location.setMain_image(url);
        return locationMapper.toResponse(locationRepository.save(location));
    }

    @Transactional
    public LocationResponse deleteImageInProduct(Long idImage, Long idLocation) throws CustomException {
        ImageLocation imageLocation = findImageLocationById(idImage);
        Location location = findLocationById(idLocation);
        location.getImages().remove(imageLocation);
        imageLocationService.delete(idImage);
        return locationMapper.toResponse(location);
    }

    public ImageLocation findImageLocationById(Long idImage) throws CustomException {
        Optional<ImageLocation> optionalImageProduct = imageLocationRepository.findById(idImage);
        return optionalImageProduct.orElseThrow(() -> new CustomException("Image not found"));
    }

    //    Change status cua Location
    public LocationResponse changeStatus(Long id) throws CustomException {
        Location location = findLocationById(id);
        location.setStatus(!location.isStatus());
        return locationMapper.toResponse(locationRepository.save(location));
    }

    public List<ImageLocationResponse> findAllImageByIdLocation(Long idLocation) throws CustomException {
        List<ImageLocation> imageLocations = new ArrayList<>();

        Optional<Location> location = locationRepository.findById(idLocation);
        if (location.isPresent()) {
            imageLocations = imageLocationRepository.findAllByLocation(location.get());
            return imageLocations.stream()
                    .map(imageLocation -> imageLocationMapper.toResponse(imageLocation))
                    .collect(Collectors.toList());
        } else {
            throw new CustomException("Location isn't exist");
        }
    }
}
