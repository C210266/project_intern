package ra.projectintern.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.*;
import ra.projectintern.model.dto.request.LocationRequest;
import ra.projectintern.model.dto.request.LocationUpdateRequest;
import ra.projectintern.model.dto.response.LocationResponse;
import ra.projectintern.repository.IConvenientRepository;
import ra.projectintern.repository.IHostRepository;
import ra.projectintern.repository.IRoomRepository;
import ra.projectintern.service.IGenericMapper;
import ra.projectintern.service.upload_aws.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LocationMapper implements IGenericMapper<Location, LocationRequest, LocationResponse> {
    @Autowired
    private StorageService storageService;

    @Autowired
    private IHostRepository hostRepository;

    @Autowired
    private IRoomRepository roomRepository;
    @Autowired
    private IConvenientRepository convenientRepository;

    @Override
    public Location toEntity(LocationRequest locationRequest) throws CustomException {

        Host host = hostRepository.findById(locationRequest.getHost_id()).orElseThrow(() -> new CustomException("Cant find Host"));
        List<Convenient> convenients = convenientRepository.findAllById(locationRequest.getConvenients_id());
        List<Room> rooms = roomRepository.findAllById(locationRequest.getRooms_id());

        List<String> list = new ArrayList<>();
        String imgUrl = null;
        for (MultipartFile file : locationRequest.getImage()) {
            imgUrl = storageService.uploadFile(file);
            list.add(imgUrl);
        }
        List<ImageLocation> imageLocations = list.stream()
                .map(url -> ImageLocation.builder()
                        .image(url)
                        .location(new Location())
                        .build())
                .collect(Collectors.toList());

        return Location.builder()
                .name(locationRequest.getName())
                .address(locationRequest.getAddress())
                .host(host)
                .rooms(rooms)
                .price(locationRequest.getPrice())
                .images(imageLocations)
                .convenients(convenients)
                .title(locationRequest.getTitle())
                .description(locationRequest.getDescription())
                .favourites(null)
                .status(locationRequest.isStatus())
                .type(locationRequest.getType())
                .build();
    }

    public Location toEntity(LocationUpdateRequest locationUpdateRequest) throws CustomException {
        Host host = hostRepository.findById(locationUpdateRequest.getHost_id()).orElseThrow(() -> new CustomException("Cant find Host"));
        List<Convenient> convenients = convenientRepository.findAllById(locationUpdateRequest.getConvenients());
        List<Room> rooms = roomRepository.findAllById(locationUpdateRequest.getRooms());
        return Location.builder()
                .name(locationUpdateRequest.getName())
                .address(locationUpdateRequest.getAddress())
                .host(host)
                .rooms(rooms)
                .price(locationUpdateRequest.getPrice())
                .convenients(convenients)
                .status(locationUpdateRequest.isStatus())
                .type(locationUpdateRequest.getType())
                .title(locationUpdateRequest.getTitle())
                .description(locationUpdateRequest.getDescription())
                .favourites(null)
                .build();
    }

    @Override
    public LocationResponse toResponse(Location location) {
        List<String> imageUrl = location.getImages().stream().map(ImageLocation::getImage).collect(Collectors.toList());

        return LocationResponse.builder()
                .name(location.getName())
                .id(location.getId())
                .main_image(location.getMain_image())
                .images(imageUrl)
//                .reviews_name(location.getReviews().stream().map(Review::getDescription).collect(Collectors.toList()))
                .host_name(location.getHost().getName())
                .rooms_name(location.getRooms().stream()
                        .map(Room::getName)
                        .collect(Collectors.toList()))
                .status(location.isStatus())
                .title(location.getTitle())
                .description(location.getTitle())
                .type(location.getType())
                .address(location.getAddress())
                .convenient_names(location.getConvenients().stream()
                        .map(Convenient::getName)
                        .collect(Collectors.toList()))
                .price(location.getPrice())
                .build();
    }
}
