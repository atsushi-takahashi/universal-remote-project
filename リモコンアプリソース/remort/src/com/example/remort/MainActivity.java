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


/*�ԊO�������R���R�[�h����
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
	private Button button5; 	//�A�v���I��
	private Button button6; 	//�w���v�\��
	private Button button7; 	//����+
	private Button button8; 	//����-
	private Button button9; 	//�d��
	private Button button10;	//learn

	private int count;					//�J�E���g�ϐ�
	private TextView textView1;			//�e�L�X�g�r���[
	private boolean longclick = true;	//�������t���O
	private boolean learnf = false;		//�w�K���[�h�t���O
	private SS2012FPGA fpga;			//fpga�C���^�[�t�F�C�X
	private irData ch_1;				//�ԊO�������R���R�[�h
	private irData ch_2;
	private irData ch_3;
	private irData ch_4;
	private irData ch_plu;
	private irData ch_min;
	private irData ch_pow;




/**************************�ԊO�������R���R�[�h�f�[�^�N���X**************************/

	public class irData{
		private long ch_code;

		/*������*/
		irData(){ ch_code = 0; }

		/*�����R���R�[�h�̃Q�b�^*/
		public long get_data(){ return ch_code; }

		/*�����R���R�[�h�̃Z�b�^*/
		public void set_data(long data){ ch_code = data; }
	}



/**************************onCreate**************************/




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fpga = new SS2012FPGA_Impl();

        //�����R���f�[�^�̏�����
    	ch_1 = new irData();
    	ch_2 = new irData();
    	ch_3 = new irData();
    	ch_4 = new irData();
    	ch_plu = new irData();
    	ch_min = new irData();
    	ch_pow = new irData();
    	ch_1.set_data(111111111111L);
    	ch_2.set_data(222222222222L);
    	ch_3.set_data(333333333333L);
    	ch_4.set_data(444444444444L);
    	ch_plu.set_data(555555555555L);
    	ch_min.set_data(666666666666L);
    	ch_pow.set_data(777777777777L);


        textView1 = (TextView)findViewById(R.id.textView1);
    	textView1.setText("*�ԊO�������R���A�v��*");


    	//1ch �J�E���g�A�b�v
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);

        //2ch �J�E���g�_�E��
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);

        //3ch �O�ŏ�����
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

        //4ch �X�g�b�v�E�H�b�`
        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);

        //end �A�v���I��
        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(this);

        //help �w���v�\��
        button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(this);

        //+ ����
        button7 = (Button)findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button7.setOnLongClickListener(this);
        button7.setOnTouchListener(this);

        //- ����
        button8 = (Button)findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button8.setOnLongClickListener(this);
        button8.setOnTouchListener(this);

        //POW �d��
        button9 = (Button)findViewById(R.id.button9);
        button9.setOnClickListener(this);

        //Learn �w�K���[�h
        button10 = (Button)findViewById(R.id.button10);
        button10.setOnClickListener(this);


        }







/**************************�^�b�`����**************************/






    public boolean onTouch(View v,MotionEvent event) {

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

/**************************�N���b�N����**************************/
    
    /*�{�^�����N���b�N�����Ƃ��̓���*/
    public void onClick(View v){

    	switch(v.getId()){


    	/*button1���������Ƃ��ɂ�1ch*/
    	case R.id.button1:
    		if(learnf == true)
    			Learn(v,'1');
    		else{
    		fpga.sendIrDAdata(ch_1.get_data());
    		textView1.setText(""+ch_1.get_data()+"");
    		}
    		break;


    	/*button2���������Ƃ��ɂ�2ch*/
    	case R.id.button2:
    		if(learnf == true)
    			Learn(v,'2');
    		else{
    			fpga.sendIrDAdata(ch_2.get_data());
    			textView1.setText(""+ch_2.get_data()+"");
    		}
    		break;

    	/*button3���������Ƃ��ɂ�3ch*/
    	case R.id.button3:
    		if(learnf == true)
    			Learn(v,'3');
    		else{
    			fpga.sendIrDAdata(ch_3.get_data());
    			textView1.setText(""+ch_3.get_data()+"");
    		}
    		break;

    	/*button4���������Ƃ��ɂ�4ch*/
    	case R.id.button4:
    		if(learnf == true)
    			Learn(v,'4');
    		else{
    			fpga.sendIrDAdata(ch_4.get_data());
    			textView1.setText(""+ch_4.get_data()+"");
    		}
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
    		dlg.setMessage("���ɉ����Ȃ�('�t'*)");
    		dlg.show();
    		break;

    	/*button7���������Ƃ��ɂ͉���+*/
    	case R.id.button7:
    		if(learnf == true)
    			Learn(v,'p');
    		else{
    		count++;
    		fpga.sendIrDAdata(ch_plu.get_data());
    		textView1.setText(""+ch_plu.get_data()+":"+count+"");
    		}
    		break;

    	/*button8���������Ƃ��ɂ͉���-*/
    	case R.id.button8:
    		if(learnf == true)
    			Learn(v,'m');
    		else{
    		count--;
    		fpga.sendIrDAdata(ch_min.get_data());
    		textView1.setText(""+ch_min.get_data()+":"+count+"");
    		}
    		break;

    	/*button9���������Ƃ��ɂ͓d��*/
    	case R.id.button9:
    		if(learnf == true)
    			Learn(v,'P');
    		else{
    			fpga.sendIrDAdata(ch_pow.get_data());
    			textView1.setText(""+ch_pow.get_data()+"");
    		}
    		break;

    	/*button10���������Ƃ��ɂ͊w�K���[�h*/
    	case R.id.button10:
    		if(learnf==false){
    		learnf = true;
    		textView1.setTextColor(Color.RED);
    	    textView1.setBackgroundColor(Color.BLACK);
    	    textView1.setText("�w�K���[�h");

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






/**************************����������**************************/





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








/**************************�w�K���[�h�֐�**************************/






    public void Learn(View v ,char ch){
    	switch(ch){
    	case '1':
    		ch_1.set_data(fpga.recieveIrDAdata());
    		textView1.setText(""+ch+"�̊w�K");
    		break;
    	
    	case '2':
        	ch_2.set_data(fpga.recieveIrDAdata());
        	textView1.setText(""+ch+"�̊w�K");
        	break;
        	
    	case '3':
        	ch_3.set_data(fpga.recieveIrDAdata());
        	textView1.setText(""+ch+"�̊w�K");
        	break;
        	
    	case '4':
        	ch_4.set_data(fpga.recieveIrDAdata());
        	textView1.setText(""+ch+"�̊w�K");
        	break;
        	
    	case 'p':
        	ch_plu.set_data(fpga.recieveIrDAdata());
        	textView1.setText(""+ch+"�̊w�K");
        	break;
        	
    	case 'm':
        	ch_min.set_data(fpga.recieveIrDAdata());
        	textView1.setText(""+ch+"�̊w�K");
        	break;
        	
    	case 'P':
        	ch_pow.set_data(fpga.recieveIrDAdata());
        	textView1.setText(""+ch+"�̊w�K");
        	break;
    	}
    }



/**************************�I�v�V�������j���[**************************/



    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}