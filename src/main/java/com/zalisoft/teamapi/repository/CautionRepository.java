package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Caution;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CautionRepository extends JpaRepository<Caution,Long> {


//    @Query("SELECT c.user FROM Caution c JOIN c.user u GROUP BY u HAVING COUNT(c) > :number")
//    List<User> countUsersWithMoreThanTheGivenOccurrences(long number);

    @Query("SELECT u FROM Caution c JOIN c.user u GROUP BY u HAVING COUNT(c) > :number")
    List<User> countUsersWithMoreThan3Caution(long number);




    @Query(value = "select c.* from caution as c inner join  caution_user  as cs on c.id=cs.caution_id where cs.user_id=:id " ,nativeQuery = true)
   List<Caution> findByCurrentUser(long id);


    @Transactional
    @Modifying
    @Query(value= "UPDATE caution AS c SET deleted = true FROM caution_user AS cs WHERE c.id = cs.caution_id AND cs.user_id IN :id", nativeQuery = true)
    void deleteByUserId(List<Long> id);






}
