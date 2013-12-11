package cn.niot.util;


public class RecoUtil {
	public static final String JNDI_NAME = "java:comp/env/jdbc/IoTDataSource";
	public static final String SELECT_IOTID = "select * from iotid where id=?";
	public static final String COLUMNNAME = "columnName";
	
	//administrative division
	public static final String SELECT_ADMINDIVISION = "select * from admindivision where id=?";
	
	//country and region code
	public static final String SELECT_COUNTRYREGIONCODE = "select * from countryregioncode where twocharcode=? or threecharcode=? or numcode=?";
	

	public static final String ID_LEN = "Len";
	public static final String ID_NAME = "IDName";
	public static final int INTERVAL_WIDTH = 2;
	public static final int COUNT_NUMBER_CHARS = 64;

	//�̲ݻ�е��Ʒ������
	public static final String SELECT_TABACCOMACHINEPRODUCT = "select * from tabaccomachineproduct where categorycode=? and groupcode=? and variatycode=?";
	
	//��Ʒ����������Ʒ����EAN UPCǰ3λǰ׺��
	public static final String SELECT_EANUPC = "select * from EANUPC where begincode<=? and endcode>=?";
	
	//�̲ݻ�е���� ����ͱ����2���֣�ר�ü� ��¼D�еĵ�λ����
	public static final String SELECT_TABACCOMACHINEPRODUCER = "select * from tabaccomachineproducer where id=?";
	
	//CID����4λ������������
	public static final String SELECT_DISTRICTNO = "select * from districtno where id=?";

}
