package com.felix.project.controller;

import com.felix.project.convert.OfficeConvert;
import com.felix.project.utils.FileUtils;
import com.felix.project.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author Felxi
 * @date 2018-12-27
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {
	@Value("${upload.path}")
	private String uploadpath;
	@Value("${upload.pdf.temp}")
	private String pdftemp;
	@GetMapping(value = "/file")
	public String uploadl(){
		return "upload";
	}
	
	@PostMapping(value = "/file")
	public String upload(@RequestParam(value = "file",required = false) MultipartFile file, Model model)throws Exception{
		//图片名字
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		//新文件名
		String uuid= UuidUtil.get32UUID();
		String newFileName = uuid+ suffixName;
		FileUtils.save(file.getBytes(), uploadpath, newFileName);
		//输出文件名
        File output = new File( pdftemp+uuid + ".pdf");
		OfficeConvert.convert( new File(uploadpath+newFileName), output);
		model.addAttribute("docname",output.getName());
		return "upload";
	}
	@GetMapping(value = "/view")
	public String view() {
		return "viewer";
	}
	
	@GetMapping(value = "view/{fileName}")
	public void index(@PathVariable("fileName")String fileName,
	                  HttpServletResponse response) throws Exception{
			FileUtils.download(pdftemp,fileName,response);
	}
}
