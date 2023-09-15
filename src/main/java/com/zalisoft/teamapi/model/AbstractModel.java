package com.zalisoft.teamapi.model;

import com.sun.istack.NotNull;
import com.zalisoft.teamapi.util.LogUtil;
import com.zalisoft.teamapi.util.SecurityUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@MappedSuperclass

public class AbstractModel implements Serializable {


    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private LocalDateTime lastModifiedDate;

    @NotNull
    @JsonIgnore
    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;

    @PrePersist
    private void prePersist() {
        createdDate = LocalDateTime.now();
        createdBy = LogUtil.isBatchProcess() ? LogUtil.batchName() : SecurityUtils.getCurrentUsername();
    }

    @PreUpdate
    private void preUpdate() {
        lastModifiedDate = LocalDateTime.now();
        lastModifiedBy = LogUtil.isBatchProcess() ? LogUtil.batchName() : SecurityUtils.getCurrentUsername();
    }
}
