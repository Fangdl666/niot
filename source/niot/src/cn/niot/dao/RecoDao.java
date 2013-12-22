package cn.niot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cn.niot.util.JdbcUtils;

import cn.niot.util.*;

public class RecoDao {
	private static RecoDao recoDao = new RecoDao();
	
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
	 * 参数也是返回值 返回hashMapTypeToRules、rmvRuleSet、rmvIDSet、hashMapRuleToTypes
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
				ArrayList<String> rules = new ArrayList<String>();// 函数返回值中的ArrayList
				String idType = results.getString("id");
				String lengthRule = results.getString("length");
				String byteRule = results.getString("byte");
				String functionRules = results.getString("function");
				double priorProbability = results.getDouble("priorProbability");
				if (lengthRule.length() != 0) {
					lengthRule = "IoTIDLength)(?#PARA=" + lengthRule + "){]";
					rules.add(lengthRule);// eg.length8)(?#PARA=8){]
					// 函数名字叫length8,参数8
					rmvRuleSet.put(lengthRule, 0.5);// 向rmvRuleSet添加length规则
					hashMapTypeToRulesSwitchhashMapRuleToTypes(
							hashMapRuleToTypes, lengthRule, idType);// hashMapTypeToRules转换为hashMapRuleToTypes,处理length
				}
				if (byteRule.length() != 0) {
					byteRule = "IoTIDByte)(?#PARA=" + byteRule + "){]";
					rules.add(byteRule);
					rmvRuleSet.put(byteRule, 0.5);// 向rmvRuleSet添加byte规则
					hashMapTypeToRulesSwitchhashMapRuleToTypes(
							hashMapRuleToTypes, byteRule, idType);// hashMapTypeToRules转换为hashMapRuleToTypes,处理byte
				}
				rmvIDSet.put(idType, priorProbability);// 向rmvRuleSet添加ID,先验概率0.5
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
		if (hashMapRuleToTypes.get(rule) == null) {// hashMapTypeToRules转换为hashMapRuleToTypes,处理function
			types.add(idType);
			hashMapRuleToTypes.put(rule, types);
		} else {
			types = hashMapRuleToTypes.get(rule);
			types.add(idType);
			hashMapRuleToTypes.put(rule, types);
		}

	}
	
	///行政区划代码(296)
	public boolean getAdminDivisionID(String id){
		Connection connection = JdbcUtils.getConnection();
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
	
	///世界各国和地区名称代码(279)
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
	
	//烟草机械产品用物料 分类和编码 第3部分：机械外购件(7)
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
	
	//商品条码零售商品编码EAN UPC前3位前缀码
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
	
	//烟草机械物料 分类和编码第2部分：专用件 附录D中的单位编码(672)
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
	
	//CID调用4位数字行政区号
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
	
	//烟草机械产品用物料 企业机械标准件 编码中的类别代码，组别代码和品种代码（6）
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
	
	//烟草机械产品用物料分类和编码 第6部分：原、辅材料(4)
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
	
	//粮食信息分类与编码 财务会计分类与代码(15)
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
	
	//粮食信息分类与代码 粮食设备分类与代码（23）
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
	
	//粮食信息分类与编码 粮食设施分类与编码（24）
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
	
	//烟草机械产品用物料 分类和编码 第5部分：电器元器件 （5）
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
	
	//随机取出一条行政区划代码数据
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
	
	//随机取出一条EANUPC数据
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
}
