package org.example.Storage;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties){
        this.rootLocation = Paths.get(properties.getLocation());
    }
    @Override
    public void init() throws IOException {
        if(!Files.exists(rootLocation))
            Files.createDirectory(rootLocation);
    }

    @Override
    public String SaveImage(MultipartFile file, FileSaveFormat format) throws IOException {
        String ext = format.name().toLowerCase();
        String randomFileName = UUID.randomUUID().toString()+"."+ext;
        int [] sizes = {32,150,300,600,1200};
        var bufferedImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        for (var size:sizes){
            String fileSave = rootLocation.toString()+"\\"+300+"_"+randomFileName;
            Thumbnails.of(bufferedImage).size(300,300).outputFormat(ext).toFile(fileSave);
        }
        return randomFileName;
    }
    @Override
    public void DeleteImages(String name) throws IOException {
        int [] sizes = {32,150,300,600,1200};
        for (var size:sizes){
            String fileName = rootLocation.toString()+"\\"+size+"_"+name;
            Path pathDelete = Paths.get(fileName);
            if(Files.exists(pathDelete))Files.delete(pathDelete);
        }
    }

}
