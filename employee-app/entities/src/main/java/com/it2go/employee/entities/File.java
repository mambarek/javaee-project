package com.it2go.employee.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "FILE")
public class File extends DomainEntity {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Employee owner;

    @Basic
    @Column(name = "NAME", nullable = false)
    @Size(min = 3, max = 100)
    @NotNull
    private String name;

    @Basic
    @Column(name = "TYPE", nullable = false)
    @NotNull
    private String contentType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "CONTENT", columnDefinition="BLOB NOT NULL")
    @NotNull
    private byte[] content;
}
