package cz.peinlich.c2m.midi;

import java.util.Objects;

public class Note {
    private static final int C4 = 0x3C;

    private final NoteName noteName;
    private final int octave;

    Note(NoteName noteName) {
        this(noteName, 4);
    }

    Note(NoteName noteName, int octave) {
        this.noteName = noteName;
        this.octave = octave;
    }

    int pitch() {
        return C4 + noteName.ordinal() + (octave - 4) * 12;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return octave == note.octave &&
                noteName == note.noteName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteName, octave);
    }

    @Override
    public String toString() {
        return "[" + noteName.toString() + String.valueOf(octave) + "]";
    }

    public NoteName getName() {
        return noteName;
    }

    public int getOctave() {
        return octave;
    }

}
