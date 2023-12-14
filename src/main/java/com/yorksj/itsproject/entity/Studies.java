package com.yorksj.itsproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_studies")
public class Studies {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonIgnore
    @JoinColumn(name = "student_id")
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "subject_id")
    @ManyToOne(optional = false)
    private Subjects subjects;

    @Column(name = "completed")
    private boolean completed = Boolean.FALSE;

    @Column(name = "start_subject")
    private boolean startSubject = Boolean.FALSE;

    @Column(name = "complete_quiz")
    private boolean completeQuiz = Boolean.FALSE;

    @Column(name = "enrolled_on", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp enrolledOn;
}
