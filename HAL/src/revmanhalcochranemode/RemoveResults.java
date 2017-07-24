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
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author mcasp
 */
public class RemoveResults {

    public static boolean main (String file1) {
        
       try {
           Document doc = getDocument(file1);
       
        
        Node intervention = doc.getElementsByTagName("INTERVENTION_EFFECTS").item(0);
        removeAllChildren(intervention);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(file1));
        transformer.transform(source, result);
        return true;
       }
       catch (Exception e){
           System.out.println(e);
           return false;
       }
    }
    
    
   private static void removeAllChildren(Node node)
{
  for (Node child; (child = node.getFirstChild()) != null; node.removeChild(child));
}
    
    private static Document getDocument (String file){
        
        try{ 
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        }
        catch (Exception e){
            return null;
        }
    
    }
    
}
