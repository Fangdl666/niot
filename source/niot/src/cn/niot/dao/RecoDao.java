package cn.niot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import cn.niot.util.JdbcUtils;

import cn.niot.util.*;

public class RecoDao {
	private static RecoDao recoDao = new RecoDao();
	//public static Connection connection = null;
	public static RecoDao getRecoDao() {
		return recoDao;
	}
	
	public String getIoTID(String id){		
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_IOTID);
			stmt.setString(1, id);
			results = stmt.executeQuery();

			while (results.next()) {
				String res = results.getString("id") + "  " + results.getString("length") + "  " + results.getString("byte") + "  " + results.getString("function");
				System.out.println(res);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return "ok";
	}
	
	/*
	 * ����Ҳ�Ƿ���ֵ ����hashMapTypeToRules��rmvRuleSet��rmvIDSet��hashMapRuleToTypes
	 */
	public HashMap<String, ArrayList<String>> DBreadTypeAndRules(
			HashMap<String, Double> rmvRuleSet,
			HashMap<String, Double> rmvIDSet,
			HashMap<String, ArrayList<String>> hashMapRuleToTypes) {
		HashMap<String, ArrayList<String>> hashMapTypeToRules = new HashMap<String, ArrayList<String>>();
		
		Connection connection = JdbcUtils.getConnection();
		
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TYPEANDRULES);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				ArrayList<String> rules = new ArrayList<String>();// ��������ֵ�е�ArrayList
				String idType = results.getString("id");
				String lengthRule = results.getString("length");
				String byteRule = results.getString("byte");
				String functionRules = results.getString("function");
				double priorProbability = results.getDouble("priorProbability");
				if (lengthRule.length() != 0) {
					lengthRule = "IoTIDLength)(?#PARA=" + lengthRule + "){]";
					rules.add(lengthRule);// eg.length8)(?#PARA=8){]
					// �������ֽ�length8,����8
					rmvRuleSet.put(lengthRule, 0.5);// ��rmvRuleSet���length����
					hashMapTypeToRulesSwitchhashMapRuleToTypes(
							hashMapRuleToTypes, lengthRule, idType);// hashMapTypeToRulesת��ΪhashMapRuleToTypes,����length
				}
				if (byteRule.length() != 0) {
					byteRule = "IoTIDByte)(?#PARA=" + byteRule + "){]";
					rules.add(byteRule);
					rmvRuleSet.put(byteRule, 0.5);// ��rmvRuleSet���byte����
					hashMapTypeToRulesSwitchhashMapRuleToTypes(
							hashMapRuleToTypes, byteRule, idType);// hashMapTypeToRulesת��ΪhashMapRuleToTypes,����byte
				}
				rmvIDSet.put(idType, priorProbability);// ��rmvRuleSet���ID,�������0.5
				ArrayList<String> types = new ArrayList<String>();

