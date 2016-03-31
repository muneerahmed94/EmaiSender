package com.example.muneer.emailsender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button buttonSend, buttonGotoSecondActivity;
    EditText editTextTo, editTextSubject, editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTo = (EditText) findViewById(R.id.editTextTo);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = "emailsender827@gmail.com";
                String username = "emailsender827";
                String password = "emailsender1994";

                String to = editTextTo.getText().toString();
                String subject = editTextSubject.getText().toString();
                String message = editTextMessage.getText().toString();

                EmailSender emailSender = new EmailSender(MainActivity.this);
                emailSender.send(from, username, password, to, subject, message);
            }
        });

        buttonGotoSecondActivity = (Button) findViewById(R.id.buttonGotoSecondActivity);
        buttonGotoSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
