package cn.niot.rule;

import cn.niot.dao.RecoDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleFunction {
	
	static String ERR = "ERR";
	static String OK = "OK";
	public static void main(String[] args) {
		//TODO:
	}
	
	//Function: represent a decimal integer whose value range is from 1 to 99 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String TwoByteDecimalnt(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex < 2)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];

		if ((IDstr[index1] == '0') && (IDstr[index2] == '0'))
		{
			return ERR;
		}
		
		if ((IDstr[index1] < '0') || (IDstr[index1] > '9'))
		{
			return ERR;
		}
		
		if ((IDstr[index2] < '0') || (IDstr[index2] > '9'))
		{
			return ERR;
		}

		return OK;
		
	}
	
	//Function: decide the cigarette subclass code according to different mainclass code 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//		 Index[0] is  the index of mainclass code whose values can be int 1, 2, 3, 4, 9
	//		 Index[1] and Index[2] are the index of subclass codes
	//LenIndex: the number of indexes that must be 3
	//creator: zll
	public static String CigaSubClassCode(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		
		if(LenIndex != 3){
			return ERR;
		}
		//mainclass: 1 subclass: 01, 02, 99
		if(IDstr[Index[0]] == '1'){
			if(IDstr[Index[1]] == '0'){
				if(IDstr[Index[2]] == '1' || IDstr[Index[2]] == '2'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '9'){
				if(IDstr[Index[2]] == '9'){
					return OK;
				}
			}
		}else if(IDstr[Index[0]] == '2'){	//mainclass: 2 subclass: 01-09, 10, 99
			if(IDstr[Index[1]] == '0'){
				if(IDstr[Index[2]] > '0' && IDstr[Index[2]] <= '9'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '1'){
				if(IDstr[Index[2]] == '0'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '9'){
				if(IDstr[Index[2]] == '9'){
					return OK;
				}
			}
		}else if(IDstr[Index[0]] == '3'){	//mainclass: 3 subclass: 01-08, 99
			if(IDstr[Index[1]] == '0'){
				if(IDstr[Index[2]] > '0' && IDstr[Index[2]] < '9'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '9'){
				if(IDstr[Index[2]] == '9'){
					return OK;
				}
			}
		}else if(IDstr[Index[0]] == '4'){	//mainclass: 4 subclass: 01-09, 10, 11, 99
			if(IDstr[Index[1]] == '0'){
				if(IDstr[Index[2]] > '0' && IDstr[Index[2]] <= '9'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '1'){
				if(IDstr[Index[2]] == '0' || IDstr[Index[2]] == '1'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '9'){
				if(IDstr[Index[2]] == '9'){
					return OK;
				}
			}
		}else if(IDstr[Index[0]] == '9'){	//mainclass: 9 subclass: 01
			if(IDstr[Index[1]] == '0' && IDstr[Index[2]] == '1'){
				return OK;
			}
		}
		return ERR;
	}
	
	//Function: There are four characters. The first two characters are represented as month, the other two 
	//characters are represented the date in that month.
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//		 Index[0] and Index[1] are the index of month
	//		 Index[2] and Index[3] are the index of date
	//LenIndex: the number of indexes that must be 4
	//creator: zll
	public static String MonthDate(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		try{
			int date = Integer.parseInt(String.valueOf(IDstr[Index[2]])) * 10 + Integer.parseInt(String.valueOf(IDstr[Index[3]]));
			if(LenIndex != 4){
				return ERR;
			}
			if(IDstr[Index[0]] == '0'){	//month: 01, 03, 05, 07, 08
				if(IDstr[Index[1]] == '1' || IDstr[Index[1]] == '3' || IDstr[Index[1]] == '5' ||
					IDstr[Index[1]] == '7' || IDstr[Index[1]] == '8'){
					if(date <= 31){
						return OK;
					}
				}else if(IDstr[Index[1]] == '4' || IDstr[Index[1]] == '6' || IDstr[Index[1]] == '9'){	//month: 04, 06, 09
					if(date <= 30){
						return OK;
					}
				}else if(IDstr[Index[1]] == '2'){	//month: 02
					if(date <= 29){
						return OK;
					}
				}
			}else if(IDstr[Index[0]] == '1'){	//month: 10, 12
				if(IDstr[Index[1]] == '0' || IDstr[Index[1]] == '2'){
					if(date <= 31){
						return OK;
					}
				}else if(IDstr[Index[1]] == '1'){	//month: 11
					if(date <= 30){
						return OK;
					}
				}
			}
			return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: Cigarette organization code. There are totally 2 characters.
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//		 Index[0] is the index of the first character of cigarette organization code which is 1,2 or 9.
	//		 Index[1] is the index of the second character.
	//LenIndex: the number of indexes that must be 2
	//creator: zll
	public static String CigaOrgCode(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 2){
				return ERR;
			}
			if(IDstr[Index[0]] == '1'){
				if(IDstr[Index[1]] >= '0' && IDstr[Index[1]] <= '9'){
					return OK;
				}
			}else if(IDstr[Index[0]] == '2'){
				if(IDstr[Index[1]] >= '0' && IDstr[Index[1]] <= '3'){
					return OK;
				}
			}else if(IDstr[Index[0]] == '9'){
				if(IDstr[Index[1]] == '9'){
					return OK;
				}
			}
			return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:��λ������һλΪ1ʱ���ڶ�λΪ��0~9������һλΪ2ʱ���ڶ�λΪ��0~3������һλΪ9ʱ���ڶ�λΪ9. 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, �̶�Ϊ2
	public static String Count(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2){
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		
		if (IDstr[index1] == '1'){
			if ((IDstr[index2] >= '0') && (IDstr[index2] <= '9')){
				return OK;
			}
		}
		
		if (IDstr[index1] == '2'){
			if ((IDstr[index2] >= '0') && (IDstr[index2] <= '3')){
				return OK;
			}     
		}
		
		if (IDstr[index1] == '9'){
			if (IDstr[index2] == '9'){
				return  OK;
			}			
		}
		
		return ERR;
	}
	
	//Function: Cigarette department or subordinate department code. There are totally 2 characters.
	//IDstr: ID string. Code range is 00-97.
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes that must be 2
	//creator: zll
	public static String CigaDepCode(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 2){
				return ERR;
			}
			int depCode = Integer.parseInt(String.valueOf(IDstr[Index[0]])) * 10 + Integer.parseInt(String.valueOf(IDstr[Index[1]]));
			if(depCode >= 0 && depCode <= 97){
				return OK;
			}else{
				return ERR;
			}
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: There are all together 6 chars of administrative division code. This method is to used to
	//connect to the database and get the first 4 chars of division code. 
	//IDstr: ID string, the first 4 chars of administrative division code. 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes that must be 4
	//creator: zll
	public static String First4CharsofAdminDivisionforCiga(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String id = "";
			String append = "00";
			if(LenIndex != 4){
				return ERR;
			}
			if(IDstr[Index[0]] == '0' && IDstr[Index[1]] == '0'){
				return OK;
			}
			RecoDao recoDao = new RecoDao();
			for(int i = 0; i < LenIndex; i++){
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			id = id.concat(append);
			boolean ret  = recoDao.getAdminDivisionID(id);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: 6λ�������������ǰ2λ.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: �����������������λ��
	//LenIndex: ���ȱ�����2λ
	public static String First2CharsofAdminDivision(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String id = "";
			String append = "0000";
			if(LenIndex != 2){
				return ERR;
			}
			RecoDao recoDao = new RecoDao();
			for(int i = 0; i < LenIndex; i++){
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			id = id.concat(append);
			boolean ret  = recoDao.getAdminDivisionID(id);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}

	//Function: 6λ������������.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: �����������������λ��
	//LenIndex: ���ȱ�����6λ
	//creator: zll
	public String AdminDivision(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 6 ){
				return ERR;
			}
			String id = "";
			RecoDao recoDao = new RecoDao();
			for(int i = 0; i < LenIndex; i++){
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			boolean ret  = recoDao.getAdminDivisionID(id);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: ��������͵������ƴ���ΪCPC�������,(279)�й涨���볤��Ϊ2-3λ��CPC����Ϊ�ڵ�4λ��0.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: ������������͵������ƴ����λ��
	//LenIndex: �����Ƕ��٣�һ����4λ
	//creator: zll
	public static String CountryRegionCodeforCPC(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 4 ){
				return ERR;
			}
			for(int i = 0; i < LenIndex - 1; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getCountryRegionCode(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: ��������͵������ƴ��룬(279)�й涨���볤��Ϊ2-3λ.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: ������������͵������ƴ����λ��
	//LenIndex: �����Ƕ��٣�һ����2-3λ
	//creator: zll
	public static String CountryRegionCode(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(!(LenIndex == 2 || LenIndex == 3)){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getCountryRegionCode(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: �̲ݻ�е��Ʒ������ ����ͱ��� ��3���֣���е�⹺��(7)�е�5λ����.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: �����̲ݻ�е��Ʒ�����ϴ����λ��
	//LenIndex: ������6λ
	//creator: zll
	public static String TabaccoMachineProduct(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 5){
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]);
			String groupCode = String.valueOf(IDstr[Index[1]]) + String.valueOf(IDstr[Index[2]]);
			String variatyCode = String.valueOf(IDstr[Index[3]]) + String.valueOf(IDstr[Index[4]]);
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoMachineProduct(categoryCode, groupCode, variatyCode);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: ��Ʒ����������Ʒ����EAN UPCǰ3λǰ׺��.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: ����ǰ׺���λ��
	//LenIndex: ������3λ
	//creator: zll
	public static String PrefixofRetailCommodityNumber(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			int num = 0;
			if(LenIndex != 3){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			num = Integer.parseInt(code);
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getPrefixofRetailCommodityNumber(num);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	//Function:  �̲ݻ�е���� ����ͱ����2���֣�ר�ü� ��¼D�еĵ�λ����.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: ����ǰ׺���λ��
	//LenIndex: ������2λ��Ϊ��д��ĸ
	//creator: zll
	public static String TabaccoMachineProducer(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 2){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoMachineProducer(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  4λ��������.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: �����������õ�λ��
	//LenIndex: ������4λ��Ϊ����
	//creator: zll
	public static String DistrictNo(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 4){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getDistrictNo(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	//Function:  CID�������������.
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: (18,-1),��18λ�Ժ���ַ�������������ʽ��֤
	//LenIndex: ���ȱ�Ϊ2
	//creator: zll
	public static String CIDRegex(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			String regex = "(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62}){2}\\.cid\\.iot\\.cn";
			int prefix = 18;
			
			if(Index[0] != prefix){
				return ERR;
			}
			for(int i = Index[0]; i < LenID; i++){
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret  = ma.matches();
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  �̲���ҵ��׼����������������룬�������Ʒ�ִ���
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: ���������루1λ���������루2λ����Ʒ�ִ��루2λ����λ��
	//LenIndex:���ȱ�Ϊ5
	//creator: zll
	public static String TabaccoStandardPart(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 5){
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]);
			String groupCode = String.valueOf(IDstr[Index[1]]) + String.valueOf(IDstr[Index[2]]);
			String variatyCode = String.valueOf(IDstr[Index[3]]) + String.valueOf(IDstr[Index[4]]);
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoStandardPart(categoryCode, groupCode, variatyCode);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  �̲ݻ�е��Ʒ�����Ϸ���ͱ��� ��6���֣�ԭ��������
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: ���������루2λ����Ʒ�ִ��루3λ����λ��
	//LenIndex:���ȱ�Ϊ5
	//creator: zll
	public static String TabaccoMaterial(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 5){
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]) + String.valueOf(IDstr[Index[1]]);
			String variatyCode = String.valueOf(IDstr[Index[2]]) + String.valueOf(IDstr[Index[3]]) + String.valueOf(IDstr[Index[4]]);
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoMaterial(categoryCode, variatyCode);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  ���ʻ��˴���֤��ʶ�������в���������ҵ�Զ����������ƥ��,���ֻ�����ĸ����������ĸ���档
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: ��������ĵ�����λ��
	//LenIndex:����<=16
	//creator: zll
	public static String IntFreitForwarding(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			String regex = "[a-zA-Z][a-zA-Z0-9]{0,15}";
			int prefix = 2;//18;
			
			if(Index[0] != prefix){
				return ERR;
			}
			//���һλΪУ��λ
			for(int i = Index[0]; i < LenID - 1; i++){
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret  = ma.matches();
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  ��ʳ��Ϣ��������� �����Ʒ��������(15)
	//IDstr: ��ʶ����
	//LenID: ��ʶ����ĳ��� 
	//Index: ��������ĵ�����λ��
	//LenIndex:���ȱ�Ϊ6
	//creator: zll
	public static String FoodAccount(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 6){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getFoodAccount(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:��λ������һλΪ0ʱ���ڶ�λΪ��0��1��2������һλΪ1ʱ���ڶ�λΪ��1��2,6,7������һλΪ��2��ʱ���ڶ�λΪ��0~5�� 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, �̶�Ϊ2
	public static String CPCTwoByte(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2){
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		
		if (IDstr[index1] == '0'){
			if ((IDstr[index2] == '0')||(IDstr[index2] =='1')||IDstr[index2] == '2'){
				return OK;
			}
		}
		
		if (IDstr[index1] == '1'){
			if ((IDstr[index2] == '1') ||(IDstr[index2] == '2')||(IDstr[index2] == '6')||(IDstr[index2] == '7')){
				return OK;
			}     
		}
		
		if (IDstr[index1] == '2'){
			if ((IDstr[index2] >= '0')&&(IDstr[index2] <= '5')){
				return  OK;
			}			
		}
		
		return ERR;
	}
	
	//Function: �ж������ֽ��ǲ��Ǵ����·� 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, �̶�Ϊ2
	public static String Month(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		
		if (IDstr[index1] == '0'){
			if ((IDstr[index2] > '0') && (IDstr[index2] <= '9')){
				return OK;
			}			
		}
		
		if (IDstr[index1] == '1'){
			if ((IDstr[index2] >= '0') && (IDstr[index2] <= '2')){
				return OK;
			}			
		}
		return ERR;		
	}
	//Function: �ж������ֽ��ǲ�������LS/T 1704.3-2004��1�еı���
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, �̶�Ϊ6
	public static String ClassOfGrain1 (char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 6)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		int index3 = Index[2];
		int index4 = Index[3];
		int index5 = Index[4];
		int index6 = Index[5];

		
		
		if (IDstr[index1] == '0' && IDstr[index2] == '1'){
			if (IDstr[index3] == '0'){
				if (IDstr[index4] == '1' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '4'){
							return OK;
						}
					}
				}
				else if (IDstr[index4] == '2' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '5'){
							return OK;
						}
					}
				}
			}
		}
		if (IDstr[index1] == '0' && IDstr[index2] == '2'){
			if (IDstr[index3] == '0'){
				if (IDstr[index4] == '1' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] == '1'|| IDstr[index6] == '0'){
							return OK;
						}
					}
				}
				else if (IDstr[index4] == '2' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '2'){
							return OK;
						}
					}
				}
				else if(IDstr[index4] == '3' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '1'){
							return OK;
						}
					}
				}
			}
		}
		if (IDstr[index1] == '0' && IDstr[index2] == '3'){
			if (IDstr[index3] == '0'){
				if (IDstr[index4] == '1' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '8'){
							return OK;
						}
					}
					else if (IDstr[index5] == '1' ){
						if (IDstr[index6] >= '1'&& IDstr[index6] <= '2'){
							return OK;
						}					
					}
				}
				else if (IDstr[index4] == '2' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '8'){
							return OK;
						}
					}
					else if (IDstr[index5] == '1' ){
						if (IDstr[index6] >= '1'&& IDstr[index6] <= '3'){
							return OK;
						}					
					}
				}
				else if (IDstr[index4] == '3' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '8'){
							return OK;
						}
					}
					else if(IDstr[index5] == '1'&& IDstr[index6] == '1'){
						return OK;
					}
				}
				else if (IDstr[index4] == '4' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] == '0'|| IDstr[index6] == '1'){
							return OK;
						}
					}
				}
				else if (IDstr[index4] == '5' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] == '0'|| IDstr[index6] == '1'){
							return OK;
						}
					}
				}
				
			}
		}
		return ERR;
	}
	
	//Function: �ж�2���ֽ��ǲ�������(01-07,99)
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, �̶�Ϊ2
	public static String TwobytleCode07 (char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
	
		if  (IDstr[index1] == '0' ){
			if (IDstr[index2] >= '1' && IDstr[index2] <= '7'){
				return OK;
			}
		}
		
		if  (IDstr[index1] == '9' && IDstr[index2] == '9'){
				return OK;
			}
		
	 return ERR;		
	}
	
	//Function: �ж�2���ֽ��ǲ�������(01-06,99)
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, �̶�Ϊ2
	public static String TwobytleCode06 (char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
	
		if  (IDstr[index1] == '0' ){
			if (IDstr[index2] >= '1' && IDstr[index2] <= '6'){
				return OK;
			}
		}
		
		if  (IDstr[index1] == '9' && IDstr[index2] == '9'){
				return OK;
			}		
		return ERR;	 
	}
	
	//Function: UCODE ��Top Level Domain Code: TLDc��ȡֵ����Ϊ"E000"�͡�FFFF��
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, �̶�Ϊ2
	public static String CountUcode(char [] IDstr, int LenID, int [] Index, int LenIndex){
		if (LenIndex != 4){
			return "ERR";
		}		
		int index1 = Index[0];
		int index2 = Index[1];
		int index3 = Index[2];
		int index4 = Index[3];		
		if (IDstr[index1] =='E'){
			if ((IDstr[index2] == '0') && (IDstr[index3] == '0')&&(IDstr[index4] == '0')){
				return "ERR";
			}
		}		
		if (IDstr[index1] == 'F'){
			if ((IDstr[index2] == 'F') && (IDstr[index3] == 'F')&&(IDstr[index4] == 'F')){
				return "ERR";
			}     
		}		
		return "OK";
	}

	//Function: EPC���������������(Domain Manager)����ȡֵΪ0xA011363��ȫ0
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String DomainManagerInEPCCheck(char [] IDstr, int LenID,
		int [] Index, int LenIndex) {
		int i = 0;
		int Index_k = 0;	//is 0xA011363 or not
		int Zero_k = 0;         //is 0 or not
		char[] str = {'A','0','1','1','3','6','3'};
			
		//the length of domain manager is from 28 to 128
		if(LenIndex<28 || LenIndex>128) {
			return ERR;         
		}
			
		for(i=0;i<LenIndex;i++) {
			if(IDstr[Index[i]] == str[Index_k]) {       //as long as str is in the IDstr,Index_k will be 7
				Index_k++;
				if(Index_k == 7){
					return ERR;
				}
			} else {
				Index_k = 0;
			}
				
			if(IDstr[Index[i]] == '0') {
				Zero_k++;
			}
		}
			
		if(Zero_k == LenIndex) {
			return ERR;
		}			
		return OK;
	}
	
	
}
