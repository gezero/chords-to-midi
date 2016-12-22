package cz.peinlich.c2m.midi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Jiri
 */
public class MidiGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MidiGenerator.class);
    private static final int PLAY_NOTE = 0x90;
    private static final int RELEASE_NOTE = 0x80;

    private final Track track;
    private final Sequence sequence;
    private long curTick;


    public MidiGenerator() {
        try {
            sequence = new Sequence(Sequence.PPQ, 24);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException("Could not start a sequence", e);
        }
        track = sequence.createTrack();
        curTick = 1;
    }


    public void initialize() {
        try {
            //****  General MIDI sysex -- turn on General MIDI sound set  ****
            byte[] b = {(byte) 0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte) 0xF7};
            SysexMessage sm = new SysexMessage();
            sm.setMessage(b, 6);
            MidiEvent me = new MidiEvent(sm, (long) 0);
            track.add(me);

//****  set tempo (meta event)  ****
            MetaMessage mt = new MetaMessage();
            byte[] bt = {0x02, (byte) 0x00, 0x00};
            mt.setMessage(0x51, bt, 3);
            me = new MidiEvent(mt, (long) 0);
            track.add(me);

//****  set track name (meta event)  ****
            mt = new MetaMessage();
            String TrackName = "midifile track";
            mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
            me = new MidiEvent(mt, (long) 0);
            track.add(me);

//****  set omni on  ****
            ShortMessage mm = new ShortMessage();
            mm.setMessage(0xB0, 0x7D, 0x00);
            me = new MidiEvent(mm, (long) 0);
            track.add(me);

//****  set poly on  ****
            mm = new ShortMessage();
            mm.setMessage(0xB0, 0x7F, 0x00);
            me = new MidiEvent(mm, (long) 0);
            track.add(me);

//****  set instrument to Piano  ****
            mm = new ShortMessage();
            mm.setMessage(0xC0, 0x00, 0x00);
            me = new MidiEvent(mm, (long) 0);
            track.add(me);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }


    public void playChords(Iterable<Chord> chords) {
        for (Chord chord : chords) {
            playChord(chord);
        }
    }

    private void playChord(Chord chord) {
        for (Note note : chord) {
            pressNote(note);
        }

        curTick += 100;

        for (Note note : chord) {
            releaseNote(note);
        }
    }


    private void releaseNote(Note note) {
        try {
            ShortMessage mm;
            MidiEvent me;

            mm = new ShortMessage();
            mm.setMessage(RELEASE_NOTE, note.pitch(), 0x40);
            me = new MidiEvent(mm, curTick);
            track.add(me);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    private void pressNote(Note note) {
        try {
            ShortMessage mm = new ShortMessage();
            mm.setMessage(PLAY_NOTE, note.pitch(), 0x60);
            MidiEvent me = new MidiEvent(mm, curTick);
            track.add(me);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }


    public void closeMidi() {

        try {
            MetaMessage mt;
            MidiEvent me;
//****  set end of track (meta event) 19 ticks later  ****
            mt = new MetaMessage();
            byte[] bet = {}; // empty array
            mt.setMessage(0x2F, bet, 0);
            me = new MidiEvent(mt, (long) 140);
            track.add(me);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToFile(Path path) {
        try {
            MidiSystem.write(sequence, 1, path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
