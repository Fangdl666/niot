package cn.niot.controller;

import cn.unitTest.RuleFuncTest;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
* @Title: RespCode.java 
* @Package cn.niot.zt 
* @Description:ǰ��̨���ݴ������� 
* @author Zhang Tao
* @date 2013-12-3 ���� 
* @version V1.0
 */

public class IoTIDRecognitionAction extends ActionSupport {
	
	 /**
	  * �û���ǰ̨��������Ҫ��ѯ�ı���
	  */
	private String code;
	
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
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String execute() throws Exception
	{
		System.out.println(this.code);
//		
//		�߼�����������
//		�߼�����������
//		�߼�����������
//		�߼�����������
//		�߼�����������
//		�߼�����������
//		
		//this.status = "2";
		//this.data = "[{codeName:'cpc',probability:0.12},{codeName:'eCode',probability:0.88}]";
		
		//this.status="5";
		//this.data = "[{'codeName':'GB/T 19251-2003_EANUCC-8','probability':'0.2'},{'codeName':'GB/T 19251-2003_UCC-12','probability':'0.2'},{'codeName':'Ecode_1','probability':'0.2'},{'codeName':'GB/T 19251-2003_EANUCC-14','probability':'0.2'},{'codeName':'GB/T 19251-2003_EANUCC-13','probability':'0.2'}]";
		

		
		
		this.status = "1";
		this.data = "CPC";
		
		//this.status = "error";
		//this.statement = "��������Ӧʱ�䳬ʱ";
		
		//this.status = "0";
		
		//System.out.println(this.status+this.data+this.statement);
		
		
		return SUCCESS;
	}

}
