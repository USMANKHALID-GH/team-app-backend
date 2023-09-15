package com.zalisoft.teamapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

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
    @ManyToOne()
    private Project project;
    @Column(name = "personal_learning", nullable = false)
    private String personLeanrning;
    @Column(name = "details", nullable = false)
    private String details;
    @Column(name = "is_completed",insertable = false)
    private boolean isCompleted;



}
