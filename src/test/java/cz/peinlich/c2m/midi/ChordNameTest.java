package cz.peinlich.c2m.midi;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jiri
 */
class ChordNameTest {

    @Test
    public void parseChords() {
        List<ChordName> chords = ChordName.parseChords("Am G F Em C");

        assertEquals(Arrays.asList(ChordName.Am, ChordName.G, ChordName.F, ChordName.Em, ChordName.C), chords);

    }


}