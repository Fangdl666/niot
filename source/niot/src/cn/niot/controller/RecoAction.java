package cn.niot.controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.niot.dao.*;

public class RecoAction extends ActionSupport {
	/**
	 * @return
	 */
	private String iotID;
	
	
	public String getIotID() {
		return iotID;
	}

	public void setIotID(String iotID) {
		this.iotID = iotID;
	}
	
	private String Msg;    // ���һ������


	public String getMsg() {    // ���getter
	return Msg;
	}
	public String execute() {
		if (iotID.equals("123456")){
			Msg = "                   "+ iotID + " is ID type A";     // �߼�����
		} else{
			Msg = "                   "+ iotID + " is not ID type A";     // �߼�����
		}
		
		RecoDao recodao = RecoDao.getRecoDao();
		recodao.getIoTID("1");
		
	
		return "sucess"; //Ԥ���峣��
	} 

}