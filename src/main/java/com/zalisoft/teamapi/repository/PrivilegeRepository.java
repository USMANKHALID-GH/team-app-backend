package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Privilege> findByNameIgnoreCase(String name);
 }
