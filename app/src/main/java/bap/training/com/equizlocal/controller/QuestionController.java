package bap.training.com.equizlocal.controller;

import java.util.ArrayList;
import java.util.List;

import bap.training.com.equizlocal.model.Question;
import bap.training.com.equizlocal.datahelper.QuestionStore;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by D'van on 1/18/2017.
 */
public class QuestionController {
    private static QuestionStore sQuestionStore = QuestionStore.sGetsInstance();
    private static QuestionController sInstance = null;

    public static QuestionController sGetsInstance() {
        if (sInstance == null) {
            sInstance = new QuestionController();
        }
        return sInstance;
    }

    //return a List of Question
    public List<Question> getAllDataFromDatabase(Realm mRealm) {
        List<Question> mLists = new ArrayList<>();
        RealmResults<Question> mResults = sQuestionStore.getQuestions(mRealm);
        for (Question mQuestion : mResults) {
            mLists.add(mQuestion);
        }
        return mLists;
    }

    public static List<String> sAllAnswerById(int id, List<Question> mLists) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }

        List<String> mListResults = new ArrayList<>();
        for (Question q : mLists) {
            if (q.getIdQ() == id) {
                mListResults.add(q.getOptionA());
                mListResults.add(q.getOptionB());
                mListResults.add(q.getOptionC());
                mListResults.add(q.getOptionD());
                break;
            }
        }
        return mListResults;
    }

    public static String sGetQuestionById(int id, List<Question> mLists) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }
        String mResult = "";
        for (Question mQuestion : mLists) {
            if (mQuestion.getIdQ() == id) {
                //this question first have form : "1. CAD is the abbreviation of……"
                //split string to get question have form : "CAD is the abbreviation of……"
                mResult = mQuestion.getContentQ().replaceFirst(mQuestion.getContentQ().split("\\.")[0], "").replaceFirst(".", "").toString();
                break;
            }
        }
        return mResult;
    }
}
