package com.zalisoft.teamapi.model;

import com.zalisoft.teamapi.enums.ProjectStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "project")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SQLDelete(sql = "UPDATE project SET deleted=true WHERE id=?")
public class Project extends AbstractModel{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "starting", nullable = false)
    private LocalDate starting;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "deadline",nullable = false)
    private LocalDate deadline;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToOne(optional = false)
    @JoinColumn(name = "project_manager_id",nullable = false)
    private User projectManager;

    @Column(name = "budget")
    private Double budget;


}
