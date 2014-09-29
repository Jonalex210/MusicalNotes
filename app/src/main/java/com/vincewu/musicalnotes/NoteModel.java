package com.vincewu.musicalnotes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model class that represents a musical note
 */
public class NoteModel {

    /*
     * Setup the range of valid notes in an octave
     */
    public static final String _C = "C";
    public static final String _D = "D";
    public static final String _E = "E";
    public static final String _F = "F";
    public static final String _G = "G";
    public static final String _A = "A";
    public static final String _B = "B";

    // For
    public static final List<String> sNotesCMaj = new ArrayList<String>();
    static {
        sNotesCMaj.add(_C);
        sNotesCMaj.add(_D);
        sNotesCMaj.add(_E);
        sNotesCMaj.add(_F);
        sNotesCMaj.add(_G);
        sNotesCMaj.add(_A);
        sNotesCMaj.add(_B);
    }

    /* For generating a random note
     * {@link #getRandomNote()}
     */
    private static final Random sRandom = new Random();

    /**
     * Generate a random note
     */
    public static NoteModel getRandomNote() {
        String letter = sNotesCMaj.get(sRandom.nextInt(sNotesCMaj.size()));
        int octave;

        if (letter.equals(_B)) {
            // In range: B1 - B5
            octave = sRandom.nextInt(5) + 1;
        } else if (letter.equals(_C) || letter.equals(_D)) {
            // In range: C2 - C6, D2 - D6
            octave = sRandom.nextInt(5) + 2;
        } else {
            // In range octaves: 2 - 5
            octave = sRandom.nextInt(4) + 2;
        }

        return new NoteModel(letter, octave);
    }

    /**
     * Model representing a music cleff
     */
    public static enum Cleff {
        F, G
    }


    /* Start of NoteModel class */

    private int octave;
    private String letter;

    /**
     * Middle C is C4 in scientific notation, where C is the "letter" and 4 is the "octave".
     * We (somewhat arbitrarily) define a valid note as being in the 4 octave range
     * starting from B1 and ending at D6.
     *
     * @param letter
     * @param octave
     */
    public NoteModel(String letter, int octave) {
        if (letter == null || !sNotesCMaj.contains(letter)) {
            throw new IllegalArgumentException("Note letter not valid: " + letter);
        }

        // Valid range is B1 - D6
        if (octave <1 || octave > 6
                || (octave == 1 && !letter.equals(_B))
                || (octave == 6 && !(letter.equals(_C) || letter.equals(_D)))) {
            throw new IllegalArgumentException("Note not in range: " + letter + octave);
        }

        this.letter = letter;
        this.octave = octave;
    }

    /**
     * @param preferredCleff return this if a note can be displayed on this cleff (for cases
     *                       where multiple cleffs match)
     * @return Music cleff that this note belongs to.
     */
    public Cleff getCleff(Cleff preferredCleff) {
        Cleff result = preferredCleff;

        // Overlapping region of F Cleff and G Cleff for the piano is between G3-F4.
        // Return preferredCleff if the note falls in that region.

        if (this.octave <=2) {
            result =  Cleff.F;
        } else if (this.octave >= 5) {
            result = Cleff.G;
        } else if (this.octave == 3 &&
                (sNotesCMaj.indexOf(this.letter) < sNotesCMaj.indexOf(_G))) {
            result = Cleff.F;
        } else if (this.octave == 4 &&
                (sNotesCMaj.indexOf(this.letter) > sNotesCMaj.indexOf(_F))) {
            result = Cleff.G;
        }

        return result;
    }

    /**
     * @param otherNote
     * @return number of notes between this note and otherNote. E.g. C5 - C4 would be 7.
     */
    public int getDistance(NoteModel otherNote) {
        int thisRelPos = (this.octave * 7) + sNotesCMaj.indexOf(this.letter);
        int otherRelPos = (otherNote.octave * 7) + sNotesCMaj.indexOf(otherNote.letter);
        return thisRelPos - otherRelPos;
    }

    @Override
    public String toString() {
        return letter+octave;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other instanceof NoteModel) {
            return this.toString().equals(other.toString());
        }
        return false;
    }
}
