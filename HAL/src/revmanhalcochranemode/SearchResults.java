/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package revmanhalcochranemode;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 
import java.util.Date; 

/**
 *
 * @author mcasp
 */
public class SearchResults 
{
    
    private String language;
    
    public SearchResults(String lang)
    {
    language = lang;
    }
    
    public boolean main (String file) {
        
        String value;
        String [] textFlow = new String[9];
        String [] zahlenFlow = new String [9];
        Complex1 cleaner = new Complex1(null);
        
        if(language.equalsIgnoreCase("English"))
        {
        
        
        try {
          
            
            Document doc = getDocument(file);
            
            
            System.out.println("doc: "+doc.toString());
            
           // int anzahl = doc.getElementsByTagName("EXTENSIONS").getLength();
            Element comp = (Element) doc.getElementsByTagName("EXTENSION").item(0);
            String figure = comp.getAttribute("ID");
            NodeList listFlow = comp.getElementsByTagName("FLOWCHARTBOX");
            NodeList listOut = comp.getElementsByTagName("OUT");
            Node search = doc.getElementsByTagName("SEARCH_RESULTS").item(0);
            
            
            
            //Flowchartboxen auslesen
            for (int i = 0; i < listFlow.getLength(); i++){
                value = listFlow.item(i).getAttributes().getNamedItem("TEXT").getNodeValue();
                value = value.replaceAll("<p>", "");
                value = value.replaceAll("</p>", "");
                //System.out.println();
                if (value.contains("database search")){
                    textFlow[0] = value; 
                    zahlenFlow[0] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("other sources")){
                    textFlow[1] = value;
                    zahlenFlow[1] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("duplicates removed")){
                    textFlow[2] = value;
                    zahlenFlow[2] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("records screened")){
                    textFlow[3] = value;
                    zahlenFlow[3] = value.substring(0,value.indexOf(" "));
                }   
                else if(value.contains("eligibility")) {
                    textFlow[4] = value;
                    zahlenFlow[4] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("qualitative synthesis")) {
                    textFlow[5] = value;
                    zahlenFlow[5] = value.substring(0,value.indexOf(" "));
                }  
                else if(value.contains("quantitative synthesis") || value.contains("meta-analysis")) {
                    textFlow[6] = value;  
                    zahlenFlow[6] = value.substring(0,value.indexOf(" "));
                }              
            }
            //Outboxen auslesen
            for (int i = 0; i < listOut.getLength(); i++){
                value = listOut.item(i).getAttributes().getNamedItem("TEXT").getNodeValue();
                value = value.replaceAll("<p>", "");
                value = value.replaceAll("</p>", "");
                //System.out.println();
                if (value.contains("records excluded")){
                    textFlow[7] = value; 
                    zahlenFlow[7] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("articles excluded")){
                    textFlow[8] = value;
                    zahlenFlow[8] = value.substring(0,value.indexOf(" "));
                }
                             
            }
            //remove old search results section
            
            removeAllChildren(search);
            
            // write new results of search section
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date(); 
            
            Element paragraph2 = doc.createElement("P");
            search.appendChild(paragraph2);
            Element marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ Start of HAL generated text "+formatter.format(currentTime)+" ------*"));
            paragraph2.appendChild(marker);
            
            Element paragraph = doc.createElement("P");
            paragraph.appendChild(doc.createTextNode("Electronic searches identified "+zahlenFlow[0]+" references"));
            search.appendChild(paragraph);
            if (zahlenFlow[1] == null){
                paragraph.appendChild(doc.createTextNode("."));
                search.appendChild(paragraph);
            }
            else {
                paragraph.appendChild(doc.createTextNode(" with "+ zahlenFlow[1]+" additional records identified through other sources."));
                search.appendChild(paragraph);
            }
            if (zahlenFlow[2] == null){
                paragraph.appendChild(doc.createTextNode(" After duplicates were removed, we screened "+zahlenFlow[3]+" records. "));
                search.appendChild(paragraph);
            }
            else {
                paragraph.appendChild(doc.createTextNode(" After duplicates were removed, we screened "+zahlenFlow[2]+" records. "));
                search.appendChild(paragraph);
            }
            paragraph.appendChild(doc.createTextNode(zahlenFlow[4]+" potentially relevant records were obtained and scrutinised and "+zahlenFlow[8]+" of these reports did not meet the inclusion criteria (see "));
            search.appendChild(paragraph);
            Element link = doc.createElement("LINK");
            link.appendChild(doc.createTextNode("Characteristics of excluded studies"));
            paragraph.appendChild(link);
            Attr attr = doc.createAttribute("TAG");
            attr.setValue("CHARACTERISTICS_OF_EXCLUDED_STUDIES");
            link.setAttributeNode(attr);
            Attr attr1 = doc.createAttribute("TYPE");
            attr1.setValue("SECTION");
            link.setAttributeNode(attr1);
            paragraph.appendChild((doc.createTextNode(") and had to be excluded. ")));
            search.appendChild(paragraph);
            if (zahlenFlow[5] == null) {
                paragraph.appendChild(doc.createTextNode(zahlenFlow[6]+" trials are included ("));
                search.appendChild(paragraph);
            }
            else paragraph.appendChild(doc.createTextNode(zahlenFlow[5]+" trials are included ("));
            search.appendChild(paragraph);
            Element link1 = doc.createElement("LINK");
            link1.appendChild(doc.createTextNode("Figure "+(figure.substring(figure.indexOf("_")+1))));
            paragraph.appendChild(link1);
            Attr attr2 = doc.createAttribute("REF");
            attr2.setValue("FIG-0"+(figure.substring(figure.indexOf("_")+1)));
            link1.setAttributeNode(attr2);
            Attr attr3 = doc.createAttribute("TYPE");
            attr3.setValue("FIGURE");
            link1.setAttributeNode(attr3);
            paragraph.appendChild(doc.createTextNode(")."));
            search.appendChild(paragraph);
            
            paragraph2 = doc.createElement("P");
            search.appendChild(paragraph2);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ End of HAL generated text "+formatter.format(currentTime)+" ------*"));
            paragraph2.appendChild(marker);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file));
            transformer.transform(source, result);
            
            
               cleaner.deleteSpecialChars(file);
          

              
          return true;
	  } catch (Exception ex) {
              ex.printStackTrace();
		return false;
	  }
    }
        
         if(language.equalsIgnoreCase("German"))
        {       
        
        try {
           
           
            Document doc = getDocument(file);
            
           // int anzahl = doc.getElementsByTagName("EXTENSIONS").getLength();
            
            
            
            Element comp = (Element) doc.getElementsByTagName("EXTENSION").item(0);
            String figure = comp.getAttribute("ID");
            NodeList listFlow = comp.getElementsByTagName("FLOWCHARTBOX");
            NodeList listOut = comp.getElementsByTagName("OUT");
            Node search = doc.getElementsByTagName("SEARCH_RESULTS").item(0);
            
            
            
            //Flowchartboxen auslesen
            for (int i = 0; i < listFlow.getLength(); i++){
                value = listFlow.item(i).getAttributes().getNamedItem("TEXT").getNodeValue();
                value = value.replaceAll("<p>", "");
                value = value.replaceAll("</p>", "");
                //System.out.println();
                if (value.contains("database search")){
                    textFlow[0] = value; 
                    zahlenFlow[0] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("other sources")){
                    textFlow[1] = value;
                    zahlenFlow[1] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("duplicates removed")){
                    textFlow[2] = value;
                    zahlenFlow[2] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("records screened")){
                    textFlow[3] = value;
                    zahlenFlow[3] = value.substring(0,value.indexOf(" "));
                }   
                else if(value.contains("eligibility")) {
                    textFlow[4] = value;
                    zahlenFlow[4] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("qualitative synthesis")) {
                    textFlow[5] = value;
                    zahlenFlow[5] = value.substring(0,value.indexOf(" "));
                }  
                else if(value.contains("quantitative synthesis") || value.contains("meta-analysis")) {
                    textFlow[6] = value;  
                    zahlenFlow[6] = value.substring(0,value.indexOf(" "));
                }              
            }
            //Outboxen auslesen
            for (int i = 0; i < listOut.getLength(); i++){
                value = listOut.item(i).getAttributes().getNamedItem("TEXT").getNodeValue();
                value = value.replaceAll("<p>", "");
                value = value.replaceAll("</p>", "");
                //System.out.println();
                if (value.contains("records excluded")){
                    textFlow[7] = value; 
                    zahlenFlow[7] = value.substring(0,value.indexOf(" "));
                }
                else if(value.contains("articles excluded")){
                    textFlow[8] = value;
                    zahlenFlow[8] = value.substring(0,value.indexOf(" "));
                }
                             
            }
            //remove old search results section
            
            removeAllChildren(search);
            
            // write new results of search section
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date(); 
            
            Element paragraph2 = doc.createElement("P");
            search.appendChild(paragraph2);
            Element marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ Start des von HAL generierten Textes "+formatter.format(currentTime)+" ------*"));
            paragraph2.appendChild(marker);
            
            Element paragraph = doc.createElement("P");
            paragraph.appendChild(doc.createTextNode("Die elektronische Suche identifizierte "+zahlenFlow[0]+" Referenzen"));
            search.appendChild(paragraph);
            if (zahlenFlow[1] == null){
                paragraph.appendChild(doc.createTextNode("."));
                search.appendChild(paragraph);
            }
            else {
                paragraph.appendChild(doc.createTextNode(" mit "+ zahlenFlow[1]+" zusaetzlichen Studien, die von anderen Quellen identifiziert wurden."));
                search.appendChild(paragraph);
            }
            if (zahlenFlow[2] == null){
                paragraph.appendChild(doc.createTextNode("Nachdem Duplikate entfernt wurden, wurden "+zahlenFlow[3]+" Studien gescreent. "));
                search.appendChild(paragraph);
            }
            else {
                paragraph.appendChild(doc.createTextNode("Nachdem Duplikate entfernt wurden, wurden "+zahlenFlow[2]+" Studien gescreent. "));
                search.appendChild(paragraph);
            }
            paragraph.appendChild(doc.createTextNode(zahlenFlow[4]+" potenziell relevante Studien wurden akquiriert und geprueft und von diesen erfuellten "+zahlenFlow[8]+" die Einschlusskriterien nicht, (siehe"));
            search.appendChild(paragraph);
            Element link = doc.createElement("LINK");
            link.appendChild(doc.createTextNode("Characteristics of excluded studies"));
            paragraph.appendChild(link);
            Attr attr = doc.createAttribute("TAG");
            attr.setValue("CHARACTERISTICS_OF_EXCLUDED_STUDIES");
            link.setAttributeNode(attr);
            Attr attr1 = doc.createAttribute("TYPE");
            attr1.setValue("SECTION");
            link.setAttributeNode(attr1);
            paragraph.appendChild((doc.createTextNode(") und musste ausgeschlossen werden.")));
            search.appendChild(paragraph);
            if (zahlenFlow[5] == null) {
                paragraph.appendChild(doc.createTextNode(zahlenFlow[6]+" Studien sind inkludiert ("));
                search.appendChild(paragraph);
            }
            else paragraph.appendChild(doc.createTextNode(zahlenFlow[5]+" Studien sind inkludiert ("));
            search.appendChild(paragraph);
            Element link1 = doc.createElement("LINK");
            link1.appendChild(doc.createTextNode("Figure "+(figure.substring(figure.indexOf("_")+1))));
            paragraph.appendChild(link1);
            Attr attr2 = doc.createAttribute("REF");
            attr2.setValue("FIG-0"+(figure.substring(figure.indexOf("_")+1)));
            link1.setAttributeNode(attr2);
            Attr attr3 = doc.createAttribute("TYPE");
            attr3.setValue("FIGURE");
            link1.setAttributeNode(attr3);
            paragraph.appendChild(doc.createTextNode(")."));
            search.appendChild(paragraph);
            
            paragraph2 = doc.createElement("P");
            search.appendChild(paragraph2);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ Ende des von HAL generierten Textes "+formatter.format(currentTime)+" ------*"));
            paragraph2.appendChild(marker);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file));
            transformer.transform(source, result);
            
            
               cleaner.deleteSpecialChars(file);
          

              
          return true;
	  } catch (Exception ex) {
              ex.printStackTrace();
		return false;
	  }
    }
        
        else
        {
        return false;
        }
    
    }   
     private static Document getDocument (String file)
     {
        
         try{ 
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        }
        catch (Exception e)
        {
            System.out.println("that's the file catch: "+file);
            e.printStackTrace();
            return null;
        }
    }
     public static void removeAllChildren(Node node)
     {
        for (Node child; (child = node.getFirstChild()) != null; node.removeChild(child));
     }
     
     
     public static boolean checkFigures (String file) {
         Document doc = getDocument(file);
         if(doc.getElementsByTagName("EXTENSIONS").item(0).hasChildNodes() == true)
             return true;
         else
             return false;
     }
     
    
}
