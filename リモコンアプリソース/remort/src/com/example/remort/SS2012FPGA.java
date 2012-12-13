package com.example.remort;
/**
 *
 */

/**
 * @author ohkawa
 *
 */
public interface SS2012FPGA {

	/*�O,�P�̃f�[�^ �Qdata�z�����ɂ���
	 *
	 * �Ⴆ��data[]={1,0,0,1,0}
	 * ���M�M���@->�@�P|�Q�Q|�P|�Q
	 * */
	public void sendIrDAdata(long data);
	public long recieveIrDAdata();


	/*��M�����M�����n�C�̎��ɂ�1��Ԃ��A���[�̎��ɂ�0��Ԃ�*/
	//public int recieveIrDAdata();

}
