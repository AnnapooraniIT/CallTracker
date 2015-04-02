package com.calltracker;
import java.sql.Date;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class calldetails extends Activity {
 
 TextView textView = null;
 Button inc,out,mssd;
 public static  int c1,c2,c3;
 static  int tt;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.calllog);
  textView = (TextView) findViewById(R.id.textview_call);
  inc=(Button)findViewById(R.id.incoming);
  out=(Button)findViewById(R.id.Outgoing);
  mssd=(Button)findViewById(R.id.MissedCall);
  textView.setMovementMethod(new ScrollingMovementMethod());

  inc.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		tt=1;
		textView.setText("");
		getCallDetails();
	}
   });
  
  out.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		tt=2;
		textView.setText("");
		getCallDetails();
	}
   });
  
  mssd.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		tt=3;
		textView.setText("");
		getCallDetails();
		
		}
	});
  
 }

 private void getCallDetails() {
  StringBuffer sb1 = new StringBuffer();
  StringBuffer sb2 = new StringBuffer();
  StringBuffer sb3 = new StringBuffer();
  String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
  /* Query the CallLog Content Provider */
  Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,null, null, strOrder);
  int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
  int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
  int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
  int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
  int  name=managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
  
   
  while (managedCursor.moveToNext()) {
	  String names = managedCursor.getString(name);
   String phNum = managedCursor.getString(number);
   String callTypeCode = managedCursor.getString(type);
   String strcallDate = managedCursor.getString(date);
   Date callDate = new Date(Long.valueOf(strcallDate));
   String callDuration = managedCursor.getString(duration);
   String callType = null;
   int callcode = Integer.parseInt(callTypeCode);
   if(callcode==CallLog.Calls.OUTGOING_TYPE && tt==2)
   {
	   callType = "Outgoing";
       sb2.append("\nName:--"+names+"\nPhone Number:--- " + phNum + " \n Call Type:--- "
    	     + callType + " \n Call Date:--- " + callDate
    	     + " \n Call duration in sec :--- " + callDuration+"\n");
    c2++;
    textView.setText(sb2);
   }
   
   if(callcode==CallLog.Calls.INCOMING_TYPE && tt==1)
   {
	   callType = "Incoming";
       sb1.append("\nName:--"+names+"\n Phone Number:--- " + phNum + "\n  Call Type:--- "
    	     + callType + "\n Call Date:--- " + callDate
    	     + "\n Call duration in sec :--- " + callDuration+"\n");
       c1++;
       textView.setText(sb1);
   }  
   
   
   if(callcode== CallLog.Calls.MISSED_TYPE && tt==3)
   {
 
    callType = "Missed";
    sb3.append("\nName:--"+names+"\n Phone Number:--- " + phNum + " \n Call Type:--- "
    	     + callType + " \n Call Date:--- " + callDate
    	     + "" +
    	     "\n Call duration in sec :--- " + callDuration+"\n");
    
    c3++;
   textView.setText(sb3);  
   }

 
  }// End while
 
  managedCursor.close();
  tt=0;
 }//End getdetail()

}//end class




