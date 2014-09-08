package com.vincewu.musicalnotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView;

import java.util.Random;

public class NoteImageView extends ImageView {

    // for setting preferred cleff
    private static Random sRandom = new Random();

    /*
     * For measuring note distances:
     * ...lowest note of F Cleff is B1
     * ...lowest note of G Cleff is G3
     */
    private static final NoteModel sBaseGCleffNote = new NoteModel(NoteModel._G, 3);
    private static final NoteModel sBaseFCleffNote = new NoteModel(NoteModel._B, 1);

    // notes from lowest to highest that can be drawn on a line staff
    private static int[] sNoteDrawables = {
            R.drawable.note_1_1,
            R.drawable.note_1_2,
            R.drawable.note_1_3,
            R.drawable.note_1_4,
            R.drawable.note_1_5,
            R.drawable.note_1_6,
            R.drawable.note_1_7,
            R.drawable.note_2_1,
            R.drawable.note_2_2,
            R.drawable.note_2_3,
            R.drawable.note_2_4,
            R.drawable.note_2_5,
            R.drawable.note_2_6,
            R.drawable.note_2_7,
            R.drawable.note_3_1,
            R.drawable.note_3_2,
            R.drawable.note_3_3,
            R.drawable.note_3_4,
            R.drawable.note_3_5
    };



    private Context mContext;
    private Drawable[] mLayers;
    private NoteModel noteModel;


    public NoteImageView(Context context) {
        super(context);
        this.mContext = context;
        this.mLayers = new Drawable[3];
        this.mLayers[0] = getResources().getDrawable(R.drawable.line_staff);
    }

    /**
     * Draw a new note on screen
     * @param noteModel
     */
    public void setNoteModel(NoteModel noteModel) {
        this.noteModel = noteModel;

        // A note is comprised of multiple layers:
        // 1: line staff
        // 2: note
        // 3: cleff

        NoteModel.Cleff cleff = noteModel.getCleff(
                (sRandom.nextBoolean()) ? NoteModel.Cleff.G : NoteModel.Cleff.F);

        this.mLayers[1] = getResources().getDrawable(sNoteDrawables[
                noteModel.getDistance(
                        cleff == NoteModel.Cleff.G ? sBaseGCleffNote : sBaseFCleffNote)
                ]);

        this.mLayers[2] = getResources().getDrawable(
                cleff == NoteModel.Cleff.G ? R.drawable.g_cleff : R.drawable.f_cleff);

        LayerDrawable layerDrawable = new LayerDrawable(mLayers);
        setImageDrawable(layerDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
