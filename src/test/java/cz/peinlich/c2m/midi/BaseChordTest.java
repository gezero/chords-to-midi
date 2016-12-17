package cz.peinlich.c2m.midi;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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

}