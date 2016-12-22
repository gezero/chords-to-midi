package cz.peinlich.c2m.midi;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void inversionFlipsNotesOnC() {
        Chord inverted = Chord.from( ChordName.Am ).firstInversion().withOctave( 4 );

        List<Note> notes = new ArrayList<>( 3 );
        Iterables.addAll( notes, inverted );

        assertEquals( Arrays.asList( new Note( NoteName.E, 4 ), new Note( NoteName.A, 4 ), new Note( NoteName.C, 5 ) ), notes );

    }
}
