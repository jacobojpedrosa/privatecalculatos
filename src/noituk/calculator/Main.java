package noituk.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	private AppView view;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        view = new ViewCalc();
        Controller.getInstance().setView(view);
        Controller.getInstance().init(this.getApplication());
        Controller.getInstance().startCalculator();
    }
    
    private void goToPrivateArea(){
    	if(Controller.getInstance().isPasswordOk()){
    		Intent privateArea = new Intent(getApplicationContext(), PrivateArea.class);
			startActivity(privateArea);
			Controller.getInstance().reset();
    	}
    }
    


	@Override
	protected void onPause() {
		super.onPause();
		Controller.getInstance().encryptDataDir();
	}
    
	@Override
	protected void onStop() {
		super.onStop();
		Controller.getInstance().encryptDataDir();
	}
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Controller.getInstance().encryptDataDir();
	}
	
    
    public class ViewCalc implements AppView{
    	public TextView screen;
    	
    	public ViewCalc(){
    		this.screen = (TextView)findViewById(R.id.TextView01);
    		//Row 01
            final Button buttonMc = (Button) findViewById(R.id.Button01);
            final Button buttonMPlus = (Button) findViewById(R.id.Button02);
            final Button buttonMsubs = (Button) findViewById(R.id.Button03);
            final Button buttonMr = (Button) findViewById(R.id.Button04);
            //Row 02
            final Button buttonC = (Button) findViewById(R.id.Button11);
            final Button buttonSign = (Button) findViewById(R.id.Button12);
            final Button buttonDiv = (Button) findViewById(R.id.Button13);
            final Button buttonX = (Button) findViewById(R.id.Button14);
            //Row 11
            final Button button1 = (Button) findViewById(R.id.Button21);
            final Button button2 = (Button) findViewById(R.id.Button22);
            final Button button3 = (Button) findViewById(R.id.Button23);
            final Button buttonMinus = (Button) findViewById(R.id.Button24);
            //Row 12
            final Button button4 = (Button) findViewById(R.id.Button31);
            final Button button5 = (Button) findViewById(R.id.Button32);
            final Button button6 = (Button) findViewById(R.id.Button33);
            final Button buttonPlus = (Button) findViewById(R.id.Button34);
            //Row 13
            final Button button7 = (Button) findViewById(R.id.Button41);
            final Button button8 = (Button) findViewById(R.id.Button42);
            final Button button9 = (Button) findViewById(R.id.Button43);
            final Button buttonEqual = (Button) findViewById(R.id.Button44);
            //Row 14
            final Button button0 = (Button) findViewById(R.id.Button51);
            final Button buttonComma = (Button) findViewById(R.id.Button52);
            
            
          //First Row (Mc M+ M- Mr)
            buttonMc.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().memoryAction('c');
    			}});
            buttonMPlus.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().memoryAction('+');
    			}});
            buttonMsubs.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().memoryAction('-');
    			}});

            buttonMr.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().memoryAction('r');
    			}});
            
            

            //Second Row (C +/- / x)
            buttonC.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().reset();
    	    		screen.setText("0");
    			}});
            buttonSign.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().setOperation('s');
    			}});
            buttonDiv.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().setOperation('/');
    			}});

            buttonX.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().setOperation('x');
    			}});
            
            
            
            //Third Row (1 2 3 -)
            button1.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('1');
    			}});
            button2.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('2');
    			}});
            button3.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('3');
    			}});

            buttonMinus.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().setOperation('-');
    			}});
            
            
            
            
            //Fourth Row (4 5 6 +)
            button4.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('4');
    			}});
            button5.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('5');
    			}});
            button6.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('6');
    			}});

            buttonPlus.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().setOperation('+');
    			}});
            
            
            
            
            //Fifth Row (7 8 9 ,)
            button7.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('7');
    			}});
            button8.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('8');
    			}});
            button9.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('9');
    			}});

            buttonComma.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('.');
    				goToPrivateArea();
    			}});
            
            
            
            //Sixth row 0 = 
            button0.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().fixNumber('0');
    			}});

            buttonEqual.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				Controller.getInstance().setOperation('r');
    			}});
            
            
    	}
    	
    	public void displayNum(String n){
			this.screen.setText(n);
		}
    	
		@Override
		public void showError(Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
			
		}

		@Override
		public void showMessage(String string) {
			Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
			toast.show();
			
		}
		
		
    	
    }


}