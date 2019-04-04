package com.mmciel.tool;



/**
 * 把错误数据净化成规范形式返回到String[]
 * @author mmciel
 *
 */
public class Tools {
	public static String[] UploadData = null;
	public static String[] getSerialString(String temp ) {
		int len = 0;
		
		String[] t = temp.split("#");

		for(String i: t) {
			if(i.length() == 11) {
				len++;
			}
		}
		String t2[] = new String[len];
		int index = 0;
		for(String i: t) {
			if(i.length() == 11) {
				t2[index] = i;
				index++;
			}
		}
		return t2;
	}
}