package cn.niot.controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.niot.dao.*;
import cn.niot.rule.RuleFunction;
import cn.niot.service.*;
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
		
//		RecoDao recodao = RecoDao.getRecoDao();
//		recodao.getAdminDivisionID("110114");
		
		char [] IDstr = new char[4];
		IDstr[0] = 'A';
		IDstr[1] = 'F';
		IDstr[2] = 'G';
		IDstr[3] = 'A';
		int [] index = new int[3];
		index[0] = 0;
		index[1] = 1;
		index[2] = 2;
		System.out.println(RuleFunction.CountryRegionCodeforCPC(IDstr, 4, index, 3));
		
		CollisionDetectAlgorithm collisionDetecAlg = CollisionDetectAlgorithm.getCollisionDetectAlgorithm();
		System.out.println(collisionDetecAlg.jsonStr2HashMap("{\"name\": \"123\",\"array\":\"abc\",\"address\":\"guangzhou\"}"));
		
		return "sucess"; //Ԥ���峣��
	} 

}