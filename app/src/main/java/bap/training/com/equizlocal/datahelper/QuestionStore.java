package bap.training.com.equizlocal.datahelper;

import bap.training.com.equizlocal.model.Question;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by D'van on 1/18/2017.
 */
public class QuestionStore {
    private static QuestionStore instance = null;

    public static QuestionStore sGetsInstance() {
        if (instance == null) {
            instance = new QuestionStore();
        }
        return instance;
    }

    public RealmResults<Question> getQuestions(Realm mRealm) {
        RealmResults<Question> results = mRealm.where(Question.class).findAll();
        return results;
    }

    public void addData(Realm mRealm, String[] questions, String[] answers, String[] correctAnswers) {
        clearData(mRealm);
        int dem = 0;
        mRealm.beginTransaction();
        for (int i = 0; i < questions.length; i++) {
            Question question = mRealm.createObject(Question.class);
            question.setIdQ(i + 1);
            question.setContentQ(questions[i]);
            question.setOptionA(answers[dem]);
            question.setOptionB(answers[dem + 1]);
            question.setOptionC(answers[dem + 2]);
            question.setOptionD(answers[dem + 3]);
            question.setCorrectAnswer(correctAnswers[i]);
            dem = dem + 4;
        }
        mRealm.commitTransaction();
    }

    private void clearData(Realm mRealm) {
        RealmResults<Question> results = getQuestions(mRealm);
        mRealm.beginTransaction();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }
}
