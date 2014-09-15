package com.vincewu.musicalnotes;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        // TODO: used for tagging keys. Refactor this.
        private final static char[] sNotesInAScale = {'C', 'D', 'E', 'F', 'G', 'A', 'B'};

        private NoteModel mNoteModel;
        private NoteImageView mNoteImageView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            LinearLayout rootView = (LinearLayout)inflater.inflate(
                    R.layout.fragment_main, container, false);
            rootView.setBackground(getResources().getDrawable(R.drawable.debug_border));

            /*
             * Show a random note
             */
            mNoteImageView = new NoteImageView(getActivity());
            LinearLayout.LayoutParams noteImageViewLayoutParams = new  LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            noteImageViewLayoutParams.gravity = Gravity.CENTER;
            noteImageViewLayoutParams.weight = 3.0f;
            mNoteImageView.setLayoutParams(noteImageViewLayoutParams);
            mNoteImageView.setBackground(getResources().getDrawable(R.drawable.debug_border));

            mNoteModel = NoteModel.getRandomNote();
            mNoteImageView.setNoteModel(mNoteModel);

            mNoteImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (v instanceof NoteImageView) {
                        NoteModel newNote = NoteModel.getRandomNote();
                        int guard = 0;
                        while (newNote.equals(mNoteModel) && guard < 10) {
                            newNote = NoteModel.getRandomNote();
                            guard++;
                        }
                        mNoteModel = newNote;
                        ((NoteImageView) v).setNoteModel(mNoteModel);
                    }
                }
            });

            /*
             * 4-octave piano scale
             * TODO: turn this into a custom viewgroup
             * TODO: require minimum width, and dynamically calculate size for keys
             */
            RelativeLayout rl = new RelativeLayout(getActivity());
            rl.setBackground(getResources().getDrawable(R.drawable.debug_border));
            LinearLayout.LayoutParams pianoKeysLayoutParams = new  LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            pianoKeysLayoutParams.gravity = Gravity.CENTER;
            rl.setLayoutParams(pianoKeysLayoutParams);


            // draw white keys
            for (int i=0; i<28; i++) {
                ImageView iv;
                RelativeLayout.LayoutParams params;

                iv = new ImageView(getActivity());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.white_key));
                iv.setScaleType(ImageView.ScaleType.FIT_XY);

                // for debugging: which key was tapped, e.g. "C4"
                iv.setTag(R.id.NOTES_TAG, "" + sNotesInAScale[i%7] + (i/7 + 2));
                iv.setOnClickListener(this);

                params = new RelativeLayout.LayoutParams(62, 220);
                params.leftMargin = 60 * i;
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

                params = new RelativeLayout.LayoutParams(46, 132);
                // black key pattern is groups of 2 and 3
                params.leftMargin = (60 * ((i/5 * 2) + (i%5 > 1 ? i+1 : i)) ) + 37;
                params.topMargin = 11;
                params.bottomMargin = 10;
                rl.addView(iv, params);
            }

            rootView.addView(mNoteImageView);
            rootView.addView(rl);

            return rootView;
        }

        @Override
        public void onDestroyView() {
            // Must clean up static resources in MotivatorDialog
            MotivatorDialog.onDestroyView();

            super.onDestroyView();
        }


        /**
         * Check whether selected note matches and show appropriate message.
         * If note matches, display another random note.
         */
        @Override
        public void onClick(View view) {
            // Highlight the piano key
            final ImageView iv = (ImageView)view;
            iv.setImageDrawable(getResources().getDrawable(R.drawable.highlight_key));

            // Check whether user is right or wrong
            String touchedNote = (String)view.getTag(R.id.NOTES_TAG);
            final boolean isAnswerCorrect = touchedNote.equals(mNoteModel.toString());

            // Draw "motivator" dialog
            MotivatorDialog motivatorDialog =
                    new MotivatorDialog(getActivity(), isAnswerCorrect, touchedNote);
            // When dismissed, show another random musical note
            motivatorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(final DialogInterface dialog) {
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.white_key));
                    if (isAnswerCorrect) {
                        NoteModel newNote = NoteModel.getRandomNote();
                        int guard = 0;
                        while (newNote.equals(mNoteModel) && guard < 10) {
                            newNote = NoteModel.getRandomNote();
                            guard++;
                        }
                        mNoteModel = newNote;
                        mNoteImageView.setNoteModel(mNoteModel);
                    }
                }
            });
            motivatorDialog.show();
        }

        public PlaceholderFragment() {
        }
    }
}
