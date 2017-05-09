package test.util;

import junit.framework.Assert;

import org.junit.Test;

import util.LoadBalance;

public class LoadBalanceTest {
	
	@Test
	public void isLoadBalanceTest(){
		boolean flag = LoadBalance.isLoadBalance();
		Assert.assertEquals(flag, true);
	}
	@Test
	public void calculateTableTest3(){
		int re = LoadBalance.calculateTable("aCcSDF8654ssdf654");
		System.out.println(re);
		Assert.assertEquals(3, re);
	}
	@Test
	public void calculateTableTest4(){
		int re = LoadBalance.calculateTable("aDcSDF8654ssdf654");
		System.out.println(re);
		Assert.assertEquals(4 ,re);
	}
	@Test
	public void calculateTableTest2(){
		int re = LoadBalance.calculateTable("aBcSDF8654ssdf654");
		System.out.println(re);
		Assert.assertEquals(2, re);
	}
	@Test
	public void calculateTableTest1(){
		int re = LoadBalance.calculateTable("aAcSDF8654ssdf654");
		System.out.println(re);
		Assert.assertEquals(1, re);
	}
}
