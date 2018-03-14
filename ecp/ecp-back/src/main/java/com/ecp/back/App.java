package com.ecp.back;

import java.io.File;

public class App {
	public static void main(String[] args) {
		File[] files = new File("D:\\upload").listFiles();
        for (int i = 0; i < files.length; i++){
        	System.out.println(files[i].getName());
            if(files[i].getName().replaceAll("(.jpg|.png|.bmp|.gif)+","").length() != files[i].getName().length()){
                System.out.println(files[i].getName());
            }
        }
	}
}
