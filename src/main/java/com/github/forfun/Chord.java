package com.github.forfun;

import java.util.Arrays;
import java.util.List;

public enum Chord {

    C(Note.C, Note.E, Note.G), Am(Note.A, Note.C, Note.E), Dm(Note.D, Note.F, Note.A), F(Note.F, Note.A, Note.C), G(Note.G, Note.B, Note.D);

    private final List<Note> notes;

    Chord(Note... notes) {
        this.notes = Arrays.asList(notes);
    }

    public List<Note> getNotes() {
        return notes;
    }
}
