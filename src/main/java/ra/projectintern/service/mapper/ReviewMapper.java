package ra.projectintern.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Location;
import ra.projectintern.model.domain.Review;
import ra.projectintern.model.domain.Users;
import ra.projectintern.model.dto.request.ReviewRequest;
import ra.projectintern.model.dto.response.ReviewResponse;
import ra.projectintern.repository.ILocationRepository;
import ra.projectintern.repository.IUserRepository;
import ra.projectintern.service.IGenericMapper;

@Component
public class ReviewMapper implements IGenericMapper<Review, ReviewRequest, ReviewResponse> {
    @Autowired
    private ILocationRepository locationRepository;
    @Autowired
    private IUserRepository userRepository;


    @Override
    public Review toEntity(ReviewRequest reviewRequest) throws CustomException {

        Location location = locationRepository.findById(reviewRequest.getLocation_id())
                .orElseThrow(() -> new CustomException("Location not found"));
        Users users = userRepository.findById(reviewRequest.getUser_id())
                .orElseThrow(() -> new CustomException("Users not found"));

        return Review.builder()
                .location(location)
                .description(reviewRequest.getDescription())
                .users(users)
                .star(reviewRequest.getStar())
                .build();
    }

    @Override
    public ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .description(review.getDescription())
                .location_name(review.getLocation().getName())
                .users_name(review.getUsers().getUsername())
                .star(review.getStar())
                .build();
    }
}
