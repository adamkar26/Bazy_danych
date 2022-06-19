package com.example.bazydanych;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("Studenci", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS STUDENCI (Id INTEGER, Imie VARCHAR, Nazwisko VARCHAR)";
        db.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM STUDENCI";
        Cursor cursor = db.rawQuery(sqlCount, null);
        cursor.moveToFirst();
        int ilosc = cursor.getInt(0);

        if(ilosc == 0){
            String sqlStudent = "INSERT INTO STUDENCI VALUES (?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sqlStudent);

            statement.bindLong(1, 1);
            statement.bindString(2, "Jan");
            statement.bindString(3, "Kowalski");
            statement.executeInsert();

            statement.bindLong(1, 2);
            statement.bindString(2, "Anna");
            statement.bindString(3, "Nowak");
            statement.executeInsert();
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> wyniki = new ArrayList<String>();
                Cursor c = db.rawQuery("SELECT Id, Imie, Nazwisko FROM STUDENCI", null);

                if(c.moveToFirst()){
                    do{
                        int id = c.getInt(c.getColumnIndex("Id"));
                        String imie = c.getString(c.getColumnIndex("Imie"));
                        String nazwisko = c.getString(c.getColumnIndex("Nazwisko"));
                        wyniki.add("Id: "+id+" Imie: "+imie+" Nazwisko: "+ nazwisko);
                    } while(c.moveToNext());
                }

                ListView listView = findViewById(R.id.listView);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_list_item_1, wyniki);
                listView.setAdapter(adapter);
                c.close();
            }
        });


    }
}