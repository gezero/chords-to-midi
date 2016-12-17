package cz.peinlich.c2m.midi;

/**
 * @author Jiri
 */
public interface Chord extends Iterable<Note> {
    Chord baseChord();

    Chord firstInversion();

    Chord secondInversion();
}
