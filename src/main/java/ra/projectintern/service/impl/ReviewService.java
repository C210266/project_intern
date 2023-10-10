package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Location;
import ra.projectintern.model.domain.Review;
import ra.projectintern.model.domain.Room;
import ra.projectintern.model.dto.request.ReviewRequest;
import ra.projectintern.model.dto.response.ReviewResponse;
import ra.projectintern.repository.ILocationRepository;
import ra.projectintern.repository.IReviewRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.ReviewMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IGenericService<ReviewResponse, ReviewRequest, Long> {
    @Autowired
    private IReviewRepository reviewRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ILocationRepository locationRepository;

    @Override
    public List<ReviewResponse> findAll() {
        return reviewRepository.findAll().stream()
                .map(p -> reviewMapper.toResponse(p)).collect(Collectors.toList());
    }

    @Override
    public ReviewResponse findById(Long id) throws CustomException {
        return reviewRepository.findById(id)
                .map(host -> reviewMapper.toResponse(host))
                .orElseThrow(() -> new CustomException("Host not found"));
    }

    @Override
    public ReviewResponse save(ReviewRequest reviewRequest) throws CustomException {
        return reviewMapper.toResponse(reviewRepository.save(reviewMapper.toEntity(reviewRequest)));
    }

    @Override
    public ReviewResponse update(ReviewRequest reviewRequest, Long id) throws CustomException {
        Review review = reviewMapper.toEntity(reviewRequest);
        review.setId(null);
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse delete(Long id) throws CustomException {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            reviewRepository.deleteById(id);
            return reviewMapper.toResponse(review.get());
        }
        return null;
    }

    public List<ReviewResponse> findByLocationId(Long locaId) throws CustomException {
        Location location = locationRepository.findById(locaId)
                .orElseThrow(() -> new CustomException("Location not found"));
        List<Review> review = reviewRepository.findAllByLocation(location);
        return review.stream()
                .map(review1 -> reviewMapper.toResponse(review1))
                .collect(Collectors.toList());
    }
}
