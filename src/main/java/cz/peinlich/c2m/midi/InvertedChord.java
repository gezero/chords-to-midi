package cz.peinlich.c2m.midi;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiri
 */
public class InvertedChord implements Chord {

    private final BaseChord baseChord;
    private final int inversion;
    private final List<Note> notes = new LinkedList<>();


    InvertedChord(BaseChord baseChord, int inversion) {
        this.baseChord = baseChord;
        this.inversion = inversion % 3;
        for (Note note : baseChord) {
            notes.add(note);
        }

        Collections.rotate(notes, inversion);

    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }

    @Override
    public Chord baseChord() {
        return baseChord;
    }

    @Override
    public Chord firstInversion() {
        return baseChord.firstInversion();
    }

    @Override
    public Chord secondInversion() {
        return baseChord.secondInversion();
    }
}
