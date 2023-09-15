package com.zalisoft.teamapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "team")
@SQLDelete(sql = "UPDATE team SET deleted=true WHERE id=?")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Team extends AbstractModel{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image" ,unique = true)
    private String image;

    @Column(name = "name", unique = true)
    private String name;

    @OneToOne(optional = false )
    private User captain;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;
}
