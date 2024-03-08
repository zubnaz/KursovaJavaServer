package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="post_table")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 5000,nullable = false)
    private String short_description;

    @Column(length = 5000,nullable = false)
    private String description;

    @Column(length = 1000, nullable = false)
    private String meta;

    @Column(length = 200, nullable = false)
    private String urlSlug;

    @Column(nullable = false)
    private boolean published;

    @Column(name="posted_on",nullable = false)
    private LocalDateTime postedOn;

    @Column(name="modified")
    private LocalDateTime modified;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("posts")
    private CategoryEntity category;

    @OneToMany(mappedBy="post")
    @JsonIgnoreProperties("post")
    private List<PostTagEntity> postTags;
}
