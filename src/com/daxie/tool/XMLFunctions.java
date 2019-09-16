package com.daxie.tool;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.daxie.log.LogFile;

/**
 * Methods to handle XML.
 * @author Daba
 *
 */
public class XMLFunctions {
	/**
	 * Creates a XML file.
	 * @param file File
	 * @param document org.w3c.dom.Document
	 * @return -1 on error and 0 on success
	 */
	public static int WriteXML(File file,Document document) {
		Transformer transformer=null;
		try {
			TransformerFactory factory=TransformerFactory.newInstance();
			transformer=factory.newTransformer();
		}
		catch(TransformerConfigurationException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[XMLFunctions-WriteXML] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("encoding", "UTF-8");
		
		try {
			transformer.transform(new DOMSource(document), new StreamResult(file));
		}
		catch(TransformerException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[XMLFunctions-WriteXML] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		
		return 0;
	}
}
