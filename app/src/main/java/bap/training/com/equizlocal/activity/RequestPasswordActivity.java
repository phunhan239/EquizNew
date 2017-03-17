package bap.training.com.equizlocal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import bap.training.com.equizlocal.R;
import bap.training.com.equizlocal.model.User;
import io.realm.Realm;

public class RequestPasswordActivity extends AppCompatActivity {
    private EditText mEdtUsername, mEdtEmail;
    private Button mBtnSubmit;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_password_layout);
        init();
        getWidget();
        setWidget();
    }

    private void init() {
        mRealm = Realm.getDefaultInstance();
    }

    private void getWidget() {
        mEdtEmail = (EditText) findViewById(R.id.edtEmail);
        mEdtUsername = (EditText) findViewById(R.id.edtUsername);
        mBtnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    private void setWidget() {
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEdtUsername.getText().toString().trim();
                String email = mEdtEmail.getText().toString().trim();
                User user = mRealm.where(User.class).equalTo(User.FIELD_USERNAME, username).findFirst();
                if (user != null && user.getUsername().equals(username) && !email.equals("")) {
                    sendEmail(email);
                    startActivity(new Intent(RequestPasswordActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RequestPasswordActivity.this, "Thông tin nhập chưa chính xác!", Toast.LENGTH_LONG).show();
                    mEdtUsername.setText("");
                    mEdtEmail.setText("");
                }
            }
        });
    }

    private void sendEmail(String email) {

    }

    /**
     * return String password random
     *
     * @return
     */
    private String random() {
        Random generator = new Random();
        int numLetters = 10;
        String randomLetters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String content = "";
        for (int n = 0; n < numLetters; n++) {
            content = content + randomLetters.charAt(generator.nextInt(randomLetters.length()));
        }
        return content;
    }
}