package com.zalisoft.teamapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zalisoft.teamapi.enums.UserType;

import com.zalisoft.teamapi.enums.WorkType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
public class User  extends AbstractModel{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 150)
    private String firstName;

    @Column(name = "last_name", length = 150)
    private String lastName;


    @JsonIgnore
    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    @Column(name = "phone", length = 20, nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "title", length = 20, nullable = false)
    private WorkType title;

    @Column(name = "image_url", length = 20, nullable = false)
    private  String image;

    @Column(name = "experience", length = 20, nullable = false)
    private int experience;
    @Column(name = "tc", length = 11, nullable = false, unique = true)
    private String tc;

    @Column(name = "is_active", columnDefinition = "boolean default false")
    private Boolean active = Boolean.FALSE;

    @Embedded
    private  Address address;


    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Transient
    private Set<String> privileges;

    public Set<String> getPrivileges() {
        if (privileges == null) {
            privileges = new HashSet<>();
            if (!CollectionUtils.isEmpty(getRoles())) {
                getRoles().stream().forEach(role -> {
                    role.getPrivileges().forEach(privilege -> privileges.add(privilege.getName()));
                });
            }
        }
        return privileges;
    }

    public String getFullName() {
        if (lastName == null) {
            return firstName;
        } else {
            return firstName + " " + lastName;
        }
    }


}
