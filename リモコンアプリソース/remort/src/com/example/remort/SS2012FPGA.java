package com.example.remort;
/**
 * 
 */

/**
 * @author ohkawa
 *
 */
public interface SS2012FPGA {
	
	/*０,１のデータ 群data配列を引数にする
	 * 
	 * 例えばdata[]={1,0,0,1,0}
	 * 送信信号　->　￣|＿＿|￣|＿
	 * */
	public void sendIrDAdata(int data);

	
	/*受信した信号がハイの時には1を返し、ローの時には0を返す*/
	//public int recieveIrDAdata();
	
}
