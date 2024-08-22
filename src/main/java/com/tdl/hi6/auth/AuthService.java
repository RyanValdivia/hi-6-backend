package com.tdl.hi6.auth;

import com.tdl.hi6.jwt.JwtService;
import com.tdl.hi6.models.user.Role;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Transactional (rollbackOn = Exception.class)
@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login (LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + request.getEmail() + " not found"));
        String token = jwtService.getToken(user);
        log.info("Log In user with email {}", request.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register (RegisterRequest request) {
        log.info("Registering user with email {}", request);
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .names(request.getNames())
                .surnames(request.getSurnames())
                .description(request.getDescription())
                .role(Role.USER)
                .imageURL(request.getImageURL())
                .build();

        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
