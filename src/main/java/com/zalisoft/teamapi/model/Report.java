package com.zalisoft.teamapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "report")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SQLDelete(sql = "UPDATE report SET deleted=true WHERE id=?")
public class Report extends AbstractModel{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hours", nullable = false)
    private int hours;

    @Column(name = "minutes")
    private int minutes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "report_project", joinColumns = @JoinColumn(name = "report_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> project;

    @Column(name = "personal_learning", nullable = false)
    private String personLearning;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "is_completed",insertable = false)
    private boolean isCompleted;

    @ManyToOne
    private User user;

    @ManyToOne
    private Team team;



}
