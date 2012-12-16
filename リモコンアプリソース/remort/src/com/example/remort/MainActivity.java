package com.example.remort;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;


/*赤外線リモコンコード資料
 * http://www7a.biglobe.ne.jp/~kanikani/h8sekigai/h8sekigai.html
 * http://jr1wfhbbs.s5.pf-x.net/micom/text/miconlesson28.pdf
http://elm-chan.org/docs/ir_format.html
sharp aquos lc-65gx5
https://sh-dev.sharp.co.jp/android/modules/d3forum/index.php?topic_id=176
https://sh-dev.sharp.co.jp/android/reference/r329d/jp/co/sharp/android/io/irrc/package-frame.html
http://dpmi486.blog83.fc2.com/?tag=iPhone
http://circledays.net/blog/tamura/2011/06/android.html*/




public class MainActivity extends Activity implements OnClickListener, OnLongClickListener,OnTouchListener{



	private Button button1; 	//1ch
	private Button button2; 	//2ch
	private Button button3; 	//3ch
	private Button button4; 	//4ch
	private Button button5; 	//アプリ終了
	private Button button6; 	//ヘルプ表示
	private Button button7; 	//音量+
	private Button button8; 	//音量-
	private Button button9; 	//電源
	private Button button10;	//learn


	private TextView textView1;			//テキストビュー
	private boolean longclick = true;	//長押しフラグ
	private boolean learnf = false;		//学習モードフラグ
	private SS2012FPGA fpga;			//fpgaインターフェイス
	private IRdata irdata;				//赤外線リモコンコードコンテナ
	private int count;					//カウント変数




/**************************赤外線リモコンコードデータクラス**************************/


	public class IRdata{
		private long ch1;
		private long ch2;
		private long ch3;
		private long ch4;
		private long chp;
		private long chm;
		private long chP;

		/*初期化*/
		IRdata(){
			ch1 = 0;
			ch2 = 0;
			ch3 = 0;
			ch4 = 0;
			chp = 0;
			chm = 0;
			chP = 0;
		}

		/*リモコンコードのゲッタ*/
		public long getData(char chname){
			switch(chname){
			case '1':
				return ch1;

			case '2':
				return ch2;

			case '3':
				return ch3;

			case '4':
				return ch4;

			case 'p':
				return chp;

			case 'm':
				return chm;
			case 'P':
				return chP;

			default:
				return 0;
			}
		}

		/*リモコンコードのセッタ*/
		public void setData(char chname,long data){
			switch(chname){
			case '1':
				ch1 = data;
				break;
			case '2':
				ch2 = data;
				break;
			case '3':
				ch3 = data;
				break;
			case '4':
				ch4 = data;
				break;
			case 'p':
				chp = data;
				break;
			case 'm':
				chm = data;
				break;
			case 'P':
				chP = data;
				break;
			default:
				break;
			}
		}


	}



/**************************onCreate**************************/




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fpga = new SS2012FPGA_Impl();

        //リモコンデータの初期化
    	irdata = new IRdata();
    	irdata.setData('1',111111111111L);
    	irdata.setData('2',222222222222L);
    	irdata.setData('3',333333333333L);
    	irdata.setData('4',444444444444L);
    	irdata.setData('p',555555555555L);
    	irdata.setData('m',666666666666L);
    	irdata.setData('P',777777777777L);


        textView1 = (TextView)findViewById(R.id.textView1);
    	textView1.setText("*赤外線リモコンアプリ*");


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

    	switch(v.getId()){


    	/*button1を押したときには1ch*/
    	case R.id.button1:
    		if(learnf == true)
    			Learn(v,'1');
    		else{
    		fpga.sendIrDAdata(irdata.getData('1'));
    		textView1.setText("1chが押されました");
    		}
    		break;


    	/*button2を押したときには2ch*/
    	case R.id.button2:
    		if(learnf == true)
    			Learn(v,'2');
    		else{
    			fpga.sendIrDAdata(irdata.getData('2'));
    			textView1.setText("2chが押されました");
    		}
    		break;

    	/*button3を押したときには3ch*/
    	case R.id.button3:
    		if(learnf == true)
    			Learn(v,'3');
    		else{
    			fpga.sendIrDAdata(irdata.getData('3'));
    			textView1.setText("3chが押されました");
    		}
    		break;

    	/*button4を押したときには4ch*/
    	case R.id.button4:
    		if(learnf == true)
    			Learn(v,'4');
    		else{
    			fpga.sendIrDAdata(irdata.getData('4'));
    		textView1.setText("4chが押されました");
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
    		dlg.setMessage("特に何もなし('д'*)");
    		dlg.show();
    		break;

    	/*button7を押したときには音量+*/
    	case R.id.button7:
    		if(learnf == true)
    			Learn(v,'p');
    		else{
    		count++;
    		fpga.sendIrDAdata(irdata.getData('p'));
    		textView1.setText("音量+が押されました");
    		}
    		break;

    	/*button8を押したときには音量-*/
    	case R.id.button8:
    		if(learnf == true)
    			Learn(v,'m');
    		else{
    		count--;
    		fpga.sendIrDAdata(irdata.getData('m'));
    		textView1.setText("音量-が押されました");
    		}
    		break;

    	/*button9を押したときには電源*/
    	case R.id.button9:
    		if(learnf == true)
    			Learn(v,'P');
    		else{
    			fpga.sendIrDAdata(irdata.getData('P'));
    			textView1.setText("電源が押されました");
    		}
    		break;

    	/*button10を押したときには学習モード*/
    	case R.id.button10:
    		if(learnf==false){
    		learnf = true;
    		textView1.setTextColor(Color.RED);
    	    textView1.setBackgroundColor(Color.BLACK);
    	    textView1.setText("学習モード");

    		}
    		else{
    		learnf = false;
    		textView1.setTextColor(Color.BLACK);
    	    textView1.setBackgroundColor(Color.WHITE);
    	    textView1.setText("");

    		}

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








/**************************学習モード関数**************************/






    public void Learn(View v ,char ch){
    	irdata.setData(ch,fpga.recieveIrDAdata());
    	textView1.setText(""+ch+"の学習");
    }



/**************************オプションメニュー**************************/



    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}