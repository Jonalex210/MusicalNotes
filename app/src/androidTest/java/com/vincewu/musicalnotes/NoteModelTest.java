package com.vincewu.musicalnotes;

import android.test.InstrumentationTestCase;

public class NoteModelTest extends InstrumentationTestCase {

    private NoteModel C2;
    private NoteModel C4;
    private NoteModel A2;
    private NoteModel A3;
    private NoteModel B1;
    private NoteModel B5;
    private NoteModel D3;
    private NoteModel D4;
    private NoteModel D6;
    public void setUp() {
        C2 = new NoteModel(NoteModel._C, 2);
        C4 = new NoteModel(NoteModel._C, 4);
        A2 = new NoteModel(NoteModel._A, 2);
        A3 = new NoteModel(NoteModel._A, 3);
        B1 = new NoteModel(NoteModel._B, 1);
        B5 = new NoteModel(NoteModel._B, 5);
        D3 = new NoteModel(NoteModel._D, 3);
        D4 = new NoteModel(NoteModel._D, 4);
        D6 = new NoteModel(NoteModel._D, 6);
    }

    public void testEquals() throws Exception {
        // C4 is C4
        final NoteModel anotherC4 = new NoteModel(NoteModel._C, 4);
        assertEquals(C4, anotherC4);

        // C2 is not C4
        assertFalse(C2.equals(C4));
        assertFalse(C2.equals(anotherC4));

        // others
        assertFalse(A2.equals(anotherC4));
        assertFalse(B5.equals(anotherC4));
        assertFalse(D6.equals(D3));
        assertFalse(D6.equals(B1));
        assertFalse(A3.equals(D3));
    }

    public void testIllegalArgumentsToConstructor() throws Exception {
        // invalid note letter
        try {
            new NoteModel("c", 4);
            fail( "Didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        // invalid note letter
        try {
            new NoteModel("h", 4);
            fail( "Didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        // invalid: below range
        try {
            new NoteModel(NoteModel._A, 1);
            fail( "Didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        // invalid: above range
        try {
            new NoteModel(NoteModel._E, 6);
            fail( "Didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        // invalid: below range
        try {
            new NoteModel(NoteModel._E, 0);
            fail( "Didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

    public void testGetRandomNote() throws Exception {
        for (int i=0; i<200; i++) {
            NoteModel anotherNote = NoteModel.getRandomNote();
            assertNotNull(anotherNote);
        }
    }

    public void testGetCleff() throws Exception {
        assertEquals(B1.getCleff(NoteModel.Cleff.F), NoteModel.Cleff.F);
        assertEquals(B1.getCleff(NoteModel.Cleff.G), NoteModel.Cleff.F);

        assertEquals(C4.getCleff(NoteModel.Cleff.G), NoteModel.Cleff.G);
        assertEquals(C4.getCleff(NoteModel.Cleff.F), NoteModel.Cleff.F);

        assertEquals(C2.getCleff(NoteModel.Cleff.G), NoteModel.Cleff.F);
        assertEquals(C2.getCleff(NoteModel.Cleff.F), NoteModel.Cleff.F);

        assertEquals(D4.getCleff(NoteModel.Cleff.G), NoteModel.Cleff.G);
        assertEquals(D4.getCleff(NoteModel.Cleff.F), NoteModel.Cleff.F);

        assertEquals(B5.getCleff(NoteModel.Cleff.G), NoteModel.Cleff.G);
        assertEquals(B5.getCleff(NoteModel.Cleff.F), NoteModel.Cleff.G);

        assertEquals(D6.getCleff(NoteModel.Cleff.F), NoteModel.Cleff.G);
        assertEquals(D6.getCleff(NoteModel.Cleff.G), NoteModel.Cleff.G);
    }

    public void testToString() throws Exception {
        assertEquals(C2.toString(), "C2");
        assertEquals(C4.toString(), "C4");
        assertEquals(A2.toString(), "A2");
        assertEquals(A3.toString(), "A3");
        assertEquals(B1.toString(), "B1");
        assertEquals(B5.toString(), "B5");
        assertEquals(D3.toString(), "D3");
        assertEquals(D4.toString(), "D4");
        assertEquals(D6.toString(), "D6");
    }

    public void testGetDistance() throws Exception {
        assertEquals(C2.getDistance(B1), 1);
        assertEquals(C4.getDistance(B1), 15);
        assertEquals(A2.getDistance(B1), 6);
        assertEquals(A3.getDistance(B1), 13);
        assertEquals(B1.getDistance(B1), 0);
        assertEquals(B5.getDistance(B1), 28);
        assertEquals(D3.getDistance(B1), 9);
        assertEquals(D4.getDistance(B1), 16);
        assertEquals(D6.getDistance(B1), 30);

        assertEquals(B1.getDistance(D6), -30);
        assertEquals(C4.getDistance(D6), -15);
    }
}
