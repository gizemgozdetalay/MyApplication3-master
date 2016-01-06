package com.example.gizem.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    static final int PICK_CONTACT_REQUEST=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContactDB db = new ContactDB(getApplicationContext());

        final List<Contact> contactList;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Touch to Add for New", Snackbar.LENGTH_LONG)
                        .setAction("'Contact'", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_INSERT);
                                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                                startActivityForResult(intent, PICK_CONTACT_REQUEST);
                            }
                        }).show();

            }
        });


        list = (ListView)findViewById(R.id.listView);

         getAllContacts(db);
        Log.e("on create ici", "selam");
       getAllCalls(db);
       contactList = showAllContacts(list, db);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                araDialog dialog = new araDialog(MainActivity.this, contactList.get(position).getNumber());
                //  Log.e("No :",contactArrayList.get(position).getNumber());
                dialog.show();
            }
        });


    }

    public void getAllContacts(ContactDB db)
    {
        db.deleteRows();
        db.getWritableDatabase();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);

        while (phones.moveToNext())
        {
          //  db.getWritableDatabase();
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
           String number =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ;
            Log.w("gelen numara :",number);
            Contact response = db.findContact(number);
            if(response ==null)
            {
                Contact c = new Contact(name,number,"E-mail","Location","0","0",0,0,0);
                db.addContact(c);
                db.close();
                Log.w("yoktu","eklendi");
            }
            else
            {
                Log.w("zaten","kayitli");
            }

        }
        phones.close();
         db.close();
    }

    public List<Contact> showAllContacts(ListView list, ContactDB db)
    {
        List<Contact> contactList = db.getAllContactList();
        Log.e("metod ici", "BOK");

        Log.e("contact list", String.valueOf(contactList));
        ContactAdapter adapter =new ContactAdapter(this,contactList);
        Log.e("metod ici : ", String.valueOf(adapter.getCount()));
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        return contactList;

    }

    public void getAllCalls(ContactDB db)
    {
        Cursor callManager = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int duration = callManager.getColumnIndex(CallLog.Calls.DURATION);
        int numbers = callManager.getColumnIndex(CallLog.Calls.NUMBER);
        int type = callManager.getColumnIndex(CallLog.Calls.TYPE);

        while (callManager.moveToNext()){
            String number = callManager.getString(numbers);
            String callType =callManager.getString(type);
            String callDuration = callManager.getString(duration);
            int callcode = Integer.parseInt(callType);

            db.getWritableDatabase();
           Contact con = db.findContact(number);
            Log.e("con.num: ",con.getNumber());
            if(con!=null)
            {
                switch (callcode){
                    case CallLog.Calls.OUTGOING_TYPE:
                    {
                        callType = "OUTGOING";
                        Log.e("gelen out: ",con.getOutgoing());
                        int outtime = Integer.parseInt(con.getOutgoing())+Integer.parseInt(callDuration);
                        Log.e("outtime : ",String.valueOf(outtime));
                        con.setOutgoing(String.valueOf(outtime));
                        db.updateContact(con);
                        break;
                    }
                    case CallLog.Calls.INCOMING_TYPE:
                    {
                        callType = "INCOMING";
                        int intime = Integer.parseInt(con.getIncoming())+Integer.parseInt(callDuration);
                        con.setIncoming(String.valueOf(intime));
                        db.updateContact(con);
                        break;
                    }
                    case CallLog.Calls.MISSED_TYPE:
                    {
                        callType = "MISSED";
                        int missed = con.getMissed()+1;
                        con.setMissed(missed);
                        db.updateContact(con);
                        break;

                    }


                }
                // veritabanını update et saniyeyi ekle;
            }
           // SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
            //String bok = formatter.format(callDuration);
            Log.e("type",String.valueOf(callType));
            Log.e("number: ",String.valueOf(number));
            Log.e("duration",String.valueOf(callDuration));
        }


    }

    public class araDialog extends Dialog {
        String number;
        ImageView call,message;

        public araDialog(final Context context, final String number) {
            super(context);
            this.number=number;
            setContentView(R.layout.dialog_layout);

            final LinearLayout sendLayout = (LinearLayout)findViewById(R.id.sendlayout);
            final LinearLayout choiceLayout = (LinearLayout) findViewById(R.id.choiceLayout);



            call= (ImageView)findViewById(R.id.button);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("number", number);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + number));
                    Uri no = intent.getData();
                    System.out.println(no);
                    startActivity(intent);
                }
            });
            message = (ImageView)findViewById(R.id.button2);
            sendLayout.setVisibility(View.INVISIBLE);
            choiceLayout.setVisibility(View.VISIBLE);
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sendLayout.getVisibility()==View.INVISIBLE)
                       sendLayout.setVisibility(View.VISIBLE);
                    choiceLayout.setVisibility(View.INVISIBLE);
                    ImageView send = (ImageView) findViewById(R.id.sentButton);
                    final EditText editText = (EditText)findViewById(R.id.input_name);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, editText.getText().toString(), null, null);
                            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_SHORT).show();
                            sendLayout.setVisibility(View.INVISIBLE);
                            choiceLayout.setVisibility(View.VISIBLE);
                          /*  Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto:"+number));
                            intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                            startActivity(intent);*/

                        }
                    });

                }
            });
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

