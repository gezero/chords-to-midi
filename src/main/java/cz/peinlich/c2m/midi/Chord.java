package cz.peinlich.c2m.midi;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Jiri
 */
public class Chord implements Iterable<Note>
{
    private static final Logger logger = LoggerFactory.getLogger( Chord.class );

    private final ChordName chordName;
    private final int inversion;
    private final int octave;
    private final List<Note> notes;

    private final List<NoteName> noteNames;

    public Chord( ChordName chordName ) {
        this( chordName, 0, 4 );
    }

    private Chord( ChordName chordName, int inversion, int octave ) {
        this.chordName = chordName;
        this.inversion = inversion;
        this.octave = octave;
        noteNames = new LinkedList<>();
        for( NoteName noteName : chordName ) {
            noteNames.add( noteName );
        }
        Collections.rotate( noteNames, inversion );
        notes = new ArrayList<>( 3 );

        for( int i = 0; i < noteNames.size(); i++ ) {
            notes.add( new Note( noteNames.get( i ), (inversion + i) < 3 ? octave : octave + 1 ) );
        }
    }

    static Chord from( ChordName chordName ) {
        return new Chord( chordName );
    }

    static List<Chord> from( Collection<ChordName> chordNames ) {
        List<Chord> result = new ArrayList<>( chordNames.size() );
        for( ChordName chordName : chordNames ) {
            result.add( from( chordName ) );
        }
        return result;
    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }

    Chord inversionFrom( Chord previous ) {

        for( int i = 0; i < previous.noteNames.size(); i++ ) {
            NoteName note = previous.noteNames.get( i );
            if( noteNames.contains( note ) ) {
                if( noteNames.get( i ).equals( note ) ) {
                    return movedTo( notes.get( i ) );
                }
                if( firstInversion().noteNames.get( i ).equals( note ) ) {
                    return firstInversion().movedTo( notes.get( i ) );
                }
                if( secondInversion().noteNames.get( i ).equals( note ) ) {
                    return secondInversion().movedTo( notes.get( i ) );
                }
                return this;

            }
        }
        return withOctave( previous.octave );
    }

    private Chord movedTo( Note note ) {
        for( Note myNote : notes ) {
            if( note.getName().equals( myNote.getName() ) ) {
                return withOctave( octave + (note.getOctave() - myNote.getOctave()) );
            }
        }
        throw new IllegalArgumentException( "Note " + note.toString() + " is not present in chord " + toString() );
    }

    Chord firstInversion() {
        return new Chord( chordName, inversion + 1, octave );
    }

    Chord secondInversion() {
        return new Chord( chordName, inversion + 2, octave );
    }

    Chord withOctave( int octave ) {
        return new Chord( chordName, inversion, octave );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper( this )
                .add( "notes", notes )
                .toString();
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o )
            return true;
        if( o == null || getClass() != o.getClass() )
            return false;
        Chord notes1 = (Chord) o;
        return inversion == notes1.inversion &&
                octave == notes1.octave &&
                chordName == notes1.chordName &&
                Objects.equals( notes, notes1.notes ) &&
                Objects.equals( noteNames, notes1.noteNames );
    }

    @Override
    public int hashCode() {
        return Objects.hash( chordName, inversion, octave, notes, noteNames );
    }

}
