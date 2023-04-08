package com.ufcg.psoft.mercadofacil;

public class Utils {

	public boolean stringChecker(String s) {
		if (s.isBlank() || s.isEmpty() || s == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean multipleStringChecker(String[] array) {
		if (array.length == 0) {
			return false;
		}
		
		for (String s:array) {
			if (stringChecker(s) == false) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean barCodeCheckerEAN13(String barcode) {
		
		if (barcode.length() != 13 
			|| stringChecker(barcode) == false 
			|| barcode.substring(0,2) != "789" // Código identificador do Brasil no padrão EAN-13
			|| barcode.substring(3,7) != "91375" // Código da empresa
			|| Integer.parseInt(barcode.substring(8,11)) < 0001) {
			return false;
		}
		
		int[] nums = new int[barcode.length()];
		
		for (int i=0;i<barcode.length();i++) {
			nums[i] = Integer.valueOf(barcode.charAt(i));
		}
		
		int passos1e2 = (nums[0] + nums[2] + nums[4] + nums[6] + nums[8] + nums[10])*3;
		int passo3 = nums[1] + nums[3] + nums[5] + nums[7] + nums[9] + nums[11];
		int dv = nums[12];
		
		if ((passos1e2+passo3+dv) % 10 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean valueChecker(Number n) {
		if (n == null || n.doubleValue() <= 0 || n.floatValue() <= 0 || n.intValue() <= 0 || n.longValue() <= 0) {
			return false;
		} else {
			return true;
		}
	}
}
