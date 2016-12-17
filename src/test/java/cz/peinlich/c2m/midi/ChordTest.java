package cz.peinlich.c2m.midi;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jiri
 */
class ChordTest {

    @Test
    public void parseChords() {
        List<Chord> chords = Chord.parseChords("Am G F Em C");

        assertEquals(Arrays.asList(Chord.Am, Chord.G, Chord.F, Chord.Em, Chord.C), chords);

    }

}