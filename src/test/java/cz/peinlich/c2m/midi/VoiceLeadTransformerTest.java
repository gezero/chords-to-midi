package cz.peinlich.c2m.midi;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VoiceLeadTransformerTest
{
    private VoiceLeadTransformer transformer = new VoiceLeadTransformer();

    @Test
    void singleChord() {
        List<Chord> source = Collections.singletonList( Chord.from( ChordName.C ) );
        List<Chord> transformed = transformer.transform( source );

        assertEquals( source, transformed );
    }

    @Test
    void independentChordsShouldStayUnchanged() {
        List<Chord> source = Chord.from( Arrays.asList( ChordName.C, ChordName.Dm ) );
        List<Chord> transformed = transformer.transform( source );

        assertEquals( source, transformed );
    }

    @Test
    void singleInversion() {
        List<Chord> source = Chord.from( Arrays.asList( ChordName.C, ChordName.G ) );
        List<Chord> transformed = transformer.transform( source );

        List<Chord> expectedInversion = Arrays.asList(
                Chord.from( ChordName.C ),
                Chord.from( ChordName.G ).secondInversion().withOctave( 3 )
        );
        assertEquals( expectedInversion, transformed );

    }

    @Test
    void fourChords() {
        List<Chord> source = Chord.from( Arrays.asList( ChordName.C, ChordName.G, ChordName.Am, ChordName.F ) );
        List<Chord> transformed = transformer.transform( source );

        List<Chord> expectedInversion = Arrays
                .asList(
                        Chord.from( ChordName.C ),
                        Chord.from( ChordName.G ).secondInversion().withOctave( 3 ),
                        Chord.from( ChordName.Am ).secondInversion().withOctave( 4 ),
                        Chord.from( ChordName.F ).firstInversion().withOctave( 4 )
                );
        assertEquals( expectedInversion, transformed );

    }
}
