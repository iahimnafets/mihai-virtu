package com.thevirtugroup.postitnote.service;


import com.thevirtugroup.postitnote.exception.ApiRequestException;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.User;
import com.thevirtugroup.postitnote.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@Service
@Slf4j
public class NoteService {

    @Autowired
    private UserRepository userRepository;

    private HashMap<Long, HashMap<Long,Note> > allUsersNotes = new HashMap<>();
    private  int index = 0;


    private boolean userExists(Long idUser){
        if(userRepository.findById(idUser) == null){
            throw new ApiRequestException(" User not exist in our database");
        }
        return true;
    }


    public void saveNote(Note note, Long idUser) {
        log.info(" saveNote-RUN  idUser: {} note: {}", idUser, note);
        if(Objects.isNull( idUser) ||
            Objects.isNull( note ) ||
            Objects.isNull( note.getName()) ||
            Objects.isNull( note.getNoteText())
        ){
           throw new ApiRequestException(" Insert all this fields: idUser, name, noteText ");
        }
        userExists(idUser);
        index++;
        if(allUsersNotes.containsKey(idUser)){
            note.setDataInsert( new Date() );
            note.setIdNote( new Long( index) );
            allUsersNotes.get(idUser).put( new Long( index), note );
        }else{
            note.setDataInsert( new Date() );
            note.setIdNote( new Long( index) );
            HashMap<Long,Note> userNotes = new HashMap<>();
            userNotes.put( note.getIdNote(), note );

            allUsersNotes.put(idUser, userNotes );
        }
    }

    public void updateNote(Long idUser, Long idNote, String noteText) {
        log.info(" updateNote-RUN  idUser: {} idNote: {} noteText: {}", idUser, idNote, noteText);
        userExists(idUser);
        if( !(allUsersNotes.containsKey(idUser) && allUsersNotes.get(idUser).containsKey(idNote)) ){
            throw new ApiRequestException(" Note not exist for this user");
        }
        allUsersNotes.get(idUser).get(idNote).setNoteText(noteText);
    }

    public HashMap<Long,Note> getAllNoteByUser(Long idUser) {
        log.info(" getAllNoteByUser-RUN  idUser: {} ", idUser );
        userExists(idUser);
        if( ! allUsersNotes.containsKey(idUser)  ){
            throw new ApiRequestException(" Note not exist for this user");
        }
       return allUsersNotes.get(idUser);
    }

    public void deleteNoteForUser(Long idUser, Long idNote) {
        userExists(idUser);
        log.info(" deleteNoteForUser-RUN  idUser: {} ", idUser );
        if( !(allUsersNotes.containsKey(idUser) && allUsersNotes.get(idUser).containsKey(idNote)) ){
            throw new ApiRequestException(" Note not exist for this user");
        }
        allUsersNotes.remove(idUser);
    }
}
