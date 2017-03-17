package bap.training.com.equizlocal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import bap.training.com.equizlocal.R;

public class MainActivity extends AppCompatActivity {
    private Button mBtnLogout;
    private Button mBtnExam;
    private Button mBtnLearn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        init();
        getWidgets();
        setWidgets();
    }

    private void init() {

    }

    private void getWidgets() {
        mBtnLogout = (Button) findViewById(R.id.btnLogout);
        mBtnExam = (Button) findViewById(R.id.btnExam);
        mBtnLearn = (Button) findViewById(R.id.btnLearn);
    }

    private void setWidgets() {
        mBtnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLearn();
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLogout();
            }
        });
        mBtnExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToExam();
            }
        });
    }

    private void goToLearn() {
        startActivity(new Intent(MainActivity.this, LearnActivity.class));
    }

    private void goToExam() {
        startActivity(new Intent(this, ExamActivity.class));
        finish();
    }

    private void showDialogLogout() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Cảnh báo!");
        mBuilder.setMessage("Bạn đang cố đăng xuất khỏi ứng dụng?");
        mBuilder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //return login page
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        mBuilder.create().show();
    }
}
