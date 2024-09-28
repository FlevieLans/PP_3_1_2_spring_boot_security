package application.pp_3_1_2_spring_boot_security.repository;

import application.pp_3_1_2_spring_boot_security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> { }
