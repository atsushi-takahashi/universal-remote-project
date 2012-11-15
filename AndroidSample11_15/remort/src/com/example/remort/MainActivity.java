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

/*�ԊO�������R���R�[�h����
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
	private boolean longclick = true;	//�������t���O
	private boolean time_flag = false; 	//�^�C�}�[�t���O
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

        //�{�^�������ꂽ�璷�����t���O��������
        if (event.getAction() == MotionEvent.ACTION_UP) {
        	longclick = false;
        	Log.v("OnTouch", "��������");
        }
        
        //�{�^�������ꂽ�璷�����t���O�𗧂Ă�
        else if(event.getAction() == MotionEvent.ACTION_DOWN){
        	longclick = true;
        	Log.v("OnTouch", "��������");}

        return false;
      }
    
    /*�{�^�����N���b�N�����Ƃ��̓���*/
    public void onClick(View v){
    	
    	switch(v.getId()){
    	
    	/*button1���������Ƃ��ɂ̓J�E���g�A�b�v*/
    	case R.id.button1:
    		count++;
    		countnum=String.valueOf(count);
    		SetText(textView1,"count:"+countnum);
    		SetText(textView2,"2�i��:"+_10tobin(count));
    		bin="";
    		break;
    		
    	/*button2���������Ƃ��ɂ̓J�E���g�_�E��*/
    	case R.id.button2:
    		count--;
    		countnum=String.valueOf(count);
    		SetText(textView1,"count:"+countnum);
    		SetText(textView2,"2�i��:"+_10tobin(count));
    		bin="";
    		break;
    	
    	/*button3���������Ƃ��ɂ�0�ŏ�����*/
    	case R.id.button3:
    		count=0;
    		textView1.setText("count:0");
    		textView2.setText("2�i��:0");
    		textView3.setText("0sec000");
    		break;
    		
    	/*button4���������Ƃ��ɂ̓X�g�b�v�E�H�b�`�N��*/	
    	case R.id.button4:
    		//Log.v("OnTouch", "�{�^���S�������ꂽ��");
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
    		
    	/*button5���������Ƃ��ɂ̓A�v���I��*/	
    	case R.id.button5:
    		finish();
    		break;
    		
    	/*button6���������Ƃ��ɂ̓w���v�\��*/	
    	case R.id.button6:
    		AlertDialog.Builder dlg;
    		dlg = new AlertDialog.Builder(this);
    		dlg.setTitle("Help");
    		dlg.setMessage( "1ch:�J�E���g�A�b�v\n" +
    						"2ch:�J�E���g�_�E��\n" +
    						"3ch:�J�E���^��0�ɏ�����\n" +
    						"4ch:�X�g�b�v�E�H�b�`�N��\n" +
    						"      ����(1���):�N��\n" +
    						"      ����(2���):��~\n" +
    						"end:�A�v���I��");
    		dlg.show();
    		break;
    		
    	}
    }
    
    //���������ꂽ���̏���
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
  

    /*10�i����2�i���ϊ�����֐�*/
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
    		//�r�b�g���]
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


