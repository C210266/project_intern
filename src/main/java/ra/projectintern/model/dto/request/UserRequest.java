package ra.projectintern.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.projectintern.model.domain.Role;
import ra.projectintern.security.validate.NoNullOrEmpty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NoNullOrEmpty(message = "FullName cannot be null or empty")
    @Size(min = 6, message = "FullName minimum 6 characters")
    private String fullName;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email invalidate")
    private String email;

    @NoNullOrEmpty(message = "Username cannot be null or empty")
    @Size(min = 6, message = "Username minimum 6 characters")
    private String username;

    @NoNullOrEmpty(message = "Password cannot be null or empty")
    @Size(min = 6, message = "Password minimum 6 characters")
    private String password;

    @NoNullOrEmpty(message = "Phone number cannot be null or empty")
    @Size(min = 6,max = 10, message = "Phone number exactly 10 characters")
    private String phoneNumber;

    private boolean status;

    private Set<Role> roles = new HashSet<>();
}

