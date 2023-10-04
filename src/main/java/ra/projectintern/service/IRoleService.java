package ra.projectintern.service;

import ra.projectintern.model.domain.Role;
import ra.projectintern.model.domain.RoleName;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
