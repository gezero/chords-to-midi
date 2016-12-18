package cz.peinlich.c2m.midi;

public class Note {
    private static final int C4 = 0x3C;
    private final NoteName noteName;
    private final int octave;

    public Note(NoteName noteName) {
        this(noteName, 4);
    }

    private Note(NoteName noteName, int octave) {
        this.noteName = noteName;
        this.octave = octave;
    }

    int pitch() {
        return C4 + noteName.ordinal() + (octave - 4) * 12;
    }

}
