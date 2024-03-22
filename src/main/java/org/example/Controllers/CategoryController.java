package org.example.Controllers;

import org.example.DTO.Category.CategoryCreateDto;
import org.example.DTO.Category.CategoryEditDto;
import org.example.DTO.Category.CategoryItemDto;
import org.example.Entities.CategoryEntity;
import org.example.Entities.PostEntity;
import org.example.Entities.PostTagEntity;
import org.example.Entities.TagEntity;
import org.example.Mapper.CategoryMapper;
import org.example.Repositories.CategoryRepository;
import org.example.Repositories.PostRepository;
import org.example.Repositories.PostTagRepository;
import org.example.Repositories.TagRepository;
import org.example.Services.InitializerService;
import org.example.Storage.FileSaveFormat;
import org.example.Storage.FileSystemStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final FileSystemStorageService fileSystemStorageService;
    private final InitializerService initializerService;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    public CategoryController(CategoryRepository categoryRepository,
                              CategoryMapper mapper,
                              InitializerService initializerService,
                              FileSystemStorageService fileSystemStorageService,
                              PostRepository postRepository,
                              TagRepository tagRepository,
                              PostTagRepository postTagRepository){
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.initializerService = initializerService;
        this.fileSystemStorageService = fileSystemStorageService;
        this.postRepository =postRepository;
        this.tagRepository =tagRepository;
        this.postTagRepository =postTagRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> get(){
        List<CategoryEntity> list = categoryRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("find_by_id/{id}")
    public ResponseEntity<CategoryEntity> find_by_id(@PathVariable int id){
        CategoryEntity category = categoryRepository.findById(id).get();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @PutMapping("edit")
    public ResponseEntity<CategoryEntity> edit(CategoryEditDto editCateg)throws IOException{
        Optional<CategoryEntity> editCategory = categoryRepository.findById(editCateg.getId());
        if(editCategory.isEmpty())return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        System.out.println("Name - "+editCateg.getName());
        CategoryEntity category = editCategory.get();
        if(!editCateg.getName().isEmpty()) {
            category.setName(editCateg.getName());
            String slug = initializerService.DoSlugUrl(editCateg.getName());
            category.setUrlSlug(slug);
        }
        if(!editCateg.getDescription().isEmpty())category.setDescription(editCateg.getDescription());
        if(editCateg.getImage() != null && !editCateg.getImage().isEmpty()){
            String image = fileSystemStorageService.SaveImage(editCateg.getImage(),FileSaveFormat.JPG);
            fileSystemStorageService.DeleteImages(category.getImage());
            category.setImage(image);
        }

        categoryRepository.save(category);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws IOException {

        Optional<CategoryEntity> deleteCategory = categoryRepository.findById(id);
        if(deleteCategory.isEmpty())return new ResponseEntity<>("Категорію не знайдено",HttpStatus.NOT_FOUND);

        CategoryEntity category = deleteCategory.get();

        fileSystemStorageService.DeleteImages(category.getName());

        List<PostEntity> posts = postRepository.findAll();
        PostEntity post = null;
        for (var item:posts){
            if(item.getCategory().getId()==category.getId())post = item;
        }
        if(post!=null){
            List<PostTagEntity> postTags = postTagRepository.findAll();
            for (var item : postTags){
                if(item.getPost().getId()==post.getId()){
                    TagEntity tag = item.getTag();
                    postTagRepository.delete(item);
                    tagRepository.delete(tag);
                    postRepository.delete(post);
                }
            }
        }


        categoryRepository.delete(category);


        return new ResponseEntity<>("Категорію успішно видалено",HttpStatus.OK);
    }
  @PostMapping("/create")
  public ResponseEntity<CategoryEntity> create(CategoryCreateDto newCategory) throws IOException {
        CategoryEntity category = new CategoryEntity();
        category.setName(newCategory.getName());
        category.setDescription(newCategory.getDescription());
        String slug = initializerService.DoSlugUrl(newCategory.getName());
        category.setUrlSlug(slug);
        String image = fileSystemStorageService.SaveImage(newCategory.getImage(), FileSaveFormat.JPG);
        category.setImage(image);
        categoryRepository.save(category);
        return new ResponseEntity<>(category,HttpStatus.OK);
  }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryItemDto>> search(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false)String description,
                                                        Pageable pageable) {

        Page<CategoryEntity> categoriesName = categoryRepository.findByNameContainingIgnoreCase(name, pageable);


        Page<CategoryEntity> categoriesDescription = categoryRepository.findByDescriptionContainingIgnoreCase(description,pageable);


        List<CategoryEntity> categoriesList = new ArrayList<>();
        for (var item : categoriesName){
            for (var item2 : categoriesDescription){
                if(item.getId()==item2.getId()){categoriesList.add(item);}

            }
        }
        Page<CategoryEntity> categories = new PageImpl<>(categoriesList,pageable,categoriesList.size());

        Page<CategoryItemDto> result = categories.map(mapper::categoryItemDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/sort_name")
    public ResponseEntity<Page<CategoryItemDto>> sortByName(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false)String description,
                                                            Pageable pageable) {
        Page<CategoryEntity> categoriesName = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        Page<CategoryEntity> categoriesDescription = categoryRepository.findByDescriptionContainingIgnoreCase(description,pageable);
        List<CategoryEntity> categoriesList = new ArrayList<>();
        for (var item : categoriesName){
            for (var item2 : categoriesDescription){
                if(item.getId()==item2.getId())categoriesList.add(item);

            }
        }

        categoriesList.sort((a,b)->a.getName().compareToIgnoreCase(b.getName()));
        Page<CategoryEntity> categories = new PageImpl<>(categoriesList,pageable,categoriesList.size());

        Page<CategoryItemDto> sortCategories = categories.map(mapper::categoryItemDto);

        return new ResponseEntity<>(sortCategories, HttpStatus.OK);
    }
    @GetMapping("/sort_description")
    public ResponseEntity<Page<CategoryItemDto>> sortByDescription(@RequestParam(required = false) String name,
                                                                   @RequestParam(required = false)String description,
                                                                   Pageable pageable) {
        Page<CategoryEntity> categoriesName = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        Page<CategoryEntity> categoriesDescription = categoryRepository.findByDescriptionContainingIgnoreCase(description,pageable);
        List<CategoryEntity> categoriesList = new ArrayList<>();
        for (var item : categoriesName){
            for (var item2 : categoriesDescription){
                if(item.getId()==item2.getId())categoriesList.add(item);

            }
        }

        categoriesList.sort((a,b)->a.getDescription().compareToIgnoreCase(b.getDescription()));
        Page<CategoryEntity> categories = new PageImpl<>(categoriesList,pageable,categoriesList.size());

        Page<CategoryItemDto> sortCategories = categories.map(mapper::categoryItemDto);

        return new ResponseEntity<>(sortCategories, HttpStatus.OK);
    }
}
