package cz.peinlich.c2m.midi;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jiri
 */
public class ChordTest {
    private static final Logger logger = LoggerFactory.getLogger(ChordTest.class);

    @Test
    public void chordName() {
        Chord chord = new Chord(ChordName.C);
        assertThat(chord, contains(new Note(NoteName.C), new Note(NoteName.E), new Note(NoteName.G)));
    }

    @Test
    public void voiceLeadInversionLookup() {
        Chord first = Chord.from(ChordName.C);
        Chord second = Chord.from(ChordName.G);

        Chord inversion = second.inversionFrom(first);

        logger.info("First: {}, Second: {}, Inversion: {}", first, second, inversion);
        assertEquals(second.secondInversion().withOctave(3), inversion);
    }
}
