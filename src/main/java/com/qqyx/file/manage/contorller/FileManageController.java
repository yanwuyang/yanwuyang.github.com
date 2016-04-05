package com.qqyx.file.manage.contorller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qqyx.file.manage.biz.AttachmentService;
import com.qqyx.file.manage.biz.FileManageService;
import com.qqyx.file.manage.biz.SysDictService;
import com.qqyx.file.manage.biz.TranslationFileService;
import com.qqyx.file.manage.common.BaseController;
import com.qqyx.file.manage.constant.BizConstant;
import com.qqyx.file.manage.framedata.FrameData;
import com.qqyx.file.manage.model.mysql.Attachment;
import com.qqyx.file.manage.model.mysql.TranslationFile;
import com.qqyx.file.manage.model.mysql.User;
import com.qqyx.file.manage.utils.IOUtils;
import com.qqyx.file.manage.utils.JSONTools;
import com.qqyx.file.manage.utils.Page;
import com.qqyx.file.manage.utils.PageContext;
import com.qqyx.file.manage.utils.UserUtils;

@Controller
@Scope("prototype")
@RequestMapping("/filemanage")
@SuppressWarnings("all")
public class FileManageController extends BaseController {
	
	@Autowired
	private  FileManageService fileManageService;
	
	
	@Autowired
	private  TranslationFileService fileService;
	
	@Autowired
	private  AttachmentService attachmentService;
	
	@Autowired
	private SysDictService sysDictService;
	
	@RequestMapping("index")
	public String index(ModelMap modelMap) {
		return this.toView("function/customer/tab");
	}
	
	@RequestMapping("toupload")
	public String toupload(ModelMap modelMap) {
		modelMap.put("sessionId", request.getSession().getId());
		return this.toView("function/customer/upload");
	}
	
	@RequestMapping("list")
	public String list(ModelMap modelMap,String status) {
		modelMap.put("status", status);
		return this.toView("function/customer/list");
	}
	
	@RequestMapping("setting_list")
	public String settingList(ModelMap modelMap) {
		return this.toView("function/customer/setting_list");
	}
	
	
	
	@RequestMapping("upload")
	@ResponseBody
	public boolean upload(@RequestParam(value="Filedata")MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException{
		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		long fileSize = file.getSize();
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String saveFileName = df.format(date) + RandomStringUtils.randomNumeric(6)+fileType;
		String attachPath = FrameData.getInstance().getConfigItem("base-config", "config", "attach-path");
		File dest = new File(attachPath);
		if(!dest.isDirectory()){
			dest.mkdir();
		}
		file.transferTo(new File(attachPath+saveFileName));
		Attachment model = new Attachment();
		model.setCreateDate(new Date());
		model.setFileName(fileName);
		model.setSaveName(saveFileName);
		model.setFileType(fileType);
		model.setSize(fileSize);
		
		TranslationFile translationFile = new TranslationFile();
		User uesr = UserUtils.getUser(request);
		translationFile.setAttachId(model.getId());
		translationFile.setUserId(uesr.getId());
		translationFile.setCreateDate(new Date());
		translationFile.setUpdateDate(new Date());
		translationFile.setStatus(BizConstant.FILE_STATUS_UPLOAD);
		fileManageService.save(model, translationFile);
		return true;
	}
	
	/**
	 * 上传翻译后的文件
	 * @param id
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("uploadTranslationFile")
	@ResponseBody
	public Object uploadTranslationFile(String id,@RequestParam(value="Filedata")MultipartFile file) throws IllegalStateException, IOException{
		TranslationFile translationFile =fileService.get(id);
		
		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		long fileSize = file.getSize();
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String saveFileName = df.format(date) + RandomStringUtils.randomNumeric(6)+fileType;
		String attachPath = FrameData.getInstance().getConfigItem("base-config", "config", "attach-path");
		File dest = new File(attachPath);
		if(!dest.isDirectory()){
			dest.mkdir();
		}
		file.transferTo(new File(attachPath+saveFileName));
		String targetAttachId = translationFile.getTargetAttachId();
		if(targetAttachId!=null){
			//删除以前上传的
			Attachment attachModel = attachmentService.get(targetAttachId);
			String oldFilePath = attachModel.getSaveName();
			File oldfile = new File(attachPath+oldFilePath);
			oldfile.delete();
		}
		Attachment model = new Attachment();
		model.setCreateDate(new Date());
		model.setFileName(fileName);
		model.setSaveName(saveFileName);
		model.setFileType(fileType);
		model.setSize(fileSize);
		
		attachmentService.saveOrUpdate(model);
		translationFile.setTargetAttachId(model.getId());
		translationFile.setUpdateDate(new Date());
		fileService.saveOrUpdate(translationFile);
		return true;
	}
	
	/**
	 * 下载文件
	 * @param id
	 * @param type
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("downFile")
	public void downFile(String id,String type,HttpServletResponse response) throws Exception{
		TranslationFile translationFile =fileService.get(id);
		String attachId = translationFile.getAttachId();
		if("target".equals(type)){
			attachId = translationFile.getTargetAttachId();
		}
		Attachment attach = attachmentService.get(attachId);
		String savePath = attach.getSaveName();
		String attachPath = FrameData.getInstance().getConfigItem("base-config", "config", "attach-path");
		IOUtils.download(response, attachPath+savePath, attach.getFileName());
	}
	
	/**
	 * 获取用户上传的文件和分配给译员的的文件
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping("getAllFile")
	@ResponseBody
	public Object getAllFile(HttpServletRequest request,String status,String type){
		User user = UserUtils.getUser(request);
		HashMap params = new HashMap();
		params.put("status", status);
		if("customer".equals(type)){
			params.put("userId", user.getId());
		}else if("interpreter".equals(type)){
			params.put("translationId", user.getId());
		}
		return fileService.getFiles(params);
	}
	
	@RequestMapping("setting")
	@ResponseBody
	public boolean setting(String data,String actionType){
		JSONArray jsonArray = JSONArray.parseArray(data);
		for(int i =0;i<jsonArray.size();i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i); 
			TranslationFile t=fileService.get(jsonObj.getString("id"));
			t.setSourceLanguage(jsonObj.getString("sourceLanguage"));
			t.setTargetLanguage(jsonObj.getString("targetLanguage"));
			t.setEmergencyDegree(jsonObj.getInteger("emergencyDegree"));
			t.setDescs(jsonObj.getString("desc"));
			t.setUpdateDate(new Date());
			t.setStatus(actionType);
			fileService.saveOrUpdate(t);
		}
		return true;
	}
	
	/**
	 * 更新进度
	 * @param id
	 * @return
	 */
	@RequestMapping("uploadProgress")
	@ResponseBody
	public String uploadProgress(String data){
		JSONArray jsonArray = JSONArray.parseArray(data);
		for(int i =0;i<jsonArray.size();i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i); 
			TranslationFile t=fileService.get(jsonObj.getString("id"));
			Attachment attach = attachmentService.get(t.getAttachId());
			t.setProgress(jsonObj.getInteger("progress"));
			if(t.getProgress()==100){
				if(t.getTargetAttachId()==null){
					return "请上传："+attach.getFileName()+"的翻译文件";
				}
				t.setStatus(BizConstant.FILE_STATUS_COMPLETE);
				
			}
			t.setUpdateDate(new Date());
			fileService.saveOrUpdate(t);
		}
		return "ok";
	}
	
