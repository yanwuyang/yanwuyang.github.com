package com.qqyx.file.manage.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
public class RandomNC {
	    /** 
	     * 英语字母 
	     */  
	    public static final char fontBase_english[] = {'A' ,'B' ,'C' ,'D' ,'E' ,'F' ,'G' ,'H' ,'I' ,'J' ,'K' ,'L' ,'M' ,'N' ,'O' ,'P' ,'Q' ,'R' ,'S' ,'T' ,'U' ,'V' ,'W' ,'X' ,'Y' ,'Z','a' ,'b' ,'c' ,'d' ,'e' ,'f' ,'g' ,'h' ,'i' ,'j' ,'k' ,'l' ,'m' ,'n' ,'o' ,'p' ,'q' ,'r' ,'s' ,'t' ,'u' ,'v' ,'w' ,'x' ,'y' ,'z'};  
	    /** 
	     * 不足account位的数前使用0进行填充 
	     */  
	    public static final boolean ZERO = true;  
	    public static final boolean UNZERO = false;  
	      
	    /** 
	     * 生成英文字符时,对大小写是否敏感 
	     */  
	    public static final boolean CASE_SENSITIVE = true;  
	    public static final boolean UNCASE_SENSITIVE = false;  
	      
	    /** 
	     * 区分大小写过滤 
	     */  
	    public static final boolean UNCASE = false;  
	    public static final boolean CASE = true;  
	      
	    /** 
	     * <pre> 
	     * 得到n位编号 
	     * </pre> 
	     *  
	     * @param account 
	     *            需要要产生的数字位数 
	     * @param fill 
	     *            是否使用0查询:RandomNC.zero|RandomNC.unzero 
	     * @return 
	     *  
	     */  
	    public static String generaterNumber(int account, boolean fill) {  
	        Random r = new Random();  
	        String result = null;  
	        double max = Math.pow(10, account);  
	        while (true) {  
	            int temp = r.nextInt((int) max);  
	            result = String.valueOf(temp);  
	            if (fill) {  
	                result = String.format("%0" + account + "d", temp);// 不足account位的数前使用0进行填充  
	            }  
	            return result;  
	        }  
	    }  
	  
	    /** 
	     * <pre> 
	     * 得到n位编号,默认使用不足account位的数前使用0进行填充 
	     * </pre> 
	     *  
	     * @param account 
	     *            n位数字编号 
	     * @return 得到n位编号,不足n位者使用0进行填充 
	     *  
	     */  
	    public static String generaterNumber(int account) {  
	        return generaterNumber(account, ZERO);  
	    }  
	  
	    /** 
	     * <pre> 
	     * 生成account个简体汉字(当然也有你不认识的) 
	     * </pre> 
	     *  
	     * @param account 所需要的字符个数 
	     * @return 产生的字符个数 
	     *  
	     */  
	    public static String generaterFontChinese(int account) {  
	        StringBuffer sb = new StringBuffer();  
	        Random r = new Random();  
	        while (account > 0) {  
	            sb.append((char) (19968 + r.nextInt(20901)));  
	            account--;  
	        }  
	        return sb.toString();  
	    }  
	      
	    /** 
	     * <pre> 
	     * 生成英语字符 
	     * </pre> 
	     * @param account 数量 
	     * @param sensitive 对大小是否敏感 
	     * @return 所满足的生成英语字符 
	     * 
	     */  
	    public static String generaterFontEnglish(int account, boolean sensitive, boolean case_char) {  
	        StringBuffer sb = new StringBuffer();  
	        Random r = new Random();  
	        while (account > 0) {  
	            if(case_char){  
	                sb.append(fontBase_english[(r.nextInt(52))]);  
	            }else{  
	                sb.append(fontBase_english[(r.nextInt(25))]);  
	            }  
	            account--;  
	        }  
	        return sb.toString();  
	    }  
	      
	    /** 
	     * <pre> 
	     * 当我需要使用数字+字母+-的时候, 我们可以采用UUID到得序列号 
	     * </pre> 
	     * @param args 
	     * 
	     */  
	    public static String generaterChar(){  
	        String res = UUID.randomUUID().toString();  
	        StringBuffer sb = new StringBuffer(res);  
	        sb.insert(4, '-');  
	        sb.insert(29, '-');  
	        sb.insert(34, '-');  
	        return sb.toString();  
	    }  
	    
	    
	    /**
	     * @param key
	     * @return
	     */
	    public static String createID (String key) {
	    	key = String.valueOf(System.currentTimeMillis()) + key ; 
	    	StringBuffer buf = null ;
	    	try {
	    	              MessageDigest md = MessageDigest.getInstance("MD5");
	    	              md.update(key.getBytes());
	    	              byte b[] = md.digest();
	    	              int i;
	    	              buf = new StringBuffer("");
	    	              for (int offset = 0; offset < b.length; offset++) {
	    	                      i = b[offset];
	    	                      if (i < 0)
	    	                      i += 256;
	    	                      if (i < 16)
	    	                      buf.append("0");
	    	                      buf.append(Integer.toHexString(i));
	    	              }
	    	                } catch (NoSuchAlgorithmException e) {
	    	               e.printStackTrace();
	    	      }
	    	  return  buf.toString().substring(8, 24); // 16位的加密
//	    	  return buf.toString());// //         32位的加密
	    	} 
	    
	    
	    /**
	     * 生成16位唯一编码
	     * @return
	     */
	    public static String uniqCode(){
	    	return createID(UUID.randomUUID().toString());
	    }
	    /** 
	     * 获得一个UUID 
	     * @return String UUID 
	     */ 
	    public static String getUUID(){ 
	        String s = UUID.randomUUID().toString(); 
	        //去掉“-”符号 
	        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	    } 
	    
	    
	    
	    public static void main(String [] args){
	    	System.out.println(getUUID());
//	    	String  MD5 = createID(UUID.randomUUID().toString());
	    	String  MD5 = createID("f8ab1cdd-84e5-488d-908f-3193ec830867");
	    	String md6 =createID("f8ab1cdd-84e5-488d-908f-3193ec830867");
	    	System.err.println(MD5+"\n"+md6+"\n"+uniqCode());
	    	 
	    	System.out.println(generaterNumber(6));
	    }
	}  
