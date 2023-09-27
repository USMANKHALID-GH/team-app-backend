package com.zalisoft.teamapi.repository;


import com.zalisoft.teamapi.model.Team;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {


    @Query("select t from Team t " +
            " where '' = :searchText or :searchText is null " +
            "       or lower(t.name) like lower(concat('%', :searchText,'%')) " +
            "       or lower(t.captain.lastName) like lower(concat('%', :searchText,'%'))" +
            "       or lower(t.captain.firstName) like lower(concat('%', :searchText,'%'))" )
    Page<Team> search(String searchText, Pageable pageable);


    @Query("select t.members from Team as t  where  t.captain.tc=:tc")
    Optional<List<User>> findMembersByCaptainTc(String  tc);

    boolean existsByMembersContains(User user);

    



}
