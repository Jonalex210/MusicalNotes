package com.vincewu.musicalnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment  implements OnTouchListener {

        private final static char[] sNotesInAScale = {'C', 'D', 'E', 'F', 'G', 'A', 'B'};

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            LinearLayout rootView = (LinearLayout)inflater.inflate(
                    R.layout.random_musical_note, container, false);

            /*
             * Show a random note
             */
            NoteImageView noteImageView = new NoteImageView(getActivity());
            LinearLayout.LayoutParams noteImageViewLayoutParams = new  LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            noteImageViewLayoutParams.gravity = Gravity.CENTER;
            noteImageViewLayoutParams.weight = 3.0f;
            noteImageView.setLayoutParams(noteImageViewLayoutParams);
            noteImageView.setBackground(getResources().getDrawable(R.drawable.debug_border));

            /*
             * 4-octave piano scale
             * TODO: turn this into a custom viewgroup
             * TODO: require minimum width, and dynamically calculate size for keys
             */
            RelativeLayout rl = new RelativeLayout(getActivity());
            rl.setBackground(getResources().getDrawable(R.drawable.debug_border));

            // draw white keys
            for (int i=0; i<28; i++) {
                ImageView iv;
                RelativeLayout.LayoutParams params;

                iv = new ImageView(getActivity());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.white_key));
                iv.setScaleType(ImageView.ScaleType.FIT_XY);

                // for debugging: which key was tapped, e.g. "C4"
                iv.setTag(R.id.NOTES_TAG, "" + sNotesInAScale[i%7] + (i/4));
                iv.setOnTouchListener(this);

                params = new RelativeLayout.LayoutParams(56, 220);
                params.leftMargin = 54 * i + 10;
                params.topMargin = 10;
                params.bottomMargin = 10;
                rl.addView(iv, params);
            }

            // draw black keys
            for (int i=0; i<20; i++) {
                ImageView iv;
                RelativeLayout.LayoutParams params;

                iv = new ImageView(getActivity());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.black_key));
                iv.setScaleType(ImageView.ScaleType.FIT_XY);

                params = new RelativeLayout.LayoutParams(40, 132);
                // black key pattern is groups of 2 and 3
                params.leftMargin = (54 * ((i/5 * 2) + (i%5 > 1 ? i+1 : i)) ) + 10 + 35;
                params.topMargin = 11;
                params.bottomMargin = 10;
                rl.addView(iv, params);
            }

            rootView.addView(noteImageView);
            rootView.addView(rl);

            return rootView;
        }


        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            final ImageView iv = (ImageView)view;
            iv.setImageDrawable(getResources().getDrawable(R.drawable.highlight_key));

            new AlertDialog.Builder(getActivity())
                .setTitle("Touched " + view.getTag(R.id.NOTES_TAG))
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(final DialogInterface dialog) {
                        iv.setImageDrawable(getResources().getDrawable(R.drawable.white_key));
                    }
                })
                .show();

            return false;
        }
    }


}
