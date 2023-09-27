package com.zalisoft.teamapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited(targetAuditMode = NOT_AUDITED)
@SQLDelete(sql = "UPDATE permission SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Permission extends AbstractModel{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "is_approved")
    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @ManyToOne
    @JoinColumn(name = "issued_by")
    private User issuedBy;

    @Column(name = "starting", nullable = false)
    private LocalDate starting;

    @Column(name = "expiry_date" , nullable = false)
    private LocalDate expiryDate;
}
