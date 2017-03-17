package bap.training.com.equizlocal.model;

import io.realm.RealmObject;

/**
 * table question
 * Created by D'van on 1/18/2017.
 */
public class Question extends RealmObject {
    public static final String TABLE_QUESTION = "Question";
    public static final String FIELD_IDQ = "idQ";
    public static final String FIELD_CONTENTQ = "contentQ";
    public static final String FIELD_OPTIONA = "optionA";
    public static final String FIELD_OPTIONB = "optionB";
    public static final String FIELD_OPTIONC = "optionC";
    public static final String FIELD_OPTIOND = "optionD";
    public static final String FIELD_CORRECTANSWER = "correctAnswer";
    private int idQ;
    private String contentQ;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;

    public int getIdQ() {
        return idQ;
    }

    public void setIdQ(int idQ) {
        this.idQ = idQ;
    }

    public String getContentQ() {
        return contentQ;
    }

    public void setContentQ(String contentQ) {
        this.contentQ = contentQ;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
