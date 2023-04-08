package com.ufcg.psoft.mercadofacil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
	
	Utils u = new Utils();
	
	@Test
	@DisplayName("String válidas quaisquer")
	void testStringsValidas() {
		assertEquals(true,"af afsjkdfp	ajskd3");
		assertEquals(true,"18792mkasdm");
		assertEquals(true,"blankityblank");
		assertEquals(true,"               .");
		assertEquals(true,"The Silmarillion");
	}
    
	@Test
	@DisplayName("String vazia")
	void testStringVazia() {
		assertEquals(false,u.stringChecker(""));
	}
	
	@Test
	@DisplayName("String em branco (espaço)")
	void testStringEmBranco() {
		assertEquals(false,u.stringChecker(" "));
	}
	
	@Test
	@DisplayName("String em branco (tab)")
	void testStringEmBrancoTab() {
		assertEquals(false,u.stringChecker("	"));
	}
	
	@Test
	@DisplayName("String em branco (nova linha)")
	void testStringEmBrancoNovaLinha() {
		assertEquals(false,u.stringChecker("\n"));
	}
	
	@Test
	@DisplayName("String em branco - vários valores")
	void testStringEmBrancoV1() {
		assertEquals(false,u.stringChecker(" 	\n"));
	}
	
	@Test
	@DisplayName("String em branco - vários valores")
	void testStringEmBrancoV2() {
		assertEquals(false,u.stringChecker("\n	 \n  	 "));
	}
	
	@Test
	@DisplayName("String em branco - vários valores")
	void testStringEmBrancoV3() {
		assertEquals(false,u.stringChecker("    	 	 	"));
	}
	
	@Test
	@DisplayName("Null é passado como argumento no lugar de uma string")
	void testStringNull() {
		assertEquals(false,u.stringChecker(null));
	}
	
	@Test
	@DisplayName("Testa o método multipleStringChecker")
	void testMultipleStringChecker() {
		
		String[] a1 = new String[] {};
		String[] a2 = new String[] {"fellowship", "towers", "king", null};
		String[] a3 = new String[] {"","neuromancer","count zero","mona lisa overdrive"};
		String[] a4 = new String[] {"stonehenge","	","azincourt","waterloo"};
		String[] a5 = new String[] {"0 1 0 0 0 1 0 1"," 	01001110","01000100    \n"};
		String[] a6 = new String[] {"01010100","01000101","01010011","01010100"};
		
		assertEquals(false,u.multipleStringChecker(a1));
		assertEquals(false,u.multipleStringChecker(a2));
		assertEquals(false,u.multipleStringChecker(a3));
		assertEquals(false,u.multipleStringChecker(a4));
		assertEquals(true,u.multipleStringChecker(a5));
		assertEquals(true,u.multipleStringChecker(a6));
	}
	
	@Test
	@DisplayName("Testa a aceitação de valores numéricos inválidos")
	void testNumeros() {
		float f0 = (float) 0.0;
		float fn = (float) -6.7;
		double d0 = 0.0;
		double dn = -88.0000;
		
		assertEquals(false,u.valueChecker(null));
		assertEquals(false,u.valueChecker(f0));
		assertEquals(false,u.valueChecker(fn));
		assertEquals(false,u.valueChecker(d0));
		assertEquals(false,u.valueChecker(dn));
		assertEquals(false,u.valueChecker(0));
		assertEquals(false,u.valueChecker(-99));
		assertEquals(false,u.valueChecker(0L));
		assertEquals(false,u.valueChecker(-3141592L));
	}
	
	
}
