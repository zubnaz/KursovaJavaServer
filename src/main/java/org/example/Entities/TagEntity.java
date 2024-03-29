package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="tag_table")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String urlSlug;

    @Column(length = 200,nullable = true)
    private String description;

    @OneToMany(mappedBy="tag")
    @JsonIgnoreProperties("tag")
    private List<PostTagEntity> postTags;
}
