package com.ameda.book.file;/*
*
@author ameda
@project Books
*
*/

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;
    public String  saveFile(@Nonnull MultipartFile file,
                            @Nonnull Integer bookId,
                            @Nonnull Integer userId) {
        final String fileUploadSubPath = "users" + File.separator + userId;
        return uploadFile(file,fileUploadSubPath);
    }
    private String uploadFile(@Nonnull MultipartFile file, @Nonnull String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if(!targetFolder.exists()){
            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated){
                log.warn("failed to create target folder");
                return null;
            }
        }
        //extract file extension to create target file path
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        final String targetFilePath = finalUploadPath + File.separator
                + System.currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try{
            Files.write(targetPath,file.getBytes());
            log.info("file saved correctly:{}",targetFilePath);
            return targetFilePath;
        }catch (IOException ex){
            log.error("File not saved:{}",ex);
            return null;
        }
    }

    private String getFileExtension(String originalFilename) {
        if(originalFilename == null || originalFilename.isEmpty()){
            return "";
        }
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if(lastDotIndex == -1){
            //if it has no extension
            //return empty string
            return "";
        }
        // if JPG OR PNG -> jpg and png
        return originalFilename.substring(lastDotIndex + 1).toLowerCase();
    }
}
