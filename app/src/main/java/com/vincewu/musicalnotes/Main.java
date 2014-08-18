package com.vincewu.musicalnotes;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


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
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.random_musical_note, container, false);

            NoteImageView noteImageView = new NoteImageView(getActivity());
            LinearLayout.LayoutParams noteImageViewLayoutParams = new  LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            noteImageViewLayoutParams.gravity = Gravity.CENTER;
            noteImageViewLayoutParams.weight = 3.0f;
            noteImageView.setLayoutParams(noteImageViewLayoutParams);

            ImageView placeholder = new ImageView(getActivity());
            placeholder.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
            placeholder.setBackground(getResources().getDrawable(R.drawable.layer_testnote));
            LinearLayout.LayoutParams placeholderLayoutParams = new  LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            placeholderLayoutParams.gravity = Gravity.CENTER;
            placeholderLayoutParams.weight = 1.0f;
            placeholder.setLayoutParams(placeholderLayoutParams);

            rootView.addView(noteImageView);
            rootView.addView(placeholder);

            return rootView;
        }
    }
}
