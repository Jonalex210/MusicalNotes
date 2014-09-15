package com.vincewu.musicalnotes;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Custom View for showing a "motivational" dialog.
 * Clients are responsible for calling onDestroyView to clean-up.
 */
public class MotivatorDialog implements DialogInterface.OnDismissListener {

    /* There are only two possible dialogs: correct or wrong.
     * Store the two possibilities as static members for reuse. */
    private static LinearLayout sCorrectAnswerLayout;
    private static LinearLayout sWrongAnswerLayout;

    /* Other shared resources */
    private static final ScheduledExecutorService sExecutor =
            Executors.newSingleThreadScheduledExecutor();
    private static MediaPlayer sCorrectSound;
    private static MediaPlayer sWrongSound;

    /**
     *  Calling class must call onDestroyView to clean up
     */
    public static void onDestroyView() {
        if (sCorrectSound != null) {
            sCorrectSound.release();
            sCorrectSound = null;
        }
        if (sWrongSound != null) {
            sWrongSound.release();
            sWrongSound = null;
        }
        sCorrectAnswerLayout = null;
        sWrongAnswerLayout = null;
    }


    private Context mContext;
    private AlertDialog mAlertDialog;
    private ImageView mMotivatorImageView;
    private boolean mIsAnswerCorrect;
    private DialogInterface.OnDismissListener mDismissListener;

    /* TODO: change this to static factory with 2 pre-built objects */

    MotivatorDialog(Context context, boolean isAnswerCorrect, String selectedKey) {
        mContext = context;
        mIsAnswerCorrect = isAnswerCorrect;

        // We open a dialog every single time the answer guesses -- reuse the linear layouts
        if (sCorrectAnswerLayout == null || sWrongAnswerLayout == null) {
            // Correct Answer - never changes
            sCorrectAnswerLayout =
                    (LinearLayout) View.inflate(mContext, R.layout.dialog_motivator, null);
            TextView textView =
                    (TextView)sCorrectAnswerLayout.findViewById(R.id.motivatorDialogTextView);
            textView.setText("Awesome!");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    mContext.getResources().getInteger(R.integer.MotivatorTextSize_Correct));
            ((ImageView) sCorrectAnswerLayout.findViewById(R.id.motivatorDialogImageView))
                    .setImageResource(R.drawable.correct_motivator);

            // Wrong Answer - text changes
            sWrongAnswerLayout =
                    (LinearLayout) View.inflate(mContext, R.layout.dialog_motivator, null);
            ((TextView) sWrongAnswerLayout.findViewById(R.id.motivatorDialogTextView))
                    .setTextSize(TypedValue.COMPLEX_UNIT_SP,
                            mContext.getResources().getInteger(R.integer.MotivatorTextSize_Wrong));
            ((ImageView) sWrongAnswerLayout.findViewById(R.id.motivatorDialogImageView))
                    .setImageResource(R.drawable.wrong_motivator);
        }

        // Same music played every time - reuse them
        if (sCorrectSound == null || sWrongSound == null) {
            sCorrectSound = MediaPlayer.create(mContext, R.raw.correct_sound);
            sWrongSound = MediaPlayer.create(mContext, R.raw.wrong_sound);
        }


        // If answer is wrong, tell the user what they selected
        if (!mIsAnswerCorrect) {
            ((TextView) sWrongAnswerLayout.findViewById(R.id.motivatorDialogTextView))
                    .setText("Oops... " + selectedKey + " is wrong.\nTry again!");
        }
        mMotivatorImageView = (ImageView) (mIsAnswerCorrect
                ? sCorrectAnswerLayout.findViewById(R.id.motivatorDialogImageView)
                : sWrongAnswerLayout.findViewById(R.id.motivatorDialogImageView));

        // Create an alert dialog for this instance
        mAlertDialog = new AlertDialog.Builder(mContext)
                .setView(mIsAnswerCorrect ? sCorrectAnswerLayout : sWrongAnswerLayout).create();
        mAlertDialog.setOnDismissListener(this);

    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        mDismissListener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        View layout = mIsAnswerCorrect ? sCorrectAnswerLayout : sWrongAnswerLayout;
        ((ViewGroup)layout.getParent()).removeView(layout);

        mDismissListener.onDismiss(dialog);
    }


    public void show() {
        mAlertDialog.show();

        // Animate: image "bounces in" from above
        ObjectAnimator imageAnimator =
                ObjectAnimator.ofFloat(mMotivatorImageView, ImageView.TRANSLATION_Y, -40f, 0f);
        imageAnimator.setDuration(1000); // 1sec
        imageAnimator.setInterpolator(new BounceInterpolator());
        imageAnimator.start();

        // Auto dismiss dialog after 1.5s
        Runnable hideDialog = new Runnable() {
            public void run() {
                mAlertDialog.dismiss();
            }
        };
        sExecutor.schedule(hideDialog, 1500, TimeUnit.MILLISECONDS);

        // Play music
        if (mIsAnswerCorrect) {
            sCorrectSound.start();
        } else {
            sWrongSound.start();
        }
    }


}
