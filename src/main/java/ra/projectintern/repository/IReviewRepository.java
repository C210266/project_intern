package ra.projectintern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.projectintern.model.domain.Location;
import ra.projectintern.model.domain.Review;

import java.util.List;
import java.util.Optional;
@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByLocation(Location location);
}
