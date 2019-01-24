package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 当文件上传完成后,重定向到文件上传页面.
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/fileDemo")
	public String fileDeme(MultipartFile image) throws IllegalStateException, IOException{
		String dir = "E:/jt-upload"; //定义文件夹路径
		//获取文件名称
		String fileName = image.getOriginalFilename();
		
		//E:/jt-upload/abc.jpg
		//判断文件夹是否存在
		File file = new File(dir);
		if(!file.exists()){
			//新建文件夹
			file.mkdirs();
		}
		//上传图片
		image.transferTo(new File(dir+"/"+fileName));
		return "redirect:/file.jsp";
	}
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult fileUpload
				(MultipartFile uploadFile){
		
		return fileService.fileUpload(uploadFile);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
