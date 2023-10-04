package ra.projectintern.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.projectintern.security.validate.ValidEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormSignUpDto {

    @NotBlank(message = "Name cannot be blank")
    @NotEmpty(message = "Name cannot be empty!!!")
    private String fullName;
    @ValidEmail
    private String email;

    @NotBlank(message = "Username cannot be blank")
    @NotEmpty(message = "Username cannot be empty!!!")
    @Size(min = 5, message = "Username must be at least 5 characters")
    private String username;

    @NotBlank(message = "Password name cannot be blank")
    @NotEmpty(message = "Password name cannot be empty!!!")
    @Size(min = 6, message = "Password name must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    private List<String> roles;
}
