package com.vincewu.musicalnotes;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.FrameLayout;
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

            /*
            FrameLayout frame = (FrameLayout)rootView.findViewById(R.id.using_frameLayout);

            ImageView img1 = new ImageView(getActivity());
            img1.setImageDrawable(getResources().getDrawable(R.drawable.note_f4));
            img1.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            frame.addView(img1);

            ImageView img2 = new ImageView(getActivity());
            img2.setImageDrawable(getResources().getDrawable(R.drawable.f_cleff));
            img2.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            frame.addView(img2);

            ImageView img3 = new ImageView(getActivity());
            img3.setImageDrawable(getResources().getDrawable(R.drawable.line_staff));
            img3.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            frame.addView(img3);



            Drawable[] layers = new Drawable[3];
            layers[0] = getResources().getDrawable(R.drawable.note_f4);
            layers[1] = getResources().getDrawable(R.drawable.g_cleff);
            layers[2] = getResources().getDrawable(R.drawable.line_staff);
            LayerDrawable layerDrawable = new LayerDrawable(layers);

            ImageView testImage = (ImageView)rootView.findViewById(R.id.using_layerDrawable);
            testImage.setImageDrawable(layerDrawable);
            */

            NoteImageView noteImageView = new NoteImageView(getActivity());
            noteImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            rootView.addView(noteImageView);

            return rootView;
        }
    }
}
