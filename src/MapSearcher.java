import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Abstrakt superklass för alla sökklasser. 
 * 
 * @author billing
 * @author denniso
 */
public abstract class MapSearcher {
	
	/**
	 * Laddar en XML-fil och returnerar ett DOM-dokument. 
	 * Se http://www.jdom.org/ för mer info. 
	 * @param srcFile XML-fil med kartan.
	 * @return DOM Document
	 * @throws IOException
	 * @throws JDOMException
	 * 
	 */
	public static Document loadXmlMap(File srcFile) throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(srcFile);
		return doc;
	}
	
	/**
	 * Specifiserar kartan. 
	 * 
	 * Denna metod anropas alltid före sökning och lagrar kartan som ett privat attribut. 
	 * Hur kartan representeras är upp till er att specificera.
	 * @param map XML-fil med kartan.
	 *  
	 */
	abstract public void setMap(File map);
	
	/**
	 * Utför sökning med bredden först. 
	 * @param from Id för startnod
	 * @param to Id för målnod
	 * @return Sträng som representerar vägen från start till mål. 
	 *  
	 */
	abstract public String breadthFirst(String from,String to);
	
	/**
	 * Utför sökning med djupet först. 
	 * @param from Id för startnod
	 * @param to Id för målnod
	 * @return Sträng som representerar vägen från start till mål. 
	 *  
	 */
	abstract public String depthFirst(String from,String to);
	
	/**
	 * Utför sökning med A*. 
	 * @param from Id för startnod
	 * @param to Id för målnod
	 * @param fastest Anger om "snabbaste" eller (om falsk) kortaste vägen söks
	 * @return Sträng som representerar vägen från start till mål. 
	 *  
	 */
	abstract public String aStar(String from,String to,boolean fastest);
	
	/**
	 * Utför sökning med Greedy Search. 
	 * @param from Id för startnod
	 * @param to Id för målnod
	 * @return Sträng som representerar vägen från start till mål. 
	 *  
	 */
	abstract public String greedySearch(String from,String to);
}
