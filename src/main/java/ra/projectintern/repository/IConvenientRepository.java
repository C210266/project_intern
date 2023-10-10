package ra.projectintern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.projectintern.model.domain.Convenient;

import java.util.List;
@Repository
public interface IConvenientRepository extends JpaRepository<Convenient,Long> {

//    List<Long> findAllBy
}
