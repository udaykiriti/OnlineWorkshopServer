package com.studentworkshop.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    /**
     * Saves the uploaded material (file) into the local filesystem.
     *
     * @param material The file uploaded via MultipartFile (from frontend/client).
     * @return The full path of the saved file.
     */
    public String saveMaterial(MultipartFile material) {
        
        // Define the directory where materials will be stored
        String directory = "src/materials"; 
        
        // Construct the complete file path (directory + original filename)
        String filePath = directory + "/" + material.getOriginalFilename();

        try {
            // Create the directory if it does not exist
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs(); // make parent directories as well if needed
            }

            // Create a File object for the target path
            File file = new File(filePath);

            // Save the uploaded file to the specified path
            material.transferTo(file);

        } catch (IOException e) {
            // Print error details if file saving fails
            e.printStackTrace();
        }

        // Return the file path after saving
        return filePath; 
    }
}
