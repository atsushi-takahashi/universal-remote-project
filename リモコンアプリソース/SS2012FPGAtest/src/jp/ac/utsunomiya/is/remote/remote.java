package jp.ac.utsunomiya.is.remote;

public class remote {
	/**************************�ԊO�������R���R�[�h�f�[�^�N���X**************************/

	public class Irdata{
		private int ch_code1;
		private short ch_code2;
		

		/*������*/
		Irdata(){ ch_code1 = 0; ch_code2 = 0;}

		/*�����R���R�[�h�̃Q�b�^*/
		public int getData1(){ return ch_code1; }
		public short getData2(){ return ch_code2; }

		/*�����R���R�[�h�̃Z�b�^*/
		public void setData(int data1,short data2){ ch_code1 = data1; ch_code2 = data2; }
	}
		
	
	
}