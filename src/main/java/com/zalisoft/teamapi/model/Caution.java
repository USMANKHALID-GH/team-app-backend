package com.zalisoft.teamapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "caution")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited(targetAuditMode = NOT_AUDITED)
@SQLDelete(sql = "UPDATE caution SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Caution extends AbstractModel{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(insertable = false,updatable = false,name = "issued_by")
    private User issuedBy;

    @Column(name = "issued_by")
    private Long issuedUserId;

    @ManyToMany
    @JoinTable(name = "caution_user",joinColumns = @JoinColumn(name = "caution_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user;
}
