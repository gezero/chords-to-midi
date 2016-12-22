package cz.peinlich.c2m.midi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class VoiceLeadTransformer
{
    private static final Logger logger = LoggerFactory.getLogger( VoiceLeadTransformer.class );

    public List<Chord> transform( List<Chord> source ) {
        List<Chord> result = new ArrayList<>( source.size() );

        Chord previous = null;
        for( Chord chord : source ) {
            if( previous == null ) {
                previous = chord;
                result.add( previous );
                continue;
            }
            Chord inversion = chord.inversionFrom( previous );
            logger.info( "Previous: {}, current: {}, inversion: {}", previous, chord, inversion );
            result.add( inversion );
        }
        return result;
    }
}
