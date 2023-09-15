package com.zalisoft.teamapi.model;

import com.zalisoft.teamapi.enums.TaskStatus;
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
@Table(name = "task")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SQLDelete(sql = "UPDATE task SET deleted=true WHERE id=?")
public class Task extends AbstractModel{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Column(name ="name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private LocalDate beginning;
    @Column(name = "deadline")
    private LocalDate deadline;


}
