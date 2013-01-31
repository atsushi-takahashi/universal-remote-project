package jp.ac.utsunomiya.is.ss2012fpgatest;


//import com.example.remort.MainActivity.Irdata;

//import com.example.remort.R;

//import com.example.remort.R;

import jp.ac.utsunomiya.is.FPGAController;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends Activity implements OnClickListener, OnLongClickListener,OnTouchListener {
	private FPGAController fc;
	private EditText editTextIPaddress;
	private Activity thisActivity;
	private CheckBox checkBoxAlarmSwitchState;
	private EditText editTextIrDAdata;
	private Irdata ch_1;				//赤外線リモコンコード
	private Irdata ch_2;
	private Irdata ch_3;
	private Irdata ch_4;
	private Irdata ch_plu;
	private Irdata ch_min;
	private Irdata ch_pow;
	private Button button1; 	//1ch
	private Button button2; 	//2ch
	private Button button3; 	//3ch
	private Button button4; 	//4ch
	private Button button5; 	//+
	private Button button6; 	//-
	private Button button7; 	//Pow
	private Button buttonExit; 	//exit
	private boolean longclick = true;	//長押しフラグ
	/**************************赤外線リモコンコードデータクラス**************************/

	public class Irdata{
		private int ch_code1;
		private short ch_code2;
		

		/*初期化*/
		Irdata(){ ch_code1 = 0; ch_code2 = 0;}

		/*リモコンコードのゲッタ*/
		public int getData1(){ return ch_code1; }
		public short getData2(){ return ch_code2; }

		/*リモコンコードのセッタ*/
		public void setData(int data1,short data2){ ch_code1 = data1; ch_code2 = data2; }
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_main);
        this.init();
		
		//リモコンデータの初期化
    	ch_1 = new Irdata();
    	ch_2 = new Irdata();
    	ch_3 = new Irdata();
    	ch_4 = new Irdata();
    	ch_plu = new Irdata();
    	ch_min = new Irdata();
    	ch_pow = new Irdata();
    	
    	ch_1.setData((int)0x555AF148,(short)0x80C9);
    	ch_2.setData((int)0x555AF148,(short)0x40C5);
    	ch_3.setData((int)0x555AF148,(short)0xC0CD);
    	ch_4.setData((int)0x555AF148,(short)0x20C3);
    	ch_plu.setData((int)0x555AF148,(short)0x288F);
    	ch_min.setData((int)0x555AF148,(short)0xA887);
    	ch_pow.setData((int)0x555AF148,(short)0x688B);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void init(){
    	setContentView(R.layout.activity_main);
               
        fc = new FPGAController();
		fc.initialize(new String[]{});

		Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
		buttonConnect.setOnClickListener(connectButtonListener);
		editTextIPaddress = (EditText)findViewById(R.id.editTextIPaddress);
		
		//setupTorqueBar(R.id.seekBarLeft, 1);
		//setupTorqueBar(R.id.seekBarRight, 0);
		
		//Button buttonAlarmSound = (Button) findViewById(R.id.buttonAlarmSound);
		//buttonAlarmSound.setOnClickListener(alarmButtonListener);

		//Button buttonGetAlarmSwitchState = (Button) findViewById(R.id.buttonGetAlarmSwitchState);
		//buttonGetAlarmSwitchState.setOnClickListener(getAlarmSwitchStateButtonListener);
		
		//checkBoxAlarmSwitchState = (CheckBox) findViewById(R.id.checkBoxAlarmSwitchState);
/*
		Button buttonSendIrDA =  (Button) findViewById(R.id.buttonSendIrDA);
		buttonSendIrDA.setOnClickListener(sendIrDAButtonListener);
		editTextIrDAdata = (EditText)findViewById(R.id.editTextIrDAdata);*/
		
		//1ch 
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);

        //2ch 
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);

        //3ch 
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

        //4ch 
        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);

        //+
        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button5.setOnLongClickListener(this);
        button5.setOnTouchListener(this);

        //-
        button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button6.setOnLongClickListener(this);
        button6.setOnTouchListener(this);

        //電源
        button7 = (Button)findViewById(R.id.button7);
        button7.setOnClickListener(this);
        
        //Exit
        buttonExit = (Button)findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(this);

    }

	
	private boolean connected = false;
    private View.OnClickListener connectButtonListener = new View.OnClickListener() {
		public void onClick(View v) {
			if(connected) return;
			if(editTextIPaddress.getText().toString().equals("")) return;
			connected=true;
			
			Button thisButton = (Button) v;
			thisButton.setText("Connected");
			
			try {
				fc.connect(editTextIPaddress.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    };
    
    /**************************タッチ処理**************************/


    public boolean onTouch(View v,MotionEvent event) {

        //ボタン離されたら長押しフラグを下げる
        if (event.getAction() == MotionEvent.ACTION_UP) {
        	longclick = false;
        	Log.v("OnTouch", "離したよ");
        }

        //ボタン押されたら長押しフラグを立てる
        else if(event.getAction() == MotionEvent.ACTION_DOWN){
        	longclick = true;
        	Log.v("OnTouch", "押したよ");}

        return false;
      }
    
/**************************クリック処理**************************/
    
    /*ボタンをクリックしたときの動作*/
    public void onClick(View v){
    	if(!connected) return;

    	switch(v.getId()){


    	/*button1を押したときには1ch*/
    	case R.id.button1:
    		try {
				fc.sendIrdaData(ch_1.getData1(), ch_1.getData2());
			} catch (Exception e) {
				e.printStackTrace();
			}

    		break;


    	/*button2を押したときには2ch*/
    	case R.id.button2:
    		try {
				fc.sendIrdaData(ch_2.getData1(), ch_2.getData2());
			} catch (Exception e) {
				e.printStackTrace();
			}

    		break;

    	/*button3を押したときには3ch*/
    	case R.id.button3:
    		try {
				fc.sendIrdaData(ch_3.getData1(), ch_3.getData2());
			} catch (Exception e) {
				e.printStackTrace();
			}

    		break;

    	/*button4を押したときには4ch*/
    	case R.id.button4:
    		try {
				fc.sendIrdaData(ch_4.getData1(), ch_4.getData2());
			} catch (Exception e) {
				e.printStackTrace();
			}
 
    		break;

    	/*button5を押したときには+*/
    	case R.id.button5:
    		try {
				fc.sendIrdaData(ch_plu.getData1(), ch_plu.getData2());
			} catch (Exception e) {
				e.printStackTrace();
			}

    		break;

    	/*button6を押したときには-*/
    	case R.id.button6:
    		try {
				fc.sendIrdaData(ch_min.getData1(), ch_min.getData2());
			} catch (Exception e) {
				e.printStackTrace();
			}

    		break;

    	/*button7を押したときにはPow*/
    	case R.id.button7:
    		try {
				fc.sendIrdaData(ch_pow.getData1(), ch_pow.getData2());
			} catch (Exception e) {
				e.printStackTrace();
			}

    		break;

    	/*button8を押したときにはExit*/
    	case R.id.buttonExit:
    		thisActivity.finish();
    		finish();
   
    		break;


    	}
    }






/**************************長押し処理**************************/
    public boolean onLongClick(final View v) {


    	new Thread (new Runnable() {

    		Handler handler = new Handler();
    		public void run(){

    			handler.post(new Runnable(){
    				public void run(){
    						Log.v("OnTouch", "new thread");
    						v.performClick();} });

    			if(longclick == true)
    				handler.postDelayed(this,150);
    			else
    				return;
    			}

    	}).start();

		return true;
	}
    
    
    //リモコンボタンリスナー
     
    /*private View.OnClickListener sendIrDAButtonListener = new View.OnClickListener() {
  		public void onClick(View v) {
 			if(!connected) return;
 			try {
 				String IrDAstring = editTextIrDAdata.getText().toString();
 				String IrDAhigh = IrDAstring.substring(0, 8);
 				String IrDAlow  = IrDAstring.substring(0, 4);
 				int dataHigh  = Integer.parseInt(IrDAhigh, 16);
 				short dataLow = Short.parseShort(IrDAlow, 16);
			
				fc.sendIrdaData(dataHigh, dataLow);
			} catch (Exception e) {
				e.printStackTrace();
			}
  		}
     };*/
  
}
