package ra.projectintern.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String access_token;
    private String refresh_token;
    private String type = "Bearer";
    private String fullname;
    private String username;
    private String email;
    private String phoneNumber;
    private boolean status;

    private List<String> roles = new ArrayList<>();
}