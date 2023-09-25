package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Role;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User>  findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String telefon);

    List<User>  findUserByRoles(Role role);

    @Query("SELECT u FROM DailyReport r " +
            "JOIN r.user u " +
            "JOIN Team t ON t.captain.id = u.id " +
            "WHERE r.deleted = false AND t.captain.tc = :tc")
    List<User> findUserUnsentReportByCaptainTc(String tc);

    Optional<User>  findByTc(String tc);

    @Query("FROM User r inner  join  Team  t on r.id=t.captain.id where t.captain.id=:id")
    Optional<User>  checkIfCaptain(long id);
 }
