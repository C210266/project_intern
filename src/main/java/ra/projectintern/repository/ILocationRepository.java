package ra.projectintern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.projectintern.model.domain.Location;
@Repository
public interface ILocationRepository extends JpaRepository<Location,Long> {
}
