package cz.peinlich.c2m.midi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public enum BaseChord implements Chord {

    C(Note.C, Note.E, Note.G),
    Am(Note.A, Note.C, Note.E),
    Dm(Note.D, Note.F, Note.A),
    F(Note.F, Note.A, Note.C),
    G(Note.G, Note.B, Note.D),
    Em(Note.E, Note.G, Note.B);

    private final List<Note> notes;

    BaseChord(Note... notes) {
        this.notes = Arrays.asList(notes);
    }

    public static List<BaseChord> parseChords(String source) {
        String trim = source.trim();
        String[] split = trim.split(" ");
        List<BaseChord> result = new ArrayList<>(split.length);
        for (String part : split) {
            result.add(BaseChord.valueOf(part));
        }

        return result;

    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }
}
