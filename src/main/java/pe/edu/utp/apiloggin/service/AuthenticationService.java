package pe.edu.utp.apiloggin.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.apiloggin.dto.LoginResponseDTO;
import pe.edu.utp.apiloggin.dto.RegisterResponseDTO;
import pe.edu.utp.apiloggin.entity.Role;
import pe.edu.utp.apiloggin.entity.User;
import pe.edu.utp.apiloggin.repository.RoleRepository;
import pe.edu.utp.apiloggin.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    public RegisterResponseDTO registerUser(String username, String password, String email) {
        RegisterResponseDTO response = new RegisterResponseDTO(true);

        if (userRepository.findByUsername(username).isPresent()) {
            response.setStatus(false);
            return response;
        }

        String encodedPassword = passwordEncoder.encode(password);

        // Obtener el primer elemento del conjunto de roles
        Role userRole = roleRepository.findByAuthority("USER").stream()
                .findFirst()
                .orElse(null);

        if (userRole == null) {
            // Manejar el caso en que el conjunto de roles esté vacío
            response.setStatus(false);
            return response;
        }

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User newUser = new User(0, username, encodedPassword, authorities);
        Set<User> users = new HashSet<>();

        users.add(newUser);

        userRepository.save(newUser);

        return response;
    }
    public LoginResponseDTO loginUser(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        } catch (BadCredentialsException e) {
            return new LoginResponseDTO(null, "");
        }
    }
}