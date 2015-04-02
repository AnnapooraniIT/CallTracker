package com.calltracker;

import java.sql.Date;
import java.util.*;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class graph extends Activity{
	 String strOrder;
	 Cursor managedCursor;
	Spinner dropdown;
	int date,type,date1;
	static int gi=0,go=0,gm=0;
	TextView tv1,tv2,tv3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		dropdown= (Spinner)findViewById(R.id.spinner1);
		tv1=(TextView)findViewById(R.id.textView1);
		tv2=(TextView)findViewById(R.id.textView2);
		tv3=(TextView)findViewById(R.id.textView3);
				
	    strOrder = android.provider.CallLog.Calls.DATE + " DESC";
		
	   managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,null, null, strOrder);
		  date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		 
   		 List<String> l=new ArrayList<String>();
		 while (managedCursor.moveToNext()) {
			 
			 String strcallDate = managedCursor.getString(date);
			 Date callDate = new Date(Long.valueOf(strcallDate));
			 l.add(callDate.toString()); 
		 }
		 managedCursor.close();
		removeDuplicate(l);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, l);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dropdown.setAdapter(adapter);
		
		dropdown.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				 strOrder = android.provider.CallLog.Calls.DATE + " DESC";
					
				   managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,null, null, strOrder);
				   date1 = managedCursor.getColumnIndex(CallLog.Calls.DATE);
					  type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);		
				String item= dropdown.getSelectedItem().toString();
				  Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
				
			while (managedCursor.moveToNext()) {
					
				   String callTypeCode1 = managedCursor.getString(type);
				   String strcallDate1 = managedCursor.getString(date1);
				   Date callDate1 = new Date(Long.valueOf(strcallDate1));
				   int callcode1 = Integer.parseInt(callTypeCode1);
				   
				   if(item.equals(callDate1.toString()))
				   {
					   
				   if(callcode1==CallLog.Calls.OUTGOING_TYPE)
				   {
					       go++;
				            
				   }
				   
				   if(callcode1==CallLog.Calls.INCOMING_TYPE )
				   {
					  gi++;
					  
				   }  
				   
				   
				   if(callcode1== CallLog.Calls.MISSED_TYPE)
				   {
				            gm++;
				   }

				   }//end if
				  }//end while	
				
				  managedCursor.close();
			   int swap;
					 String temp;
					 int[] a=new int[3];
					 a[0]=gi;
					 a[1]=go;
				 a[2]=gm;
				     
					 String[] b={"INCOMING","OUTGOING","UNATTEND"};
					 for(int i=0;i<a.length-1;i++)
					 {
						 for(int j=i+1;j<a.length;j++)
							 if(a[i]<a[j])
							 {
								 swap=a[i];
								 temp=b[i];
								 a[i]=a[j];
								 b[i]=b[j];
								 a[j]=swap;
								 b[j]=temp;
							 }
					 }
					
					
					 tv1.setText(b[0]+"\t" +a[0]+"\t\t\t\t\t ");
					 tv1.setBackgroundColor(Color.CYAN);
					 if(a[0]==a[1])
					 tv2.setText(b[1]+"\t" +a[1]+"\t\t\t\t\t");
					 else
						 tv2.setText(b[1]+"\t" +a[1]+"\t\t\t");
					 tv2.setBackgroundColor(Color.GREEN);
					 if(a[0]==a[2])
					 tv3.setText(b[2]+"\t" +a[2]+"\t\t\t\t\t");
					
						 
					 if(a[1]==a[2])
						 tv3.setText(b[2]+"\t" +a[2]+"\t\t\t");
					 else
						 tv3.setText(b[2]+"\t" +a[2]);
					 tv3.setBackgroundColor(Color.YELLOW);
					
				gm=0;gi=0;go=0;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});

			
	}

	public static  void removeDuplicate(List<String> l) {
		// TODO Auto-generated method stub
			 int size=l.size();
			
			  for(int i=0;i<size-1;i++)
			  {
				  for(int j=i+1;j<size;j++)
				  {
					  
					  if(!l.get(j).equals(l.get(i)))
						  continue;
					  
					  l.remove(j);
					  j--;
					  size--;
					  
				  }
				  
			  }
			 
	}    
	 
}  
