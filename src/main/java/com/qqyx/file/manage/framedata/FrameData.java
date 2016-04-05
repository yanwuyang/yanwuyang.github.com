package com.qqyx.file.manage.framedata;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.asm.SpringAsmInfo;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FrameData {
	Log log = LogFactory.getLog(FrameData.class);
	private static FrameData instance = null;
	private LinkedHashMap arrayMappedItem = null;
	private Resource[] configs;

	public Resource[] getConfigs() {
		return configs;
	}

	public void setConfigs(Resource[] configs) {
		this.configs = configs;
	}

	public void setLocation(Resource config) {
		this.configs = new Resource[] { config };
	}

	public void setLocations(Resource... configs) {
		this.configs = configs;
	}

	private FrameData() {
		if (arrayMappedItem == null) {
			arrayMappedItem = new LinkedHashMap();
			instance = this;
		}
	}

	public void init() {
		destroy();
		for (Resource resource : this.configs) {
			String filename = resource.getFilename();
			InputStream stream;
			try {
				stream = resource.getInputStream();
				loadConfig(stream, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadConfig(InputStream stream, String filename) {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document doc = null;

		int i = 0;
		String s = null;
		NodeList apps = null;
		String[][] strCategory = null;
		String[][] strItem = null;
		filename = filename.substring(0, filename.lastIndexOf(".xml"));
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(stream);
			// 取FORM设置
			apps = doc.getElementsByTagName("category");
			strCategory = new String[apps.getLength()][2];
			for (i = 0; i < apps.getLength(); i++) {
				Element app = (Element) apps.item(i);
				strCategory[i][0] = app.getAttribute("id");
				strCategory[i][1] = app.getAttribute("name");
				int itemLength = app.getElementsByTagName("item").getLength();
				strItem = new String[itemLength][3];
				for (int n = 0; n < itemLength; n++) {
					Element appItem = (Element) app
							.getElementsByTagName("item").item(n);
					strItem[n][0] = appItem.getAttribute("key");
					strItem[n][1] = appItem.getAttribute("value");
					strItem[n][2] = appItem.getAttribute("name");
				}
				arrayMappedItem.put(filename + strCategory[i][0], strItem);
			}
		} catch (Exception e) {
			log.error(
					"解析" + filename + " 错误，当前:"
							+ (strCategory != null ? strCategory[i][0] : "")
							+ e.getMessage(), e);
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("", e);
				}
			}
		}
		if (log.isInfoEnabled()) {
			log.info("已经从文件" + filename + "中载入了" + i + "组设置.");
		}
	}

	/**
	 * 外部直接获得FrameData单例对象
	 * 
	 * @return
	 */
	public static FrameData getInstance() {
		return instance;
	}

	public String getConfigItem(String sDictFile, String sCatalog, String sKey) {
		String[][] str = (String[][]) arrayMappedItem.get(sDictFile + sCatalog);
		if (str == null) {
			return null;
		}
		for (int i = 0; i < str.length; i++) {
			if (str[i][0] == null) {
				continue;
			}
			if (str[i][0].equals(sKey)) {
				return str[i][1];
			}
		}
		return null;
	}
	
	public void destroy(){
		arrayMappedItem.clear();
	}

}
