package com.example.jwt.config.base;

import com.example.jwt.utils.UIDGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    private String UID;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public abstract String getEntityType();

    @PrePersist
    public void prePersist() {
        this.UID = UIDGenerator.generateUID();
    }

}
