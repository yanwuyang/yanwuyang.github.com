package com.qqyx.file.manage.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class IOUtils {

	private static Logger log = LogManager.getLogger(IOUtils.class);
	
	public static boolean download(HttpServletResponse response,
			String strSource, String strDest) throws Exception {
		InputStream in = null;
		boolean bRet = false;
		String disHeader = "Attachment;Filename=";
		String filetype = "";

		if (strDest != null && strDest.indexOf(".") != -1) {
			filetype = strDest.substring(strDest.lastIndexOf("."));
			filetype = filetype.toLowerCase();
		}
		response.reset();
		if (filetype.indexOf(".docx") == 0) {
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		} else if (filetype.indexOf(".xlsx") == 0) {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		} else if (filetype.indexOf(".pptx") == 0) {
			response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
		} else if (filetype.indexOf(".doc") == 0) {
			response.setContentType("application/msword");
		} else if (filetype.indexOf(".xls") == 0) {
			response.setContentType("application/msexcel");
		} else if (filetype.indexOf(".ppt") == 0) {
			response.setContentType("application/mspowerpoint");
		} else if (filetype.indexOf(".tif") == 0) {
			response.setContentType("image/tiff");
		} else if (filetype.indexOf(".gif") == 0) {
			response.setContentType("image/gif");
		} else if (filetype.indexOf(".jpg") == 0) {
			response.setContentType("image/jpeg");
		} else if (filetype.indexOf(".png") == 0) {
			response.setContentType("image/png");
		} else if (filetype.indexOf(".bmp") == 0) {
			response.setContentType("image/bmp");
		} else if (filetype.indexOf(".rar") == 0) {
			response.setContentType("application/rar");
		} else if (filetype.indexOf(".zip") == 0) {
			response.setContentType("application/zip");
		} else if (filetype.indexOf(".pdf") == 0) {
			response.setContentType("application/pdf");
		} else {
			response.setContentType("application/x-download");
			disHeader = "Attachment;Filename=";
		}
		disHeader += strDest;
		response.setHeader("Content-Disposition", new String(disHeader.getBytes("GB2312"), "ISO8859_1"));
		response.setDateHeader("Expires", 0);

		File f = new File(strSource);
		if (!f.exists()) {
			log.warn("请求下载的文件：" + strSource + "不存在！");
			return bRet;
		}
		// 将文件解密到上级目录

		in = new FileInputStream(f);
		byte[] buffer = new byte[4 * 1024];
		int n;
		while ((n = in.read(buffer, 0, buffer.length)) != -1) {
			response.getOutputStream().write(buffer, 0, n);
		}
		response.getOutputStream().flush();
		in.close();
		response.getOutputStream().close();
		bRet = true;
		buffer = null;
		f = null;
		return bRet;
	}
}
