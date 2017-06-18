package com.example.shobhit.notesmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        TextView textView = (TextView)findViewById(R.id.textView3);
        Button btnLogout =(Button)findViewById(R.id.buttonLogout);

        final SessionManagement session = new SessionManagement(getApplicationContext());

        textView.setText(session.sharedPreferences.getString("uname",null));

        //checking Login status
        session.checkLogin();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
    }
}
