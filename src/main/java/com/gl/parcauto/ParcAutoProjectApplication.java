package com.gl.parcauto;

import com.gl.parcauto.dto.ROLES;
import com.gl.parcauto.entity.Role;
import com.gl.parcauto.entity.User;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.repository.RoleRepository;
import com.gl.parcauto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ParcAutoProjectApplication implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(ParcAutoProjectApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        addRoles();
        addUsers();
    }
    public void addRoles() {
        if(!roleRepository.findAll().isEmpty())
            return;

        for(ROLES roleEnum : ROLES.values()) {
            Role role = new Role();
            role.setName(roleEnum.name());
            roleRepository.save(role);
        }
    }

    public void addUsers() {
        if(!userRepository.findAll().isEmpty())
            return;

        // Get role admin
        Role roleAdmin = roleRepository.findByName(ROLES.ROLE_ADMIN.name()).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", ROLES.ROLE_ADMIN.name())
        );

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole(roleAdmin);

        userRepository.save(user);
    }

}
