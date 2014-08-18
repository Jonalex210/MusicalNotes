package com.vincewu.musicalnotes;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Random;

public class NoteImageView extends ImageView {

    private Context context;
    private Drawable[] layers;
    private boolean g_cleff;

    private static int[] all_notes = {
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

    private static Random randum = new Random();

    public NoteImageView(Context context) {
        super(context);

        this.context = context;

        this.layers = new Drawable[3];
        this.layers[0] = getResources().getDrawable(R.drawable.line_staff);
        this.layers[1] = getResources().getDrawable(R.drawable.note_1_7);
        this.layers[2] = getResources().getDrawable(R.drawable.f_cleff);

        LayerDrawable layerDrawable = new LayerDrawable(layers);
        setImageDrawable(layerDrawable);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
/*
        new AlertDialog.Builder(this.context)
                .setTitle("Touched!")
                .show();
*/

        g_cleff = (randum.nextInt(2) == 1);
        this.layers[2] = getResources().getDrawable(g_cleff ? R.drawable.g_cleff : R.drawable.f_cleff);
        this.layers[1] = getResources().getDrawable(all_notes[randum.nextInt(all_notes.length)]);

        LayerDrawable layerDrawable = new LayerDrawable(layers);
        setImageDrawable(layerDrawable);

        return super.onTouchEvent(event);

    }
}
