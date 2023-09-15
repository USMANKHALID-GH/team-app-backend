package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Privilege;
import com.zalisoft.teamapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    boolean existsByNameIgnoreCase(String name);

    @Query("select r.privileges from  Role  as r where  r.id=?1")
     List<Privilege>   findPrivilegesByRoleId(long id);
}
