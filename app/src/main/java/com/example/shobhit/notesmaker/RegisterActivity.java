package com.example.shobhit.notesmaker;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Edit Text
        final EditText username = (EditText)findViewById(R.id.editText3);
        final EditText password = (EditText)findViewById(R.id.editText4);

        //Button
        Button btnSignup = (Button)findViewById(R.id.button2);

        final Users users = new Users();

        //Session Management
        final SessionManagement session = new SessionManagement(getApplicationContext());

        //DatabaseHelper
        final DatabaseHelper databaseHelper = DatabaseHelper.getDbHelper(getApplicationContext());

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.setUname(username.getText().toString());
                users.setPassword(password.getText().toString());

                //Check if username and password are filled
                if(users.getUname().trim().length()>0 && users.getPassword().trim().length()>0)
                {
                    //checking if unique username
                    if(!databaseHelper.ifUserExists(users)){

                        //creating the login session
                        session.createLoginSession(users.getUname());

                        //creating user database
                        databaseHelper.createUser(users);

                        //Changing activity to Final Activity
                        Intent i = new Intent(RegisterActivity.this,FinalActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Username already exists",Toast.LENGTH_LONG).show();
                    }
                }else if(users.getUname().trim().length()<3){

                    Toast.makeText(RegisterActivity.this,"Username too short(min 3 chars)",Toast.LENGTH_LONG).show();
                }
                else if(users.getPassword().trim().length()<6){

                    Toast.makeText(RegisterActivity.this,"Password too short (min 6 chars)",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Username or password empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
