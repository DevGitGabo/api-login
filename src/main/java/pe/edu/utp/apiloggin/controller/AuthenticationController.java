package pe.edu.utp.apiloggin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.edu.utp.apiloggin.dto.LoginDTO;
import pe.edu.utp.apiloggin.dto.LoginResponseDTO;
import pe.edu.utp.apiloggin.dto.RegisterDTO;
import pe.edu.utp.apiloggin.dto.RegisterResponseDTO;
import pe.edu.utp.apiloggin.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public RegisterResponseDTO registerUser(@RequestBody RegisterDTO body) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getGmail());
    }
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}