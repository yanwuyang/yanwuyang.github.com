package com.qqyx.file.manage.utils;
import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * Copyright(c) 2015-2015 by yonyouup. 
 * All Rights Reserved
 * 正则表达式工具类
 * 提供一些常用正则的验证
 * @author wang zhuo
 * @version <类版本> , 2015年5月15日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class RegularExpressionUtil {
    
    private static Logger log = Logger.getLogger(RegularExpressionUtil.class);
    
    private RegularExpressionUtil() {
    }
 
    
    /**
     * 匹配email地址
     *
     *
     * 格式: XXX@XXX.XXX.XX
     */
    public static final String EMAIL_REGEXP = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
 
    
 
    /**
     * 大小写敏感的正规表达式批配
     *
     * @param source
     *            批配的源字符串
     * @param regexp
     *            批配的正规表达式
     * @return 如果源字符串符合要求返回真,否则返回假
     */
    public static boolean isHardRegexpValidate(String source, String regexp) {
        
        if (source == null){
            return false;
        }
        
        try {
            // 用于定义正规表达式对象模板类型
            PatternCompiler compiler = new Perl5Compiler();
 
            // 正规表达式比较批配对象
            PatternMatcher matcher = new Perl5Matcher();
 
            // 实例大小大小写敏感的正规表达式模板
            Pattern hardPattern = compiler.compile(regexp);
 
            // 返回批配结果
            return matcher.contains(source, hardPattern);
 
        } catch (MalformedPatternException e) {
            log.warn(e);
        }
 
        return false;
    }
}
