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
import android.widget.LinearLayout;


public class Main extends Activity {

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private NoteModel mNoteModel;
        private NoteImageView mNoteImageView;
        private PianoView mPianoView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            LinearLayout rootView = (LinearLayout)inflater.inflate(
                    R.layout.fragment_main, container, false);
            rootView.setBackground(getResources().getDrawable(R.drawable.debug_border));

            /*
             * View displays a random note for the user to guess
             * TODO: this is hardcoded to show notes relevant for a piano. Make this configurable?
             */
            mNoteImageView = new NoteImageView(getActivity());
            LinearLayout.LayoutParams noteImageViewLayoutParams = new  LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            noteImageViewLayoutParams.gravity = Gravity.CENTER;
            noteImageViewLayoutParams.weight = 3.0f;
            mNoteImageView.setLayoutParams(noteImageViewLayoutParams);
            //mNoteImageView.setBackground(getResources().getDrawable(R.drawable.debug_border));

            nextRandomNote();

            mNoteImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // let the user skip a note by tapping
                    // TODO: do we really need this?
                    if (v instanceof NoteImageView) nextRandomNote();
                }
            });

            /*
             * View displays a musical instrument
             * TODO: this is hardcoded to the piano. Make this configurable?
             */
            mPianoView = new PianoView(getActivity());
            LinearLayout.LayoutParams pianoViewLayoutParams = new  LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            pianoViewLayoutParams.gravity = Gravity.CENTER;
            mPianoView.setLayoutParams(pianoViewLayoutParams);
            //mPianoView.setBackground(getResources().getDrawable(R.drawable.debug_border));

            mPianoView.setPianoOnClickListener(new PianoView.PianoOnClickListener() {
                public void onClick(NoteModel clickedNote) {
                    // Check whether user is right or wrong
                    if (clickedNote == null) return;
                    final boolean isAnswerCorrect = clickedNote.equals(mNoteModel);

                    // Draw "motivator" dialog
                    MotivatorDialog motivatorDialog =
                            new MotivatorDialog(getActivity(),
                                    isAnswerCorrect, clickedNote.toString());
                    // When dismissed, show another random musical note
                    motivatorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        public void onDismiss(final DialogInterface dialog) {
                            mPianoView.resetClickedKey();
                            if (isAnswerCorrect) nextRandomNote();
                        }
                    });
                    motivatorDialog.show();
                }
            });

            // attach
            rootView.addView(mNoteImageView);
            rootView.addView(mPianoView);

            return rootView;
        }

        /* Display another random note */
        private void nextRandomNote() {
            NoteModel newNote = NoteModel.getRandomNote();
            int guard = 0;
            while (newNote.equals(mNoteModel) && guard < 10) {
                newNote = NoteModel.getRandomNote();
                guard++;
            }
            mNoteModel = newNote;
            mNoteImageView.setNoteModel(mNoteModel);
        }

        @Override
        public void onDestroyView() {
            // Must clean up static resources in MotivatorDialog
            MotivatorDialog.onDestroyView();

            super.onDestroyView();
        }

        public PlaceholderFragment() {}
    }




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

}
