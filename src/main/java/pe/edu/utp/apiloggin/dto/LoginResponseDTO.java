package pe.edu.utp.apiloggin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import pe.edu.utp.apiloggin.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LoginResponseDTO {
    private UserDTO user;
    private String token;
    public LoginResponseDTO(User applicationUser, String token) {
        this.user = new UserDTO(applicationUser.getUserId(), applicationUser.getUsername(), getRoles(applicationUser));
        this.token = token;
    }
    private List<String> getRoles(User applicationUser) {
        return applicationUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)  // Access the authority directly
                .collect(Collectors.toList());
    }
    @Getter
    @Setter
    private static class UserDTO {

        private int id;
        private String username;
        private List<String> role;

        public UserDTO(int id, String username, List<String> roles) {
            this.id = id;
            this.username = username;
            this.role = roles;
        }
    }
}