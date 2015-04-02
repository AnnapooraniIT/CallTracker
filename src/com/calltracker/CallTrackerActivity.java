package com.calltracker;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.ArrayList;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.content.Context;
 

public class CallTrackerActivity extends Activity implements  OnItemClickListener, OnItemSelectedListener {
    AutoCompleteTextView textView=null;
    private ArrayAdapter<String> adapter;
     
    // Store contacts values in these arraylist
    public static ArrayList<String> phoneValueArr = new ArrayList<String>();
    public static ArrayList<String> nameValueArr = new ArrayList<String>();
     
    EditText toNumber=null;
    String toNumberValue="";
    
     Button Send,cll,b3,b5,freq;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_tracker);
        cll=(Button)findViewById(R.id.button1);
         Send = (Button) findViewById(R.id.Send);
         b3=(Button)findViewById(R.id.button2);
         freq=(Button)findViewById(R.id.button3);
        
         b5=(Button)findViewById(R.id.button4);
         
         b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent ii=new Intent(CallTrackerActivity.this,calldetails.class);
				startActivity(ii);
				
			}
		});
       
         b5.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				Intent ii=new Intent(CallTrackerActivity.this,graph.class);
 				startActivity(ii);
 				
 			}
 		});
         freq.setOnClickListener(new OnClickListener() {
  			
  			@Override
  			public void onClick(View arg0) {
  				// TODO Auto-generated method stub
  				Intent it=new Intent(CallTrackerActivity.this,frequent.class);
  				startActivity(it);
  				
  			}
  		});
     
         
      
                 // add PhoneStateListener for monitoring
                 MyPhoneListener phoneListener = new MyPhoneListener();
                 TelephonyManager telephonyManager = 
                         (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                 // receive notifications of telephony state changes 
                 telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
  

        
        // Initialize AutoCompleteTextView values
         
            textView = (AutoCompleteTextView) findViewById(R.id.toNumber);
             
            //Create adapter    
            adapter = new ArrayAdapter<String>
                      (this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
            textView.setThreshold(1);
             
           //Set adapter to AutoCompleteTextView
            textView.setAdapter(adapter);
            textView.setOnItemSelectedListener(this);
            textView.setOnItemClickListener(this);
         
        // Read contact data and add data to ArrayAdapter
        // ArrayAdapter used by AutoCompleteTextView
             
           readContactData();
          cll.setOnClickListener(new OnClickListener() {
        	  	             
        	               @Override
        	   	            public void onClick(View v) {
        	   	       BtnAction(textView);         
        	                      try {
        	   	                    // set the data
        	                    	String  check=toNumberValue.toString();
        	                    	  if(check.equals(null))
        	                    		  Toast.makeText(getApplicationContext(), "Please Select any Contact",Toast.LENGTH_LONG).show();
        	                    	  else
        	                    	  {
        	   	                    String uri = "tel:"+check;
        	   	                    
        	   	                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
        	   	                     
        	   	                    startActivity(callIntent);
        	                    	  }
        	   	                }catch(Exception e) {
        	   	                    Toast.makeText(getApplicationContext(),"Your call has failed...",
        	   	                        Toast.LENGTH_LONG).show();
        	   	                    e.printStackTrace();
        	   	                }
        	   	            }
        	   	        });
        	           
        	   	  
               
               Send.setOnClickListener(new OnClickListener() {
        	              
        	               @Override
        	   	            public void onClick(View v) {
        	   	               BtnAction(textView); 
        	                       try {
        	   	                    String uri = "tel:"+toNumberValue.toString();
        	   	                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
        	   	                     
        	   	                    startActivity(dialIntent);
        	   	                }catch(Exception e) {
        	   	                    Toast.makeText(getApplicationContext(),"Your call has failed...",
        	   	                        Toast.LENGTH_LONG).show();
        	   	                    e.printStackTrace();
        	   	                }
        	   	            }
        	   	        });
         }//oncreate
        	   	     
        	   	    private class MyPhoneListener extends PhoneStateListener {
        	   	          
        	           private boolean onCall = false;
        	   	  
        	   	        @Override
        	   	        public void onCallStateChanged(int state, String incomingNumber) {
        	   	  
        	   	            switch (state) {
        	   	            case TelephonyManager.CALL_STATE_RINGING:
        	                   // phone ringing...
        	   	                  Toast.makeText(CallTrackerActivity.this, incomingNumber + " calls you", Toast.LENGTH_LONG).show();
        	   	             break;
        	   	            			            
        	  	            case TelephonyManager.CALL_STATE_OFFHOOK:
       		                // one call exists that is dialing, active, or on hold
        	   	                    Toast.makeText(CallTrackerActivity.this, "on call...",Toast.LENGTH_LONG).show();
        	 	                //because user answers the incoming call
        	   	       	                onCall = true;
        	                break;
        	   	            	
        	   	             case TelephonyManager.CALL_STATE_IDLE:
        	   	               // in initialization of the class and at the end of phone call 
        	   	               // detect flag from CALL_STATE_OFFHOOK
        	   	                      if (onCall == true) {
        	   	                             Toast.makeText(CallTrackerActivity.this, "restart app after call", Toast.LENGTH_LONG).show();
        	   	                  // restart our application
        	   	                      Intent restart = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        	   	                      restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	   	                       startActivity(restart);
        	   	                        onCall = false;
        	   	            		             }
        	                  break;
        	   	               default:
        	   	               break;
        	   	                 }//switch
        	   	                    
        	   	  	        }//onCallStateChanged
        	   	     }//MyphoneListener
        	   	            		
        	   	            			
 

           
           


    
    private OnClickListener BtnAction(final AutoCompleteTextView toNumber) {
        return new OnClickListener() {
             
            public void onClick(View v) {
                 
                String NameSel = "";
                NameSel = toNumber.getText().toString();
                 
                 
                final String ToNumber = toNumberValue;
                 
                 
                if (ToNumber.length() == 0 ) {
                    Toast.makeText(getBaseContext(), "Please fill phone number",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getBaseContext(), NameSel+" : "+toNumberValue,
                                Toast.LENGTH_LONG).show();
                }
                 
            }
        };
    }   
     
     
    // Read phone contact name and phone numbers 
     
    private void readContactData() {
         
        try {
             
            /*********** Reading Contacts Name And Number **********/
             
            String phoneNumber = "";
            ContentResolver cr = getBaseContext()
                    .getContentResolver();
             
            //Query to get contact name
             
            Cursor cur = cr
                    .query(ContactsContract.Contacts.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
             
            // If data data found in contacts 
            if (cur.getCount() > 0) {
                 
                Log.i("AutocompleteContacts", "Reading   contacts........");
                 
                int k=0;
                String name = "";
                 
                while (cur.moveToNext()) 
                {
                     
                    String id = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                     
                    //Check contact have phone number
                    if (Integer
                            .parseInt(cur
                                    .getString(cur
                                        .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
                    {
                             
                        //Create query to get phone number by contact id
                        Cursor pCur = cr
                                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null,
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                    + " = ?",
                                            new String[] { id },
                                            null);
                            int j=0;
                             
                            while (pCur
                                    .moveToNext()) 
                            {
                                // Sometimes get multiple data 
                                if(j==0)
                                {
                                    // Get Phone number
                                    phoneNumber =""+pCur.getString(pCur
                                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                     
                                    // Add contacts names to adapter
                                    adapter.add(name);
                                     
                                    // Add ArrayList names to adapter
                                    phoneValueArr.add(phoneNumber.toString());
                                    nameValueArr.add(name.toString());
                                     
                                    j++;
                                    k++;
                                }
                            }  // End while loop
                            pCur.close();
                        } // End if
                     
                }  // End while loop
 
            } // End Cursor value check
            cur.close();
                     
          
        } catch (Exception e) {
             Log.i("AutocompleteContacts","Exception : "+ e);
        }
                     
     
   }
 
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
            long arg3) {
        // TODO Auto-generated method stub
        //Log.d("AutocompleteContacts", "onItemSelected() position " + position);
    }
 
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
         
        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
 
    }
 
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
         
            // Get Array index value for selected name
             int i = nameValueArr.indexOf(""+arg0.getItemAtPosition(arg2));
            
            // If name exist in name ArrayList
            if (i >= 0) {
                 
                // Get Phone Number
                toNumberValue = phoneValueArr.get(i);
                 
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
 
                // Show Alert       
                Toast.makeText(getBaseContext(), 
                            "Position:"+arg2+" Name:"+arg0.getItemAtPosition(arg2)+" Number:"+toNumberValue,
                            Toast.LENGTH_LONG).show();
                 
                Log.d("AutocompleteContacts", 
                            "Position:"+arg2+" Name:"+arg0.getItemAtPosition(arg2)+" Number:"+toNumberValue);
                 
            }
         
    }
     
    protected void onResume() {
        super.onResume();
    }
  
    protected void onDestroy() {
        super.onDestroy();
    }
     


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.call_tracker, menu);
        return true;
    }
    
}
