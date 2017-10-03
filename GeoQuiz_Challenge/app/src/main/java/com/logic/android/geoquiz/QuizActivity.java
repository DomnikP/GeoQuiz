package com.logic.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {



    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX ="index";
    private static final String KEY_INDEX_QUESTIONS_ANSWERED ="index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String IS_CHEATER = "false";
    private static final String TAG2 = "CheatActivity";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private Button mCheatButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;






    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mIsCheater = false;
        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean(IS_CHEATER, false);
        }

        if(savedInstanceState !=null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

            boolean[] questionAnswered = savedInstanceState.getBooleanArray(KEY_INDEX_QUESTIONS_ANSWERED);
            assert questionAnswered != null;
            for (int i = 0; i < questionAnswered.length; i++)
                mQuestionBank[i].setAlreadyAnswered(questionAnswered[i]);

        }





        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mQuestionTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               checkAnswer(true);


            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                --mCurrentIndex;


                if(mCurrentIndex < 0)
                {
                    mCurrentIndex = mQuestionBank.length-1;
                }
                updateQuestion();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();

            }
        });
        updateQuestion();

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);

                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });


    }

        @Override
        public void onStart(){
           super.onStart();
            Log.d(TAG, "onStart() called");
        }

        @Override
        public void onResume(){
            super.onResume();
            Log.d(TAG, "onResume() called");
        }

        @Override
        public void onPause(){
            super.onPause();
            Log.d(TAG, "onPause() called");
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            Log.i(TAG, "onSaveInstanceState");
            savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

            boolean[] questionsAnswered = new boolean[mQuestionBank.length];
            for(int i = 0; i < mQuestionBank.length; i++){
                questionsAnswered[i] = mQuestionBank[i].isAlreadyAnswered();
                savedInstanceState.putBooleanArray(KEY_INDEX_QUESTIONS_ANSWERED,questionsAnswered);
            }
        }


    public void saveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(IS_CHEATER, mIsCheater);
    }




        @Override
        public void onStop(){
            super.onStop();
            Log.d(TAG, "onStop() called");
        }

        @Override
        public void onDestroy(){
            super.onDestroy();
            Log.d(TAG, "onDestroy() called");
        }




        private void updateQuestion() {

            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
        }

        private void checkAnswer(boolean userPressedTrue)
        {
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

            int messageResId = 0;

            if (mIsCheater) {
                messageResId = R.string.judgment_toast;
            } else {

                if (userPressedTrue == answerIsTrue) {
                    messageResId = R.string.correct_toast;

                } else {
                    messageResId = R.string.incorrect_toast;
                }
            }

            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            if(resultCode != Activity.RESULT_OK){
                if (data==null){
                    return;
                }
                mIsCheater = CheatActivity.wasAnswerShown(data);
            }
        }










    }

