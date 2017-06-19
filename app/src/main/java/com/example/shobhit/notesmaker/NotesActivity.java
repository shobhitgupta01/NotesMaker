package com.example.shobhit.notesmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Session Management
        final SessionManagement session = new SessionManagement(getApplicationContext());

        //DatabaseHelper
        final DatabaseHelper databaseHelper = DatabaseHelper.getDbHelper(getApplicationContext());

        //Notes
        final Notes notes = new Notes();

        //EditTexts
        final EditText title = (EditText)findViewById(R.id.editText_title);
        final EditText content = (EditText)findViewById(R.id.editText_content);

        //Button
        Button buttonSave = (Button)findViewById(R.id.buttonSave);

        //String
        final String username = session.getUname();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting the user entered title and content
                notes.setTitle(title.getText().toString());
                notes.setContent(content.getText().toString());

                //checking idf title or content empty
                if(notes.getTitle().trim().length()>0 && notes.getContent().trim().length() >0){

                    //storing them to database
                    databaseHelper.createNote(notes,username);

                    //Making Toast
                    Toast.makeText(NotesActivity.this,"Note Saved",Toast.LENGTH_SHORT).show();

                    //Going back to FinalActivity
                    Intent i = new Intent(NotesActivity.this,FinalActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(NotesActivity.this,"Enter title and content",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(NotesActivity.this,FinalActivity.class);
        startActivity(i);
        finish();
    }
}
