package cz.peinlich.c2m.midi;

import java.util.*;

/**
 * @author Jiri
 */
public class Chord implements Iterable<Note> {

    private final ChordName chordName;
    private final int inversion;
    private final int octave;

    private final List<NoteName> noteNames;


    public Chord(ChordName chordName) {
        this(chordName, 0, 4);
    }

    private Chord(ChordName chordName, int inversion, int octave) {
        this.chordName = chordName;
        this.inversion = inversion % 3;
        this.octave = octave;
        noteNames = new LinkedList<>();
        for (NoteName noteName : chordName) {
            noteNames.add(noteName);
        }
        Collections.rotate(noteNames, inversion);
    }


    @Override
    public Iterator<Note> iterator() {
        List<Note> result = new ArrayList<>(3);
        for (int i = 0; i < noteNames.size(); i++) {
            result.add(new Note(noteNames.get(i), (inversion + i) < 3 ? octave : octave + 1));
        }
        return result.iterator();
    }
}
