package com.example.remort;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

/*赤外線リモコンコード資料
http://elm-chan.org/docs/ir_format.html 
sharp aquos lc-65gx5
https://sh-dev.sharp.co.jp/android/modules/d3forum/index.php?topic_id=176
https://sh-dev.sharp.co.jp/android/reference/r329d/jp/co/sharp/android/io/irrc/package-frame.html
http://dpmi486.blog83.fc2.com/?tag=iPhone
http://circledays.net/blog/tamura/2011/06/android.html

 
*/
/*
 * public interface SS2012FPGA{
 * public void sedIrDAdata(int i);}
 * */

public class MainActivity extends Activity implements OnClickListener, OnLongClickListener,OnTouchListener{
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private boolean longclick = true;	//長押しフラグ
	private boolean time_flag = false; 	//タイマーフラグ
	private long start_time;
	private long inner_time;
	private int count=0;
	private String bin="";
	private String countnum="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView1 = (TextView)findViewById(R.id.textView1);
    	textView2 = (TextView)findViewById(R.id.textView2);
    	textView3 = (TextView)findViewById(R.id.textView3);
      
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button1.setOnLongClickListener(this);
        button1.setOnTouchListener(this);
        
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button2.setOnLongClickListener(this);
        button2.setOnTouchListener(this);
        
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);
        //button4.setOnLongClickListener(this);
        //button4.setOnTouchListener(this);
        
        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(this);
        
        button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(this);
         
         
        } 
    
 
    
    
    public boolean onTouch(View v,MotionEvent event) {
        //super.onTouchEvent(event);

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
    
    /*ボタンをクリックしたときの動作*/
    public void onClick(View v){
    	
    	switch(v.getId()){
    	
    	/*button1を押したときにはカウントアップ*/
    	case R.id.button1:
    		count++;
    		countnum=String.valueOf(count);
    		SetText(textView1,"count:"+countnum);
    		SetText(textView2,"2進数:"+_10tobin(count));
    		bin="";
    		break;
    		
    	/*button2を押したときにはカウントダウン*/
    	case R.id.button2:
    		count--;
    		countnum=String.valueOf(count);
    		SetText(textView1,"count:"+countnum);
    		SetText(textView2,"2進数:"+_10tobin(count));
    		bin="";
    		break;
    	
    	/*button3を押したときには0で初期化*/
    	case R.id.button3:
    		count=0;
    		textView1.setText("count:0");
    		textView2.setText("2進数:0");
    		textView3.setText("0sec000");
    		break;
    		
    	/*button4を押したときにはストップウォッチ起動*/	
    	case R.id.button4:
    		//Log.v("OnTouch", "ボタン４が押されたよ");
    		if(time_flag == false){

    			start_time = System.currentTimeMillis();
    			
    			new Thread (new Runnable() {

    	    		Handler handler = new Handler();
    	    		public void run(){
    	    			
    	    			handler.post(new Runnable(){
    	    				public void run(){
    	    						inner_time = System.currentTimeMillis();
    	    						SetText(textView3,""
    	    						  +(inner_time-start_time)/1000
    	    								+"sec "
    	    								+(inner_time-start_time
    	    								-(inner_time-start_time)/1000*1000)
    	    								+"msec");
    	    		    			} 
    	    				});
    	    			
    	    			if(time_flag == true)
    	    				handler.postDelayed(this,0);
    	    			else
    	    				return;
    	    			}
    	    		
    	    	}).start();
    			time_flag = true;
    			
    		}
    		
    		else
    			time_flag = false;
    		
    		break;
    		
    	/*button5を押したときにはアプリ終了*/	
    	case R.id.button5:
    		finish();
    		break;
    		
    	/*button6を押したときにはヘルプ表示*/	
    	case R.id.button6:
    		AlertDialog.Builder dlg;
    		dlg = new AlertDialog.Builder(this);
    		dlg.setTitle("Help");
    		dlg.setMessage( "1ch:カウントアップ\n" +
    						"2ch:カウントダウン\n" +
    						"3ch:カウンタを0に初期化\n" +
    						"4ch:ストップウォッチ起動\n" +
    						"      押す(1回目):起動\n" +
    						"      押す(2回目):停止\n" +
    						"end:アプリ終了");
    		dlg.show();
    		break;
    		
    	}
    }
    
    //長押しされた時の処理
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
  

    /*10進数を2進数変換する関数*/
    public String _10tobin(int num)
    {
    	
    	if(num>=0)
    	for(;;){
			bin = String.valueOf(num%2)+bin;
			num = num/2;
			if(num<1)
				break;}

    	/*else if(num<0){
    		
    		num = Math.abs(num);
    		
    		for(;;){
    			bin = String.valueOf(num%2)+bin;
    			num = num/2;
    			if(num<1)
    				break;}
    		//ビット反転
    			Log.v("OnTouch", "befor"+bin);
    		bin = bin.replace("1", "2");
    			Log.v("OnTouch", "...  "+bin);
    		bin = bin.replace("0", "1");
    			Log.v("OnTouch", "...  "+bin);
    		bin = bin.replace("2", "0");
    			Log.v("OnTouch", "after"+bin);
    		//Log.v("OnTouch", String.valueOf()a);
    	}*/
    	
    	
    	return bin;
    }
    
    public void SetText(TextView t,String s){
    	t.setText(s);
    }
    
    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
}


