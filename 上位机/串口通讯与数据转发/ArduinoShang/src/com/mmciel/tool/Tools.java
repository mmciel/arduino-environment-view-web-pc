package com.mmciel.tool;



/**
 * �Ѵ������ݾ����ɹ淶��ʽ���ص�String[]
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