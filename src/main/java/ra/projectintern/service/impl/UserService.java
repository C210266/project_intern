package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Role;
import ra.projectintern.model.domain.RoleName;
import ra.projectintern.model.domain.Users;
import ra.projectintern.model.dto.request.FormSignUpDto;
import ra.projectintern.repository.IUserRepository;
import ra.projectintern.service.IRoleService;
import ra.projectintern.service.IUserService;

import javax.persistence.EntityExistsException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users save(FormSignUpDto form) {
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new EntityExistsException("Username is exist");
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new EntityExistsException("Email is exist");
        }
        Set<Role> roles = new HashSet<>();
        if (form.getRoles() == null || form.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        } else {
            form.getRoles().stream().forEach(
                    role -> {
                        switch (role) {
                            case "admin":
                                roles.add(roleService.findByRoleName(RoleName.ROlE_ADMIN));
                            case "user":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                        }
                    }
            );
        }
        Users users = Users.builder()
                .email(form.getEmail())
                .username(form.getUsername())
                .fullName(form.getFullName())
                .phoneNumber(form.getPhoneNumber())
                .password(passwordEncoder.encode(form.getPassword()))
                .roles(roles)
                .status(true)
                .build();
        return userRepository.save(users);
    }

    @Override
    public void changeStatus(Long userId) throws CustomException {

    }
}
