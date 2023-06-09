package com.example.tugaspraktikum8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    LinearLayout mainLayout, emptyLayout;
    TextInputLayout layoutSearch;
    ImageButton btnAdd;
    TextInputEditText etSearch;
    RecyclerView rvNotes;
    ProgressBar progressBar;
    DatabaseHelper dbHelper;
    List<Note> notes;
    NotesAdapter notesAdapter;
    TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btn_add);
        etSearch = findViewById(R.id.et_search);
        rvNotes = findViewById(R.id.rv_notes);
        progressBar = findViewById(R.id.progress_bar);
        mainLayout = findViewById(R.id.main_layout);
        emptyLayout = findViewById(R.id.empty_layout);
        tvEmpty = findViewById(R.id.tv_empty);
        layoutSearch = findViewById(R.id.layout_search);

        Intent toNote = new Intent(MainActivity.this, NoteActivity.class);
        notes = new ArrayList<>();

        insertAllNotes(null, null, notes);

        if (notes.size() == 0) {
            mainLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.GONE);
        } else {
            notesAdapter = new NotesAdapter(notes);
            rvNotes.setAdapter(notesAdapter);
            mainLayout.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.VISIBLE);
            rvNotes.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(v -> {
            toNote.putExtra("action", "add");
            startActivity(toNote);
            finish();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                progressBar.setVisibility(View.VISIBLE);
                rvNotes.setVisibility(View.GONE);
                loadingScreen();
                filterNotes(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void insertAllNotes(String selection, String[] selectionArgs, List<Note> list){
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] notesColumn = {"id", "title", "description", "createdAt"};
        Cursor cursor = db.query("notes",
                notesColumn,
                selection,
                selectionArgs,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("createdAt"));
            Note note = new Note(id, title, description, createdAt);
            list.add(note);
        }
        cursor.close();
        db.close();
    }

    public void deleteAllNotes() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("notes", null, null);
        db.close();
    }

    public void printAllNotes() {
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

            System.out.println("ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Description: " + description);
            System.out.println("Created At: " + createdAt);
            System.out.println("---------------");
        }

        cursor.close();
        db.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void loadingScreen(){
        Random rand = new Random();
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(rand.nextInt(500));
                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    rvNotes.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void filterNotes(String searchText){
        List<Note> filteredNotes = new ArrayList<>();

        String selection = "title LIKE ? OR description LIKE ?";
        String[] selectionArgs = new String[] {
                "%"+searchText+"%",
                "%"+searchText+"%"
        };

        insertAllNotes(selection, selectionArgs, filteredNotes);

        if (filteredNotes.size() == 0) {
            mainLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            tvEmpty.setText(R.string.catatan_tidak_ditemukan);
        } else {
            notesAdapter = new NotesAdapter(notes);
            mainLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            tvEmpty.setText(R.string.belum_ada_catatan);
            NotesAdapter adapter = new NotesAdapter(filteredNotes);
            rvNotes.setAdapter(adapter);
        }
    }
}