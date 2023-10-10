package ra.projectintern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.projectintern.model.domain.Host;
@Repository
public interface IHostRepository extends JpaRepository<Host,Long> {
}
