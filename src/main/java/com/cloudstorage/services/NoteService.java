package com.cloudstorage.services;
import com.cloudstorage.mappers.NoteMapper;
import com.cloudstorage.models.Notes;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Notes> getNotes(int userID) {
        return noteMapper.getNotes(userID);
    }


    public Notes getNote(Integer noteID) {
        return noteMapper.getNote(noteID);
    }

    public boolean insertOrUpdate(Notes note, Integer userID) {
        Integer noteID = note.getNoteID();
        if (noteID != null && noteID > 0) {
            noteMapper.update(note, noteID);
        } else {
            noteMapper.insert(note, userID);
        }

        return true;
    }

    public boolean delete(Integer noteID) {
        noteMapper.delete(noteID);
        return true;
    }
}
