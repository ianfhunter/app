package com.fightingmongooses.fightingmongooses;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public void onResume(){
    	super.onResume();

        createButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void createButtons(){
    	LinearLayout ll = (LinearLayout)findViewById(R.id.main_layout);
    	ll.removeAllViews();
    	
    	Button b = new Button(this);
		b.setText("Update Cons");
		b.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				upDateDBClicked(v);
			}
		});
		
		@SuppressWarnings("deprecation") // FILL_PARENT is deprecated since API level 8, but we're targeting 7
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		ll.addView(b, lp);
		
    	
    	Database db = new Database(this);
        db.open();
        
        Conference cons[] = null;
        
        // Don't spazz when new fields are added to the db
        try
        {
        	cons = db.returnConference();
        }
        catch(Exception e)
        {
        	db.clear();
        	cons = db.returnConference();
        	b.setText("Update Cons (update needed)");
        }
        
        
        db.close();
        
		for(int i = 0; i < cons.length; i++) 
			this.addConButton(cons[i]);	
    }
    
    private class ConOnClickListener implements View.OnClickListener{
    	public int con_id;
		
		@Override
		public void onClick(View v) {
			conClicked(v, con_id);	
		}
    }
    
    public void conClicked(View view, int id)
    {
    	Intent intent = new Intent(this, ViewConAct.class);
    	intent.putExtra("id", id);
    	startActivity(intent);
    }
    
    public void addConButton(Conference con)
    {
	    Button b = new Button(this);
		b.setText(con.getName());
		
		ConOnClickListener click = new ConOnClickListener();
		click.con_id = con.getId();
		
		b.setOnClickListener(click);
		
		LinearLayout ll = (LinearLayout)findViewById(R.id.main_layout);
		
		@SuppressWarnings("deprecation") // FILL_PARENT is deprecated since API level 8, but we're targeting 7
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		ll.addView(b, lp);
    }
    
    public void upDateDBClicked(View view)
    {
    	Intent intent = new Intent(this, UpdateDBActivity.class);
    	startActivity(intent);
    }    
}
