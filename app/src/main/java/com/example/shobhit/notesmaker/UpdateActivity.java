package com.example.shobhit.notesmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Session Management
        final SessionManagement session = new SessionManagement(getApplicationContext());

        //DatabaseHelper
        final DatabaseHelper databaseHelper = DatabaseHelper.getDbHelper(getApplicationContext());

        //Notes
        final Notes notes = new Notes();


        //EditTexts
        final EditText title = (EditText)findViewById(R.id.titleUpdate);
        final EditText content = (EditText)findViewById(R.id.contenUpdate);

        //setting them to their previous value


         notes.setTitle(session.sharedPreferences.getString("title",null));
        notes.setContent(session.sharedPreferences.getString("content",null));

        final Notes oldNotes = new Notes(session.getUname(),notes.getTitle(),notes.getContent());

        title.setText(notes.getTitle());
        content.setText(notes.getContent());

        //Button
        Button buttonUpdate = (Button)findViewById(R.id.button);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting the user entered title and content
                notes.setTitle(title.getText().toString());
                notes.setContent(content.getText().toString());

                if(notes.getTitle().trim().length()>0 && notes.getContent().trim().length() >0){
                    int a = databaseHelper.updateNote(notes,oldNotes,session.getUname());

                    if(a==-1)
                    {
                        Toast.makeText(UpdateActivity.this,"Update Not successful",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(UpdateActivity.this,"Update Successful",Toast.LENGTH_SHORT).show();
                    }

                    //going back to finalActivity
                    Intent intent = new Intent(UpdateActivity.this,FinalActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(UpdateActivity.this,"Enter title and content",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
