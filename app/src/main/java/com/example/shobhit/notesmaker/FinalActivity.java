package com.example.shobhit.notesmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        //Session Management
        final SessionManagement session = new SessionManagement(getApplicationContext());

        //DatabaseHelper
        final DatabaseHelper databaseHelper = DatabaseHelper.getDbHelper(getApplicationContext());

        //Text View
        TextView textView = (TextView)findViewById(R.id.textView3);

        //setting textView
        textView.setText(session.sharedPreferences.getString("uname",null));

        //checking Login status
        session.checkLogin();

        //RecyclerView
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        //Notes list for adapter
        List<Notes> notesList = new ArrayList<>();


        //If the user has notes , read them else provide empty notes list
        if(databaseHelper.ifNotes(session.getUname())) {
            notesList = databaseHelper.getNotes(session.getUname()); //creating notes List
        }
        else {
            Notes notes = new Notes("","No Notes present","");
            notesList.add(notes);
        }

        NotesAdapter notesAdapter = new NotesAdapter(notesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);
        notesAdapter.notifyDataSetChanged();




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
                Intent i = new Intent(FinalActivity.this,NotesActivity.class);
                startActivity(i);
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
