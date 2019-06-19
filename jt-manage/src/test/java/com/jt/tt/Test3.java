package com.jt.tt;

import java.util.ArrayList;
import java.util.List;

public class Test3 {
	public static void main(String[] args) {
		int[] ary = {0,1,2,3,4,5,6,7,8,9};//要分割的数组
		int splitSize = ary.length>>1;
		Object[] subAry = splitAry(ary, splitSize);
		for(Object obj: subAry){//打印输出结果
			int[] aryItem = (int[]) obj;
			for(int i = 0; i < aryItem.length; i++){
				System.out.print(aryItem[i] + ", ");
			}
			System.out.println();
		}
	}
	private static Object[] splitAry(int[] ary, int splitSize) {
		 int count = ary.length % splitSize == 0 ? ary.length / splitSize: ary.length / splitSize + 1;
		  List<List<Integer>> subAryList = new ArrayList<List<Integer>>();
		  for (int i = 0; i < count; i++) {
		   int index = i * splitSize;
		   List<Integer> list = new ArrayList<Integer>();
		   int j = 0;
			   while (j < splitSize && index < ary.length) {
				    list.add(ary[index++]);
				    j++;
			   }
		   subAryList.add(list);
		  }
		  
		  Object[] subAry = new Object[subAryList.size()];
		  
		  for(int i = 0; i < subAryList.size(); i++){
			   List<Integer> subList = subAryList.get(i);
			   int[] subAryItem = new int[subList.size()];
			   for(int j = 0; j < subList.size(); j++){
				   subAryItem[j] = subList.get(j).intValue();
			   }
			   subAry[i] = subAryItem;
		  }
		  
		  return subAry;
		 }
	}
