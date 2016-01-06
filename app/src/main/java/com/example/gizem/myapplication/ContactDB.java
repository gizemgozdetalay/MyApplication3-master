package com.example.gizem.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gizem on 03.01.2016.
 */
public class ContactDB extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
            + ContactDBSchema.ContactTable.NAME + "(" + ContactDBSchema.ContactTable.Cols.NAME + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.NUMBER + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.EMAIL + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.LOCATION + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.INCOMING + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.OUTCALL + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.MISSED + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.SENDMESS + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.COMEMES + " TEXT);";

    public ContactDB(Context context)
    {
        super(context,ContactDBSchema.ContactTable.NAME,null,1);
        Log.e("database", "kaydedildi");
    }

    public void addContact(Contact c)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactDBSchema.ContactTable.Cols.NAME,c.getName());
        values.put(ContactDBSchema.ContactTable.Cols.NUMBER, c.getNumber());
        values.put(ContactDBSchema.ContactTable.Cols.EMAIL, c.getEmail());
        values.put(ContactDBSchema.ContactTable.Cols.LOCATION, c.getLocation());
        values.put(ContactDBSchema.ContactTable.Cols.INCOMING, c.getIncoming());
        values.put(ContactDBSchema.ContactTable.Cols.OUTCALL,c.getOutgoing());
        values.put(ContactDBSchema.ContactTable.Cols.MISSED,c.getMissed());
        values.put(ContactDBSchema.ContactTable.Cols.SENDMESS,c.getSendMess());
        values.put(ContactDBSchema.ContactTable.Cols.COMEMES, c.getComeMes());

      //  Log.e("numara:",c.getNumber());
        // values.put(OgrenciDBSchema.OgrenciTable.Cols.DTARIHI, o.getDtarihi());

// insert row in table
        db.insert(ContactDBSchema.ContactTable.NAME, null, values);
        //    Boolean b = Boolean.parseBoolean(db.insert(ContactDBSchema.ContactTable.NAME,null,values));
        Log.i("veritabanina eklendi", "kaydedildi.");
        db.close();
    }
    public void deleteRows()
    {
        //String selectQuery = "DELETE FROM "+ ContactDBSchema.ContactTable.NAME;
      //  Log.d("TAG", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ContactDBSchema.ContactTable.NAME, null, null);
        db.close();
       // db.execSQL(selectQuery);
    }

    public Contact findContact(String number)
    {
        String selectQuery = "SELECT * FROM "+ContactDBSchema.ContactTable.NAME+" WHERE TRIM ("+ ContactDBSchema.ContactTable.Cols.NUMBER+") = '"+ number.trim()+"'";
        Log.d("TAG", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery(selectQuery, null);

        if (c!=null && c.moveToFirst())
        {
            Contact cont = new Contact();
            cont.name = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.NAME));
            cont.number = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.NUMBER));
            cont.email = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.EMAIL));
            cont.location = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.LOCATION));
            cont.incoming = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.INCOMING));
            cont.outgoing = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.OUTCALL));
            cont.missed = c.getInt(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.MISSED));
            cont.sendMess = c.getInt(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.SENDMESS));
            cont.comeMes = c.getInt(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.COMEMES));
            return  cont;

        }
        else
        {
            return null;
        }

    }

    public void updateContact(Contact con)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactDBSchema.ContactTable.Cols.NAME,con.getName());
        values.put(ContactDBSchema.ContactTable.Cols.NUMBER,con.getNumber());
        values.put(ContactDBSchema.ContactTable.Cols.EMAIL, con.getEmail());
        values.put(ContactDBSchema.ContactTable.Cols.LOCATION, con.getLocation());
        values.put(ContactDBSchema.ContactTable.Cols.INCOMING, con.getIncoming());
        values.put(ContactDBSchema.ContactTable.Cols.OUTCALL,con.getOutgoing());
        values.put(ContactDBSchema.ContactTable.Cols.MISSED, con.getMissed());
        values.put(ContactDBSchema.ContactTable.Cols.SENDMESS, con.getSendMess());
        values.put(ContactDBSchema.ContactTable.Cols.COMEMES, con.getComeMes());

            db.update(ContactDBSchema.ContactTable.NAME, values, ContactDBSchema.ContactTable.Cols.NUMBER +" = ?",new String[]{con.getNumber()});
      //  String selectQuery = "UPDATE "
    }


    public List<Contact> getAllContactList() {
        List<Contact> contactsArrayList = new ArrayList<Contact>();

        String selectQuery = "SELECT  * FROM " + ContactDBSchema.ContactTable.NAME+" ORDER BY "+ ContactDBSchema.ContactTable.Cols.NAME+" ASC";
        Log.d("TAG", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Log.e("cursor : ",String.valueOf(c));
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Contact contacts = new Contact();
                contacts.name = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.NAME));
                contacts.number = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.NUMBER));
                contacts.email = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.EMAIL));
                contacts.location = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.LOCATION));
                contacts.incoming = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.INCOMING));
                contacts.outgoing = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.OUTCALL));
                contacts.missed = c.getInt(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.MISSED));
                contacts.sendMess = c.getInt(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.SENDMESS));
                contacts.comeMes = c.getInt(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.COMEMES));



                // adding to Students list
                contactsArrayList.add(contacts);
            } while (c.moveToNext());
        }

        return contactsArrayList;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    }
