package com.example.myapplication.DB;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class NewEventActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditEventView1;
    private EditText mEditEventView2;
    private EditText mEditEventView3;
    private EditText mEditEventView4;
    private EditText mEditEventView5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        mEditEventView1 = findViewById(R.id.plain_text_input11);
        mEditEventView2 = findViewById(R.id.plain_text_input12);
        mEditEventView3 = findViewById(R.id.plain_text_input16);
        mEditEventView4 = findViewById(R.id.plain_text_input17);
        mEditEventView5 = findViewById(R.id.plain_text_input18);

        final Button button = findViewById(R.id.button11);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditEventView1.getText().toString())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String eventName = mEditEventView1.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, eventName);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }


}
