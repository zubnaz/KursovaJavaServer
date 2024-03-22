package org.example.DTO.Category;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryCreateDto {
    private String name;
    private String description;
    private MultipartFile image;
}
