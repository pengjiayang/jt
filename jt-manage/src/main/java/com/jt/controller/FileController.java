package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

@Controller
public class FileController {

	
	//当用户上传完成时重定向到上传页面上
	
	/**
	 * 1,获取用户文件信息,包含文件名称
	 * 2,制定文件上传路径 
	 * 3,实现文件的上传
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="/file",method=RequestMethod.POST)
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		//1,获取input标签中的name属性
		String inputName = fileImage.getName();
		System.out.println("1"+inputName);
		//2.获取文件名称
		String fileName = fileImage.getOriginalFilename();
		//3,定义文件路径
		File fileDir = new File("D:/1-jt/image");
		if(!fileDir.exists()) {
			fileDir.mkdir();
		}
		//实现文件上传
		fileImage.transferTo(new File("D:/1-jt/image/"+fileName));
		return "redirect:/file.jsp";			
	}
	
	@Autowired 
	private FileService fileService;
	//实现文件上传
	@RequestMapping(value="/pic/upload",method=RequestMethod.POST)
	@ResponseBody
	public ImageVO fileUpdate(MultipartFile uploadFile) {
		return fileService.updateFile(uploadFile);
	}
}


















