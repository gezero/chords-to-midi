package cz.peinlich.c2m.midi;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jiri
 */
class BaseChordTest {

    @Test
    public void parseChords() {
        List<BaseChord> chords = BaseChord.parseChords("Am G F Em C");

        assertEquals(Arrays.asList(BaseChord.Am, BaseChord.G, BaseChord.F, BaseChord.Em, BaseChord.C), chords);

    }

    @Test
    public void baseChord() {
        Chord chord = BaseChord.C.baseChord();
        assertThat(chord, Matchers.contains(Note.C, Note.E, Note.G));
    }

    @Test
    public void firstInversion() {
        Chord chord = BaseChord.C.firstInversion();
        assertThat(chord, Matchers.contains(Note.G, Note.C, Note.E));
    }

    @Test
    public void secondInversion() {
        Chord chord = BaseChord.C.secondInversion();
        assertThat(chord, Matchers.contains(Note.E, Note.G, Note.C));
    }

}