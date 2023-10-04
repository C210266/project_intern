package ra.projectintern.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.dto.response.JwtResponse;
import ra.projectintern.security.user_principle.UserDetailService;
import ra.projectintern.security.user_principle.UserPrinciple;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    @Autowired
    private UserDetailService userDetailService;
    public final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
    @Value("${jwt.secret-key}")
    private String SECRET;
    @Value("${jwt.expirated}")
    private Long EXPIRED;
    @Value("${jwt.refresh_token_expirated}")
    private Long REFRESHEXPIRED;

    //    Lam viec voi refresh token
    public String generateRefreshToken(UserPrinciple userPrinciple) {
        return Jwts.builder()
                .setSubject(userPrinciple.getUsername()) // set chủ đề
                .setIssuedAt(new Date()) // Thời gian bắt đầu
                .setExpiration(new Date(new Date().getTime() + REFRESHEXPIRED))// thời gian kết thúc
                // custom access key vs reset key
                .signWith(SignatureAlgorithm.HS512, SECRET) // chuwx kí và thuật toán mã hóa , chuỗi bí mật
                .compact();
    }

    public JwtResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        try {
            String refresh_token = getTokenFromRequest(request);
            if (refresh_token != null && validateToken(refresh_token)) {
                // lấy ra đối tượng userdetail thông qua userdetailservice và refresh_token
                String username = getUserNameFromToken(refresh_token);

                UserPrinciple userPrinciple = (UserPrinciple) userDetailService.loadUserByUsername(username);

//            Kiem tra refresh token con valid hay ko 
//            Tao moi access_token
                if (isTokenValid(refresh_token, userPrinciple) && !isTokenExpired(refresh_token)) {
                    String access_token = generateToken(userPrinciple);

                    List<String> roles = userPrinciple.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

                    JwtResponse jwtResponse = JwtResponse.builder()
                            .access_token(access_token)
                            .refresh_token(refresh_token)
                            .fullname(userPrinciple.getFullName())
                            .username(userPrinciple.getUsername())
                            .phoneNumber(userPrinciple.getPhoneNumber())
                            .email(userPrinciple.getEmail())
                            .roles(roles)
                            .type("Bearer")
                            .status(userPrinciple.isStatus())
                            .build();
                    return jwtResponse;
                }
            }
        } catch (Exception e) {
            logger.error("Un  authentication ->>>", e.getMessage());
        }
        throw new CustomException("Cant generate new access token");
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername());
    }

    public boolean isTokenExpired(String token) throws CustomException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();

            return expirationDate.before(currentDate);
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

//    End voi Refresh Token

    //    Lam viec voi Access Token
    public String generateToken(UserPrinciple userPrinciple) {
        return Jwts.builder()
                .setSubject(userPrinciple.getUsername()) // set chủ đề
                .setIssuedAt(new Date()) // Thời gian bắt đầu
                .setExpiration(new Date(new Date().getTime() + EXPIRED))// thời gian kết thúc
                // custom access key vs reset key
                .signWith(SignatureAlgorithm.HS512, SECRET) // chuwx kí và thuật toán mã hóa , chuỗi bí mật
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token); //Lưu ý chính tả
            return true;

        } catch (ExpiredJwtException e) {
            logger.error("Failed -> Expired Token Message {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Failed -> Unsupported Token Message {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Failed -> Invalid Format Token Message {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Failed -> Invalid Signature Token Message {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Failed -> Claims Empty Token Message {}", e.getMessage());
        }
        return false;
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token).getBody().getSubject(); // lưu ý chính tả
    }

}
