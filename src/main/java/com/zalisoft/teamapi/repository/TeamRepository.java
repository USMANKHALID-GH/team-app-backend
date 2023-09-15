package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface TeamRepository extends JpaRepository<Team,Long> {
}
