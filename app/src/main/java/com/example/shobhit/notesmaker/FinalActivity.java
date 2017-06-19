package com.example.shobhit.notesmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        TextView textView = (TextView)findViewById(R.id.textView3);


        final SessionManagement session = new SessionManagement(getApplicationContext());

        textView.setText(session.sharedPreferences.getString("uname",null));

        //checking Login status
        session.checkLogin();




    }

    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        final SessionManagement session = new SessionManagement(getApplicationContext());
        switch(item.getItemId()){

            //adding new note
            case R.id.new_note:
                return true;

            case R.id.logout:
                session.logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar_1,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
