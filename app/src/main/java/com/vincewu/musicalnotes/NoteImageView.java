package com.vincewu.musicalnotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Random;

public class NoteImageView extends ImageView {

    private static Random sRandom = new Random();

    private Context mContext;
    private Drawable[] mLayers;
    private boolean mIsGCleff;

    private static int[] mNoteDrawables = {
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

    public NoteImageView(Context context) {
        super(context);

        this.mContext = context;

        this.mLayers = new Drawable[3];
        this.mLayers[0] = getResources().getDrawable(R.drawable.line_staff);
        this.mLayers[1] = getResources().getDrawable(R.drawable.note_1_7);
        this.mLayers[2] = getResources().getDrawable(R.drawable.f_cleff);

        LayerDrawable layerDrawable = new LayerDrawable(mLayers);
        setImageDrawable(layerDrawable);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mIsGCleff = (sRandom.nextInt(2) == 1);
        this.mLayers[2] = getResources().getDrawable(mIsGCleff ? R.drawable.g_cleff : R.drawable.f_cleff);
        this.mLayers[1] = getResources().getDrawable(mNoteDrawables[sRandom.nextInt(mNoteDrawables.length)]);

        LayerDrawable layerDrawable = new LayerDrawable(mLayers);
        setImageDrawable(layerDrawable);

        return super.onTouchEvent(event);
    }
}
