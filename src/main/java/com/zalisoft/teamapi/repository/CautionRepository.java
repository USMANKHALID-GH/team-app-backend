package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Caution;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CautionRepository extends JpaRepository<Caution,Long> {


    @Query(value = "SELECT cu.user_id " +
            " FROM caution_user cu " +
            " JOIN caution c ON cu.caution_id = c.id " +
            " GROUP BY cu.user_id  " +
            " HAVING COUNT(c.id) >= :n", nativeQuery = true)
    List<Long>  findUsersWhoExceedCautionLimit(@Param("n") long n);


    @Query("SELECT cu " +
            "  FROM Team  cu " +
            "  JOIN Caution c " +
            "  GROUP BY cu.members " +
            "  HAVING COUNT(c) > :n")
    List<User> countUsersWithMoreThan3Caution(long n);


    @Query(value = "select c.* from caution as c inner join " +
            "   caution_user  as cs on c.id=cs.caution_id where cs.user_id=:id " ,nativeQuery = true)
   List<Caution> findByCurrentUser(long id);


    @Transactional
    @Modifying
    @Query(value= "UPDATE caution AS c SET deleted = true FROM " +
            "    caution_user AS cs WHERE c.id = cs.caution_id AND cs.user_id IN :id", nativeQuery = true)
    void deleteByUserId(List<Long> id);






}
