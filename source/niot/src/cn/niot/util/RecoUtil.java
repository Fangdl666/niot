package cn.niot.util;

public class RecoUtil {
	public static final String JNDI_NAME = "java:comp/env/jdbc/IoTDataSource";
	public static final String SELECT_IOTID = "select * from iotid where id=?";
	public static final String COLUMNNAME = "columnName";
	public static final String ID_LEN = "Len";
	public static final String ID_NAME = "IDName";
	public static final int INTERVAL_WIDTH = 2;
	public static final int COUNT_NUMBER_CHARS = 64;

	// administrative division
	public static final String SELECT_ADMINDIVISION = "select * from admindivision where id=?";

	// country and region code
	public static final String SELECT_COUNTRYREGIONCODE = "select * from countryregioncode where twocharcode=? or threecharcode=? or numcode=?";

	// �̲ݻ�е��Ʒ������
	public static final String SELECT_TABACCOMACHINEPRODUCT = "select * from tabaccomachineproduct where categorycode=? and groupcode=? and variatycode=?";

	// ��Ʒ����������Ʒ����EAN UPCǰ3λǰ׺��
	public static final String SELECT_EANUPC = "select * from EANUPC where begincode<=? and endcode>=?";

	// �̲ݻ�е���� ����ͱ����2���֣�ר�ü� ��¼D�еĵ�λ����(672)
	public static final String SELECT_TABACCOMACHINEPRODUCER = "select * from tabaccomachineproducer where id=? limit 1";

	// CID����4λ������������
	public static final String SELECT_DISTRICTNO = "select * from districtno where id=?";

	// �̲ݻ�е��Ʒ������ ��ҵ��е��׼�� �����е������룬�������Ʒ�ִ��루6��
	public static final String SELECT_TABACCOSTANDARDPART = "select * from tabaccostandardpart where categorycode=? and groupcode=? and variatycode=?";

	// �̲ݻ�е��Ʒ�����Ϸ���ͱ��� ��6���֣�ԭ��������(4)
	public static final String SELECT_TABACCOMATERIAL = "select * from tabaccomaterial where categorycode=? and variatycode=?";

	// ��ʳ��Ϣ��������� �����Ʒ��������(15)
	public static final String SELECT_FOORDACCOUNT = "select * from foodaccount where id=?";

	// ��ʳ��Ϣ��������� ��ʳ�豸��������루23��
	public static final String SELECT_GRAINEQUIPMENT = "select * from grainequipment where id=?";

	// ��ʳ��Ϣ��������� ��ʳ��ʩ��������루24��
	public static final String SELECT_GRAINESTABLISHMENT = "select * from grainestablishment where id=?";

	// �̲ݻ�е��Ʒ������ ����ͱ��� ��5���֣�����Ԫ���� ��5��
	public static final String SELECT_TABACCOELECTRICCOMPONENT = "select * from tabaccoelectriccomponent where categorycode=? and groupcode=?";

	// ���������������ȡһ����¼
	public static final String SELECT_RANDOMADMINDIVISION = "select * from admindivision where id>=convert(floor(((SELECT MAX(convert(Id,signed)) FROM admindivision)-(SELECT MIN(convert(Id,signed)) FROM admindivision)) * rand() + (SELECT MIN(convert(Id,signed)) FROM admindivision)),char(6)) limit 1";
	
	//EANUPC�������һ����¼
	public static final String SELECT_RANDOMEANUPC = "select floor(rand()*(endcode-begincode)+begincode) as code from (select * from EANUPC where rowno >= (select floor(rand()*(max(rowno)-min(rowno))) + min(rowno) from EANUPC) limit 1) t";
	
	public static final String SELECT_TYPEANDRULES="select * from iotid";
	
	//��û��ƥ��ɹ��κ�һ�ֱ�ʶ
	public static final int NO_ID_MATCHED = 0;
	
	//��ƥ��ɹ�һ�ֱ�ʶ
	public static final int ONE_ID_MATCHED = 1;


}
