package bap.training.com.equizlocal.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bap.training.com.equizlocal.R;
import bap.training.com.equizlocal.model.User;
import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {
    private EditText mEdtUsername, mEdtPassword;
    private Button mBtnLogin, mBtnGoToRegister;
    private TextView mTvForgotPassword;
    private CheckBox mChkRemember;
    private Realm mRealm;
    private static final String FILE_PREF = "user_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        init();
        getWidgets();
        setWidgets();
    }

    private void init() {
        mRealm = Realm.getDefaultInstance();
    }

    private void getWidgets() {
        mBtnGoToRegister = (Button) findViewById(R.id.btnGoToRegister);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mEdtUsername = (EditText) findViewById(R.id.edtUsername);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);
        mTvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        mChkRemember = (CheckBox) findViewById(R.id.chkRemember);
    }

    private void setWidgets() {
        mTvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RequestPasswordActivity.class));
                finish();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEdtUsername.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();
                User user = mRealm.where(User.class).equalTo(User.FIELD_USERNAME, username).findFirst();
                if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Thông tin nhập chưa đúng hoặc bạn chưa có tài khoản!", Toast.LENGTH_LONG).show();
                    mEdtUsername.setText("");
                    mEdtPassword.setText("");
                }
            }
        });
        mBtnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPref();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restorePref();
    }

    private void savingPref() {
        SharedPreferences sp = getSharedPreferences(FILE_PREF, MODE_PRIVATE);
        SharedPreferences.Editor spEdit = sp.edit();
        String username = mEdtUsername.getText().toString().trim();
        String password = mEdtPassword.getText().toString().trim();
        boolean chkRemember = mChkRemember.isChecked();
        if (!chkRemember) {
            spEdit.clear();
        } else {
            spEdit.putString("username", username);
            spEdit.putString("password", password);
            spEdit.putBoolean("remember", chkRemember);
        }
        spEdit.commit();
    }

    private void restorePref() {
        SharedPreferences sp = getSharedPreferences(FILE_PREF, MODE_PRIVATE);
        boolean chkRemember = sp.getBoolean("remember", false);
        if (chkRemember) {
            mEdtUsername.setText(sp.getString("username", ""));
            mEdtPassword.setText(sp.getString("password", ""));
        }
        mChkRemember.setChecked(chkRemember);
    }
}

