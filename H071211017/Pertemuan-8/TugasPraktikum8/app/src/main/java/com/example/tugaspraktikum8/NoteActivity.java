package com.example.tugaspraktikum8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity {
    EditText etTitle, etDescription;
    Button btnSubmitOrUpdate, btnDelete;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        btnSubmitOrUpdate = findViewById(R.id.btn_submit_or_update);
        btnDelete = findViewById(R.id.btn_delete);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        Intent toMain = new Intent(NoteActivity.this, MainActivity.class);

        String action = getIntent().getStringExtra("action");

        if (Objects.equals(action, "add")){
            String submit = "Submit";
            btnSubmitOrUpdate.setText(submit);
            btnDelete.setVisibility(View.GONE);
            btnSubmitOrUpdate.setOnClickListener(v -> {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                Note note = new Note(title, description);
                if (title.isEmpty() && description.isEmpty()){
                    Toast.makeText(this, "Catatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addNote(note);
                    startActivity(toMain);
                    finish();
                }
            });
        } else if (Objects.equals(action, "update")){
            String update = "Update";
            int id = getIntent().getIntExtra("id", 0);
            String titleOld = getIntent().getStringExtra("title");
            String descriptionOld = getIntent().getStringExtra("description");
            etTitle.setText(titleOld);
            etDescription.setText(descriptionOld);
            btnSubmitOrUpdate.setText(update);
            btnDelete.setVisibility(View.VISIBLE);
            btnSubmitOrUpdate.setOnClickListener(v -> {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                Note note = new Note(title, description);
                if (etTitle.getText().toString().isEmpty() && etDescription.getText().toString().isEmpty()){
                    Toast.makeText(this, "Catatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.updateNote(id, note);
                    startActivity(toMain);
                    finish();
                }
            });
        }

        btnDelete.setOnClickListener(v -> {
            int id = getIntent().getIntExtra("id", 0);
            printNote(id);
            dbHelper.deleteNote(id);
            startActivity(toMain);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NoteActivity.this, MainActivity.class));
        finish();
    }

    public void printNote(int ids) {
        System.out.println("PRINT NOTE");
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] notesColumn = {"id", "title", "description", "createdAt"};
        Cursor cursor = db.query("notes",
                notesColumn,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("createdAt"));
            if (id == ids) {
                System.out.println("ID: " + id);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("Created At: " + createdAt);
                System.out.println("---------------");
            }
        }

        cursor.close();
        db.close();
    }

}