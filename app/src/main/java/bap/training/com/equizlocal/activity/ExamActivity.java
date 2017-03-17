package bap.training.com.equizlocal.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bap.training.com.equizlocal.R;
import bap.training.com.equizlocal.adapter.AnswerArrayAdapter;
import bap.training.com.equizlocal.controller.QuestionController;
import bap.training.com.equizlocal.controller.QuizController;
import bap.training.com.equizlocal.model.Question;
import bap.training.com.equizlocal.datahelper.QuestionStore;
import io.realm.Realm;

public class ExamActivity extends AppCompatActivity {
    private FloatingActionButton mFabPauseExam;
    private TextView mTvTotalQuestion, mTvCurrentAnswer, mTvQuestionContent, mTvWatch;
    private ListView mLvAnswerContent;
    private ProgressBar mProgressBarTime;
    private List<Question> mListQuestions = new ArrayList<>();
    private List<String> mListAnswers = null;
    private String mChoice, mCorrectAnswer;
    private final int TIME_TOTAL_FOR_EXAM = 60, TIME_FOR_WAIT_NEXT_ANSWER = 1500, TIME_FOR_WAIT_CHANGE_COLOR = 1000;
    private int mCurrentTime = TIME_TOTAL_FOR_EXAM, mCurrentIdQ = 1, mIndex = 1;
    private QuizController mQuizController;
    private QuestionController mQuestionController;
    private Thread mThreadTimer;
    private Realm mRealm;
    private AnswerArrayAdapter mAnswerArrayAdapter;
    private ListView.OnItemClickListener mOnItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_layout);
        init();
        getWidget();
        setWidget();
        loadFirstQuestion();
    }

    private void init() {
        mRealm = Realm.getDefaultInstance();
        mQuestionController = QuestionController.sGetsInstance();
        mQuizController = QuizController.sGetInstance();
        mListAnswers = new ArrayList<>();
        createDatabaseThenGetAllData();
    }

    private void getWidget() {
        mFabPauseExam = (FloatingActionButton) findViewById(R.id.fabPauseExam);
        mTvTotalQuestion = (TextView) findViewById(R.id.tvTotalQuestion);
        mTvQuestionContent = (TextView) findViewById(R.id.tvQuestionContent);
        mTvCurrentAnswer = (TextView) findViewById(R.id.tvCurrentQuestion);
        mLvAnswerContent = (ListView) findViewById(R.id.lvAnswer);
        mTvWatch = (TextView) findViewById(R.id.tvWatch);
        mProgressBarTime = (ProgressBar) findViewById(R.id.progressBarTime);
    }

    private void setWidget() {
        //start setup progressBar for Timers
        mProgressBarTime.setMax(TIME_TOTAL_FOR_EXAM);
        mProgressBarTime.setProgress(mProgressBarTime.getMax());
        mTvWatch.setText("01:00");
        mThreadTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (mCurrentTime > 0) {
                        Thread.sleep(1000);
                        mCurrentTime--;
                        mProgressBarTime.setProgress(mCurrentTime);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mCurrentTime >= 10) {
                                    mTvWatch.setText("00:" + mCurrentTime);
                                } else {
                                    mTvWatch.setText("00:" + "0" + mCurrentTime);
                                }
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mCurrentTime == 0 && mCurrentIdQ <= mListQuestions.size()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
                                LayoutInflater mLayoutInflater = LayoutInflater.from(getApplicationContext());
                                View layout = mLayoutInflater.inflate(R.layout.dialog_confirm_timeout, null);
                                Button mBtnResult = (Button) layout.findViewById(R.id.btnResult);
                                mBtnResult.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        goToResultActivity();
                                    }
                                });
                                builder.setView(layout);
                                builder.setCancelable(false);
                                MediaPlayer mMediaPlayer = MediaPlayer.create(ExamActivity.this, R.raw.audio_ring);
                                mMediaPlayer.start();
                                final AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                } catch (InterruptedException eDialog) {
                    eDialog.printStackTrace();
                }
            }
        });
        mThreadTimer.start();
        //end setup progressBar for Timers
        //start set on item list view click listener
        mOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIndex = position;
                mLvAnswerContent.setEnabled(false);
                TextView tv = (TextView) view.findViewById(R.id.tvAnswerItem);
                tv.setTextColor(Color.parseColor("#4CAF50"));
                mChoice = mListAnswers.get(mIndex).toString();
                mCorrectAnswer = mListQuestions.get(mCurrentIdQ - 1).getCorrectAnswer();
                /**
                 * dung 1 handler
                 */
                android.os.Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mQuizController.changeColorAnswerIsCorrect(mCorrectAnswer, mLvAnswerContent);
                        mQuizController.checkAndCompare(mIndex, mChoice, mCorrectAnswer, ExamActivity.this);
                    }
                }, TIME_FOR_WAIT_CHANGE_COLOR);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onNextAnswer();
                    }
                }, TIME_FOR_WAIT_NEXT_ANSWER);
            }
        };
        mLvAnswerContent.setOnItemClickListener(mOnItemClickListener);
        //end set on item list view click listener
        //start setup pause exam button
        mFabPauseExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogConfirmStopOrContinune();
            }
        });
        //end setup pause exam button
    }

    private void loadFirstQuestion() {
        mTvTotalQuestion.setText("Total" + " : " + mListQuestions.size());
        mListAnswers = mQuestionController.sAllAnswerById(mCurrentIdQ, mListQuestions);
        mAnswerArrayAdapter = new AnswerArrayAdapter(this, R.layout.item_list_answer, mListAnswers);
        mLvAnswerContent.setAdapter(mAnswerArrayAdapter);
        mTvQuestionContent.setText(mQuestionController.sGetQuestionById(mCurrentIdQ, mListQuestions));
        mTvCurrentAnswer.setText("Question" + " " + mCurrentIdQ + "/" + mListQuestions.size());
    }

    private void onNextAnswer() {
        mLvAnswerContent.setOnItemClickListener(null);
        mLvAnswerContent.setOnItemClickListener(mOnItemClickListener);
        mCurrentIdQ++;
        if (mCurrentIdQ <= mListQuestions.size()) {
            resetNewQuestionAndAnswer();
        } else if (mThreadTimer.isAlive() && (mCurrentTime != 0)) {
            goToResultActivity();
        }
        mLvAnswerContent.setEnabled(true);
    }

    private void goToResultActivity() {
        //stop thread timer
        mThreadTimer.interrupt();
        //
        List<Integer> mCorrectOrInCorrects = mQuizController.correctOrIncorrect();
        Intent mIntent = new Intent(this, ResultActivity.class);
        mIntent.putExtra("score", mCorrectOrInCorrects.get(0).toString());
        startActivity(mIntent);
        finish();
    }

    private void resetNewQuestionAndAnswer() {
        mChoice = "";
        mCorrectAnswer = "";
        mListAnswers = mQuestionController.sAllAnswerById(mCurrentIdQ, mListQuestions);
        mAnswerArrayAdapter = new AnswerArrayAdapter(this, R.layout.item_list_answer, mListAnswers);
        mLvAnswerContent.setAdapter(mAnswerArrayAdapter);
        mTvQuestionContent.setText(mQuestionController.sGetQuestionById(mCurrentIdQ, mListQuestions));
        mTvCurrentAnswer.setText("Question" + " " + mCurrentIdQ + "/" + mListQuestions.size());
    }

    private void createDatabaseThenGetAllData() {
        if (mRealm.where(Question.class).isValid()) {
            String[] mQuestions = getResources().getStringArray(R.array.questions);
            String[] mAnswers = getResources().getStringArray(R.array.answers);
            String[] mCorrects = getResources().getStringArray(R.array.correctAnswers);
            QuestionStore mQuestionStore = QuestionStore.sGetsInstance();
            mQuestionStore.addData(mRealm, mQuestions, mAnswers, mCorrects);
        }
        mListQuestions = mQuestionController.getAllDataFromDatabase(mRealm);
    }

    private void showDialogConfirmStopOrContinune() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View mLayout = mLayoutInflater.inflate(R.layout.dialog_confirm_onbackpressed, null);
        Button mBtnTieptuc = (Button) mLayout.findViewById(R.id.btnTiepTuc);
        Button mBtnDung = (Button) mLayout.findViewById(R.id.btnDung);
        mBuilder.setView(mLayout);
        final Dialog mDialog = mBuilder.create();
        mBtnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mBtnDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToResultActivity();
            }
        });
        mDialog.show();
    }

    @Override
    protected void onStop() {
        mQuizController.correct = 0;
        mQuizController.incorrect = 0;
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        showDialogConfirmStopOrContinune();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
