package com.example.akshit_2013245;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener,DialogInterface.OnClickListener, AdapterView.OnItemLongClickListener
{

    EditText editName, editEmail, editNumber;
    Button btnAdd, btnUpdate, btnSort;
    ListView listViewContact;

    ArrayList<ContactBook> listOfContacts;
    ArrayAdapter<ContactBook> contactBookAdapter;
    AlertDialog.Builder alertDialog;

    int c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editNumber = findViewById(R.id.editNumber);

        btnAdd = findViewById(R.id.btnAdd);
        btnSort = findViewById(R.id.btnSort);
        btnUpdate = findViewById(R.id.btnUpdate);

        listViewContact = findViewById(R.id.listViewContact);

        listOfContacts = new ArrayList<>();

        contactBookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfContacts);

        listViewContact.setAdapter(contactBookAdapter);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Delete a Contact?");
        alertDialog.setPositiveButton("Yes", this);
        alertDialog.setNegativeButton("No", this);

        listViewContact.setOnItemLongClickListener(this);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSort.setOnClickListener(this);
    }

    private void sort(){
        Collections.sort(listOfContacts, new Comparator<ContactBook>() {
            @Override
            public int compare(ContactBook contactBook, ContactBook t1) {
                return contactBook.getName().compareToIgnoreCase(t1.getName());
            }
        });
        contactBookAdapter.notifyDataSetChanged();
        editName.setText("");
        editEmail.setText("");
        editNumber.setText("");
    }

    private void add() {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String number = editNumber.getText().toString();

        ContactBook cB = new ContactBook(name, email, number);

        boolean isFound = false;
        for (int i = 0; i < listOfContacts.size(); i++) {
            if (listOfContacts.get(i).getName().equals(name)) {
                isFound = true;
                Toast.makeText(this, "Contact Exists...", Toast.LENGTH_SHORT).show();
                break;
            }
        }if(!isFound){
            listOfContacts.add(cB);
            contactBookAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Contact Added Successfully...", Toast.LENGTH_SHORT).show();
        }
        editName.setText("");
        editEmail.setText("");
        editNumber.setText("");
    }

    private void update(){
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String number = editNumber.getText().toString();

        ContactBook cB = new ContactBook(name, email, number);

        boolean isFound = false;
        for(int i = 0; i<listOfContacts.size(); i++){
            if(listOfContacts.get(i).getName().equals(name)){
                listOfContacts.set(i,cB);
                contactBookAdapter.notifyDataSetChanged();
                isFound = true;
                Toast.makeText(this,"Contact Updated...", Toast.LENGTH_SHORT).show();
                break;
            }
        }if(!isFound){
            Toast.makeText(this,"Oops!!! Contact not found...", Toast.LENGTH_SHORT).show();
        }
        editName.setText("");
        editEmail.setText("");
        editNumber.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){

    }

    @Override
    public void onClick(DialogInterface dI, int i){
        switch(i){
            case Dialog.BUTTON_POSITIVE:
                listOfContacts.remove(c);
                contactBookAdapter.notifyDataSetChanged();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
        editName.setText("");
        editEmail.setText("");
        editNumber.setText("");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l){
        c = i;
        AlertDialog dB = alertDialog.create();
        dB.show();
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.btnSort:
                sort();
                break;
            case R.id.btnAdd:
                add();
                break;
            case R.id.btnUpdate:
                update();
                break;
        }
    }
}