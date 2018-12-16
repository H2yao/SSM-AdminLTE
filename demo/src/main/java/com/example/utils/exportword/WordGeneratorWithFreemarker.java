package com.example.utils.exportword;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.example.utils.exportword.RichHtmlHandler;
import com.example.utils.exportword.WordGeneratorWithFreemarker;
import com.example.utils.exportword.WordHtmlGeneratorHelper;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;

public class WordGeneratorWithFreemarker {
	private static Configuration configuration = null;

	static {
		configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassicCompatible(true);
		configuration.setClassForTemplateLoading(
				WordGeneratorWithFreemarker.class,
				"/com/example/utils/exportword/ftl");
	}

	public static void createDoc(Map<String, Object> dataMap,String templateName, OutputStream out)throws Exception {
		Template t = configuration.getTemplate(templateName);
		t.setEncoding("utf-8");
		WordHtmlGeneratorHelper.handleAllObject(dataMap);
		try {
			Writer w = new OutputStreamWriter(out,Charset.forName("utf-8"));
			t.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public static String exportWord(String remark,String remarkTitle,String path)throws Exception {
		HashMap<String, Object> data = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		remark=remark.replace("../../",path); //将相对路径替换为服务器绝对路径
		sb.append(remark);
		RichHtmlHandler handler = new RichHtmlHandler(sb.toString());

		handler.setDocSrcLocationPrex("file:///C:/8595226D");
		handler.setDocSrcParent("file3405.files");
		handler.setNextPartId("01D214BC.6A592540");
		handler.setShapeidPrex("_x56fe__x7247__x0020");
		handler.setSpidPrex("_x0000_i");
		handler.setTypeid("#_x0000_t75");

		handler.handledHtml(false);

		String bodyBlock = handler.getHandledDocBodyBlock();
		System.out.println("BodyBlock :\n"+bodyBlock);

		String handledBase64Block = "";
		if (handler.getDocBase64BlockResults() != null
				&& handler.getDocBase64BlockResults().size() > 0) {
			for (String item : handler.getDocBase64BlockResults()) {
				handledBase64Block += item + "\n";
			}
		}
		data.put("imagesBase64String", handledBase64Block);

		String xmlimaHref = "";
		if (handler.getXmlImgRefs() != null
				&& handler.getXmlImgRefs().size() > 0) {
			for (String item : handler.getXmlImgRefs()) {
				xmlimaHref += item + "\n";
			}
		}
		data.put("imagesXmlHrefString", xmlimaHref);
		data.put("content", bodyBlock);
		
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");  
        String dateNowStr = sdf.format(d);  
		
		remarkTitle = StringUtils.isBlank(remarkTitle) ? "未命名文档" : remarkTitle; 
		String docFilePath = "D:\\"+dateNowStr+"_"+remarkTitle+".doc";
		System.out.println(docFilePath);
		File f = new File(docFilePath);
		OutputStream out;
		try {
			out = new FileOutputStream(f);
			WordGeneratorWithFreemarker.createDoc(data, "temp.ftl", out);

		} catch (FileNotFoundException e) {

		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docFilePath;
	}
}
