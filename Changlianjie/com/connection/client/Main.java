package com.connection.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import sun.misc.BASE64Encoder;

public class Main {
	
	public static void main(String[] args) throws Exception {
		/*Thread thread = new Thread(new Task());
		thread.start();
		
		Manager.getInstance().addData("mac1", "data00");
		Manager.getInstance().sendDataListener("mac1");*/
		
	}
	static class Task implements Runnable{
		int a=0;
		public void run() {
			while(true)
			{
				a++;
				Manager.getInstance().addData("mac1", "data00"+a);
				System.out.println("ss");
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
     * 将文件转为byte[]
     * @param filePath 文件路径
     * @return
	 * @throws IOException 
     */
    public static String javagetBytes(String filePath) throws IOException{
    	File file = new File(filePath);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        
        String s = new String(Base64.getEncoder().encode(buffer),"8859_1");
        System.out.println(s);
    	return null;
    }
    
    public static String encodeBase64File(String filePath) throws Exception {
    	File file = new File(filePath);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        String s = new BASE64Encoder().encode(buffer);
        System.out.println(s);
        return null;
    }
}
