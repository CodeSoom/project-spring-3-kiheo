package project.spring.kiheo.application;

import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.spring.kiheo.domain.User;
import project.spring.kiheo.domain.UserRepository;
import project.spring.kiheo.errors.LoginFailException;
import project.spring.kiheo.utils.JwtUtil;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository,
                                 JwtUtil jwtUtil,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String authentication(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new LoginFailException(email));

        if (!user.authenticate(password, passwordEncoder)) {
            throw new LoginFailException(email);
        }

        return jwtUtil.encode(email);
    }

    public String parseToken(String accessToken) {
        Claims claims = jwtUtil.decode(accessToken);
        return claims.get("email", String.class);
    }
}
