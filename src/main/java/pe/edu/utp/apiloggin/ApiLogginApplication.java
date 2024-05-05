package pe.edu.utp.apiloggin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.utp.apiloggin.entity.Role;
import pe.edu.utp.apiloggin.entity.User;
import pe.edu.utp.apiloggin.repository.RoleRepository;
import pe.edu.utp.apiloggin.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ApiLogginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLogginApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        return args -> {
            // Admin role
            if (!roleRepository.findByAuthority("ADMIN").isEmpty())
                return;

            Role adminRole = roleRepository.save(new Role("ADMIN"));

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            User admin = new User(1, "admin", passwordEncoder.encode("password"), adminRoles);
            userRepository.save(admin);

        };
    }

}
