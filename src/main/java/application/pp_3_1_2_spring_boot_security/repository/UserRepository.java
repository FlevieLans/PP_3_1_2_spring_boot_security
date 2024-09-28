package application.pp_3_1_2_spring_boot_security.repository;

import application.pp_3_1_2_spring_boot_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
