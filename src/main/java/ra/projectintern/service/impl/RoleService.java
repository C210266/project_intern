package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.model.domain.Role;
import ra.projectintern.model.domain.RoleName;
import ra.projectintern.repository.IRoleRepository;
import ra.projectintern.service.IRoleService;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
