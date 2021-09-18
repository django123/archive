package com.derteuffel.archive.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadHelper {

    private final String UPLOAD_DIR = "./downloadFile";

    public boolean uploadFile(MultipartFile multipartFile, String subdir){

        boolean f = false;

        try{

            String dir = UPLOAD_DIR + subdir;
            File file = new File(dir);
            if(file.exists() == false){
                file.mkdirs();
                System.out.println("Create file "+dir);
            }
            Files.copy(multipartFile.getInputStream(), Paths.get(dir + File.separator+ multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            f = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
}
