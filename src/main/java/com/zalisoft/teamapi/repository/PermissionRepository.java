package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Permission;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission ,Long> {

    @Query("from Permission  as p  where  p.approvedBy.id=:id and p.isApproved<>true ")
  List<Permission>  findByCaptain(long id);

    @Query("from Permission  where issuedBy in (select members from Team   where captain.id=:id)")
    List<Permission> findPermissionByCaptain1(long id);

    @Query("SELECT p FROM Permission p WHERE p.issuedBy IN (SELECT m FROM Team t JOIN t.members m WHERE t.captain.id = :id)")
    List<Permission> findPermissionByCaptain(@Param("id") long id);


    @Query(value = "select p from permission  p " +
                   " where p.issued_by = (select tm.user_id from team  t inner join" +
                   "                        team_members  tm on t.id=tm.team_id " +
                  "                          where tm.user_id=:userId and t.captain_id=:captainId)" +
                   " and p.id=:id   ",nativeQuery = true)
    Optional<Permission>   checkToApprovePermission(long captainId, long userId, long id);

    @Query("from Permission  p  where p.approvedBy.id=:captainId AND p.id=:id")
    Optional<Permission>   checkToRejectPermission(long id, long captainId);

 @Query(value = "SELECT p.issued_by FROM permission  p WHERE p.starting >=:date AND p.expiry_date <=:date", nativeQuery = true)
    List<User>  findUserBetweenDeadlineAndStartingDate(LocalDate date);

}
