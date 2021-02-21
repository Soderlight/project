package com.interview.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "Requests")
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime requestDate;
    private String description;

    public Request(UUID id, LocalDateTime requestDate, String description) {
        this.id = id;
        this.requestDate = requestDate;
        this.description = description;
    }
}
