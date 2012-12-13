package com.example.remort;


public  class SS2012FPGA_Impl implements SS2012FPGA {

	public void sendIrDAdata(long data) {
		System.out.println("sendIrDAdata called: data="+data);
	}
	public long recieveIrDAdata() {
		System.out.println("recieveIrDAdata called");
		return 20120012013L;
		}
}
