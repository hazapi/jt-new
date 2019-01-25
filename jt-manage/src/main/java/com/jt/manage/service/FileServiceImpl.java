package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Service	 //单例!!!!!!!!!!   多例????
public class FileServiceImpl implements FileService {
	
	//大忌  成员变量进行引用,但是不要在程序中修改. #获取集合信息中的元素 util =id   $从spring容器中直接获取key
	@Value("${image.fileDir}")
	private String fileDir;  //文件存储的根目录
	@Value("${image.urlPath}")
	private String urlPath; //定义图片的虚拟路径
	
	
	/**
	 * 1.判断是否为图片类型 jpg|png|git
	 * 2.判断是否为恶意程序.
	 * 3.分文件存储  分类存储   /yyyy/MM/dd/HH
	 * 4.不能让图片重名  UUID() + 随机数
	 * 5.实现文件上传
	 */
	@Override
	public PicUploadResult fileUpload(MultipartFile uploadFile) {
		PicUploadResult result = new PicUploadResult();
		//1.获取图片的名称   abc.jpg
		String fileName 
				= uploadFile.getOriginalFilename();
		//将图片信息转化为小写字符
		fileName = fileName.toLowerCase();
		//2.获取数据  .jpg
		String fileType = 
				fileName.substring
				(fileName.lastIndexOf("."));
		//3.判断是否为图片类型  正则表达式   abc.jpg
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")){
			
			result.setError(1);//图片类型不匹配
			return result;
		}
		
		//二:判断程序是否为恶意程序
		try {
			BufferedImage imageBuffer = 
		ImageIO.read(uploadFile.getInputStream());
			int width = imageBuffer.getWidth();
			int height = imageBuffer.getHeight();
			if(width == 0 || height == 0){
				result.setError(1);
				return result;
			}
			
			/**
			 * 表示图片正常 
			 * 三.进行分文件存储
			 */
			String dateDir = 
			new SimpleDateFormat("yyyy/MM/dd")
			.format(new Date());
			
			//定义文件存储的目录   E:\jt-upload\2018\12\29
			String localPathDir = 
					fileDir + dateDir;
			File dataDirFile = new File(localPathDir);
			if(!dataDirFile.exists()){
				dataDirFile.mkdirs();
			}
			
			//四:生成唯一的文件名称
			String uuid = UUID.randomUUID()
					.toString()
					.replace("-","");
			int randomNum = new Random().nextInt(1000);
			//获取文件名称: xxxxx110.jpg
			String imageFileName = 
				uuid + randomNum + fileType;
					
			//五.实现文件上传  E:\jt-upload\2018\12\29\xxxxx110.jpg
			String realFilePath = 
					localPathDir + "/" + imageFileName;
			File imageFile = new File(realFilePath);
			uploadFile.transferTo(imageFile);
			
			//6.实现图片回显
			
			String realUrlPath = urlPath + dateDir + "/" + 
					imageFileName;		//???行   不行?
			result.setUrl(realUrlPath);
			result.setHeight(height+"");
			result.setWidth(width+"");
			//result.setUrl("https://img14.360buyimg.com/n0/jfs/t1/6533/1/368/115452/5bc87e70Ed0814bb8/5085d26ed83537e3.jpg");
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1); //文件上传失败;
			return result;
		}
		
		return result;
	}
}
