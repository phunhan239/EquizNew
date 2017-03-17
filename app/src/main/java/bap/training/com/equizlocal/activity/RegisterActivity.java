package bap.training.com.equizlocal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bap.training.com.equizlocal.R;
import bap.training.com.equizlocal.controller.UserController;
import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEdtUsername, mEdtPassword, mEdtConfirmPassword;
    private Button mBtnRegister;
    private Realm mRealm;
    private UserController mUserController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        init();
        getWidgets();
        setWidgets();
    }

    private void init() {
        mRealm = Realm.getDefaultInstance();
        mUserController = UserController.sGetInstance();
    }

    private void getWidgets() {
        mBtnRegister = (Button) findViewById(R.id.btnRegister);
        mEdtUsername = (EditText) findViewById(R.id.edtUsername);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);
        mEdtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
    }

    private void setWidgets() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEdtUsername.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();
                String confirmPassword = mEdtConfirmPassword.getText().toString().trim();
                if (password.equals(confirmPassword)) {
                    mUserController.addUser(username, password, mRealm);
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    mEdtUsername.setText("");
                    mEdtPassword.setText("");
                    mEdtConfirmPassword.setText("");
                }
            }
        });
    }
}

