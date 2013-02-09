package jp.ac.utsunomiya.is.remote;

public class remote {
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
		
	
	
}