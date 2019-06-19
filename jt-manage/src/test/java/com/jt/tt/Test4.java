package com.jt.tt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test4 {
	public static void main(String[] args) {
		Integer []sz={3,2};
		List<Integer> list=new ArrayList<>(Arrays.asList(sz));//**须定义时就进行转化**
		    	list.add(1,5);
		    	Integer[] nsz=new Integer[list.size()];
		    	list.toArray(nsz);
		        System.out.println(Arrays.toString(nsz));
	
	}
}
