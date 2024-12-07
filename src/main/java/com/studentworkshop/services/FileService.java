package com.studentworkshop.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    public String saveMaterial(MultipartFile material) {
        
        String directory = "src/materials"; 
        String filePath = directory + "/" + material.getOriginalFilename();

        try {
            
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            
            File file = new File(filePath);
            material.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            
        }

        return filePath; 
    }
}