				String[] splitFunctionRules = functionRules
						.split("\\(\\?\\#ALGNAME=");
				for (int i = 0; i < splitFunctionRules.length; i++) {
					if (splitFunctionRules[i].length() != 0) {
						rules.add(splitFunctionRules[i]);
						rmvRuleSet.put(splitFunctionRules[i], 0.5);
						hashMapTypeToRulesSwitchhashMapRuleToTypes(
								hashMapRuleToTypes, splitFunctionRules[i],
								idType);
					}
				}
				hashMapTypeToRules.put(idType, rules);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return hashMapTypeToRules;
	}
	
	private void hashMapTypeToRulesSwitchhashMapRuleToTypes(
			HashMap<String, ArrayList<String>> hashMapRuleToTypes, String rule,
			String idType) {
		ArrayList<String> types = new ArrayList<String>();
		if (hashMapRuleToTypes.get(rule) == null) {// hashMapTypeToRulesת��ΪhashMapRuleToTypes,����function
			types.add(idType);
			hashMapRuleToTypes.put(rule, types);
		} else {
			types = hashMapRuleToTypes.get(rule);
			types.add(idType);
			hashMapRuleToTypes.put(rule, types);
		}

	}
	
	///������������(296)
	public boolean getAdminDivisionID(String id){
		Connection	connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_ADMINDIVISION);
			stmt.setString(1, id);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	///��������͵������ƴ���(279)
	public boolean getCountryRegionCode(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_COUNTRYREGIONCODE);
			int i = 1;
			stmt.setString(i++, code);
			stmt.setString(i++, code);
			stmt.setString(i++, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е��Ʒ������ ����ͱ��� ��3���֣���е�⹺��(7)
	public boolean getTabaccoMachineProduct(String categoryCode, String groupCode, String variatyCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOMACHINEPRODUCT);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			stmt.setString(i++, variatyCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	    //�̲ݻ�е�������úͼ����ļ����븽¼C���ѯ
		public boolean getPrefixoftabaccoC(int code){
			Connection connection = JdbcUtils.getConnection();
			PreparedStatement stmt = null;
			ResultSet results = null;
			boolean ret = false;
			try {
				stmt = connection.prepareStatement(RecoUtil.SELECT_tabaccoC);
				int i = 1;
				stmt.setInt(i++, code);
				stmt.setInt(i++, code);
				
				results = stmt.executeQuery();
				int rowcount = 0;
				while (results.next()) {
					rowcount++;				
				}
				if(1 == rowcount){
					ret =  true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils.free(null, null, connection);
			}
			return ret;
		}
	
	//��Ʒ����������Ʒ����EAN UPCǰ3λǰ׺��
	public boolean getPrefixofRetailCommodityNumber(int code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_EANUPC);
			int i = 1;
			stmt.setInt(i++, code);
			stmt.setInt(i++, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е���� ����ͱ����2���֣�ר�ü� ��¼D�еĵ�λ����(672)
	public boolean getTabaccoMachineProducer(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOMACHINEPRODUCER);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//CID����4λ������������
	public boolean getDistrictNo(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_DISTRICTNO);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е��Ʒ������ ��ҵ��е��׼�� �����е������룬�������Ʒ�ִ��루6��
	public boolean getTabaccoStandardPart(String categoryCode, String groupCode, String variatyCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOSTANDARDPART);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			stmt.setString(i++, variatyCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е��Ʒ�����Ϸ���ͱ��� ��6���֣�ԭ��������(4)
	public boolean getTabaccoMaterial(String categoryCode, String variatyCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOMATERIAL);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, variatyCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� �����Ʒ��������(15)
	public boolean getFoodAccount(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_FOORDACCOUNT);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�豸��������루23��
	public boolean getGrainEquipment(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINEQUIPMENT);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ��ʩ��������루24��
	public boolean getGrainEstablishment(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINESTABLISHMENT);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е��Ʒ������ ����ͱ��� ��5���֣�����Ԫ���� ��5��
	public boolean getTabaccoElectricComponent(String categoryCode, String groupCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOELECTRICCOMPONENT);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//���ȡ��һ������������������
	public String getRandomAdminDivision(){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String code = "";
		try{
			stmt = connection.prepareStatement(RecoUtil.SELECT_RANDOMADMINDIVISION);
			results = stmt.executeQuery();
			while(results.next()){
				code = results.getString("id");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {  
			JdbcUtils.free(null, null, connection);
		}
		return code;
	}
	
	//���ȡ��һ��EANUPC����
	public String getRandomEANUPC(){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String code = "";
		try{
			stmt = connection.prepareStatement(RecoUtil.SELECT_RANDOMEANUPC);
			results = stmt.executeQuery();
			while(results.next()){
				code = String.valueOf(results.getInt("code"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {  
			JdbcUtils.free(null, null, connection);
		}
		return code;
	}
	
	//���ر�׼��ϸ��Ϣ
	public String getIDDetail(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String name = "";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try{
			stmt = connection.prepareStatement(RecoUtil.SELECT_IDDETAIL);
			stmt.setString(1, code);
			results = stmt.executeQuery();
			while(results.next()){
				name = results.getString("name");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {  
			JdbcUtils.free(null, null, connection);
		}
		return name;
	}
	
	
	
	//�������������ࡢ��ʩ���ࡢ��Ʒ�������
	public boolean getFuneral(String id,String type){
		Connection	connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(type);
			stmt.setString(1, id);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	///�̲ݻ�е(195)
		public boolean getTobaccoMachineryID(String id){
			Connection	connection = JdbcUtils.getConnection();
			PreparedStatement stmt = null;
			ResultSet results = null;
			boolean ret = false;
			try {
				stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOMACHINEPRODUCT);
				stmt.setString(1, id);
				results = stmt.executeQuery();
				int rowcount = 0;
				while (results.next()) {
					rowcount++;				
				}
				if(1 == rowcount){
					ret =  true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils.free(null, null, connection);
			}
			return ret;
		}
		//268-�������뵳�����ش�����Ʒ���   ������ݿ�
		public boolean getPortTariff268(String code) {
			Connection connection = JdbcUtils.getConnection();
			PreparedStatement stmt = null;
			ResultSet results = null;
			boolean ret = false;
			try{
				stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF268);
				int i = 1;
				stmt.setString(i, code);
			    results = stmt.executeQuery();
				int rowcount = 0;
				while (results.next()) {
					rowcount++;				
				}
				if(1 == rowcount){
					ret =  true;
					System.out.println("results="+results.toString());
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtils.free(null, null, connection);
			}
			return ret;
		}
		//	 270-������Ȼ�ֺ����������Ʒ���   ������ݿ�
			public boolean getPortTariff270(String code) {
				Connection connection = JdbcUtils.getConnection();
				PreparedStatement stmt = null;
				ResultSet results = null;
				boolean ret = false;
				try{
					stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF270);
					int i = 1;
					stmt.setString(i, code);
				    results = stmt.executeQuery();
					int rowcount = 0;
					while (results.next()) {
						rowcount++;				
					}
					if(1 == rowcount){
						ret =  true;
						System.out.println("results="+results.toString());
					} 
				}catch (Exception e) {
					e.printStackTrace();
				}finally{
					JdbcUtils.free(null, null, connection);
				}
				return ret;
			}
			//	 	 275-����������ҵ������������Ʒ���   ������ݿ�
					public boolean getPortTariff275(String code) {
						Connection connection = JdbcUtils.getConnection();
						PreparedStatement stmt = null;
						ResultSet results = null;
						boolean ret = false;
						try{
							stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF275);
							int i = 1;
							stmt.setString(i, code);
						    results = stmt.executeQuery();
							int rowcount = 0;
							while (results.next()) {
								rowcount++;				
							}
							if(1 == rowcount){
								ret =  true;
								System.out.println("results="+results.toString());
							} 
						}catch (Exception e) {
							e.printStackTrace();
						}finally{
							JdbcUtils.free(null, null, connection);
						}
						return ret;
					}
//				 	 276-����������Ʒ���������Ʒ���   ������ݿ�
								public boolean getPortTariff276(String code) {
									Connection connection = JdbcUtils.getConnection();
									PreparedStatement stmt = null;
									ResultSet results = null;
									boolean ret = false;
									try{
										stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF276);
										int i = 1;
										stmt.setString(i, code);
									    results = stmt.executeQuery();
										int rowcount = 0;
										while (results.next()) {
											rowcount++;				
										}
										if(1 == rowcount){
											ret =  true;
											System.out.println("results="+results.toString());
										} 
									}catch (Exception e) {
										e.printStackTrace();
									}finally{
										JdbcUtils.free(null, null, connection);
									}
									return ret;
								}					
		// 	281-�����鱦��ʯ��������Ʒ���������Ʒ���   ������ݿ�
		public boolean getPortTariff280(String code) {
			Connection connection = JdbcUtils.getConnection();
			PreparedStatement stmt = null;
			ResultSet results = null;
			boolean ret = false;
			try{
				stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa281);
				int i = 1;
				stmt.setString(i, code);
			    results = stmt.executeQuery();
				int rowcount = 0;
				while (results.next()) {
					rowcount++;				
				}
				if(1 == rowcount){
					ret =  true;
					System.out.println("results="+results.toString());
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtils.free(null, null, connection);
			}
			return ret;
		}
		//281-�����鱦��ʯ���������ʷ��������Ʒ���   ������ݿ�
		public boolean getPortTariffMa281(String code) {
			Connection connection = JdbcUtils.getConnection();
			PreparedStatement stmt = null;
			ResultSet results = null;
			boolean ret = false;
			try{
				stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa281);
				int i = 1;
				stmt.setString(i, code);
			    results = stmt.executeQuery();
				int rowcount = 0;
				while (results.next()) {
					rowcount++;				
				}
				if(1 == rowcount){
					ret =  true;
					System.out.println("results="+results.toString());
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtils.free(null, null, connection);
			}
			return ret;
		}
		//282-������Ϣ��ȫ����������Ʒ���   ������ݿ�
		public boolean getPortTariffMa282(String code) {
			Connection connection = JdbcUtils.getConnection();
			PreparedStatement stmt = null;
			ResultSet results = null;
			boolean ret = false;
			try{
				stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa282);
				int i = 1;
				stmt.setString(i, code);
			    results = stmt.executeQuery();
				int rowcount = 0;
				while (results.next()) {
					rowcount++;				
				}
				if(1 == rowcount){
					ret =  true;
					System.out.println("results="+results.toString());
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtils.free(null, null, connection);
			}
			return ret;
		}
		//284-������ᾭ��Ŀ�����ʹ����  ������ݿ�
				public boolean getPortTariffMa284(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa284);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				//285-����������Ϣ����ʹ����  ������ݿ�
				public boolean getPortTariffMa285(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa285);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				
				//287-������װ����ʹ����  ������ݿ�
				public boolean getPortTariffMa287(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa287);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				//288-������װ����ʹ����  ������ݿ�
				public boolean getPortTariffMa288(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa288);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				//291-����ҽҩ��е����ʹ����  ������ݿ�
				public boolean getPortTariffMa291(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa191);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				//395-����������Ϣ�������ʹ����  ������ݿ�
				public boolean getFireInfomation395(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF395);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				//399-����������Ϣ�������ʹ����  ������ݿ�
				public boolean getFireInfomation399(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF399);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				//403-����������Ϣ�������ʹ����  ������ݿ⣺����������������
				public boolean getFireInfomation403(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF403);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				
				//409-����������Ϣ�������ʹ����  ������ݿ⣺����ѵ�����˴���
				public boolean getFireInfomation409(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF409);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				
				
				
				
				
}
