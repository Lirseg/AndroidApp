package com.example.myapplication.DB;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myapplication.R;

public class EditEventActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditEventView1;
    private EditText mEditEventView2;
    private EditText mEditEventView3;
    private EditText mEditEventView4;
    private EditText mEditEventView5;
    private EventViewModel mEventViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        mEditEventView1 = findViewById(R.id.plain_text_input111);
        mEditEventView2 = findViewById(R.id.plain_text_input112);
        mEditEventView3 = findViewById(R.id.plain_text_input116);
        mEditEventView4 = findViewById(R.id.plain_text_input117);
        mEditEventView5 = findViewById(R.id.plain_text_input118);



        mEditEventView1.setText(getIntent().getExtras().getString("Name"));
        mEditEventView2.setText(getIntent().getExtras().getString("Date"));
        mEditEventView3.setText(getIntent().getExtras().getString("From"));
        mEditEventView4.setText(getIntent().getExtras().getString("To"));
        mEditEventView5.setText(getIntent().getExtras().getString("Needed"));

        mEditEventView1.setEnabled(false);

        final Button button = findViewById(R.id.editBtn1);
        button.setOnClickListener(view -> {
//            Intent replyIntent = new Intent();


                String eventName = mEditEventView1.getText().toString();

                String eventDate = mEditEventView2.getText().toString();
                String eventFrom = mEditEventView3.getText().toString();
                String eventTo = mEditEventView4.getText().toString();
                String eventNeeded = mEditEventView5.getText().toString();
//                replyIntent.putExtra("EVENTNAME", eventName);
//                replyIntent.putExtra("EVENTDATE", eventDate);
//                replyIntent.putExtra("EVENTFROM", eventFrom);
//                replyIntent.putExtra("EVENTTO", eventTo);
//                replyIntent.putExtra("EVENTNEEDED", eventNeeded);
//                setResult(666, replyIntent);


            mEventViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(EventViewModel.class);

            Event event = new Event(eventName,eventDate,eventFrom,eventTo,eventNeeded);
            mEventViewModel.insert(event);

            finish();
        });
    }

}



