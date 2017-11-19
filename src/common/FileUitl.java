package common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.mina.util.Base64;

public class FileUitl {
	static Base64 base64=new Base64();
 /**
  * 将文件转成base64
  * @param path文件路径
  * @return  * 
  * @throws Exception
  */

 public static String encodeBase64File(String path) {
	try {
		File file = new File(path);;
		  FileInputStream inputFile;
		inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		  inputFile.read(buffer);
		  inputFile.close();
		  String s = new String(base64.encode(buffer));
		  return s;
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
 }

 /**
  * 将base64字符解码保存文件
  * @param base64Code
  * @param targetPath
  * @throws Exception
  */

 public static void decoderBase64File(String base64Code, String targetPath){
	 
	 try {
		 byte[] b= base64Code.getBytes();
		 byte[] buffer = base64.decode(b);
		 FileOutputStream out = new FileOutputStream(targetPath);
		 out.write(buffer);
		 out.close();
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
 }

 public static void main(String[] args) {
	

 }  

}