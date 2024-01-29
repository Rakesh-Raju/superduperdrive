package com.cloudstorage.services;
import com.cloudstorage.mappers.FileMapper;
import com.cloudstorage.models.File;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;


@Service
public class FileService{

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(Integer fileID) {
        return fileMapper.getFile(fileID);
    }

    public List<File> getFilesByID(Integer userID) {
        return fileMapper.getFilesByID(userID);
    }

    public File getFile(int fileID) {
        return fileMapper.getFile(fileID);
    }

    public Boolean insert(MultipartFile file, Integer userID) throws IOException {
        String fileName = file.getOriginalFilename();
        for(File f: fileMapper.getFilesByID(userID)) {
            if(f.getFileName().equals(fileName)){
                return false;
            }
        }
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        byte[] data = file.getBytes();
        fileMapper.insert(new File(null, fileName, contentType, fileSize, userID, data), userID);
        return true;
    }

    public Boolean delete(int fileID) {
         fileMapper.delete(fileID);
         return true;
    }

}