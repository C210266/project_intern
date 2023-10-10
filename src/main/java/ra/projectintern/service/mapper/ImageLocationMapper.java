package ra.projectintern.service.mapper;

import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.ImageLocation;
import ra.projectintern.model.dto.request.ImageLocationRequest;
import ra.projectintern.model.dto.response.ImageLocationResponse;
import ra.projectintern.service.IGenericMapper;

@Component
public class ImageLocationMapper implements IGenericMapper<ImageLocation, ImageLocationRequest, ImageLocationResponse> {
    @Override
    public ImageLocation toEntity(ImageLocationRequest imageLocationRequest) {
        return ImageLocation.builder()
                .image(imageLocationRequest.getImage())
                .location(imageLocationRequest.getLocation())
                .build();
    }

    @Override
    public ImageLocationResponse toResponse(ImageLocation imageLocation)  {
        return ImageLocationResponse.builder()
                .id(imageLocation.getId())
                .location_name(imageLocation.getLocation().getName())
                .image(imageLocation.getImage())
                .build();
    }
}
