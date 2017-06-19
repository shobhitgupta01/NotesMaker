package com.example.shobhit.notesmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditTexts
        final EditText username = (EditText)findViewById(R.id.editText1);
        final EditText password = (EditText)findViewById(R.id.editText2);

        //Buttons
        Button login = (Button)findViewById(R.id.buttonLogin);
        Button signup = (Button)findViewById(R.id.buttonSignUp);

        //Users
        final Users users = new Users();

        //Session Management
        final SessionManagement session = new SessionManagement(getApplicationContext());

        //DatabaseHelper
        final DatabaseHelper databaseHelper = DatabaseHelper.getDbHelper(getApplicationContext());

        //checking Login status
        if(session.isLoggedIn())
        {
            Intent i = new Intent(MainActivity.this,FinalActivity.class);
            startActivity(i);
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , RegisterActivity.class);
                startActivity(i);
                //finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                users.setUname(username.getText().toString());
                users.setPassword(password.getText().toString());

                //check for uname and password
                if(users.getUname().trim().length()>0 && users.getPassword().trim().length()>0)
                {
                    if(databaseHelper.ifUserExists(users)){

                        if(databaseHelper.ifCorrectPassword(users)){

                            //creating user session
                            session.createLoginSession(users.getUname());

                            Intent i =new Intent(MainActivity.this, FinalActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Invalid Password..",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Username not registered",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Enter Username and password..",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
