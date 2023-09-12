package com.example.chatpetproject.dal.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message extends AuditableEntity<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

}
