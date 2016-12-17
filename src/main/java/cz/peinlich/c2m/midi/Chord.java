package cz.peinlich.c2m.midi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Chord {

    C(Note.C, Note.E, Note.G),
    Am(Note.A, Note.C, Note.E),
    Dm(Note.D, Note.F, Note.A),
    F(Note.F, Note.A, Note.C),
    G(Note.G, Note.B, Note.D),
    Em(Note.E, Note.G, Note.B);

    private final List<Note> notes;

    Chord(Note... notes) {
        this.notes = Arrays.asList(notes);
    }

    public static List<Chord> parseChords(String source) {
        String trim = source.trim();
        String[] split = trim.split(" ");
        List<Chord> result = new ArrayList<>(split.length);
        for (String part : split) {
            result.add(Chord.valueOf(part));
        }

        return result;

    }

    public List<Note> getNotes() {
        return notes;
    }
}
