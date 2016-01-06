package com.example.gizem.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gizem on 03.01.2016.
 */
public class ContactAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Contact> mContactList;
    public ContactAdapter(Activity activity, List<Contact> contacts){

        mInflater=(LayoutInflater)activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
        mContactList=contacts;

    }
    public List<Contact> getData()
    {
        return mContactList;
    }
    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView=mInflater.inflate(R.layout.row_layout, null);
        TextView name =
                (TextView) rowView.findViewById(R.id.textView);
        TextView number =
                (TextView) rowView.findViewById(R.id.textView1);
        TextView incoming =
                (TextView) rowView.findViewById(R.id.inComing);
        TextView outgoing =
                (TextView) rowView.findViewById(R.id.OutGoing);
        TextView missed =
                (TextView) rowView.findViewById(R.id.missed);
        TextView sendMes =
                (TextView) rowView.findViewById(R.id.sendingMessage);
        TextView comeMes =
                (TextView)rowView.findViewById(R.id.comingMessage);
        ImageView imageView =
                (ImageView) rowView.findViewById(R.id.imageView);
        Contact contact=mContactList.get(position);
        name.setText(contact.getName());
        number.setText(contact.getNumber());
        incoming.setText("Incoming Calls :"+contact.getIncoming());
        outgoing.setText("Outgoing Calls :"+contact.getOutgoing());
        missed.setText("Missed Calls :"+contact.getMissed());
        sendMes.setText("Sending Messages : "+contact.getSendMess());
        comeMes.setText("Coming Messages : "+contact.getComeMes());

        //imageView.setImageResource(R.drawable.can);


        return rowView;
    }
}
