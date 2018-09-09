package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.File;
import lombok.Data;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Data
@Named("fileUploadBean")
public class FileUploadBean implements Serializable {

    private Part file;
    private FileUploadController uploadController;

    public void uploadFile() {
        try {
            String contentType = file.getContentType();
            String filename = file.getHeader("filename");
            InputStream inputStream = file.getInputStream();
            byte[] content = new byte[inputStream.available()];
            inputStream.read(content);

            File newFile = new File();
            newFile.setName(filename);
            newFile.setContentType(contentType);
            newFile.setContent(content);

            uploadController.upload(newFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileNameFromPart(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                String fileName = content.substring(content.indexOf('=') + 1)
                        .trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }
}
