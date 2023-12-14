package com.yorksj.itsproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @JoinColumn(name = "subject_id")
    @OneToOne(optional = false)
    private Subjects subjects;

    @JoinColumn(name = "student_id")
    @ManyToOne(optional = false)
    private User user;

    @Column(name = "result")
    private String result = "pending";

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
