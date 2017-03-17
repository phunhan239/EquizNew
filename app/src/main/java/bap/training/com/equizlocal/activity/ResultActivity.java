package bap.training.com.equizlocal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import bap.training.com.equizlocal.R;

public class ResultActivity extends AppCompatActivity {
    private TextView mTvCloseButton;
    private TextView mTvYourScoreNumber;
    private TextView mTvNumberCorrectAnswer;
    private TextView.OnClickListener mOnClickListener;
    private RatingBar mRatingBar;
    private String mScore;
    private float mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_layout);
        init();
        getWidgets();
        setWidgets();
    }

    private void init() {
        mScore = getIntent().getStringExtra("score");
    }

    private void getWidgets() {
        mTvCloseButton = (TextView) findViewById(R.id.tvCloseButton);
        mTvYourScoreNumber = (TextView) findViewById(R.id.tvYourScoreNumber);
        mTvNumberCorrectAnswer = (TextView) findViewById(R.id.tvNumberCorrectAnswer);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
    }

    private void setWidgets() {
        mTvNumberCorrectAnswer.setText("Bạn đã trả lời đúng " + mScore + "/" + "10");
        mTvYourScoreNumber.setText(mScore);
        setValueRatingBar();
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        };
        mTvCloseButton.setOnClickListener(mOnClickListener);
    }

    private void setValueRatingBar() {
        //set color layer of rating bar
        LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        //setup rating
        mRatingBar.setStepSize(0.5f);
        mRating = (Float.valueOf(mScore) / 2);
        mRatingBar.setRating(mRating);
    }
}

