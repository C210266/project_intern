package ra.projectintern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.projectintern.model.domain.Room;
@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
}
