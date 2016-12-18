package cz.peinlich.c2m.midi;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * @author Jiri
 */
public class ChordTest {

    @Test
    public void ChordName() {
        Chord chord = new Chord(ChordName.C);
        assertThat(chord, contains(new Note(NoteName.C), new Note(NoteName.E), new Note(NoteName.G)));
    }
}
