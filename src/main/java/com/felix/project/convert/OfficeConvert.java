package com.felix.project.convert;

import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Felxi
 * @date 2018-12-27
 */
@Component
public  class  OfficeConvert {
	
	private static  String openOfficeHome;

	private static  Integer openOfficePort;
	
	public static String getOpenOfficeHome() {
		return openOfficeHome;
	}
	
	public static Integer getOpenOfficePort() {
		return openOfficePort;
	}
	
	@Value("${openOfficeHome}")
	public  void setOpenOfficeHome(String openOfficeHome) {
		OfficeConvert.openOfficeHome = openOfficeHome;
	}
	@Value("${openOfficePort}")
	public  void setOpenOfficePort(Integer openOfficePort) {
		OfficeConvert.openOfficePort = openOfficePort;
	}
	
	
	public static void convert(File input , File output) throws OfficeException {

    System.out.println(openOfficeHome);
			DefaultOfficeManagerBuilder defaultOfficeManagerBuilder = new DefaultOfficeManagerBuilder();
			defaultOfficeManagerBuilder.setOfficeHome(openOfficeHome);
			defaultOfficeManagerBuilder.setPortNumber(openOfficePort);
			OfficeManager officeManager = defaultOfficeManagerBuilder.build();
			officeManager.start();
			
			//转换，自动识别转化类型
			OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
			converter.convert(input, output);
			officeManager.stop();
	
	}
}
