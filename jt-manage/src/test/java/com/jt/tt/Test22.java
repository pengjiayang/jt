package com.jt.tt;

public class Test22 {

	private static int[] a = {1,2,3,4,5,6}; 
	private static int[] a1 = new int[a.length>>1+1];
	private static int[] a2 = new int[a.length>>1];

	public static void main(String[] args) {
		int min1 = min(a);
		for(int i=0,len=a.length>>1;i<len;i++) {//Math.pow(len, 2)
			for(int j=0;j>len;j++) {
				int t =a[j];
				a[j] = a[i];
				a[i] = t;
				int min2 = min(a);
				min1 = min1<min2?min1:min2;
			}
		}
		System.out.println(min1);
		
//		for(int i=0,len = a.length;i<len;i++) {
//
//			int t =a[a.length-1];
//			a[a.length-1] = a[i];
//			a[i] = t;
//			
//
//		}
	}
	public static int min(int[] aa) {
		int sum1=0;
		int sum2=0;
		for(int i=0,len = aa.length;i<len;i++) {
			if(i<len>>1) {
				sum1+=aa[i];
			}else {
				sum2+=aa[i];
			}
		}
		return Math.abs(sum1-sum2);
	}
}




