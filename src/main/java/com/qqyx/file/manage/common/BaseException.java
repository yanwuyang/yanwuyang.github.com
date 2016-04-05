package com.qqyx.file.manage.common;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ����runtime�쳣�Ļ���
 * @author liujl
 */

@SuppressWarnings("all")
public class BaseException extends SysException {
	/**
	 * ���쳣��ָʾ���쳣ʵ�����ĸ�ԭ��.
	 */
//	protected Throwable rootCause = null;
	private Collection exceptions = new ArrayList();
	private String messageKey = null;
	private Object[] messageArgs = null;

	/**
	 * ���캯��.
	 */
	public BaseException() {
		super();
	}

	/**
	 * ���캯��.
	 * 
	 * @param msg
	 *            �쳣��Ϣ.
	 */
	public BaseException(String msg) {
		super(msg);

	}

	/**
	 * ���캯��.
	 * 
	 * @param cause
	 *            �����쳣���쳣.
	 */
	public BaseException(Throwable cause) {
		this.rootCause = cause;
	}

	/**
	 * ���캯��.
	 * 
	 * @param msg
	 *            �쳣��Ϣ.
	 * @param cause
	 *            �����쳣���쳣.
	 */
	public BaseException(String msg, Throwable cause) {
		super(msg);
		this.rootCause = cause;
	}

	/**
	 * ��ñ��쳣ʵ�����Ķ��쳣ʵ��.
	 * 
	 * @return ���쳣ʵ�����Ķ��쳣ʵ��.
	 */
	public Collection getCollection() {
		return exceptions;
	}

	/**
	 * ����һ���쳣ʵ������쳣ʵ���װ�ڱ��쳣ʵ����.
	 * 
	 * @param ex
	 *            �µ��쳣ʵ��.
	 */
	public void addException(BaseException ex) {
		exceptions.add(ex);
	}

	/**
	 * �����쳣��Ϣ��keyCode����ҪΪ֧�ֹ�ʻ�.
	 * 
	 * @param key
	 *            �쳣��Ϣ��keyCode.
	 */
	public void setMessageKey(String key) {
		this.messageKey = key;
	}

	/**
	 * ����쳣��keyCode����ҪΪ��֧�ֹ�ʻ�.
	 * 
	 * @return �쳣��Ϣ��keyCode.
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * �����쳣��Ϣ�Ĳ�����ҪΪ��֧�ֹ�ʻ�. �쳣��Ϣʹ�����¸�ʽ�����������Ϣ: {0} is larger than {1}.
	 * 
	 * @param args
	 *            �쳣��Ϣ�Ĳ�������.
	 */
	public void setMessageArgs(Object[] args) {
		this.messageArgs = args;
	}

	/**
	 * ����쳣��Ϣ�Ĳ�����ҪΪ��֧�ֹ�ʻ�.
	 * 
	 * @return �쳣��Ϣ�Ĳ�������.
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}

	/**
	 * ���ø��쳣.
	 * 
	 * @param anException
	 *            ���쳣.
	 */
	public void setRootCause(Throwable anException) {
		rootCause = anException;
	}

	/**
	 * ��ø��쳣.
	 * 
	 * @return ���쳣.
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * ���쳣��ջ��Ϣ��ӡ��System.err���ȴ�ӡ���쳣��ջ��Ϣ��Ȼ���ӡ���쳣��ջ��Ϣ.
	 */
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/**
	 * ���쳣��ջ��Ϣ��ӡ����������ȴ�ӡ���쳣��ջ��Ϣ��Ȼ���ӡ���쳣��ջ��Ϣ.
	 * 
	 * @param outStream
	 *            �����.
	 */
	public void printStackTrace(PrintStream outStream) {
		printStackTrace(new PrintWriter(outStream));
	}

	/**
	 * ���쳣��ջ��Ϣ��ӡ����������ȴ�ӡ���쳣��ջ��Ϣ��Ȼ���ӡ���쳣��ջ��Ϣ.
	 * 
	 * @param writer
	 *            �����
	 */
	public void printStackTrace(PrintWriter writer) {

		super.printStackTrace(writer);

		if (getRootCause() != null) {

			getRootCause().printStackTrace(writer);

		}

		writer.flush();
	}
}
