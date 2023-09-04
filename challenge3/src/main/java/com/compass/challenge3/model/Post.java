package com.compass.challenge3.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Data
@Entity
@Table (name = "post", schema = "blog")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Post {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "create_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate createDate;

    @Column(name = "reprocessed")
    private boolean reprocessed;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<History> history = new ArrayList<>();
}