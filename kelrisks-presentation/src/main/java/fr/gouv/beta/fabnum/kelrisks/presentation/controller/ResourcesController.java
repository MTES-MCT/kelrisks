package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourcesController {
    
    @GetMapping(value = "/api/image/{image}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] avisPdf(@PathVariable("image") String imageName) {
        
        File imageFile;
        
        try {
            imageFile = ResourceUtils.getFile("classpath:" + imageName + ".jpg");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        
        try {
            return Files.readAllBytes(imageFile.toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
