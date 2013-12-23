package cn.niot.controller;

import java.util.HashMap;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.niot.service.NewIDstdCollisionDetect;
import cn.niot.util.RecoUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
* @Title: RespRul.java 
* @Package cn.niot.zt 
* @Description:ǰ��̨���ݴ������� 
* @author Zhang Tao
* @date 2013-12-16 ���� 
* @version V1.0
 */

public class IoTNewRuleRecognitionAction extends ActionSupport {
	
	 /**
	  * �û���ǰ̨�������ı��볤��
	  */
	private String len;
	 /**
	  * �û���ǰ̨�������ĸ�λȡֵ��Χ
	  */
	private String valueRange;
	
	 /**
	  * ���
	  * ���������ظ�ǰ̨�Ĳ�ѯ״̬��
	  * �����ȡֵ�ֱ�Ϊ����0������1����������1������������error��
	  */
	private String status;
	
	 /**
	  * ��statusȡֵΪ��1�����ߴ��ڡ�1��������ʱ�����
	  * ���������ظ�ǰ̨�ı�����Ϣ��
	  * ��statusȡֵΪ��1��ʱ��data�洢��ѯ���ı������ƣ�����data="CPC",
	  * ��statusȡֵΪ����1������ʱ��data�洢���������Լ�������ʣ�
	  * ����data = "[{codeName:'cpc',probability:0.12},{codeName:'eCode',probability:0.88}]";
	  */
	private String data;
	
	 /**
	  * ��status=="error"ʱ�����
	  * ���������ظ�ǰ̨�Ĵ�����Ϣ��
	  * ��status=="error"ʱ����������Ϣ��ֵ��statement��
	  * ����statement=="��������Ӧ��ʱ"��֮�󴫵ݸ�ǰ̨
	  */
	private String statement;
	
	public String getData() {
		return data;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getStatement() {
		return statement;
	}
	
	public void setLen(String len)
	{
		this.len = len;
	}
	
	public void setValueRange(String valueRange)
	{
		this.valueRange = valueRange;
	}
	
	public String execute() throws Exception
	{
		System.out.println(this.len+"!!!!!"+this.valueRange);
<<<<<<< HEAD
		
		String resJasonStr = NewIDstdCollisionDetect.formJsonString(this.len, this.valueRange);
		HashMap<String, Double> HashMapID2Probability = NewIDstdCollisionDetect.computeCollisionRate(resJasonStr);
    	int len = HashMapID2Probability.size();
    	if (RecoUtil.NO_ID_MATCHED == len){
    		this.status = String.valueOf(RecoUtil.NO_ID_MATCHED);
    	} else if (RecoUtil.ONE_ID_MATCHED == len){
    		Iterator iterator = HashMapID2Probability.keySet().iterator();                
            while (iterator.hasNext()) {    
				Object key = iterator.next();    
				this.data = String.valueOf(key); 
				this.status = String.valueOf(RecoUtil.ONE_ID_MATCHED);
            } 
    	} else {
    		this.status = String.valueOf(len);
            
            JSONArray jsonArray = new  JSONArray();
            Iterator iterator2 = HashMapID2Probability.keySet().iterator();                
            while (iterator2.hasNext()) {    
				Object key = iterator2.next();  				
				JSONObject jsonObject = new  JSONObject();
				double probability = HashMapID2Probability.get(key);
				jsonObject.put("codeName",String.valueOf(key));
				jsonObject.put("probability",String.valueOf(probability));
				if (jsonArray.add(jsonObject)){
					System.out.println("ERROR! jsonArray.add(jsonObject)");
				}
				this.data = jsonArray.toString();
            }
    	}
    	
    	// for test only!
    	this.status = String.valueOf(2);        
        JSONArray jsonArray = new  JSONArray();
        JSONObject jsonObject = new  JSONObject();             
        jsonObject.put("codeName","b");
		jsonObject.put("probability",String.valueOf(0.3));
		jsonArray.add(jsonObject);
		JSONObject jsonObject2 = new  JSONObject();
		jsonObject2.put("codeName","a");
		jsonObject2.put("probability",String.valueOf(0.7));
=======
//		
//		�߼�����������
//		�߼�����������
//		�߼�����������
//		�߼�����������
//		�߼�����������
//		�߼�����������
//		
		//this.status = "7";
		//this.data = "[{codeName:'cpc',CollisionRatio:0.12},{codeName:'eCode',CollisionRatio:0.88},{codeName:'fCode',CollisionRatio:0.80},{codeName:'mFS',CollisionRatio:0.48},{codeName:'pdAF',CollisionRatio:0.18},{codeName:'Fnme',CollisionRatio:0.88},{codeName:'qqrf',CollisionRatio:0.56}]";
		
		this.status = "1";
		this.data = "{codeName:'cpc',CollisionRatio:0.12}";
		
		//this.status = "error";
		//this.statement = "��������Ӧʱ�䳬ʱ";
		
		//this.status = "0";
>>>>>>> xiaobaicoding-master
		
		jsonArray.add(jsonObject2);
		this.data = jsonArray.toString();
	
		//this.status = "7";
		//this.data = "[{codeName:'cpc',CollisionRatio:0.12},{codeName:'eCode',CollisionRatio:0.88},{codeName:'fCode',CollisionRatio:0.80},{codeName:'mFS',CollisionRatio:0.48},{codeName:'pdAF',CollisionRatio:0.18},{codeName:'Fnme',CollisionRatio:0.88},{codeName:'qqrf',CollisionRatio:0.56}]";
		
		System.out.println(this.status+this.data+this.statement);
		return SUCCESS;
	}

}
