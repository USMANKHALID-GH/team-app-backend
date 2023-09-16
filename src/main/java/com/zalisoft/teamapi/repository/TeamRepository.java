package com.zalisoft.teamapi.repository;


import com.zalisoft.teamapi.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {


    @Query("select t from Team t " +
            " where '' = :searchText or :searchText is null " +
            "       or lower(t.name) like lower(concat('%', :searchText,'%')) " +
            "       or lower(t.captain.lastName) like lower(concat('%', :searchText,'%'))" +
            "       or lower(t.captain.firstName) like lower(concat('%', :searchText,'%'))" )
    Page<Team> search(String searchText, Pageable pageable);

}
