package bap.training.com.equizlocal.controller;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import bap.training.com.equizlocal.R;


/**
 * Created by D'van on 1/19/2017.
 */

/**
 * this class handle logic of exam
 */
public class QuizController {
    public int correct = 0;
    public int incorrect = 0;
    private static QuizController sInstance = null;

    public static QuizController sGetInstance() {
        if (sInstance == null) {
            sInstance = new QuizController();
        }
        return sInstance;
    }

    public void changeColorAnswerIsCorrect(String mCorrectAnswer, ListView mListView) {
        int mId = 0;
        switch (mCorrectAnswer) {
            case "A":
                mId = 0;
                break;
            case "B":
                mId = 1;
                break;
            case "C":
                mId = 2;
                break;
            case "D":
                mId = 3;
                break;
        }
        LinearLayout mView = (LinearLayout) mListView.getChildAt(mId).findViewById(R.id.llItemListView);
        TransitionDrawable mTransitionDrawable = (TransitionDrawable) mView.getBackground();
        mTransitionDrawable.startTransition(500);
    }

    /**
     * kiem tra , doi chieu , so sanh cau tra loi
     *
     * @param mIndex
     * @param mChoice
     * @param mCorrectAnswer
     * @param mContext
     */
    public void checkAndCompare(int mIndex, String mChoice, String mCorrectAnswer, Context mContext) {
        //start check
        switch (mIndex) {
            case 0:
                mChoice = "A";
                break;
            case 1:
                mChoice = "B";
                break;
            case 2:
                mChoice = "C";
                break;
            case 3:
                mChoice = "D";
                break;
            default:
                break;
        }
        //end check
        //start compare
        if (mChoice.equals(mCorrectAnswer.trim())) {
            correct++;
        } else {
            MediaPlayer mMediaPlayer = MediaPlayer.create(mContext, R.raw.audio_wrong);
            mMediaPlayer.start();
            incorrect++;
        }
        //end compare
    }

    public List<Integer> correctOrIncorrect() {
        List<Integer> mListResults = new ArrayList<>();
        mListResults.add(correct);
        mListResults.add(incorrect);
        return mListResults;
    }
}
