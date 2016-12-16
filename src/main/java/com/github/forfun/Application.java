package com.github.forfun;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

public class Application {


    public static final int PLAY_NOTE = 0x90;
    private final Sequence sequence;
    private final Track track;
    private long curTick;

    public Application() throws InvalidMidiDataException {
        sequence = new Sequence(Sequence.PPQ, 24);
        track = sequence.createTrack();
        curTick = 1;
    }

    public static void main(String[] args) {
        System.out.println("midifile begin ");

        try {

            final Application application = new Application();
            application.initialize();

            for (int i = 0; i < 10; i++) {
                application.playChord(Chord.C);
                application.playChord(Chord.Am);
                application.playChord(Chord.Dm);
                application.playChord(Chord.G);
            }

            application.closeMidi();
        } //try
        catch (Exception e) {
            System.out.println("Exception caught " + e.toString());
        } //catch
        System.out.println("midifile end ");
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

//****  write the MIDI sequence to a MIDI file  ****
        File f = new File("target/midifile.mid");
        MidiSystem.write(sequence, 1, f);
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
        String TrackName = new String("midifile track");
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

    public void playChord(Chord c) throws InvalidMidiDataException {
        for (Note note : c.getNotes()) {
            pressNote(note.ordinal());
        }

        curTick += 100;

        for (Note note : c.getNotes()) {
            releaseNote(note.ordinal());
        }
    }

    public void playNote(int i, long ticks) throws InvalidMidiDataException {

        pressNote(i);
        curTick += ticks;
        releaseNote(i);
    }

    private void releaseNote(int i) throws InvalidMidiDataException {
        ShortMessage mm;
        MidiEvent me;

//****  note off - middle C - 120 ticks later  ****
        mm = new ShortMessage();
        mm.setMessage(0x80, 0x3C + i, 0x40);
        me = new MidiEvent(mm, curTick);
        track.add(me);
    }

    private void pressNote(int i) throws InvalidMidiDataException {
        ShortMessage mm = new ShortMessage();
        mm.setMessage(PLAY_NOTE, 0x3C + i, 0x60);
        MidiEvent me = new MidiEvent(mm, curTick);
        track.add(me);
    }
}
