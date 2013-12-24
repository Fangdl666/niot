package cn.niot.service;

import java.text.DateFormat;
import java.util.*;
import java.lang.reflect.*;

import cn.niot.dao.*;

public class IDstrRecognition {
	static String DEBUG = "OFF";//the value of DEBUG can be "ON" or "OFF"
	static String DEBUG_RES = "ON";//the value of DEBUG_RES can be "ON" or "OFF"
	
	static HashMap<String, Double> rmvRuleSet;
	static HashMap<String, Double> rmvIDSet;
	static HashMap<String, ArrayList<String>> hashMapTypeToRules;// ���Ͷ�Ӧ����
	static HashMap<String, ArrayList<String>> hashMapRuleToTypes;// �����Ӧ����
	
	public static HashMap<String, Double> IoTIDRecognizeAlg(String s){		
		HashMap<String, Double> typeProbability = new HashMap<String, Double>();
		hashMapTypeToRules = new HashMap<String, ArrayList<String>>();// ���Ͷ�Ӧ����
		hashMapRuleToTypes = new HashMap<String, ArrayList<String>>();// �����Ӧ����
		rmvRuleSet = new HashMap<String, Double>();// rmvRuleSet<��������Ȩ��>
		rmvIDSet = new HashMap<String, Double>();// rmvIDSet<���������������>

		RecoDao dao = RecoDao.getRecoDao();
		hashMapTypeToRules = dao.DBreadTypeAndRules(rmvRuleSet, rmvIDSet,
				hashMapRuleToTypes);
		ArrayList<String> rulesList;

		while (rmvIDSet.size() != 0 && rmvRuleSet.size() != 0) {
			sortRules();
			String maxRule = getMax();
			String[] splitRules = maxRule.split("\\)\\(\\?\\#PARA=");// ��ȡ������
			String[] splitParameter = splitRules[1].split("\\)\\{\\]");// ��ȡ����
			if ("ON" == DEBUG){
				System.out.print("matching " + splitRules[0] + "("
						+ splitParameter[0] + ").");
			}
			
			if (match(splitRules[0], splitParameter[0], s)) {
				// intersection(rmvIDSet, hashMapRuleToTypes.get(maxRule));
				if ("ON" == DEBUG){
					System.out.println("OK");
				}				
			} else {
				if ("ON" == DEBUG){
					System.out.println("ERR");
				}				
				subtraction(rmvIDSet, hashMapRuleToTypes.get(maxRule));
			}
			union(maxRule);
		}
		Date now = new Date();
	    DateFormat d1 = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG); //Ĭ�����ԣ�����µ�Ĭ�Ϸ��MEDIUM��񣬱��磺2008-6-16 20:54:53��
	    if ("ON" == DEBUG){
	    	System.out.print(d1.format(now)+":");
	    }
	    
