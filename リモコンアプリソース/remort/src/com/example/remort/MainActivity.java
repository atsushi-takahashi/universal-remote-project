package com.example.remort;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
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
 * http://www7a.biglobe.ne.jp/~kanikani/h8sekigai/h8sekigai.html
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
	private Button button1; //カウントアップ
	private Button button2; //カウントダウン
	private Button button3; //0で初期化
	private Button button4; //ストップウォッチ
	private Button button5; //アプリ終了
	private Button button6; //ヘルプ表示
	private Button button7; //音量+
	private Button button8; //音量-
	private Button button9; //電源
	private Button button10;//learn
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private boolean longclick = true;//長押しフラグ
	private boolean learnf = false;//学習モードフラグ
	private int count=0;
	private SS2012FPGA fpga;
	private IRdata irdata;
	
	
	/*赤外線リモコンコードデータクラス*/
	public class IRdata{
		private long ch1;
		private long ch2;	
		private long ch3;	
		private long ch4;	
		private long chp;
		private long chm;
		/*初期化*/
		IRdata(){
			ch1= 0;
			ch2= 0;
			ch3= 0;
			ch4= 0;
			chp= 0;
			chm= 0;
		}
		public void setData(char ch,long ch){
			switch(ch){
			case "1":
				
			
			}
		}
		
		
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fpga = new SS2012FPGA_Impl();
        
        /*リモコンデータの初期化*/
    	irdata = new IRdata();
    	
        //irdata[ = new IRdata();
        
        textView1 = (TextView)findViewById(R.id.textView1);
    	textView2 = (TextView)findViewById(R.id.textView2);
    	textView3 = (TextView)findViewById(R.id.textView3);
    	SetText(textView1,"");
    	SetText(textView2,"");
    	SetText(textView3,"");
    	
    	//1ch カウントアップ
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        
        //2ch カウントダウン
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
        
        //3ch ０で初期化
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

        //4ch ストップウォッチ
        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);
        
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
        
        //Learn 学習モード
        button10 = (Button)findViewById(R.id.button10);
        button10.setOnClickListener(this);
        } 
    
 
    
    
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
    
    /*ボタンをクリックしたときの動作*/
    public void onClick(View v){
    	
    	switch(v.getId()){
    	
    	/*button1を押したときにはカウントアップ*/
    	case R.id.button1:
    		if(learnf == true)
    			Learn(v,1);
    		else{
    		fpga.sendIrDAdata(2);
    		SetText(textView1,"1chが押されました");
    		}
    		break;
    		
    		
    	/*button2を押したときにはカウントダウン*/
    	case R.id.button2:
    		if(learnf == true)
    			Learn(v,2);
    		else{
    		SetText(textView1,"2chが押されました");
    		}
    		break;
    	
    	/*button3を押したときには0で初期化*/
    	case R.id.button3:
    		if(learnf == true)
    			Learn(v,3);
    		else{
    		SetText(textView1,"3chが押されました");
    		}
    		break;
    		
    	/*button4を押したときにはストップウォッチ起動*/	
    	case R.id.button4:
    		if(learnf == true)
    			Learn(v,4);
    		else{
    		SetText(textView1,"4chが押されました");
    		}
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
    		if(learnf == true)
    			Learn(v,7);
    		else{
    		count++;
    		SetText(textView1,"音量+が押されました："+count);
    		}
    		break;
    		
    	/*button8を押したときには音量-*/	
    	case R.id.button8:
    		if(learnf == true)
    			Learn(v,8);
    		else{
    		count--;
    		SetText(textView1,"音量-が押されました："+count);
    		}
    		break;
    		
    	/*button9を押したときには電源*/	
    	case R.id.button9:
    		if(learnf == true)
    			Learn(v,9);
    		else{
    		SetText(textView1,"電源が押されました");
    		}
    		break;
    		
    	/*button10を押したときには学習モード*/
    	case R.id.button10:
    		if(learnf==false){
    		learnf = true;
    		textView1.setTextColor(Color.RED);
    	    textView1.setBackgroundColor(Color.BLACK);
    		SetText(textView1,"学習モード");
    		}
    		else{
    		learnf = false;
    		textView1.setTextColor(Color.BLACK);
    	    textView1.setBackgroundColor(Color.WHITE);
    		SetText(textView1,"");
    		}
    			
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
  
    
    //学習モード
    public void Learn(View v ,int ch){
    	SetText(textView1,""+ch+"の学習");
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


