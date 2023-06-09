package com.example.tugaspraktikum4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvChat;
    List<Person> listPerson = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvChat = findViewById(R.id.chats);

//        Data dummy
        Person person1 = new Person(R.drawable.jaka,"Jaka","+62 823-1234-5678","Available","April 12, 2023","22:15","Sudah tidur belum?");
        listPerson.add(person1);

        Person person2 = new Person(R.drawable.indra,"Indra","+62 812-3456-7890","Available","April 12, 2023","21:45","Sudah makan malam?");
        listPerson.add(person2);

        Person person3 = new Person(R.drawable.hana,"Hana","+62 838-8765-4321","Busy","April 12, 2023","20:30","Aku baru selesai kerja, lelah banget");
        listPerson.add(person3);

        Person person4 = new Person(R.drawable.gita,"Gita","+62 812-6789-1234","Sleeping","April 12, 2023","19:10","Bisa tolong belikan buah?");
        listPerson.add(person4);

        Person person5 = new Person(R.drawable.fadhil,"Fadhil","+62 877-1234-5678","Urgent calls only","April 12, 2023","18:00","Oke, salam untuk keluargamu ya. Oh iya, Mau main futsal?");
        listPerson.add(person5);

        Person person6 = new Person(R.drawable.eka,"Eka","+62 812-4321-9876","Available","April 12, 2023","16:20","Bagus ide mu! Besok aku akan pergi ke pantai");
        listPerson.add(person6);

        Person person7 = new Person(R.drawable.dita,"Dita","+62 857-8765-4321","Busy","April 12, 2023","15:45","Aku baru pulang dari kampus");
        listPerson.add(person7);

        Person person8 = new Person(R.drawable.citra,"Citra","+62 812-7890-1234","Sleeping","April 12, 2023","13:15","Kamu mau makan siang?");
        listPerson.add(person8);

        Person person9 = new Person(R.drawable.budi,"Budi","+62 878-1234-5678","Urgent calls only","April 12, 2023","11:30","Makasih, sejauh ini masih bisa dihandle. Ada apa?");
        listPerson.add(person9);

        Person person10 = new Person(R.drawable.aisha,"Aisha","+62 812-3456-7890","Available","April 12, 2023","09:12","Halo, apa kabar?");
        listPerson.add(person10);

        PersonAdapter personAdapter = new PersonAdapter(listPerson);
        rvChat.setAdapter(personAdapter);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
    }
}