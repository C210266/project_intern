package ra.projectintern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.projectintern.model.domain.ImageLocation;
import ra.projectintern.model.domain.Location;

import java.util.List;
@Repository
public interface IImageLocationRepository extends JpaRepository<ImageLocation, Long> {
    List<ImageLocation> findAllByLocation(Location location);
}
