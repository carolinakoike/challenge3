package com.compass.challenge3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history", schema = "blog")
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    private Long id;

    private LocalDateTime processedDate;

    @Enumerated(value = EnumType.STRING)
    private PostStatus state;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", processedDate=" + processedDate +
                ", state=" + state +
                ", post=" + post.getId() +
                '}';
    }

    public void setProcessDate(LocalDate now) {

    }

    public void setStatus(PostStatus postStatus) {

    }

    public PostStatus getStatus() {
        return null;
    }
}