	    double totalProbabity = 0;
		if (rmvIDSet.size() == 0) {
			if ("ON" == DEBUG){
				System.out.println(s + " doesn't belong any Type.");
			}			
		} else {
			if ("ON" == DEBUG_RES){
				System.out.print(s + " belong to:");
			}			
			Iterator<String> iterator = rmvIDSet.keySet().iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				totalProbabity = totalProbabity + rmvIDSet.get(key);
				if ("ON" == DEBUG_RES){
					System.out.print((String) key + " ");
				}				
			}
			if ("ON" == DEBUG_RES){
				System.out.println("");
			}			
		}
		Iterator<String> iterator2 = rmvIDSet.keySet().iterator();
		while (iterator2.hasNext()) {
			Object key2 = iterator2.next();
			double probability = rmvIDSet.get(key2) / totalProbabity;
			typeProbability.put(String.valueOf(key2), probability);
		}
		return typeProbability;
	}
	
	private static boolean match(String maxRule, String parameter, String input) {
		try {
			String[] arg = new String[2];
			arg[0] = parameter;
			arg[1] = input;
			Object result = "";
			Object[] argOther = new Object[4];
			Class[] c = new Class[4];
			Class ruleFunctionClass = Class
					.forName("cn.niot.rule.RuleFunction");

			if (maxRule.equals("IoTIDByte")) {
				argOther[0] = input;
				argOther[1] = parameter;
				argOther[2] = "";
				argOther[3] = "";
				c[0] = argOther[0].getClass();
				c[1] = argOther[1].getClass();
				c[2] = argOther[2].getClass();
				c[3] = argOther[3].getClass();
			} else if (maxRule.equals("IoTIDLength")) {
				argOther[0] = input;
				argOther[1] = 0;
				argOther[2] = parameter;
				argOther[3] = 0;
				c[0] = String.class;
				c[1] = int.class;
				c[2] = String.class;
				c[3] = int.class;
			} else {
				argOther[0] = input.toCharArray();
				argOther[1] = input.length();
				String[] splitString = parameter.split(",");
				int[] index = new int[splitString.length];
				for (int i = 0; i < splitString.length; i++) {
					index[i] = Integer.parseInt(splitString[i]);
				}
				argOther[2] = index;
				argOther[3] = index.length;
				c[0] = char[].class;
				c[1] = int.class;
				c[2] = int[].class;
				c[3] = int.class;
			}
			Method method = ruleFunctionClass.getMethod(maxRule, c);
			result = method.invoke(null, argOther);
			if (result == "OK")
				return true;
			if (result == "ERR")
				return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("RuleFunction.java file can not find " + maxRule
					+ " method,error");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private static String getMax() {
		Set<String> keySet = rmvRuleSet.keySet();
		Iterator ikey = keySet.iterator();
		String maxName = (String) ikey.next();
		String nextName = "";
		double max = rmvRuleSet.get(maxName);
		double next = 0;

		while (ikey.hasNext()) {
			nextName = (String) ikey.next();
			next = rmvRuleSet.get(nextName);
			if (next > max) {
				max = next;
				maxName = nextName;
			}
		}
		return maxName;
	}

	private static void sortRules() {
		// TODO Auto-generated method stub
		double p = 0.0;
		Set<String> rulekeySet = rmvRuleSet.keySet();
		Iterator<String> ruleikey = rulekeySet.iterator();
		while (ruleikey.hasNext()) {
			p = 0.0;
			String ruleName = (String) ruleikey.next();
			Set<String> idkeySet = rmvIDSet.keySet();
			Iterator<String> idikey = idkeySet.iterator();
			while (idikey.hasNext()) {
				String idName = idikey.next();
				if (hashMapRuleToTypes.get(ruleName).indexOf(idName) >= 0
						&& rmvIDSet.containsKey(idName)) {
					p = p + (double) rmvIDSet.get(idName);
				}
			}
			if (p == 0 || p == 1) {
				System.out.println(ruleName + " p is 0 or 1,error!");
			}
			if (p > 1 || p < 0) {
				System.out.println(ruleName + " p is not in 1~0 range,error!");
			}
			rmvRuleSet.put(ruleName, w(p));// p!=0 or 1
		}
	}

	private static double w(double p) {
		double q = 1 - p;
		return p * Math.log(1 / p) / Math.log(2) + q * Math.log(1 / q)
				/ Math.log(2);
	}

	// ƥ�䲻�ɹ�����rmvISDet - arrayList
	private static void subtraction(HashMap<String, Double> rmvIDSet,
			ArrayList<String> arrayList) {
		Iterator<String> iterator = rmvIDSet.keySet().iterator();

		while (iterator.hasNext()) {
			String temp = (String) iterator.next();

			if (arrayList.indexOf(temp) >= 0) { // ��arrayList���ҵ���
				// rmvIDSet.remove(temp);
				iterator.remove();
			}
		}
	}

	// ƥ��ɹ���������
	private static void intersection(HashMap<String, Double> rmvIDSet,
			ArrayList<String> arrayList) {
		Iterator<String> iterator = rmvIDSet.keySet().iterator();

		ArrayList<String> delete_list = new ArrayList<String>();

		while (iterator.hasNext()) {
			String temp = (String) iterator.next(); // ȡ��rmvIDSet��ֵ��arrayList�����ֵ�Ƚ�

			if (arrayList.indexOf(temp) == -1) { // ��arrayList��û���ҵ�����ɾ��
				// rmvIDSet.remove(temp); //�����ٱ�����ʱ��ɾ��
				delete_list.add(temp);
			}
		}

		for (String id_str : delete_list) {
			rmvIDSet.remove(id_str);
		}
	}

	// ����rmvRuleSet
	private static void union(String delRule) {
		Iterator<String> iter = rmvIDSet.keySet().iterator();

		ArrayList<String> arrayList = new ArrayList<String>(); // ֱ�Ӱ�ÿ��ruleȫ���ϲ���һ��list�������Ƿ��ظ�
		ArrayList<String> arrayList_Rules;

		while (iter.hasNext()) {
			String ID_key = (String) iter.next();

			arrayList_Rules = new ArrayList<String>();
			arrayList_Rules = hashMapTypeToRules.get(ID_key);

			for (String rule : arrayList_Rules) {
				arrayList.add(rule);
			}
		}

		Iterator<String> iterator = rmvRuleSet.keySet().iterator();

		while (iterator.hasNext()) {
			String Rule_key = iterator.next();

			if (arrayList.indexOf(Rule_key) == -1) { // �ںϲ���list����û���ҵ��������������Ҫɾ����
				// rmvRuleSet.remove(Rule_key);
				iterator.remove();
			}
		}
		rmvRuleSet.remove(delRule);
	}
}
