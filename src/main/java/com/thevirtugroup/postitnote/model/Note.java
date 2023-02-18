package com.thevirtugroup.postitnote.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Note {

    private Long idNote;
    private String name;
    private String noteText;
    private Date dataInsert;

}
