package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter,Long> {

    Optional<Parameter>  findByKey(String key);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM parameter WHERE key=:key)",nativeQuery = true)
    boolean keyExist(String key);
}
