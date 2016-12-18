package cz.peinlich.c2m.midi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jiri
 */
public enum ChordName implements Iterable<NoteName> {

    C(NoteName.C, NoteName.E, NoteName.G),
    Am(NoteName.A, NoteName.C, NoteName.E),
    Dm(NoteName.D, NoteName.F, NoteName.A),
    F(NoteName.F, NoteName.A, NoteName.C),
    G(NoteName.G, NoteName.B, NoteName.D),
    Em(NoteName.E, NoteName.G, NoteName.B);

    private final List<NoteName> notes;

    ChordName(NoteName... notes) {
        this.notes = Arrays.asList(notes);
    }

    public static List<ChordName> parseChords(String source) {
        String trim = source.trim();
        String[] split = trim.split(" ");
        List<ChordName> result = new ArrayList<>(split.length);
        for (String part : split) {
            result.add(ChordName.valueOf(part));
        }
        return result;
    }

    @Override
    public Iterator<NoteName> iterator() {
        return notes.iterator();
    }
}
