package com.cloudstorage.mappers;
import com.cloudstorage.models.Notes;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES")
    List<Notes> getAll();

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteID}")
    Notes getNote(Integer noteID);

    @Select("SELECT * FROM NOTES WHERE notetitle = #{note.noteTitle} AND notedescription = #{note.noteDescription} AND userid = #{userID}")
    Integer getNoteID(Notes note, Integer userID);

    @Select("SELECT * FROM NOTES where userid = #{userID}")
    List<Notes> getNotes(Integer userID);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES (#{note.noteTitle}, #{note.noteDescription}, #{userID})")
    @Options(useGeneratedKeys = true, keyProperty = "note.noteID")
    int insert(Notes note, Integer userID);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteID}")
    void delete(Integer noteID);

    @Delete("DELETE FROM NOTES")
    void deleteAll();

    @Update("UPDATE notes SET notetitle = #{note.noteTitle}, notedescription = #{note.noteDescription} WHERE noteid = #{noteID}")
    int update(Notes note, Integer noteID);

}