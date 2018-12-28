package com.felix.project.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Felxi
 * @date 2018-12-27
 */
public class FileUtils {
	public static void save(byte[] file, String filePath, String fileName) throws Exception {
		
		File targetFile = new File(filePath);
		//文件夹不存在创建
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}
	
	public static void download(String path,String fileName, HttpServletResponse response) throws Exception{
		File file = new File(path+ fileName);
		FileChannel channel = new FileInputStream(file).getChannel();
		ServletOutputStream out = response.getOutputStream();
		WritableByteChannel channel_out = Channels.newChannel(out);
		channel.transferTo(0,file.length(),channel_out);
		out.flush();
		out.close();
	}
}
