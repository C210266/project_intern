package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.ImageLocation;
import ra.projectintern.model.dto.request.ImageLocationRequest;
import ra.projectintern.model.dto.response.ImageLocationResponse;
import ra.projectintern.repository.IImageLocationRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.ImageLocationMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageLocationService implements IGenericService<ImageLocationResponse, ImageLocationRequest, Long> {
    @Autowired
    private IImageLocationRepository iImageLocationRepository;

    @Autowired
    private ImageLocationMapper imageLocationMapper;

    @Override
    public List<ImageLocationResponse> findAll() {
        return iImageLocationRepository.findAll().stream()
                .map(booking -> imageLocationMapper.toResponse(booking))
                .collect(Collectors.toList());
    }

    @Override
    public ImageLocationResponse findById(Long aLong) throws CustomException {
        return iImageLocationRepository.findById(aLong)
                .map(booking -> imageLocationMapper.toResponse(booking))
                .orElseThrow(() -> new CustomException("Booking not found"));
    }

    @Override
    public ImageLocationResponse save(ImageLocationRequest imageLocationRequest) throws CustomException {
        return imageLocationMapper.toResponse(iImageLocationRepository.save(imageLocationMapper.toEntity(imageLocationRequest)));
    }

    @Override
    public ImageLocationResponse update(ImageLocationRequest imageLocationRequest, Long id) throws CustomException {
        ImageLocation imageLocation = imageLocationMapper.toEntity(imageLocationRequest);
        imageLocation.setId(null);
        return imageLocationMapper.toResponse(iImageLocationRepository.save(imageLocation));
    }

    @Override
    public ImageLocationResponse delete(Long aLong) {
        Optional<ImageLocation> imageLocation = iImageLocationRepository.findById(aLong);
        if (imageLocation.isPresent()) {
            iImageLocationRepository.deleteById(aLong);
            return imageLocationMapper.toResponse(imageLocation.get());
        }
        return null;
    }
}
