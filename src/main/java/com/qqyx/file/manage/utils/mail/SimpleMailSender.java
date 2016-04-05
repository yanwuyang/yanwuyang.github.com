package com.qqyx.file.manage.utils.mail;   
  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;



  
/**  
* 简单邮件（不带附件的邮件）发送器   
*/    
public class SimpleMailSender  {    

	/**
	 * 
	 * 方法描述:以文本格式发送邮件,待发送的邮件的信息
	 * @param toAddress 发送邮件地址
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @return
	 */
    public static boolean sendTextMail(String toAddress,String subject,String content){
        //发送信息	
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setToAddress(toAddress);//收件人地址
        mailInfo.setSubject(subject);//邮件主题
        mailInfo.setContent(content);//邮件内容    
      // 判断是否需要身份认证    
      MyAuthenticator authenticator = null;    
      Properties pro = mailInfo.getProperties();   
      if (mailInfo.isValidate()) {    
      // 如果需要身份认证，则创建一个密码验证器    
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());    
      }   
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
      try {    
      // 根据session创建一个邮件消息    
      Message mailMessage = new MimeMessage(sendMailSession);    
      // 创建邮件发送者地址    
      Address from = new InternetAddress(mailInfo.getFromAddress());    
      // 设置邮件消息的发送者    
      mailMessage.setFrom(from);    
      // 创建邮件的接收者地址，并设置到邮件消息中    
      Address to = new InternetAddress(mailInfo.getToAddress());    
      mailMessage.setRecipient(Message.RecipientType.TO,to);    
      // 设置邮件消息的主题    
      mailMessage.setSubject(mailInfo.getSubject());    
      // 设置邮件消息发送的时间    
      mailMessage.setSentDate(new Date());    
      // 设置邮件消息的主要内容    
      String mailContent = mailInfo.getContent();    
      mailMessage.setText(mailContent);    
      // 发送邮件    
      Transport.send(mailMessage);   
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      }    
      return false;    
    }    
       
    /**
	 * 
	 * 方法描述:以HTML格式发送邮件,待发送的邮件的信息
	 * @param toAddress 发送邮件地址
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @return
	 */  
    public static boolean sendHtmlMail(String toAddress,String subject,String content){
      //发送信息	
      MailSenderInfo mailInfo = new MailSenderInfo();
      mailInfo.setToAddress(toAddress);//收件人地址
      mailInfo.setSubject(subject);//邮件主题
      mailInfo.setContent(content);//邮件内容
      
      // 判断是否需要身份认证    
      MyAuthenticator authenticator = null;   
      Properties pro = mailInfo.getProperties();   
      //如果需要身份认证，则创建一个密码验证器     
      if (mailInfo.isValidate()) {    
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
      }    
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
      try {    
      // 根据session创建一个邮件消息    
      Message mailMessage = new MimeMessage(sendMailSession);
      //设置自定义发件人昵称  
      String nick ="";
      try {
		nick= javax.mail.internet.MimeUtility.encodeText("用友优普开放平台");
      } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
      }  
      // 创建邮件发送者地址    
      Address from = new InternetAddress(nick+"<"+mailInfo.getFromAddress()+">");    
      // 设置邮件消息的发送者    
      mailMessage.setFrom(from);    
      // 创建邮件的接收者地址，并设置到邮件消息中    
      Address to = new InternetAddress(mailInfo.getToAddress());    
      // Message.RecipientType.TO属性表示接收者的类型为TO    
      mailMessage.setRecipient(Message.RecipientType.TO,to);    
      // 设置邮件消息的主题    
      mailMessage.setSubject(mailInfo.getSubject());    
      // 设置邮件消息发送的时间    
      mailMessage.setSentDate(new Date()); 
      
      
      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
      Multipart mainPart = new MimeMultipart();    
      // 创建一个包含HTML内容的MimeBodyPart    
      BodyPart html = new MimeBodyPart();    
      // 设置HTML内容    
      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");   
      
      mainPart.addBodyPart(html);    
      // 将MiniMultipart对象设置为邮件内容    
      mailMessage.setContent(mainPart);    
      mailMessage.saveChanges();
      
      
      // 发送邮件    
      Transport.send(mailMessage);    
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      }    
      return false;    
    }   
    
    
    /**
	 * 
	 * 方法描述:以HTML格式发送邮件,待发送的邮件的信息
	 * @param toAddress 发送邮件地址
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @return
	 */  
	public static boolean sendHtmlMailWithImg(String toAddress, String subject, String content,List<File> attachList) {
		
		// 发送信息
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(toAddress);// 收件人地址
		mailInfo.setSubject(subject);// 邮件主题
		mailInfo.setContent(content);// 邮件内容

		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			MimeMessage message = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			message.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO
			message.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			message.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			message.setSentDate(new Date());
			
			
			// 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
			MimeMultipart mm = new MimeMultipart(); 
			// 新建一个存放信件内容的BodyPart对象
			BodyPart mdp = new MimeBodyPart(); 
			// 给BodyPart对象设置内容和格式/编码方式
			mdp.setContent(content.toString(), "text/html;charset=GBK");
			// 这句很重要，千万不要忘了
			mm.setSubType("related");

			mm.addBodyPart(mdp); 

			// add the attachments
			for( int i=0; i<attachList.size(); i++) {
				// 新建一个存放附件的BodyPart
				mdp = new MimeBodyPart();
//				File file = new File("E:/work/futureRestaurant/workSpace/wlct_web/WebRoot/template/wlct/门票印刷文件-电子券.jpg");
				File file = attachList.get(i);
				InputStream in  = new FileInputStream(file);
				DataHandler dh = new DataHandler(new ByteArrayDataSource(in, "application/octet-stream"));
				mdp.setDataHandler(dh);
				// 加上这句将作为附件发送,否则将作为信件的文本内容
				mdp.setFileName(i + ".jpg");
				mdp.setHeader("Content-ID", "IMG" + i);
				// 将含有附件的BodyPart加入到MimeMultipart对象中
				mm.addBodyPart(mdp);
			}
			// 把mm作为消息对象的内容
				
			message.setContent(mm);
			
			message.saveChanges();
			javax.mail.Transport transport = sendMailSession.getTransport("smtp");
			transport.connect(mailInfo.getMailServerHost(), mailInfo.getUserName(), mailInfo.getPassword());
			transport.sendMessage(message, message.getAllRecipients());
//			Transport.send(message);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	} 
    
    
    public static void main(String[] args) {
    	
		String toMail = "381447823@qq.com";//收件人邮件地址
		String content = "<html>" +
				"<head></head>" +
				"<body>" +
				"<div align=center>" +
				"	<a href=http://localhost:8000/controller/vcs/login/toLogin target=_blank>" +
				//"		<img src=cid:IMG0 width=500 height=400 border=0>" +
					"您好，您申请的商户已经开通，请点击地址：http://localhost:8000/controller/vcs/login/toLogin  登陆 "+
				"	</a>" +
				"</div>" +
				"</body>" +
				"</html>";//邮件内容
		String subject = "商户开通通知";//邮件主题
		/*List<File> attachList = new ArrayList<File>();
		
		
		File file = new File("E:/work/futureRestaurant/workSpace/wlct_web/WebRoot/template/wlct/门票印刷文件-电子券.jpg");
		attachList.add(file);
		
		
		sendHtmlMailWithImg(toMail,subject,content,attachList);*/
		sendHtmlMail(toMail, subject, content);
		
		
		
	}
}   
