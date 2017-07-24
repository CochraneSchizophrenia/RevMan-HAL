/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package revmanhalcochranemode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 
import java.util.Date;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.traversal.NodeFilter;

        


/**
 *
 * @author mcasp
 */
public class Complex1 
{
    
    String language = ""; 
    
    public Complex1(String lang)
    {
    language = lang;
    }
   
    public boolean main (String file1, String file2) 
    {
  
        
        
        boolean subgroup = false;
        boolean noTotals = false;
        Element subsection = null;
        Element heading = null;
        Element subsection2 = null; 
        Element subsection3 = null;
        Element paragraph2 =  null;
        Element paragraph = null;
        Element paragraph3 = null;
        Element marker = null;
        
        Complex1 cleaner = new Complex1(null);        
           
        int total = 0;
        double effectEst = 0.0;
        double ciStart = 0.0;
        double ciEnd = 0.0;
        int count = 0;
        String countstring = "no";
        int count2 = 0;
        String countstring2 = "no";
        int total2 = 0;
        double effectEst2 = 0.0;
        double ciStart2 = 0.0;
        double ciEnd2 = 0.0;
        String studytype = "";
        String studytype2 = "";
        String analysisnr = "";
        String analysisnr2 = "";
        String studyname = "";
        String studyname2 = "";
        String comparisonname = "";
        String outcomename = "";
        String subgroupname = "";
        String effectMeasure = "";
        String effectMeasureShort = "";
        String effectMeasure2 = "";
        String effectMeasure2Short = "";
        double p1 = 0.0;
        double p2 = 0.0;
        int i1 = 0;
        int i2 = 0;
        double chi1 = 0.0;
        double chi2 = 0.0;
        int df1 = 0;
        int df2 = 0;
    
       
        String [] arr = new String [46];
        
        System.out.println("language in complex1: "+language);
        
        if(language.equalsIgnoreCase("English"))
        {
            
        
        try 
        {
            String filepath = file1;            // REVMAN FILE
            Document doc = getDocument(filepath);
            String datafile = file2;            // CSV FILE
            
            Node intervention = doc.getElementsByTagName("INTERVENTION_EFFECTS").item(0);  //SEARCH THE DOCUMENT FOR THE TAG NAME
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date();  
            
            paragraph3 = doc.createElement("P");   // ADD TEXT (TEXT CAN ONLY BE ADDED IN P; MARKER IS PART OF PARAGRAPG3; PARAGRAPH3 IS PART OF INTERVENTION; INTERVENTION IS MAIN NODE)
            intervention.appendChild(paragraph3);            
            marker = doc.createElement("MARKER");   // MARKER IS NEEDED TO ADD TEXT)
            marker.appendChild(doc.createTextNode("*------ Start of HAL generated text "+formatter.format(currentTime)+" ------*     PLEASE NOTE: if an outcome is in 'Other Data' tables this information will not be included in automatically generated text. Numbering may, therefore, not be consecutive."));
            paragraph3.appendChild(marker);
            
            
            FileReader fr = new FileReader(datafile);
            BufferedReader br = new BufferedReader(fr);
            
           
            String zeile = br.readLine();
            zeile = br.readLine();
            
            while( zeile  != null  )
            {
              arr = zeile.split(";");
              System.out.println("this is line: "+zeile);
              
              if (Integer.parseInt(arr[1])== 0 && Integer.parseInt(arr[2])== 0) {  //Columns B and C equals 0?
                  
                  subsection = doc.createElement("SUBSECTION");
                  intervention.appendChild(subsection);
                  comparisonname = arr[3];
                 

                  heading = doc.createElement ("HEADING");
                  heading.appendChild(doc.createTextNode("COMPARISON "+arr[0]+": "+arr[3]));
                  subsection.appendChild(heading);
                  Attr attr = doc.createAttribute("LEVEL");
                  attr.setValue("3");
                  heading.setAttributeNode(attr);
                  
                  zeile = br.readLine();
              }
              else
                  if (Integer.parseInt(arr[1])!= 0 && Integer.parseInt(arr[2])== 0) {  // Column B not equals 0 but Column C equals 0?
                        
                      if (!arr[4].equals("")) {             //Column E not empty?
                         
                          System.out.println("in else if 1 dinna");
                          
                              subsection2 = doc.createElement("SUBSECTION");
                              subsection.appendChild(subsection2);

                              Element heading2 = doc.createElement("HEADING");
                              heading2.appendChild(doc.createTextNode(arr[0]+"."+arr[1]+" "+arr[3]));
                              subsection2.appendChild(heading2);
                              Attr attr2 = doc.createAttribute("LEVEL");
                              attr2.setValue("4");
                              heading2.setAttributeNode(attr2);                               
                              analysisnr = arr[0]+"."+arr[1];
                              outcomename = arr[3];
                              effectMeasure2 = arr[6];
                              if (arr[8].equals("No totals")){
                                    noTotals = true;
                              }
                              else {
                                    noTotals = false;
                              }
                         
                              if (!arr[18].equals("") && noTotals == false) {
                                total2 = (Integer.parseInt(arr[18]) + Integer.parseInt(arr[22]));
                                effectEst2 = Math.round(Double.parseDouble(arr[25])*100)/100.0;
                                ciStart2 = Math.round(Double.parseDouble(arr[27])*100)/100.0;
                                ciEnd2 = Math.round(Double.parseDouble(arr[28])*100)/100.0;
                                studytype = arr[4];
                                p1 = Math.round(Double.parseDouble(arr[31])*1000)/1000.0;
                                i1 = (int)Math.round(Double.parseDouble(arr[32]));
                                chi1 = Math.round(Double.parseDouble(arr[30])*100)/100.0;
                                df1 = Integer.parseInt(arr[39]);
                                
                          //      System.out.println("--"+arr[0]+"."+arr[1]+" "+arr[3]);
                               if (comparisonname.toLowerCase().contains("sensitivity")) {
                                   Double pOverall = Math.round(Double.parseDouble(arr[37])*1000)/1000.0;
                                   Double iOverall = Math.round(Double.parseDouble(arr[38])*1000)/1000.0;
                                   Double chiOverall = Math.round(Double.parseDouble(arr[36])*1000)/1000.0;
                                   if (pOverall < 0.5 ){
                                       paragraph = doc.createElement("P"); 
                                       paragraph.appendChild(doc.createTextNode("Overall result for subgroups: "+"("+effectMeasure2+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). There was a statistically significant difference between the subgroups of trials (Chi2="+chiOverall+"; df="+df1+"; P="+pOverall+"). ")); 
                                       subsection2.appendChild(paragraph);
                                   }
                                   else {
                                       paragraph = doc.createElement("P"); 
                                       paragraph.appendChild(doc.createTextNode("Overall result for subgroups: "+"("+effectMeasure2+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). There was no significant difference between the subgroups of trials (Chi2="+chiOverall+"; df="+df1+"; P="+pOverall+"). ")); 
                                       subsection2.appendChild(paragraph);
                                   }
                                   if (iOverall >= 30 && iOverall <= 50) {
                                     paragraph.appendChild(doc.createTextNode("This finding had moderate levels of heterogeneity (I2="+iOverall+"%)."));  
                                     subsection2.appendChild(paragraph);
                                    }
                                   else if (iOverall > 50){
                                   paragraph.appendChild(doc.createTextNode("This finding had important levels of heterogeneity (I2="+iOverall+"%)."));
                                   subsection2.appendChild(paragraph);
                                    }
                               }
                               
                              }
                              zeile = br.readLine();
                         
                      } else {
                          System.out.println("in der else schleife");
                            count2 = 0;
                            while (arr.length > 44 && arr[4].equals("")) {
                              studyname2 = arr[3];
                              System.out.println("studyname 2: "+studyname2);
                              count2++;
                              zeile = br.readLine();
                              if (zeile != null){
                                  arr = zeile.split(";");
                              }
                              else break;
                            }
                 
                                
                                switch (count2) {
                                    case 2:  countstring2 = "two"; break;
                                    case 3:  countstring2 = "three"; break;
                                    case 4:  countstring2 = "four"; break;
                                    case 5:  countstring2 = "five"; break;
                                    case 6:  countstring2 = "six"; break;
                                    case 7:  countstring2 = "seven"; break;
                                    case 8:  countstring2 = "eight"; break;
                                    case 9:  countstring2 = "nine"; break;    
                                    case 10: countstring2 = "ten"; break;
                                }
                                switch (effectMeasure2){
                                    case "Risk Ratio": effectMeasure2Short = "RR"; break;
                                    case "Odds Ratio": effectMeasure2Short = "OR"; break;
                                    case "Mean Difference": effectMeasure2Short = "MD"; break;
                                    case "Risk Difference": effectMeasure2Short = "RD"; break;
                                    case "Std. Mean Difference": effectMeasure2Short = "SMD"; break;
                                }
                                // ab hier Trennung
                                
                                paragraph = doc.createElement("P");
                                if (comparisonname.contains("versus")){
                                    
                                    System.out.println("in versus dinna if");
                                    
                                    if (ciStart2 < 1 && ciEnd2 > 1){
                                        if (count2 == 1) {
                                           paragraph.appendChild(doc.createTextNode("For this outcome we only found one relevant trial (n="+total2+") ("+studyname2+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));    
                                        }
                                        else if (count2 <= 10) {
                                           paragraph.appendChild(doc.createTextNode("For this outcome we found "+countstring2+" relevant trials (n="+total2+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                        else {
                                             paragraph.appendChild(doc.createTextNode("For this outcome we found "+count2+" relevant trials (n="+total2+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                    }
                                    else {
                                        if (count2 == 1) {
                                           paragraph.appendChild(doc.createTextNode("For this outcome we only found one relevant trial (n="+total2+") ("+studyname2+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));    
                                        }
                                        else if (count2 <= 10) {
                                           paragraph.appendChild(doc.createTextNode("For this outcome we found "+countstring2+" relevant trials (n="+total2+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                        else {
                                             paragraph.appendChild(doc.createTextNode("For this outcome we found "+count2+" relevant trials (n="+total2+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                    }
                                    subsection2.appendChild(paragraph);
                                    paragraph.appendChild(doc.createTextNode(" ("+effectMeasure2Short+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). "));
                                    subsection2.appendChild(paragraph);
                                }
                                else if (comparisonname.contains("vs")){ //contains "vs"
                                    System.out.println("in versus dinna else if");
                                    
                                    if (ciStart2 < 1 && ciEnd2 > 1){
                                    if (count2 == 1) {
                                       paragraph.appendChild(doc.createTextNode("For this outcome we only found one relevant trial (n="+total2+") ("+studyname2+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));    
                                    }
                                    else if (count2 <= 10) {
                                       paragraph.appendChild(doc.createTextNode("For this outcome we found "+countstring2+" relevant trials (n="+total2+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                    }
                                    else {
                                         paragraph.appendChild(doc.createTextNode("For this outcome we found "+count2+" relevant trials (n="+total2+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                    }
                                    }
                                    else {
                                        if (count2 == 1) {
                                           paragraph.appendChild(doc.createTextNode("For this outcome we only found one relevant trial (n="+total2+") ("+studyname2+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));    
                                        }
                                        else if (count2 <= 10) {
                                           paragraph.appendChild(doc.createTextNode("For this outcome we found "+countstring2+" relevant trials (n="+total2+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        }
                                        else {
                                             paragraph.appendChild(doc.createTextNode("For this outcome we found "+count2+" relevant trials (n="+total2+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        }
                                    }
                                    subsection2.appendChild(paragraph);
                                    paragraph.appendChild(doc.createTextNode(" ("+effectMeasure2Short+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). "));
                                    subsection2.appendChild(paragraph);
                                }
                                else { //Sensitivity Analysis
//                                    if (ciStart2 < 1 && ciEnd2 > 1){
//                                    if (count2 == 1) {
//                                       paragraph.appendChild(doc.createTextNode("For this outcome we only found one relevant trial (n="+total2+") ("+studyname2+"). "));    
//                                    }
//                                    else if (count2 <= 10) {
//                                       paragraph.appendChild(doc.createTextNode("For this outcome we found "+countstring2+" relevant trials (n="+total2+"). "));
//                                    }
//                                    else {
//                                         paragraph.appendChild(doc.createTextNode("For this outcome we found "+count2+" relevant trials (n="+total2+"). "));
//                                    }
//                                    }
//                                    else {
//                                        if (count2 == 1) {
//                                           paragraph.appendChild(doc.createTextNode("For this outcome we only found one relevant trial (n="+total2+") ("+studyname2+"). "));    
//                                        }
//                                        else if (count2 <= 10) {
//                                           paragraph.appendChild(doc.createTextNode("For this outcome we found "+countstring2+" relevant trials (n="+total2+"). "));
//                                        }
//                                        else {
//                                             paragraph.appendChild(doc.createTextNode("For this outcome we found "+count2+" relevant trials (n="+total2+"). "));
//                                        }
//                                    }
//                                    subsection2.appendChild(paragraph);
                                    System.out.println("in versus dinna else");
                                    
                                    if (count2 == 1){
                                    paragraph.appendChild(doc.createTextNode(" (1 RCT, n="+total2+", "+effectMeasure2Short+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). "));
                                    subsection2.appendChild(paragraph);
                                    }
                                    else {
                                    paragraph.appendChild(doc.createTextNode(" ("+count2+" RCTs, n="+total2+", "+effectMeasure2Short+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). "));
                                    subsection2.appendChild(paragraph);   
                                    }
                                }
                                if (i1 >= 30 && i1 <= 50 && count2 > 1) {
                                     paragraph.appendChild(doc.createTextNode("This outcome had moderate levels of heterogeneity (Chi2="+chi1+"; df="+df1+"; P="+p1+"; I2="+i1+"%)."));  
                                }
                                else
                                    if (i1 > 50 && count2 > 1){
                                    paragraph.appendChild(doc.createTextNode("This outcome had important levels of heterogeneity (Chi2="+chi1+"; df="+df1+"; P="+p1+"; I2="+i1+"%)."));
                                }
                                subsection2.appendChild(paragraph);
                            //    System.out.println (">>Total study count: "+count2); 
                     
                       
                      } subgroup = false;
                  }
                  else
                      if (Integer.parseInt(arr[1])!= 0 && Integer.parseInt(arr[2])!= 0 && noTotals == false){       //Column B and C not equals 0
                          
                          
                          if (!arr[4].equals("")){                  // Column E not empty
                          
                               System.out.println("in else if arr[2] if");
                              
                            subsection3 = doc.createElement("SUBSECTION");
                            subsection2.appendChild(subsection3);

                            Element heading3 = doc.createElement("HEADING");
                            heading3.appendChild(doc.createTextNode(arr[0]+"."+arr[1]+"."+arr[2]+" "+ arr[3]));
                            subsection3.appendChild(heading3);
                            Attr attr3 = doc.createAttribute("LEVEL");
                            attr3.setValue("5");
                            heading3.setAttributeNode(attr3);     
                         //   System.out.println("----"+arr[0]+"."+arr[1]+"."+arr[2]+" "+ arr[3]);
                            total = (Integer.parseInt(arr[18]) + Integer.parseInt(arr[22]));
                            effectEst = Math.round(Double.parseDouble(arr[25])*100)/100.0;
                            ciStart = Math.round(Double.parseDouble(arr[27])*100)/100.0;
                            ciEnd = Math.round(Double.parseDouble(arr[28])*100)/100.0;
                            studytype2 = arr[4];
                            analysisnr2 = arr[0]+"."+arr[1];
                            subgroupname = arr[3];
                            effectMeasure = arr[6];
                            p2 = Math.round(Double.parseDouble(arr[31])*1000)/1000.0;
                            i2 = (int)Math.round(Double.parseDouble(arr[32]));
                            chi2 = Math.round(Double.parseDouble(arr[30])*100)/100.0;
                            df2 = Integer.parseInt(arr[39]);
                           
                       //     System.out.println("Total analysis: " +"(n="+total+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0 +") ");
                            zeile = br.readLine();
                          }
                          
                          else {
                              
                               System.out.println("in else if arr[2] else "+comparisonname);
                            count = 0;
                            while (arr.length > 44 && arr[4].equals("")){
                                studyname = arr[3];
                                count ++;
                                zeile = br.readLine();
                                if (zeile != null) {
                                    arr = zeile.split(";");
                                }
                                else break;
                                
                            }
                           // if (count > 1){
                                switch (count) {
                                    case 2:  countstring = "two"; break;
                                    case 3:  countstring = "three"; break;
                                    case 4:  countstring = "four"; break;
                                    case 5:  countstring = "five"; break;
                                    case 6:  countstring = "six"; break;
                                    case 7:  countstring = "seven"; break;
                                    case 8:  countstring = "eight"; break;
                                    case 9:  countstring = "nine"; break; 
                                    case 10: countstring = "ten"; break;
                                }
                                switch (effectMeasure){
                                    case "Risk Ratio": effectMeasureShort = "RR"; break;
                                    case "Odds Ratio": effectMeasureShort = "OR"; break;
                                    case "Mean Difference": effectMeasureShort = "MD"; break;
                                    case "Risk Difference": effectMeasureShort = "RD"; break;
                                    case "Std. Mean Difference": effectMeasureShort = "SMD"; break;
                                }
                                if (comparisonname.contains("versus")||comparisonname.contains("Versus")  // contains "versus" & "VERSUS" &Versus! (contains work only for the excact term in the quotation!)
                                        ||comparisonname.contains("VERSUS")){
                                    paragraph2 = doc.createElement("P"); 
                                    if (ciStart < 1 && ciEnd > 1){
                                        String lowerCase="";
                                        try
                                        {
                                        lowerCase = comparisonname.toLowerCase();
                                        comparisonname = lowerCase;
                                        }
                                        catch (Exception e)
                                         {
                                           System.out.println("in catch new: "+e.toString());     
                                         }
                                        
                                        if (count == 1) {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we only found one relevant trial (n="+total+") ("+studyname+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));    
                                        }
                                        else if (count <= 10) {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+countstring+" relevant trials (n="+total+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                        else {
                                             paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+count+" relevant trials (n="+total+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        } 
                                    }
                                    else {
                                         if (count == 1) {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we only found one relevant trial (n="+total+") ("+studyname+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));    
                                        }
                                        else if (count <= 10) {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+countstring+" relevant trials (n="+total+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                        else {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+count+" relevant trials (n="+total+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        } 
                                    }
                                    subsection3.appendChild(paragraph2);
                                    paragraph2.appendChild(doc.createTextNode(" ("+effectMeasureShort+" "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analysis "+analysisnr+"). "));     
                                    subsection3.appendChild(paragraph2);
                                }
                                else if (comparisonname.contains("vs")||comparisonname.contains("VS")
                                        ||comparisonname.contains("Vs"))  // contains "vs" & "VS" & Vs! (contains work only for the excact term in the quotation!)
                                { 
                                     String lowerCase="";   // this is for stopping an error because of the "lowerCase" function. 
                                        try
                                        {
                                        lowerCase = comparisonname.toLowerCase();
                                        comparisonname = lowerCase;
                                        }
                                        catch (Exception e)
                                         {
                                           System.out.println("in catch new: "+e.toString());     
                                         }                                    
                                    
                                    paragraph2 = doc.createElement("P"); 
                                    if (ciStart < 1 && ciEnd > 1){
                                        
                                        if (count == 1) {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we only found one relevant trial (n="+total+") ("+studyname+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));    
                                        }
                                        else if (count <= 10) {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+countstring+" relevant trials (n="+total+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        }
                                        else {
                                             paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+count+" relevant trials (n="+total+"). There was no significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        } 
                                    }
                                    else {
                                         if (count == 1) {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we only found one relevant trial (n="+total+") ("+studyname+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));    
                                        }
                                        else if (count <= 10) {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+countstring+" relevant trials (n="+total+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        }
                                        else {
                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+count+" relevant trials (n="+total+"). There was a statistically significant difference between "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"and "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        } 
                                    }
                                    subsection3.appendChild(paragraph2);
                                    paragraph2.appendChild(doc.createTextNode(" ("+effectMeasureShort+" "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analysis "+analysisnr+"). "));     
                                    subsection3.appendChild(paragraph2);
                                } 
                                else { // Sensitivity Analysis
                                    
                                     System.out.println("in else sensitivity analysis");
                                    paragraph2 = doc.createElement("P"); 
//                                    if (ciStart < 1 && ciEnd > 1){
//                                        if (count == 1) {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we only found one relevant trial (n="+total+") ("+studyname+"). "));    
//                                        }
//                                        else if (count <= 10) {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+countstring+" relevant trials (n="+total+"). "));
//                                        }
//                                        else {
//                                             paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+count+" relevant trials (n="+total+"). "));
//                                        } 
//                                    }
//                                    else {
//                                         if (count == 1) {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we only found one relevant trial (n="+total+") ("+studyname+"). "));    
//                                        }
//                                        else if (count <= 10) {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+countstring+" relevant trials (n="+total+"). "));
//                                        }
//                                        else {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+count+" relevant trials (n="+total+"). "));
//                                        } 
//                                    }
//                                    subsection3.appendChild(paragraph2);
                                    if (count == 1){
                                        paragraph2.appendChild(doc.createTextNode(" (1 RCT, n="+total+", "+effectMeasureShort+" "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analysis "+analysisnr+"). "));     
                                        subsection3.appendChild(paragraph2);
                                    }
                                    else {
                                        paragraph2.appendChild(doc.createTextNode(" ("+count+" RCTs, n="+total+", "+effectMeasureShort+" "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analysis "+analysisnr+"). "));     
                                        subsection3.appendChild(paragraph2);
                                    }
                                }
                                if (i2 >= 30 && i2 <= 50 && count > 1) {
                                     paragraph2.appendChild(doc.createTextNode("This subgroup had moderate levels of heterogeneity (Chi2="+chi2+"; df="+df2+"; P="+p2+"; I2="+i2+"%)."));
                                    subsection3.appendChild(paragraph2);
                                }
                                else
                                    if (i2 > 50 && count > 1){
                                    paragraph2.appendChild(doc.createTextNode("This subgroup had important levels of heterogeneity (Chi2="+chi2+"; df="+df2+"; P="+p2+"; I2="+i2+"%)."));
                                    subsection3.appendChild(paragraph2);
                                }
                            
                          //      System.out.println (">>Total study count: "+count); 
                      
                            
                          }
                          subgroup = true;   
                      }
                      else if (noTotals == true){
                          paragraph = doc.createElement("P");
                          paragraph.appendChild(doc.createTextNode("*--- Missing data in this subsection. Data was not totaled. ---*"));
                          subsection2.appendChild(paragraph);
                          zeile = br.readLine();
                      }
            }
            
            if (subgroup == false) {
                paragraph3 = doc.createElement("P");
                subsection2.appendChild(paragraph3);
                marker = doc.createElement("MARKER");
                marker.appendChild(doc.createTextNode("*--- End of HAL generated text "+formatter.format(currentTime)+" ---*"));
                paragraph3.appendChild(marker);
            }
            else if(subgroup == true){
                paragraph3 = doc.createElement("P");
                subsection3.appendChild(paragraph3);
                marker = doc.createElement("MARKER");
                marker.appendChild(doc.createTextNode("*--- End of HAL generated text "+formatter.format(currentTime)+" ---*"));
                paragraph3.appendChild(marker);
            }
            
           // TRANSFORM FROM DOM TO XML AGAIN
           
           
                        
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
            try
            {
                System.out.println("FilePath: "+filepath);
            cleaner.deleteSpecialChars(filepath);
            }
            catch(Exception e)
            {
                System.out.println("FEHLER!");
                e.printStackTrace();
            }
            
            
            System.out.println("Done");
            
           
            
            
            br.close();
            fr.close();
            return true;
       
        }
            
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
        else if(language.equalsIgnoreCase("german"))
        {
              
                                
           try {
            String filepath = file1;            // REVMAN FILE
            Document doc = getDocument(filepath);
            String datafile = file2;            // CSV FILE
            
            Node intervention = doc.getElementsByTagName("INTERVENTION_EFFECTS").item(0);  //SEARCH THE DOCUMENT FOR THE TAG NAME
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date();  
            
            paragraph3 = doc.createElement("P");   // ADD TEXT (TEXT CAN ONLY BE ADDED IN P; MARKER IS PART OF PARAGRAPG3; PARAGRAPH3 IS PART OF INTERVENTION; INTERVENTION IS MAIN NODE)
            intervention.appendChild(paragraph3);            
            marker = doc.createElement("MARKER");   // MARKER IS NEEDED TO ADD TEXT)
            marker.appendChild(doc.createTextNode("*------ Beginn des von HAL generierten Textes "+formatter.format(currentTime)+" ------*     ACHTUNG: Wenn sich Ergebnisse in 'Other Data'-Tabellen befinden, befinden sich diese Informationen nicht im automatisch generierten Text. Die Nummerierung koennte daher nicht aufeinanderfolgend sein."));
            paragraph3.appendChild(marker);
                                               
            
            FileReader fr = new FileReader(datafile);
            BufferedReader br = new BufferedReader(fr);
            
           
            String zeile = br.readLine();
            zeile = br.readLine();
            
            while( zeile  != null  )
            {
              arr = zeile.split(";");
              
              if (Integer.parseInt(arr[1])== 0 && Integer.parseInt(arr[2])== 0) {  //Columns B and C equals 0?
                  
                  subsection = doc.createElement("SUBSECTION");
                  intervention.appendChild(subsection);
                  comparisonname = arr[3];
                 

                  heading = doc.createElement ("HEADING");
                  heading.appendChild(doc.createTextNode("COMPARISON "+arr[0]+": "+arr[3]));
                  subsection.appendChild(heading);
                  Attr attr = doc.createAttribute("LEVEL");
                  attr.setValue("3");
                  heading.setAttributeNode(attr);
                  
                  zeile = br.readLine();
              }
              else
                  if (Integer.parseInt(arr[1])!= 0 && Integer.parseInt(arr[2])== 0) {  // Column B not equals 0 but Column C equals 0?
                        
                      if (!arr[4].equals("")) {             //Column E not empty?
                         
                              subsection2 = doc.createElement("SUBSECTION");
                              subsection.appendChild(subsection2);

                              Element heading2 = doc.createElement("HEADING");
                              heading2.appendChild(doc.createTextNode(arr[0]+"."+arr[1]+" "+arr[3]));
                              subsection2.appendChild(heading2);
                              Attr attr2 = doc.createAttribute("LEVEL");
                              attr2.setValue("4");
                              heading2.setAttributeNode(attr2);                               
                              analysisnr = arr[0]+"."+arr[1];
                              outcomename = arr[3];
                              effectMeasure2 = arr[6];
                              if (arr[8].equals("No totals")){
                                    noTotals = true;
                              }
                              else {
                                    noTotals = false;
                              }
                         
                              if (!arr[18].equals("") && noTotals == false) {
                                total2 = (Integer.parseInt(arr[18]) + Integer.parseInt(arr[22]));
                                effectEst2 = Math.round(Double.parseDouble(arr[25])*100)/100.0;
                                ciStart2 = Math.round(Double.parseDouble(arr[27])*100)/100.0;
                                ciEnd2 = Math.round(Double.parseDouble(arr[28])*100)/100.0;
                                studytype = arr[4];
                                p1 = Math.round(Double.parseDouble(arr[31])*1000)/1000.0;
                                i1 = (int)Math.round(Double.parseDouble(arr[32]));
                                chi1 = Math.round(Double.parseDouble(arr[30])*100)/100.0;
                                df1 = Integer.parseInt(arr[39]);
                                
                          //      System.out.println("--"+arr[0]+"."+arr[1]+" "+arr[3]);
                               if (comparisonname.toLowerCase().contains("sensitivity")) {
                                   Double pOverall = Math.round(Double.parseDouble(arr[37])*1000)/1000.0;
                                   Double iOverall = Math.round(Double.parseDouble(arr[38])*1000)/1000.0;
                                   Double chiOverall = Math.round(Double.parseDouble(arr[36])*1000)/1000.0;
                                   if (pOverall < 0.5 ){
                                       paragraph = doc.createElement("P"); 
                                       paragraph.appendChild(doc.createTextNode("Uebergreifendes Ergebnis fuer die Untergruppen: "+"("+effectMeasure2+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). Es gab einen stastisch signifkanten Unterschieden zwischen den Untergruppen der Studien (Chi2="+chiOverall+"; df="+df1+"; P="+pOverall+"). ")); 
                                       subsection2.appendChild(paragraph);
                                   }
                                   else {
                                       paragraph = doc.createElement("P"); 
                                       paragraph.appendChild(doc.createTextNode("Uebergreifendes Ergebnis fuer die Untergruppen: "+"("+effectMeasure2+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). Es gab keinen signifkanten Unterschieden zwischen den Untergruppen der Studien (Chi2="+chiOverall+"; df="+df1+"; P="+pOverall+"). ")); 
                                       subsection2.appendChild(paragraph);
                                   }
                                   if (iOverall >= 30 && iOverall <= 50) {
                                     paragraph.appendChild(doc.createTextNode("Diese Feststellung wiesen ein moderates Niveau an Heterogenitaet auf. (I2="+iOverall+"%)."));  
                                     subsection2.appendChild(paragraph);
                                    }
                                   else if (iOverall > 50){
                                   paragraph.appendChild(doc.createTextNode("Diese Feststellung wiesen ein hohes Niveau an Heterogenitaetsstufen auf (I2="+iOverall+"%)."));
                                   subsection2.appendChild(paragraph);
                                    }
                               }
                               
                              }
                              zeile = br.readLine();
                         
                      } else {
                          System.out.println("in der else schleife");
                            count2 = 0;
                            while (arr.length > 44 && arr[4].equals("")) {
                              studyname2 = arr[3];
                              System.out.println("studyname 2: "+studyname2);
                              count2++;
                              zeile = br.readLine();
                              if (zeile != null){
                                  arr = zeile.split(";");
                              }
                              else break;
                            }
                 
                                
                                switch (count2) {
                                    case 2:  countstring2 = "zwei"; break;
                                    case 3:  countstring2 = "drei"; break;
                                    case 4:  countstring2 = "vier"; break;
                                    case 5:  countstring2 = "fuenf"; break;
                                    case 6:  countstring2 = "sechs"; break;
                                    case 7:  countstring2 = "sieben"; break;
                                    case 8:  countstring2 = "acht"; break;
                                    case 9:  countstring2 = "neun"; break;    
                                    case 10: countstring2 = "zehn"; break;
                                }
                                switch (effectMeasure2){
                                    case "Risk Ratio": effectMeasure2Short = "RR"; break;
                                    case "Odds Ratio": effectMeasure2Short = "OR"; break;
                                    case "Mean Difference": effectMeasure2Short = "MD"; break;
                                    case "Risk Difference": effectMeasure2Short = "RD"; break;
                                    case "Std. Mean Difference": effectMeasure2Short = "SMD"; break;
                                }

                                paragraph = doc.createElement("P");
                                  if (comparisonname.contains("versus")||comparisonname.contains("Versus")  // contains "versus" & "VERSUS" &Versus! (contains work only for the excact term in the quotation!)
                                        ||comparisonname.contains("VERSUS")){
                                    paragraph2 = doc.createElement("P"); 
                                    
                                    if (ciStart2 < 1 && ciEnd2 > 1){
                                        String lowerCase="";
                                        try
                                        {
                                        lowerCase = comparisonname.toLowerCase();
                                        comparisonname = lowerCase;
                                        }
                                        catch (Exception e)
                                         {
                                           System.out.println("in catch new: "+e.toString());     
                                         }
                                   
                                        if (count2 == 1) {
                                           paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurde nur eine relevante Studie gefunden (n="+total2+") ("+studyname2+"). Es gab keinen signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));    
                                        }
                                        else if (count2 <= 10) {
                                           paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurden "+countstring2+" relevante Studien gefunden (n="+total2+"). Es gab keinen signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                        else {
                                             paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurden "+count2+" relevante Studien gefunden (n="+total2+"). Es gab keinen signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                    }
                                    else {
                                        if (count2 == 1) {
                                           paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurde nur eine relevante Studie gefunden (n="+total2+") ("+studyname2+").Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));    
                                        }
                                        else if (count2 <= 10) {
                                           paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurden "+countstring2+" relevante Studien gefunden (n="+total2+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                        else {
                                             paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurden "+count2+" relevante Studien gefunden (n="+total2+").Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                    }
                                    subsection2.appendChild(paragraph);
                                    paragraph.appendChild(doc.createTextNode(" ("+effectMeasure2Short+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). "));
                                    subsection2.appendChild(paragraph);
                                }
                                else   if (comparisonname.contains("vs")||comparisonname.contains("VS")  // contains "versus" & "VERSUS" &Versus! (contains work only for the excact term in the quotation!)
                                        ||comparisonname.contains("Vs")){
                                    paragraph2 = doc.createElement("P"); 
                                    if (ciStart2 < 1 && ciEnd2 > 1){
                                        String lowerCase="";
                                        try
                                        {
                                        lowerCase = comparisonname.toLowerCase();
                                        comparisonname = lowerCase;
                                        }
                                        catch (Exception e)
                                         {
                                           System.out.println("in catch new: "+e.toString());     
                                         }
                                    if (count2 == 1) {
                                       paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurde nur eine relevante Studie gefunden (n="+total2+") ("+studyname2+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));    
                                    }
                                    else if (count2 <= 10) {
                                       paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurden "+countstring2+" relevante Studien gefunden (n="+total2+"). Es gab keinen signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                    }
                                    else {
                                         paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurden "+count2+" relevante Studien gefunden (n="+total2+"). Es gab keinen signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                    }
                                    }
                                    else {
                                        if (count2 == 1) {
                                           paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurde nur eine relevante Studie gefunden (n="+total2+") ("+studyname2+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));    
                                        }
                                        else if (count2 <= 10) {
                                           paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurden "+countstring2+" relevante Studien gefunden (n="+total2+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        }
                                        else {
                                             paragraph.appendChild(doc.createTextNode("Fuer diesen Outcome wurden "+count2+" relevante Studien gefunden (n="+total2+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        }
                                    }
                                    subsection2.appendChild(paragraph);
                                    paragraph.appendChild(doc.createTextNode(" ("+effectMeasure2Short+" "+effectEst2+" CI "+ciStart2+" bis "+ciEnd2+", Analyse "+analysisnr+"). "));
                                    subsection2.appendChild(paragraph);
                                }
                                else { //Sensitivity Analysis
//                                    if (ciStart2 < 1 && ciEnd2 > 1){
//                                    if (count2 == 1) {
//                                       paragraph.appendChild(doc.createTextNode("For this outcome we only found one relevant trial (n="+total2+") ("+studyname2+"). "));    
//                                    }
//                                    else if (count2 <= 10) {
//                                       paragraph.appendChild(doc.createTextNode("For this outcome we found "+countstring2+" relevant trials (n="+total2+"). "));
//                                    }
//                                    else {
//                                         paragraph.appendChild(doc.createTextNode("For this outcome we found "+count2+" relevant trials (n="+total2+"). "));
//                                    }
//                                    }
//                                    else {
//                                        if (count2 == 1) {
//                                           paragraph.appendChild(doc.createTextNode("For this outcome we only found one relevant trial (n="+total2+") ("+studyname2+"). "));    
//                                        }
//                                        else if (count2 <= 10) {
//                                           paragraph.appendChild(doc.createTextNode("For this outcome we found "+countstring2+" relevant trials (n="+total2+"). "));
//                                        }
//                                        else {
//                                             paragraph.appendChild(doc.createTextNode("For this outcome we found "+count2+" relevant trials (n="+total2+"). "));
//                                        }
//                                    }
//                                    subsection2.appendChild(paragraph);
                                    if (count2 == 1){
                                    paragraph.appendChild(doc.createTextNode(" (1 RCT, n="+total2+", "+effectMeasure2Short+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analyse "+analysisnr+"). "));
                                    subsection2.appendChild(paragraph);
                                    }
                                    else {
                                    paragraph.appendChild(doc.createTextNode(" ("+count2+" RCTs, n="+total2+", "+effectMeasure2Short+" "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analyse "+analysisnr+"). "));
                                    subsection2.appendChild(paragraph);   
                                    }
                                }
                                if (i1 >= 30 && i1 <= 50 && count2 > 1) {
                                     paragraph.appendChild(doc.createTextNode("Der Outcome wies ein moderates Niveau an Heterogenitaet auf (Chi2="+chi1+"; df="+df1+"; P="+p1+"; I2="+i1+"%)."));  
                                }
                                else
                                    if (i1 > 50 && count2 > 1){
                                    paragraph.appendChild(doc.createTextNode("Der Outcome wies ein hohes Niveau an Heterogenitaet auf (Chi2="+chi1+"; df="+df1+"; P="+p1+"; I2="+i1+"%)."));
                                }
                                subsection2.appendChild(paragraph);
                            //    System.out.println (">>Total study count: "+count2); 
                     
                       
                      } subgroup = false;
                  }
                  else
                      if (Integer.parseInt(arr[1])!= 0 && Integer.parseInt(arr[2])!= 0 && noTotals == false){       //Column B and C not equals 0
                          
                          if (!arr[4].equals("")){                  // Column E not empty
                          
                            subsection3 = doc.createElement("SUBSECTION");
                            subsection2.appendChild(subsection3);

                            Element heading3 = doc.createElement("HEADING");
                            heading3.appendChild(doc.createTextNode(arr[0]+"."+arr[1]+"."+arr[2]+" "+ arr[3]));
                            subsection3.appendChild(heading3);
                            Attr attr3 = doc.createAttribute("LEVEL");
                            attr3.setValue("5");
                            heading3.setAttributeNode(attr3);     
                         //   System.out.println("----"+arr[0]+"."+arr[1]+"."+arr[2]+" "+ arr[3]);
                            total = (Integer.parseInt(arr[18]) + Integer.parseInt(arr[22]));
                            effectEst = Math.round(Double.parseDouble(arr[25])*100)/100.0;
                            ciStart = Math.round(Double.parseDouble(arr[27])*100)/100.0;
                            ciEnd = Math.round(Double.parseDouble(arr[28])*100)/100.0;
                            studytype2 = arr[4];
                            analysisnr2 = arr[0]+"."+arr[1];
                            subgroupname = arr[3];
                            effectMeasure = arr[6];
                            p2 = Math.round(Double.parseDouble(arr[31])*1000)/1000.0;
                            i2 = (int)Math.round(Double.parseDouble(arr[32]));
                            chi2 = Math.round(Double.parseDouble(arr[30])*100)/100.0;
                            df2 = Integer.parseInt(arr[39]);
                           
                       //     System.out.println("Total analysis: " +"(n="+total+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0 +") ");
                            zeile = br.readLine();
                          }
                          
                          else {
                            count = 0;
                            while (arr.length > 44 && arr[4].equals("")){
                                studyname = arr[3];
                                count ++;
                                zeile = br.readLine();
                                if (zeile != null) {
                                    arr = zeile.split(";");
                                }
                                else break;
                                
                            }
                           // if (count > 1){
                                switch (count) {
                                    case 2:  countstring = "zwei"; break;
                                    case 3:  countstring = "drei"; break;
                                    case 4:  countstring = "vier"; break;
                                    case 5:  countstring = "fuenf"; break;
                                    case 6:  countstring = "sechs"; break;
                                    case 7:  countstring = "sieben"; break;
                                    case 8:  countstring = "acht"; break;
                                    case 9:  countstring = "neun"; break; 
                                    case 10: countstring = "zehn"; break;
                                }
                                switch (effectMeasure){
                                    case "Risk Ratio": effectMeasureShort = "RR"; break;
                                    case "Odds Ratio": effectMeasureShort = "OR"; break;
                                    case "Mean Difference": effectMeasureShort = "MD"; break;
                                    case "Risk Difference": effectMeasureShort = "RD"; break;
                                    case "Std. Mean Difference": effectMeasureShort = "SMD"; break;
                                }
                                if (comparisonname.contains("versus")){
                                    paragraph2 = doc.createElement("P"); 
                                    if (ciStart < 1 && ciEnd > 1){
                                        if (count == 1) {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurde nur eine relevante Studie gefunden (n="+total+") ("+studyname+"). Es gab keinen signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));    
                                        }
                                        else if (count <= 10) {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurden "+countstring+" relevante Studien gefunden (n="+total+"). Es gab keinen signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                        else {
                                             paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurden "+count+" relevante Studien gefunden (n="+total+"). Es gab keinen signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        } 
                                    }
                                    else {
                                         if (count == 1) {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurde nur eine relevante Studie gefunden (n="+total+") ("+studyname+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));    
                                        }
                                        else if (count <= 10) {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurden "+countstring+" relevante Studien (n="+total+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        }
                                        else {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurden "+count+" relevante Studien (n="+total+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("versus"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("versus")+7)).toLowerCase()));
                                        } 
                                    }
                                    subsection3.appendChild(paragraph2);
                                    paragraph2.appendChild(doc.createTextNode(" ("+effectMeasureShort+" "+effectEst+" CI "+ciStart+" bis "+ciEnd+", Analyse "+analysisnr+"). "));     
                                    subsection3.appendChild(paragraph2);
                                }
                                else if (comparisonname.contains("vs")){ // contains "vs"
                                    paragraph2 = doc.createElement("P"); 
                                    if (ciStart < 1 && ciEnd > 1){
                                        if (count == 1) {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurde nur eine relevante Studie gefunden (n="+total+") ("+studyname+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));    
                                        }
                                        else if (count <= 10) {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurden "+countstring+" relevante Studien gefunden (n="+total+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        }
                                        else {
                                             paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurden "+count+" relevante Studien gefunden (n="+total+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        } 
                                    }
                                    else {
                                         if (count == 1) {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurde nur eine relevante Studie gefunden (n="+total+") ("+studyname+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));    
                                        }
                                        else if (count <= 10) {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurden "+countstring+" relevante Studien gefunden (n="+total+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        }
                                        else {
                                           paragraph2.appendChild(doc.createTextNode("In dieser Untergruppe wurden "+count+" relevante Studien gefunden (n="+total+"). Es gab einen statistisch signifkanten Unterschied zwischen "+(comparisonname.substring(0, comparisonname.indexOf("vs"))).toLowerCase()+"und "+(comparisonname.substring(comparisonname.indexOf("vs")+3)).toLowerCase()));
                                        } 
                                    }
                                    subsection3.appendChild(paragraph2);
                                    paragraph2.appendChild(doc.createTextNode(" ("+effectMeasureShort+" "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analyse "+analysisnr+"). "));     
                                    subsection3.appendChild(paragraph2);
                                } 
                                else { // Sensitivity Analysis
                                    paragraph2 = doc.createElement("P"); 
//                                    if (ciStart < 1 && ciEnd > 1){
//                                        if (count == 1) {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we only found one relevant trial (n="+total+") ("+studyname+"). "));    
//                                        }
//                                        else if (count <= 10) {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+countstring+" relevant trials (n="+total+"). "));
//                                        }
//                                        else {
//                                             paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+count+" relevant trials (n="+total+"). "));
//                                        } 
//                                    }
//                                    else {
//                                         if (count == 1) {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we only found one relevant trial (n="+total+") ("+studyname+"). "));    
//                                        }
//                                        else if (count <= 10) {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+countstring+" relevant trials (n="+total+"). "));
//                                        }
//                                        else {
//                                           paragraph2.appendChild(doc.createTextNode("In this subgroup we found "+count+" relevant trials (n="+total+"). "));
//                                        } 
//                                    }
//                                    subsection3.appendChild(paragraph2);
                                    if (count == 1){
                                        paragraph2.appendChild(doc.createTextNode(" (1 RCT, n="+total+", "+effectMeasureShort+" "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analyse "+analysisnr+"). "));     
                                        subsection3.appendChild(paragraph2);
                                    }
                                    else {
                                        paragraph2.appendChild(doc.createTextNode(" ("+count+" RCTs, n="+total+", "+effectMeasureShort+" "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analyse "+analysisnr+"). "));     
                                        subsection3.appendChild(paragraph2);
                                    }
                                }
                                if (i2 >= 30 && i2 <= 50 && count > 1) {
                                     paragraph2.appendChild(doc.createTextNode("Diese Untergruppe wies ein moderates Niveau an Heterogenitaet (Chi2="+chi2+"; df="+df2+"; P="+p2+"; I2="+i2+"%)."));
                                    subsection3.appendChild(paragraph2);
                                }
                                else
                                    if (i2 > 50 && count > 1){
                                    paragraph2.appendChild(doc.createTextNode("Diese Untergruppe wies ein moderates Niveau an Heterogenitaet (Chi2="+chi2+"; df="+df2+"; P="+p2+"; I2="+i2+"%)."));
                                    subsection3.appendChild(paragraph2);
                                }
                            
                          //      System.out.println (">>Total study count: "+count); 
                      
                            
                          }
                          subgroup = true;   
                      }
                      else if (noTotals == true){
                          paragraph = doc.createElement("P");
                          paragraph.appendChild(doc.createTextNode("*--- Fehlende Daten in dieser Subsection. Die Daten wurden nicht zusammengerechnet ---*"));
                          subsection2.appendChild(paragraph);
                          zeile = br.readLine();
                      }
            }
            
            if (subgroup == false) {
                paragraph3 = doc.createElement("P");
                subsection2.appendChild(paragraph3);
                marker = doc.createElement("MARKER");
                marker.appendChild(doc.createTextNode("*--- Ende des von HAL generierten Textes "+formatter.format(currentTime)+" ---*"));
                paragraph3.appendChild(marker);
            }
            else if(subgroup == true){
                paragraph3 = doc.createElement("P");
                subsection3.appendChild(paragraph3);
                marker = doc.createElement("MARKER");
                marker.appendChild(doc.createTextNode("*--- Ende des von HAL generierten Textes "+formatter.format(currentTime)+" ---*"));
                paragraph3.appendChild(marker);
            }
            
           // TRANSFORM FROM DOM TO XML AGAIN
           
           
                        
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
            try
            {
                System.out.println("FilePath: "+filepath);
            cleaner.deleteSpecialChars(filepath);
            }
            catch(Exception e)
            {
                System.out.println("FEHLER!");
                e.printStackTrace();
            }
            
            
            System.out.println("Done");
            
           
            
            
            br.close();
            fr.close();
            return true;
       
        }
            
        catch (Exception e){
            System.out.println(e);
            return false;
        }
        }
     
        
        else
        {
        return false;
        }
        
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
            e.printStackTrace();
            return null;
        }
    
    }

public void deleteSpecialChars(String file) throws IOException
  {   
      
      Reader fr = new InputStreamReader(new FileInputStream(file), "UTF-8");
      BufferedReader br = new BufferedReader(fr);
       
      Writer fw = new OutputStreamWriter(new FileOutputStream(file+"newFile.rm5"), "ISO-8859-1");
      String line="";
      
      
      while((line=br.readLine())!= null)
      {
          
       //  System.out.println("das ist line: "+line);
          if(line.contains("Ã¼"))
          {
              line = line.replace("Ã¼", "ü");
              System.out.println("dinna if ue: "+line);  
          }
          if(line.contains("¤"))
           {
               line = line.replace("¤", "ä");
               System.out.println("dinna if ae: "+line);  
           }
          if(line.contains("¶"))
           {
               line = line.replace("¶", "ö");
               System.out.println("dinna if: oe "+line);  
           }
          if(line.contains("Ã&#156;"))
          {
              line = line.replace("Ã&#156;", "Ü");
              System.out.println("dinna if Ue: "+line);  
          }          
           if(line.contains("Ã&#159;"))
           {
               line = line.replace("Ã&#159;", "ß");
                 
           }
         if(line.contains("Ã&#132;"))
           {
               line = line.replace("Ã&#132;", "Ä");
                 
           }
         if(line.contains("Ã&#150;"))
           {
               line = line.replace("Ã&#150;", "Ö");
                
           }
           if(line.contains("Â¢"))
           {
               line = line.replace("Â¢", "¢");
                 
           }        
                                 
            if(line.contains("Â£"))
           {
               line = line.replace("Â£;", "£");
                 
           }          
          if(line.contains("Â¥"))
           {
               line = line.replace("Â¥", "¥");
                 
           }
          if(line.contains("Ã&#8800;"))
           {
               line = line.replace("Ã&#8800;", "≠");
                 
           }
          if(line.contains("Â±"))
           {
               line = line.replace("Â±", "±");
                 
           }
          if(line.contains("Ã&#8706;"))
           {
               line = line.replace("Ã&#8706;", "∂");
                 
           }
           if(line.contains("Ã&#8710;"))
           {
               line = line.replace("Ã&#8710;", "∆");
                 
           }
           if(line.contains("Ã&#8719;"))
           {
               line = line.replace("Ã&#8719;", "∏");
                 
           }          
           if(line.contains("Ã&#8721;"))
           {
               line = line.replace("Ã&#8721;", "∑");
                 
           }
          if(line.contains("Ã&#8730;"))
           {
               line = line.replace("Ã&#8730;", "√");
                 
           }
          if(line.contains("Ã&#8776;"))
           {
               line = line.replace("Ã&#8776;", "≈");
                 
           }
           if(line.contains("Ã&#8804;"))
           {
               line = line.replace("Ã&#8804;", "≤");
                 
           }
           if(line.contains("Ã&#8805;"))
           {
               line = line.replace("Ã&#8805;", "≥");
                 
           }
          if(line.contains("Ã&#8593;"))
           {
               line = line.replace("Ã&#8593;", "↑");
                 
           }
           if(line.contains("Ã&#8594;"))
           {
               line = line.replace("Ã&#8594;", "→");
                 
           }
           if(line.contains("Ã&#8595;"))
           {
               line = line.replace("Ã&#8595;", "↓");
                 
           }
           if(line.contains("Ã&#8592;"))
           {
               line = line.replace("Ã&#8592;", "←");
                 
           }
           if(line.contains("Ã&#8596;"))
           {
               line = line.replace("Ã&#8596;", "↔");
                 
           }
           if(line.contains("Ã&#129;"))
           {
               line = line.replace("Ã&#129;", "Á");
                 
           }
          if(line.contains("Ã&#128;"))
           {
               line = line.replace("Ã&#128;", "À");
                 
           }
          if(line.contains("Ã&#130;"))
           {
               line = line.replace("Ã&#130;", "Â");
                 
           }
          if(line.contains("Ã&#258;"))
           {
               line = line.replace("Ã&#258;;", "Ă");
                 
           }
          if(line.contains("Ã&#256;"))
           {
               line = line.replace("Ã&#256;", "Ā");
                 
           }
           if(line.contains("Ã&#131;"))
           {
               line = line.replace("Ã&#131;", "Ã");
                 
           }
           if(line.contains("Ã&#133;"))
           {
               line = line.replace("Ã&#133;", "Å");
                 
           }
           if(line.contains("Ã&#260;"))
           {
               line = line.replace("Ã&#260;", "Ą");
                 
           }
           if(line.contains("Ã&#134;"))
           {
               line = line.replace("Ã&#134;", "Æ");
                 
           }
           if(line.contains("Ã&#262;"))
           {
               line = line.replace("Ã&#262;", "Ć");
                 
           }
           if(line.contains("Ã&#266;"))
           {
               line = line.replace("Ã&#266;", "Ċ");
                 
           }
          if(line.contains("Ã&#264;"))
           {
               line = line.replace("Ã&#264;", "Ĉ");
                 
           }
           if(line.contains("Ã&#268;"))
           {
               line = line.replace("Ã&#268;", "Č");
                 
           }
          if(line.contains("Ã&#135;"))
           {
               line = line.replace("Ã&#135;", "Ç");
                 
           }
          if(line.contains("Ã&#270;"))
           {
               line = line.replace("Ã&#270;", "Ď");
                 
           }
          if(line.contains("Ã&#144;"))
           {
               line = line.replace("Ã&#144;", "Ð");
                 
           }
           if(line.contains("Ã&#137;"))
           {
               line = line.replace("Ã&#137;", "É");
                 
           }
          if(line.contains("Ã&#136;"))
           {
               line = line.replace("Ã&#136;", "È");
                 
           }
          if(line.contains("Ã&#278;"))
           {
               line = line.replace("Ã&#278;", "Ė");
                 
           }
          if(line.contains("Ã&#138;"))
           {
               line = line.replace("Ã&#138;", "Ê");
                 
           }
           if(line.contains("Ã&#139;"))
           {
               line = line.replace("Ã&#139;", "Ë");
                 
           }
          if(line.contains("Ã&#282;"))
           {
               line = line.replace("Ã&#282;", "Ě");
                 
           }
          if(line.contains("Ã&#276;"))
           {
               line = line.replace("Ã&#276;", "Ĕ");
                 
           }
          if(line.contains("Ã&#274;"))
           {
               line = line.replace("Ã&#274;", "Ē");
                 
           }
          if(line.contains("Ã&#280;"))
           {
               line = line.replace("Ã&#280;", "Ę");
                 
           }
          if(line.contains("Ã&#288;"))
           {
               line = line.replace("Ã&#288;", "Ġ");
                 
           }
          if(line.contains("Ã&#284;"))
           {
               line = line.replace("Ã&#284;", "Ĝ");
                 
           }
          if(line.contains("Ã&#290;"))
           {
               line = line.replace("Ã&#290;", "Ģ");
                 
           }
          if(line.contains("Ã&#292;"))
           {
               line = line.replace("Ã&#292;", "Ĥ");
                 
           }
          if(line.contains("Ã&#141;"))
           {
               line = line.replace("Ã&#141;", "Í");
                 
           }
          if(line.contains("Ã&#140;"))
           {
               line = line.replace("Ã&#140;", "Ì");
                 
           }if(line.contains("Ã&#142;"))
           {
               line = line.replace("Ã&#142;", "Î");
                 
           }if(line.contains("Ã&#143;"))
           {
               line = line.replace("Ã&#143;;", "Ï");
                 
           }
           if(line.contains("Ã&#300;"))
           {
               line = line.replace("Ã&#300;", "Ĭ");
                 
           }
           if(line.contains("Ã&#298;"))
           {
               line = line.replace("Ã&#298;", "Ī");
                 
           }
           if(line.contains("Ã&#296;"))
           {
               line = line.replace("Ã&#296;", "Ĩ");
                 
           }
           if(line.contains("Ã&#302;"))
           {
               line = line.replace("Ã&#302;", "Į");
                 
           }
           if(line.contains("Ã&#145;"))
           {
               line = line.replace("Ã&#145;", "Ñ");
                 
           }
           if(line.contains("Ã&#147;"))
           {
               line = line.replace("Ã&#147;", "Ó");
                 
           }
           if(line.contains("Ã&#146;"))
           {
               line = line.replace("Ã&#146;", "Ò");
                 
           }
           if(line.contains("Ã&#148;"))
           {
               line = line.replace("Ã&#148;", "Ô");
                 
           }
           if(line.contains("Ã&#149;"))
           {
               line = line.replace("Ã&#149;", "Õ");
                 
           }
           if(line.contains("Ã&#152;"))
           {
               line = line.replace("Ã&#152;", "Ø");
                 
           }
           if(line.contains("Ã&#158;"))
           {
               line = line.replace("Ã&#158;", "Þ");
                 
           }
           if(line.contains("Ã&#154;"))
           {
               line = line.replace("Ã&#154;", "Ú");
                 
           }
           if(line.contains("Ã&#153;"))
           {
               line = line.replace("Ã&#153;", "Ù");
                 
           }
           if(line.contains("Ã&#155;"))
           {
               line = line.replace("Ã&#155;", "Û");
                 
           }
           if(line.contains("Ã&#157;"))
           {
               line = line.replace("Ã&#157;", "Ý");
                 
           }
           if(line.contains("Ã&#376;"))
           {
               line = line.replace("Ã&#376;", "Ÿ");
                 
           }
           if(line.contains("Ã&#916;"))
           {
               line = line.replace("Ã&#916;", "Δ");
                 
           }
           if(line.contains("Ã&#928;"))
           {
               line = line.replace("Ã&#928;", "Π");
                 
           }
        
      line = line.replace("Â", "");
      line = line.replace("ƒ", "");
      line = line.replace("Ã", "");
      line = line.replace("¼", "");
      line = line.replace("Â,", "");
      line = line.replace("ƒ,", "");
      line = line.replace("Ã,", "");
      line = line.replace("¼,", "");
      line = line.replace(",Â", "");
      line = line.replace(",ƒ", "");
      line = line.replace(",Ã", "");
      line = line.replace(",¼", "");      
      line = line.replace("Ã‚Â", "");
      line = line.replace("‚±","±");         
      
      fw.append(line);
      
      }
      fw.close();
      br.close();
      fr.close();
      
      
     File file2 = new File(file);        
     boolean success = file2.delete();        
     System.out.println(success);        
     file2 = new File(file+"newFile.rm5");        
     file2.renameTo(new File(file));          
        
   
  }

}