	@RequestMapping("delete")
	public String delete(String id){
		TranslationFile translationFile= fileService.get(id);
		Attachment attach = attachmentService.get(translationFile.getAttachId());
		String attachPath = FrameData.getInstance().getConfigItem("base-config", "config", "attach-path");
		String path =attachPath+attach.getSaveName();
		attachmentService.delete(attach);
		fileService.delete(translationFile);
		File file = new File(path);
		file.delete();
		return "redirect:/filemanage/setting_list";
	}
	/**
	 * 待分配
	 * @return
	 */
	@RequestMapping("distribution_list")
	public String distributionList(int flag){
		if(flag==0){
			return "function/interpreter/distribution_list";
		}
		return "function/manager/filemanage/distribution_list";
	}
	
	
	/**
	 * 已分配
	 * @return
	 */
	@RequestMapping("allocated_list")
	public String allocatedList(){
		return "function/manager/filemanage/allocated_list";
	}
	
	/**
	 * 已完成
	 * @return
	 */
	@RequestMapping("completed_list")
	public String completedList(int flag){
		if(flag ==0 ){
			return "function/interpreter/completed_list";
		}
		return "function/manager/filemanage/completed_list";
	}
	
	/**
	 * 后台管理
	 * @return
	 */
	@RequestMapping("fileList")
	@ResponseBody
	public Object fileList() {
		PageContext.setPagesize(JSONTools.getInt(getJsonObject(), "rows"));
		PageContext.setOffset(JSONTools.getInt(getJsonObject(), "page"));
		Page page = fileService.querys(getJsonObject(), null);
		HashMap resultMap = new HashMap();
		resultMap.put("total", page.getTotal());
		resultMap.put("rows",page.getItems());
		return resultMap;
	}

	/**
	 * 分配译员
	 * @return
	 */
	@RequestMapping("distribute")
	@ResponseBody
	public boolean distribute(String id,String interpreter){
		User uesr = UserUtils.getUser(request);
		TranslationFile tf = fileService.get(id);
		tf.setTranslationId(interpreter);
		tf.setOperatorId(uesr.getId());
		tf.setUpdateDate(new Date());
		tf.setStatus(BizConstant.FILE_STATUS_DISTRIBUTE);
		fileService.saveOrUpdate(tf);
		return  true;
	}
	
	/**
	 * 根据类型返回字典
	 * @param type
	 * @return
	 */
	@RequestMapping("getDictByType")
	@ResponseBody
	public List getDictByType(String type){
		return sysDictService.getDictsByType(type);
	}
}
