package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Role;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User>  findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String telefon);

    List<User>  findUserByRoles(Role role);
}
