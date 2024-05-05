package pe.edu.utp.apiloggin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterResponseDTO {
    private boolean status;
    public RegisterResponseDTO(boolean status) {
        this.status = status;
    }
}