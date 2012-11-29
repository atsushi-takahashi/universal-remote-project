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
 * http://jr1wfhbbs.s5.pf-x.net/micom/text/miconlesson28.pdf
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
	private Button button1;//カウントアップ
	private Button button2;//カウントダウン
	private Button button3;//0で初期化
	private Button button4;//ストップウォッチ
	private Button button5;//アプリ終了
	private Button button6;//ヘルプ表示
	private Button button7;//音量+
	private Button button8;//音量-
	private Button button9;//電源
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
	
	private SS2012FPGA fpga;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fpga = new SS2012FPGA_Impl();
        
        textView1 = (TextView)findViewById(R.id.textView1);
    	textView2 = (TextView)findViewById(R.id.textView2);
    	textView3 = (TextView)findViewById(R.id.textView3);
    	SetText(textView1,"");
    	SetText(textView2,"");
    	SetText(textView3,"");
    	
    	//1ch カウントアップ
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        //button1.setOnLongClickListener(this);
        //button1.setOnTouchListener(this);
        
        //2ch カウントダウン
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
        //button2.setOnLongClickListener(this);
        //button2.setOnTouchListener(this);
        
        //3ch ０で初期化
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

        //4ch ストップウォッチ
        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);
        //button4.setOnLongClickListener(this);
        //button4.setOnTouchListener(this);
        
        //end アプリ終了
        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(this);
        
        //help ヘルプ表示
        button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(this);
         
        //+ 音量
        button7 = (Button)findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button7.setOnLongClickListener(this);
        button7.setOnTouchListener(this);
        
        //- 音量
        button8 = (Button)findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button8.setOnLongClickListener(this);
        button8.setOnTouchListener(this);
        
        //POW 電源
        button9 = (Button)findViewById(R.id.button9);
        button9.setOnClickListener(this);
        //button9.setOnLongClickListener(this);
        //button9.setOnTouchListener(this);
         
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
    		/*count++;
    		countnum=String.valueOf(count);
    		SetText(textView1,"count:"+countnum);
    		SetText(textView2,"2進数:"+_10tobin(count));
    		bin="";*/
    		fpga.sendIrDAdata(2);
    		SetText(textView1,"1chが押されました");
    		break;
    		
    	/*button2を押したときにはカウントダウン*/
    	case R.id.button2:
    		/*count--;
    		countnum=String.valueOf(count);
    		SetText(textView1,"count:"+countnum);
    		SetText(textView2,"2進数:"+_10tobin(count));
    		bin="";*/
    		SetText(textView1,"2chが押されました");
    		break;
    	
    	/*button3を押したときには0で初期化*/
    	case R.id.button3:
    		/*count=0;
    		textView1.setText("count:0");
    		textView2.setText("2進数:0");
    		textView3.setText("0sec000");*/
    		SetText(textView1,"3chが押されました");
    		break;
    		
    	/*button4を押したときにはストップウォッチ起動*/	
    	case R.id.button4:
    		//Log.v("OnTouch", "ボタン４が押されたよ");
    		/*
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
    			time_flag = false;*/
    		SetText(textView1,"4chが押されました");
    		
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
    		/*dlg.setMessage( "1ch:カウントアップ\n" +
    						"2ch:カウントダウン\n" +
    						"3ch:カウンタを0に初期化\n" +
    						"4ch:ストップウォッチ起動\n" +
    						"      押す(1回目):起動\n" +
    						"      押す(2回目):停止\n" +
    						"end:アプリ終了");*/
    		dlg.setMessage("特に何もなし('д'*)");
    		dlg.show();
    		break;
    		
    	/*button7を押したときには音量+*/	
    	case R.id.button7:
    		count++;
    		SetText(textView1,"音量+が押されました："+count);
    		
    		break;
    		
    	/*button8を押したときには音量-*/	
    	case R.id.button8:
    		count--;
    		SetText(textView1,"音量-が押されました："+count);
    		break;
    		
    	/*button9を押したときには電源*/	
    	case R.id.button9:
    		SetText(textView1,"電源が押されました");
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


