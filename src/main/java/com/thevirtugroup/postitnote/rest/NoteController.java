package com.thevirtugroup.postitnote.rest;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.Response;
import com.thevirtugroup.postitnote.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import static com.google.common.collect.ImmutableMap.of;

@RestController
@Slf4j
@RequestMapping( "/note" )
public class NoteController
{

    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "/save" , method = RequestMethod.PUT )
    public ResponseEntity<Response> saveNote(
            @RequestBody final Note note,
            @RequestParam(name = "idUser", required = true) final Long idUser) {

         noteService.saveNote(note, idUser );

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .message("Note saved correctly")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }



    @RequestMapping(value = "/update" , method = RequestMethod.PUT )
    public ResponseEntity<Response> updateNote(
            @RequestParam(name = "idNote", required = true) final Long idNote,
            @RequestParam(name = "noteText", required = true) final String noteText,
            @RequestParam(name = "idUser", required = true) final Long idUser) {

        noteService.updateNote( idUser, idNote, noteText );

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .message("Note updated correctly")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @RequestMapping(value = "/all" , method = RequestMethod.GET )
    public ResponseEntity<Response> getAllNoteByUser(@RequestParam(name = "idUser", required = true) final Long idUser) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                         .data(of("note", noteService.getAllNoteByUser(idUser) ) )
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.DELETE )
    public ResponseEntity<Response> deleteNoteForUser(
            @RequestParam(name = "idUser", required = true) final Long idUser,
           @RequestParam(name = "idNote", required = true) final Long idNote
        ) {

        noteService.deleteNoteForUser(idUser, idNote );

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .message("Note was deleted correctly")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }



}
