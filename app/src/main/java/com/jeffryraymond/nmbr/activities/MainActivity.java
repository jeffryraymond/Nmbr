package com.jeffryraymond.nmbr.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import com.jeffryraymond.nmbr.R;
import com.jeffryraymond.nmbr.data.models.ContactInformation;
import com.jeffryraymond.nmbr.interfaces.Communicator;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jeffry Raymond on 2018-05-08.
 */
public class MainActivity extends AppCompatActivity implements Communicator{
    private String numberCalling;
    private List<ContactInformation> mContactInformationList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_PHONE_STATE}, 1);
        }

        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
        }


        //Dealing with toggle button
        ToggleButton offOnToggleButton = findViewById(R.id.toggleButtonOffOn);
        offOnToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    List<ContactInformation> contactList = getContactInformation();
                    for(int i = 0; i < contactList.size(); i++){
                        String name = contactList.get(i).getContactInfo();
                        String number = contactList.get(i).getContactNumber();
                        Log.i("Contacts", "onCheckedChanged: contact " + name + " and " + number);
                    }
                }
            }
        });


        Log.i("Number", "onCreate: the number calling in main activity is: " + numberCalling);
    }

    //Function to get contact information
    public List<ContactInformation> getContactInformation() {
            mContactInformationList.clear();
            ContentResolver mContentResolver = getContentResolver();
            Cursor cursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            while(cursor.moveToNext()){
                ContactInformation newContactInfo = new ContactInformation();
                String contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                newContactInfo.setContactID(contactID);
                newContactInfo.setContactInfo(contactName);
                int hasNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if(hasNumber > 0){
                    Cursor phoneCursor = mContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactID}, null);

                    while(phoneCursor.moveToNext()){
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        newContactInfo.setContactNumber(phoneNumber);
                    }
                    phoneCursor.close();
                }
                mContactInformationList.add(newContactInfo);
            }
            cursor.close();

        return mContactInformationList;
    }

    @Override
    public void sendNumber(String number) {
        if(number != null) {
            numberCalling = number;
        }
    }
}
