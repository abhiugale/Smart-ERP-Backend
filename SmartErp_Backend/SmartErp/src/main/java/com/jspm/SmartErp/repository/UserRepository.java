package com.jspm.SmartErp.repository;

import com.jspm.SmartErp.model.User;
import com.jspm.SmartErp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}