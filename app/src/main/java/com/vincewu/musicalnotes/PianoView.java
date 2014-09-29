package com.vincewu.musicalnotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Custom View for displaying Piano keys
 *
 * Defines custom PianoOnClickListener: calling classes should attach a listener to listen for
 * "piano key" presses
 */
public class PianoView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private PianoOnClickListener mOnClickListener;
    private ImageView mClickedKey;

    public PianoView(Context context) {
        super(context);
        this.mContext = context;
        initViews();
    }

    private void initViews() {
        // Standard notes for piano spans B1 to D6: total of 31 white keys
        // TODO: dimensions are hardcoded right now to Nexus 7 landscape -- fix this

        // draw white keys
        for (int key_idx=0; key_idx<31; key_idx++) {
            ImageView iv = new ImageView(mContext);
            iv.setImageDrawable(getResources().getDrawable(R.drawable.white_key));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            // store key e.g. "C4"
            iv.setTag(R.id.NOTES_TAG,
                    (new NoteModel(NoteModel.sNotesCMaj.get((key_idx+6)%7), //starts at B, so +6
                            (key_idx+6)/7+1))); //starts at B1, so +1
            iv.setOnClickListener(this);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(62, 220);
            params.leftMargin = 60 * key_idx;
            params.topMargin = 10;
            params.bottomMargin = 10;
            this.addView(iv, params);

            // label C4 - TODO: styles all hardcoded
            if (key_idx == 15) {
                TextView tv = new TextView(mContext);
                tv.setText("C4");
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                tv.setTextColor(Color.DKGRAY);
                tv.setGravity(Gravity.CENTER);

                params = new RelativeLayout.LayoutParams(60,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                params.leftMargin = 60 * key_idx;
                params.topMargin = 180;
                params.bottomMargin = 10;
                this.addView(tv, params);
            }
        }

        // draw black keys
        for (int i=0; i<21; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setImageDrawable(getResources().getDrawable(R.drawable.black_key));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(46, 132);
            // black key pattern: groups of 2 and 3
            params.leftMargin = (60 // match white key left margin
                    * ((i/5 * 2) // each octave only has 5 black keys, *2 catches up to # of whites
                    + (i%5 > 1 ? i+1 : i)) ) // # of keys to pad within an octave
                    + 37 + 60; // relative to first black key
            params.topMargin = 11;
            params.bottomMargin = 10;
            this.addView(iv, params);
        }
    }

    public void setPianoOnClickListener(PianoOnClickListener listener) {
        this.mOnClickListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void resetClickedKey() {
        if (mClickedKey != null) {
            mClickedKey.setImageDrawable(getResources().getDrawable(R.drawable.white_key));
        }
        mClickedKey = null;
    }

    /**
     * Check whether selected note matches and show appropriate message.
     * If note matches, display another random note.
     */
    @Override
    public void onClick(View view) {
        if (view instanceof ImageView) {
            // Highlight the piano key
            mClickedKey = (ImageView) view;
            mClickedKey.setImageDrawable(getResources().getDrawable(R.drawable.highlight_key));

            // Bubble up event
            NoteModel clickedNote = (NoteModel) view.getTag(R.id.NOTES_TAG);
            if (mOnClickListener != null) mOnClickListener.onClick(clickedNote);
        }
    }

    public static interface PianoOnClickListener {
        public void onClick(NoteModel clickedNote);
    }
}
