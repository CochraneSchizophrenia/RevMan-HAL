/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package revmanhalcochranemode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
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

/**
 *
 * @author mcasp
 */
public class Complex2_Studies {
    public static boolean main(String file1, String file2) {
        
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
        String analysisModel = "";
        String analysisModel2 = "";
        double p1 = 0.0;
        double p2 = 0.0;
        int i1 = 0;
        int i2 = 0;
        double chi1 = 0.0;
        double chi2 = 0.0;
        int df1 = 0;
        int df2 = 0;
    
       
        String [] arr = new String [46];
        
        
        try {
            String filepath = file1;
            Document doc = getDocument(filepath);
            String datafile = file2;
            Complex1 cleaner = new Complex1(null);
            
            Node intervention = doc.getElementsByTagName("INTERVENTION_EFFECTS").item(0);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date();  
            
            paragraph3 = doc.createElement("P");
            intervention.appendChild(paragraph3);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ Start of HAL generated text "+formatter.format(currentTime)+" ------*     PLEASE NOTE: if an outcome is in 'Other Data' tables this information will not be included in automatically generated text. Numbering may, therefore, not be consecutive."));
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
              //    System.out.println("COMPARISON "+arr[0]+": "+arr[3]);
                  zeile = br.readLine();
              }
              else
                  if (Integer.parseInt(arr[1])!= 0 && Integer.parseInt(arr[2])== 0 ) {  // Column B not equals 0 but Column C equals 0?
                        
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
                              analysisModel2 = arr[7];
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
                       //         System.out.println("--"+arr[0]+"."+arr[1]+" "+arr[3]);
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
                            count2 = 0;
                            while (arr.length > 44 && arr[4].equals("")) {
                              studyname2 = arr[3];
                              paragraph = doc.createElement("P");                        
                              paragraph.appendChild(doc.createTextNode(arr[3]+" (n="+(Integer.parseInt(arr[18])+Integer.parseInt(arr[22]))+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0 +") ")); 
                              subsection2.appendChild(paragraph);
                       //       System.out.println("*TEXT*"+arr[3]+" (n="+(Integer.parseInt(arr[18])+Integer.parseInt(arr[22]))+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0+") ");
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
                                paragraph = doc.createElement("P");
                                if (comparisonname.contains("versus")){
                                    
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
                                paragraph.appendChild(doc.createTextNode(" ("+effectMeasure2Short+", "+analysisModel2+", "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). "));
                                subsection2.appendChild(paragraph);
                                }
                                else if (comparisonname.contains("vs")){
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
                                paragraph.appendChild(doc.createTextNode(" ("+effectMeasure2Short+", "+analysisModel2+", "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+"). "));
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
                         //       System.out.println (">>Total study count: "+count2); 
                     
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
                        //    System.out.println("----"+arr[0]+"."+arr[1]+"."+arr[2]+" "+ arr[3]);
                            total = (Integer.parseInt(arr[18]) + Integer.parseInt(arr[22]));
                            effectEst = Math.round(Double.parseDouble(arr[25])*100)/100.0;
                            ciStart = Math.round(Double.parseDouble(arr[27])*100)/100.0;
                            ciEnd = Math.round(Double.parseDouble(arr[28])*100)/100.0;
                            studytype2 = arr[4];
                            analysisnr2 = arr[0]+"."+arr[1];
                            subgroupname = arr[3];
                            effectMeasure = arr[6];
                            analysisModel = arr[7];
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
                                paragraph2 = doc.createElement("P");                        
                                studyname = arr[3];
                                paragraph2.appendChild(doc.createTextNode(arr[3]+" (n="+(Integer.parseInt(arr[18])+Integer.parseInt(arr[22]))+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0 +") "));
                                subsection3.appendChild(paragraph2);
                          //      System.out.println("*"+arr[3]+" (n="+(Integer.parseInt(arr[18])+Integer.parseInt(arr[22]))+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0+") ");
                                count ++;
                                zeile = br.readLine();
                                if (zeile != null) {
                                    arr = zeile.split(";");
                                }
                                else break;
                                
                            }
                      
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
                                paragraph2 = doc.createElement("P");
                                if (comparisonname.contains("versus")){
                                if (ciStart < 1 && ciEnd > 1){
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
                                paragraph2.appendChild(doc.createTextNode(" ("+effectMeasureShort+", "+analysisModel+", "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analysis "+analysisnr+"). "));     
                                subsection3.appendChild(paragraph2);
                                }
                                else if (comparisonname.contains("vs")){
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
                                paragraph2.appendChild(doc.createTextNode(" ("+effectMeasureShort+", "+analysisModel+", "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analysis "+analysisnr+"). "));     
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
                            
                           //     System.out.println (">>Total study count: "+count); 
                      
                            
                          } subgroup = true;
                              
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

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
            cleaner.deleteSpecialChars(filepath);
 
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
