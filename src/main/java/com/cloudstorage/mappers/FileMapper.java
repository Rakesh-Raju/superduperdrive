package com.cloudstorage.mappers;
import com.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, filedata, userid) VALUES (#{file.fileName}, #{file.contentType}, #{file.fileSize}, #{file.fileData}, #{userID})")
    int insert(File file, Integer userID);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileID}")
    File getFile(int fileID);

    @Select("SELECT * FROM FILES")
    List<File> getFiles();

    @Select("SELECT * FROM FILES WHERE userid = #{userID}")
    List<File> getFilesByID(Integer userID);


    @Delete("DELETE FROM FILES WHERE fileid = #{fileID}")
    void delete(int fileID);

}