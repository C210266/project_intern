package ra.projectintern.security.user_principle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.projectintern.model.domain.Users;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private Long id;

    private String username;

    private String fullName;
    private String email;
    private String phoneNumber;

    @JsonIgnore
    private String password;
    private boolean status;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrinciple build(Users user) {
        List<GrantedAuthority> list = user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getRoleName().name())
        ).collect(Collectors.toList());
        return UserPrinciple.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .status(user.isStatus())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .authorities(list).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
