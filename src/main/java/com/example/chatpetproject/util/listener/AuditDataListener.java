package com.example.chatpetproject.util.listener;



import com.example.chatpetproject.dal.entity.AuditableEntity;

import javax.persistence.PrePersist;
import java.time.Instant;

public class AuditDataListener {

    @PrePersist
    public void prePersist(AuditableEntity<?> entity) {
        entity.setCreatedAt(Instant.now());
    }
}
