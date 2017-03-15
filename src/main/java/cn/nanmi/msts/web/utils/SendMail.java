package cn.nanmi.msts.web.utils;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

	private String host = "";  //smtp服务器
	private String from = "";  //发件人地址
	private String to = "";    //收件人地址
	private String affix = ""; //附件地址
	private String affixName = ""; //附件名称
	private String user = "";  //用户名
	private String pwd = "";   //密码
	private String subject = ""; //邮件标题
	
	public void setAddress(String from,String to,String subject){
	    this.from = from;
	    this.to   = to;
	    this.subject = subject;
	}
	
	public void setAffix(String affix,String affixName){
		this.affix = affix;
		this.affixName = affixName;
	}
	
	public void send(String host,String user,String pwd,String txt){
		
		this.host = host;
		this.user = user;
		this.pwd  = pwd;
		
		Properties props = new Properties();

        props.put("mail.smtp.host", host);
        //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");
        //用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);
		
        session.setDebug(true);
        
        //用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        
        try {
			message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            
            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            
            //设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            
            contentPart.setContent(txt,"text/html;charset=GBK");
//            contentPart.setText(txt);
            multipart.addBodyPart(contentPart);
            
            //添加附件
//            BodyPart messageBodyPart= new MimeBodyPart();
//            DataSource source = new FileDataSource(affix);
//            //添加附件的内容
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            //添加附件的标题
//            //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
//            BASE64Encoder enc = new BASE64Encoder();
//            messageBodyPart.setFileName(MimeUtility.encodeWord(affixName));
//            multipart.addBodyPart(messageBodyPart);
//            
//            
//            //将multipart对象放到message中
            message.setContent(multipart);
            
            //保存邮件
            message.saveChanges();
            
            //发送邮件
            Transport transport = session.getTransport("smtp");
            //连接服务器的邮箱
            transport.connect(host, user, pwd);
            //把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getAffix() {
		return affix;
	}

	public void setAffix(String affix) {
		this.affix = affix;
	}

	public String getAffixName() {
		return affixName;
	}

	public void setAffixName(String affixName) {
		this.affixName = affixName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
