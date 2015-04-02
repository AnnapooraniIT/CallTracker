package com.calltracker;


import java.util.Vector;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class frequent extends Activity {  Button in,out,mis;
TextView t;
int b_no;
Vector<String> vno=new Vector<String>();
Vector<Integer> vin=new Vector<Integer>();
Vector<Integer> vout=new Vector<Integer>();
Vector<Integer> vmis=new Vector<Integer>();

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.frequent);
	in=(Button)findViewById(R.id.in);
	out=(Button)findViewById(R.id.out);
	mis=(Button)findViewById(R.id.mis);
	t=(TextView)findViewById(R.id.t1);
	 t.setMovementMethod(new ScrollingMovementMethod());

	in.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			b_no=1;
			t.setText(" ");
			getCallDetails();
			String result1="";
			for(int i=0;i<vno.size();i++)
			{
				result1=result1+"\t"+vno.elementAt(i)+"\t\t"+vin.elementAt(i)+"\n";
			}
			t.setText(result1);
		vno.clear();
		vin.clear();
		}
	   });
	  
	  out.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			b_no=2;
			getCallDetails(); 
			String result="";
			t.setText("");
			for(int i=0;i<vno.size();i++)
			{
				result=result+"\t"+vno.elementAt(i)+"\t\t"+vout.elementAt(i)+"\n";
			}
			t.setText(result);
			vno.clear();
			vout.clear();
		}
	   });
	  
	  mis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			b_no=3;
			t.setText("");
			
			getCallDetails();
		      String result2="";
			for(int i=0;i<vno.size();i++)
			{
				result2=result2+"\t"+vno.elementAt(i)+"\t\t\t\t"+vmis.elementAt(i)+"\n";
			}
			t.setText(result2);
		vno.clear();
		vmis.clear();
			}
		});
	  

}
	 private void getCallDetails() {
		 Integer val,val1,val2;
	     int pos,p,p1,p2,pos1,pos2;
	  String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
	  /* Query the CallLog Content Provider */
	  Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,null, null, strOrder);
	  int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
	  int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE); 
	  while (managedCursor.moveToNext()) {
	 
	   String phNum = managedCursor.getString(number);
	   String callTypeCode = managedCursor.getString(type);
	 	   
	    int callcode = Integer.parseInt(callTypeCode);
	  	if(callcode==CallLog.Calls.OUTGOING_TYPE && b_no==2)
	    {
		 
	  	
	     if(vno.contains(phNum))
	     {
	    	 pos=vno.indexOf(phNum);
	    	 val=(Integer)vout.get(pos);
	      	 vout.removeElementAt(pos);
	      	 val++;
	      	 vout.insertElementAt(val, pos);
	    	 
	     }
	     else
	     { 
	    	 vno.add(phNum);
	    	 p=vno.indexOf(phNum);
	    	 val=1;
	    	 vout.insertElementAt(val, p);
	    	 
	     }
	     
	   }
	   
	   if(callcode==CallLog.Calls.INCOMING_TYPE && b_no==1)
	   {
		   
		     if(vno.contains(phNum))
		     {
		    	 pos1=vno.indexOf(phNum);
		    	 val1=(Integer)vin.get(pos1);
		    	 vin.removeElementAt(pos1);
		      	 val1++;
		      	 vin.insertElementAt(val1, pos1);
		    	 
		     }
		     else
		     { 
		    	 vno.add(phNum);
		    	 p1=vno.indexOf(phNum);
		    	 val1=1;
		    	 vin.insertElementAt(val1,p1);
		    
		     }
		     
		     
	     }  
	   
	   
	   if(callcode== CallLog.Calls.MISSED_TYPE && b_no==3)
	   {
		   
		     if(vno.contains(phNum))
		     {
		    	 pos2=vno.indexOf(phNum);
		    	 val2=vmis.get(pos2);
		      	 vmis.removeElementAt(pos2);
		      	 val2++;
		      	 vmis.insertElementAt(val2,pos2);
		    	 
		     }
		     else
		     { 
		    	 vno.add(phNum);
		    	 p2=vno.indexOf(phNum);
		    	 val2=1;
		    	 vmis.insertElementAt(val2, p2);
		     }
		     
		   
	   
	   }

	 
	  }// End while
	 
	  managedCursor.close();
	  b_no=0;
	  p1=p2=p=0;
	  val=val1=val2=0;
	  pos=pos1=pos2=0;
	  
	  
	 }//End getdetail()

}
