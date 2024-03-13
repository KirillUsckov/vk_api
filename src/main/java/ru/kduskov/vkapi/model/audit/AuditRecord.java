package ru.kduskov.vkapi.model.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "audit")
@AllArgsConstructor
@NoArgsConstructor
public class AuditRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime time;
    private String request;
    private String who;
    private boolean hasAccess;
    private String body;

    public AuditRecord(LocalDateTime time, String request, String who, boolean hasAccess, String body) {
        this.time = time;
        this.request = request;
        this.who = who;
        this.hasAccess = hasAccess;
        this.body = body;
    }
}
