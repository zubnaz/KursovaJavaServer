package org.example.DTO.Category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryItemDto {
    private int id;
    private String name;
    private String description;
    private String image;
}
