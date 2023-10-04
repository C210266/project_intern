package ra.projectintern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.request.FormSignInDto;
import ra.projectintern.model.dto.request.FormSignUpDto;
import ra.projectintern.model.dto.response.JwtResponse;
import ra.projectintern.security.jwt.JwtEntryPoint;
import ra.projectintern.security.jwt.JwtProvider;
import ra.projectintern.security.user_principle.UserPrinciple;
import ra.projectintern.service.IUserService;
import ra.projectintern.service.impl.MailService;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private MailService mailService;
    @Autowired
    private IUserService userService;

    @PostMapping("/public/sign-in")
    public ResponseEntity<JwtResponse> sign_in(@RequestBody FormSignInDto formSignInDto, HttpSession session) throws CustomException {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            formSignInDto.getUsername(), formSignInDto.getPassword())
            ); // tạo đối tượng authentication để xác thực thông qua username và password

            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            if (!userPrinciple.isStatus()) {
                // Nếu trạng thái là false, không cho phép đăng nhập
                throw new CustomException("User is block");
            }

            // Tạo token và trả về cho người dùng
            String access_token = jwtProvider.generateToken(userPrinciple);
            String refresh_token = jwtProvider.generateRefreshToken(userPrinciple);

            List<String> roles = userPrinciple.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            session.setAttribute("CurrentUser", formSignInDto.getUsername());

            return ResponseEntity.ok(JwtResponse.builder()
                    .access_token(access_token)
                    .refresh_token(refresh_token)
                    .fullname(userPrinciple.getFullName())
                    .username(userPrinciple.getUsername())
                    .phoneNumber(userPrinciple.getPhoneNumber())
                    .email(userPrinciple.getEmail())
                    .roles(roles)
                    .type("Bearer")
                    .status(userPrinciple.isStatus()).build());
        } catch (AuthenticationException | CustomException e) {
            throw new CustomException("Username or password is incorrect");
        }
    }

    @PostMapping("/public/sign-up")
    public ResponseEntity<String> sign_up(@RequestBody @Valid FormSignUpDto formSignUpDto) {
        mailService.sendMail(formSignUpDto.getEmail(), "Welcome", " Register successfully");
        userService.save(formSignUpDto);
        return new ResponseEntity<>("Congratulations register successfully", HttpStatus.CREATED);
    }

    @PostMapping("/public/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        jwtProvider.refreshToken(request, response);
    }
}
