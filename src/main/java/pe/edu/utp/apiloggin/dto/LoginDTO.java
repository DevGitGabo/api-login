package pe.edu.utp.apiloggin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDTO {
    private String username;
    private String password;

    public LoginDTO() {
        super();
    }

    public LoginDTO(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
}