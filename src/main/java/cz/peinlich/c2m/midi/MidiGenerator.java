package cz.peinlich.c2m.midi;

import javax.sound.midi.*;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Jiri
 */
public class MidiGenerator {


    private static final int PLAY_NOTE = 0x90;
    private static final int RELEASE_NOTE = 0x80;

    private final Track track;
    private final Sequence sequence;
    private long curTick;


    public MidiGenerator() throws InvalidMidiDataException {
        sequence = new Sequence(Sequence.PPQ, 24);
        track = sequence.createTrack();
        curTick = 1;
    }


    public void initialize() throws InvalidMidiDataException {
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
    }


    public void playChords(Iterable<Chord> chords) throws InvalidMidiDataException {
        for (Chord chord : chords) {
            playChord(chord);
        }
    }

    private void playChord(Chord c) throws InvalidMidiDataException {
        for (Note note : c.getNotes()) {
            pressNote(note.ordinal());
        }

        curTick += 100;

        for (Note note : c.getNotes()) {
            releaseNote(note.ordinal());
        }
    }


    private void releaseNote(int i) throws InvalidMidiDataException {
        ShortMessage mm;
        MidiEvent me;

//****  note off - middle C - 120 ticks later  ****
        mm = new ShortMessage();
        mm.setMessage(RELEASE_NOTE, 0x3C + i, 0x40);
        me = new MidiEvent(mm, curTick);
        track.add(me);
    }

    private void pressNote(int i) throws InvalidMidiDataException {
        ShortMessage mm = new ShortMessage();
        mm.setMessage(PLAY_NOTE, 0x3C + i, 0x60);
        MidiEvent me = new MidiEvent(mm, curTick);
        track.add(me);
    }


    public void closeMidi() throws InvalidMidiDataException, IOException {
        MetaMessage mt;
        MidiEvent me;

//****  set end of track (meta event) 19 ticks later  ****
        mt = new MetaMessage();
        byte[] bet = {}; // empty array
        mt.setMessage(0x2F, bet, 0);
        me = new MidiEvent(mt, (long) 140);
        track.add(me);
    }

    public void writeToFile(Path path) throws IOException {
        MidiSystem.write(sequence, 1, path.toFile());
    }
}
