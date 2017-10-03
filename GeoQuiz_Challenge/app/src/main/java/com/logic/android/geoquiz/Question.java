package com.logic.android.geoquiz;

/**
 * Created by dominik on 06.09.17.
 */

public class Question {

    private int mTextResId; //question text
    private boolean mAnswerTrue; //right or wrong
    private boolean mAlreadyAnswered = false;

    public Question(int textResId, boolean answerTrue) {

        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
    public boolean isAlreadyAnswered(){return mAlreadyAnswered;}
    public void setAlreadyAnswered(boolean alreadyAnswered) { mAlreadyAnswered = alreadyAnswered; }

}
