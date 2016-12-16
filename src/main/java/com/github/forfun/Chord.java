package com.github.forfun;

import java.util.Arrays;
import java.util.List;

import static com.github.forfun.Note.C;
import static com.github.forfun.Note.E;
import static com.github.forfun.Note.G;

public enum Chord {

    A(C, E, G), Am(Note.A, C, E), Dm(Note.E, Note.F, Note.A), F(Note.F, Note.A, Note.C);

    private final List<Note> notes;

    Chord(Note... notes) {
        this.notes = Arrays.asList(notes);
    }

    public List<Note> getNotes() {
        return notes;
    }
}
