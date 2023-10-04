package ra.projectintern.service;

import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Users;
import ra.projectintern.model.dto.request.FormSignUpDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> findAll();

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    Users save(FormSignUpDto form);

    void changeStatus(Long userId) throws CustomException;
}
