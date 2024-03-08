package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "post_tag_table")
@IdClass(PostTagPK.class)
public class PostTagEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    @JsonIgnoreProperties("postTags")
    private PostEntity post;

    @Id
    @ManyToOne
    @JoinColumn(name="tag_id",nullable = false)
    @JsonIgnoreProperties("postTags")
    private TagEntity tag;
}
