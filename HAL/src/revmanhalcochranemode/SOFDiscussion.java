/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package revmanhalcochranemode;

/**
 *
 * @author mcasp
 */

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NamedNodeMap;

public class SOFDiscussion 
{
    
    private String language = "";
    private boolean twoColumns=false;
    
    private Element paragraph2;
    private Element paragraph3;
    private Element paragraph4;
    private Element paragraph5;
    private Element paragraph6;
    private int counterRow=0;
    
    
    public SOFDiscussion (String lang)
            {
            language = lang;
            }
    
    public boolean Main (String file) 
    {
        Element paragraph = null;
        Element marker = null;
        Element subsection = null;
        Element heading = null;
        Complex1 cleaner = new Complex1(null); 
        
        
        

        if(language.equalsIgnoreCase("English"))
        {
        try {
           
            String filepath =file;
            Document doc = getDocument(filepath);
            
           // int anzahl = doc.getElementsByTagName("EXTENSIONS").getLength();
            NodeList nl = doc.getElementsByTagName("SOF_TABLE");
            
            Node sof_results = doc.getElementsByTagName("SUMMARY_OF_RESULTS").item(0);
            
            //Remove old Summary of main results
            removeAllChildren(sof_results);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date();  
            
            paragraph = doc.createElement("P");
            sof_results.appendChild(paragraph);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ Start of HAL generated text "+formatter.format(currentTime)+" ------*     PLEASE NOTE: Solely from SoF tables."));
            paragraph.appendChild(marker);
            
            for (int i = 0; i < nl.getLength(); i++)
            {
                counterRow=0;
                Element comp =  (Element)doc.getElementsByTagName("SOF_TABLE").item(i);
                String comparison = comp.getElementsByTagName("TITLE").item(0).getTextContent().trim();
                String footnote = comp.getElementsByTagName("FOOTNOTES").item(0).getTextContent().trim();
                
                System.out.println("footnote: "+(i+1)+": "+footnote);
                
                String [] arFootnotes=footnote.split(".\\d ");
                arFootnotes[0]=arFootnotes[0].substring(2,arFootnotes[0].length());
                
             //   for(int t=1;t<arFootnotes.length;t++)
              //  {
              //      arFootnotes[t]=t+1+" "+arFootnotes[t];  
              //      System.out.println("arFootnotes: "+arFootnotes[t]);
              //  }
                
        
                
                subsection = doc.createElement("SUBSECTION");
                sof_results.appendChild(subsection);
 
                  heading = doc.createElement ("HEADING");
                  heading.appendChild(doc.createTextNode("COMPARISON "+(i+1)+": "+comparison));
                  subsection.appendChild(heading);
                  Attr attr = doc.createAttribute("LEVEL");
                  attr.setValue("3");
                  heading.setAttributeNode(attr);
                
                NodeList nl1 = comp.getElementsByTagName("TR"); //get a NodeList with the content of the table!
                
                try
                {
                Element tr1 = (Element) comp.getElementsByTagName("TR").item(i);
                tr1.getChildNodes();
                Element th = (Element)tr1.getElementsByTagName("TH").item(0);
                String attributeTH = th.getAttribute("ROWSPAN");
                System.out.println("attribute th: "+attributeTH);
                }
                catch (Exception e)
                {
                    
                }
               
                
                paragraph = doc.createElement("P");
                paragraph.appendChild(doc.createTextNode("Please see "));
                subsection.appendChild(paragraph);
                Element link = doc.createElement("LINK");
                link.appendChild(doc.createTextNode("Summary of findings table "+(i+1)+" for details and reasons for quality grading."));
                paragraph.appendChild(link);
                attr = doc.createAttribute("REF");
                
                if (i < 9)
                {
                     attr.setValue("SOF-0"+(i+1));
                }
                else 
                {
                     attr.setValue("SOF-"+(i+1));
                }
                link.setAttributeNode(attr);
                Attr attr1 = doc.createAttribute("TYPE");
                attr1.setValue("SOF");
                link.setAttributeNode(attr1);
                paragraph.appendChild((doc.createTextNode(".")));
                subsection.appendChild(paragraph);
                
                
                 Element trStart = (Element) comp.getElementsByTagName("TR").item(1);
                 boolean firstTime=true;
                  String stColumnOne="";
                 String stColumnTwo="";
                 String stColumnThree="";
                 String stColumnFour="";
                 String stColumnFive="";
                 
                 int counter=5;
                 
                 String comparison6 = trStart.getTextContent().toString().substring(trStart.getTextContent().toString().indexOf("Comparison:")
                         ,trStart.getTextContent().toString().length());
                 
                 String[]arRelevantText = new String[18];
                 
                 comparison6= comparison6.replaceAll("Comparison: ", "");
                 arRelevantText[4]=comparison6; 
                 System.out.println("trStart: "+comparison6);
                 
                
                for (int j = 5; j < nl1.getLength(); j++) 
                {         
                    
                 stColumnOne="";
                 stColumnTwo="";
                 stColumnThree="";
                 stColumnFour="";
                 stColumnFive="";
                    
                    Element tr2 = (Element) comp.getElementsByTagName("TR").item(j);                    
                    
                    
                    NodeList nl2 = comp.getElementsByTagName("TD");
                    String attributeTD1 ="";
                    String attributeTD2 ="";
                    Element tdTest = null;
                    boolean justThreeColumns = false;
                    
                    for(int j2=0; j2< nl2.getLength();j2++)
                    {
                        tdTest = (Element)tr2.getElementsByTagName("TD").item(j2);
                        
                       try
                       {
                            attributeTD1 = tdTest.getAttribute("ROWSPAN");
                            attributeTD2 = tdTest.getAttribute("COLSPAN");                            
                            System.out.println("this is attribute tdTest: "+attributeTD1+" tdTest2:"+attributeTD2);
                            
                            if(attributeTD1.equals("5")||attributeTD2.equals("5"))
                            {
                                justThreeColumns = true;
                                System.out.println("in if 5 dinna");
                                break;
                                
                            }
                           
                       }
                       catch(Exception e)
                       {
                           
                       }                     
                        
                        
                    }
                                        
                    
                    Element td0 = (Element)tr2.getElementsByTagName("TD").item(0);
                    
                    
                    String stColumnZero = td0.getElementsByTagName("P").item(0).getTextContent().trim();
                     System.out.println("column 0: "+stColumnZero);
                    
                    
                //    System.out.println("this is outcome: "+stItemZero);
                    
                    if (stColumnZero.startsWith("*The basis for the")) 
                    {
                        break;
                    }
                    else 
                    { 
                        String [] arColumnOne = new String[10];
                        
                        
                         if((Element)tr2.getElementsByTagName("TD").item(1)!=null)
                         {
                                Element td1 = (Element)tr2.getElementsByTagName("TD").item(1);                                               
                                arColumnOne = td1.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                                System.out.println("column 1: "+arColumnOne[0]);
                               
                                try {
                                 stColumnOne = arColumnOne[0] + " " + arColumnOne[1];

                             } catch (Exception e) {
                                 stColumnOne = arColumnOne[0];
                             }

                             
                         }
                        
                         String [] arColumnTwo = new String[2];   
                         
                            if((Element)tr2.getElementsByTagName("TD").item(2)!=null)
                            {
                                Element td2 = (Element)tr2.getElementsByTagName("TD").item(2);                                               
                                arColumnTwo = td2.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                                System.out.println("column 2: "+arColumnTwo[0]);
                                
                                
                                try {
                                  stColumnTwo=arColumnTwo[0]+" "+arColumnTwo[1];

                             } catch (Exception e) {
                                  stColumnTwo=arColumnTwo[0];
                             }
                               
                            }
                        
                        String [] arColumnThree = new String [2];                         
                        Element td3;    
                        
                        if((Element)tr2.getElementsByTagName("TD").item(3)!=null)
                        {
                            td3 = (Element)tr2.getElementsByTagName("TD").item(3);                                               
                            arColumnThree = td3.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                            System.out.println("column 3: "+arColumnThree[0]);
                            
                            
                            
                                try {
                                  stColumnThree=arColumnThree[0]+" "+arColumnThree[1];

                             } catch (Exception e) {
                                  stColumnThree=arColumnThree[0];
                             }

                            
                           
                        }
                        
                        Element td4 = null;
                        String [] arColumnFour = new String [2];
                        
                        if ((Element)tr2.getElementsByTagName("TD").item(4)!=null)
                        {
                             td4 = (Element)tr2.getElementsByTagName("TD").item(4);                                               
                            arColumnFour = td4.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                            System.out.println("column 4: "+arColumnFour[0]);                           
                            stColumnFour=arColumnFour[0];

                            
                        }
                          
                        Element td5 = null;   
                        String [] arColumnFive = new String [3];
                       
                        if ((Element)tr2.getElementsByTagName("TD").item(5)!=null)
                        {
                             td5 = (Element)tr2.getElementsByTagName("TD").item(5);                                        
                             arColumnFive = td5.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                             System.out.println("column 5: "+arColumnFive[0]);
                              stColumnFive=arColumnFive[0];                           
                              
                        }  
                        
                        paragraph = doc.createElement("P"); 
                   
                        boolean checkIfNull=false;
                        boolean checkIfNull2=false;
                        
                        if(arColumnThree[0]==null)
                        {
                            System.out.println("in check if null dinna");
                            checkIfNull=true;
                        }
                        if(stColumnZero==null)
                        {
                            System.out.println("in check if null dinna");
                            checkIfNull2=true;
                        }
                    /*
                        if (checkIfNull == false) 
                        {
                            if (arColumnThree[0].equals("Not estimable")) 
                            {
                               // System.out.println("For '" + stColumnZero + "' we did not find any study that reported this outcome in any usable form.");
                                paragraph.appendChild(doc.createTextNode("For '" + stColumnZero + "' we did not find any study that reported this outcome in any usable form."));
                                subsection.appendChild(paragraph);
                            } 
                            else
                            {
                                 paragraph.appendChild(doc.createTextNode("Outcome: "+ stColumnZero +" "));
                                 subsection.appendChild(paragraph);  
                            }
                        }
                       */ 
                        
                        if((Element)tr2.getElementsByTagName("TD").item(1)==null)
                        {
                            twoColumns = false;
                        }
                        else
                        {
                            twoColumns = true;
                        }
                        
                    }
                    
                    if(firstTime==true)
                    {
                    arRelevantText[0]=stColumnZero;
                    arRelevantText[1]=stColumnThree;
                    arRelevantText[2]=stColumnTwo;
                    arRelevantText[3]=stColumnOne;
                    arRelevantText[17]=stColumnFour;
                    firstTime=false;
                    }
                    else if(firstTime==false&&stColumnOne.isEmpty())
                    {
                        arRelevantText[counter]=stColumnZero;
                        counter=counter+1;
                        
                    }
                    else if(firstTime==false&&stColumnTwo.isEmpty())
                    {
                        arRelevantText[counter]=stColumnZero;
                        arRelevantText[counter+1]=stColumnOne;
                        counter=counter+2;
                    }
                    
                    for(int a=0;a<arRelevantText.length;a++)
                    {
                    System.out.println("array text: "+a+" text "+arRelevantText[a]);
                    }
                    
                    Element testNextRow = (Element) comp.getElementsByTagName("TR").item(j+1);  
                    String stContentNextRow = testNextRow.getTextContent().toString();
                    
                    if(stContentNextRow.contains(":"))
                    {
                    firstTime = true;
                    counterRow++;
                    counter = 5;
                    
                    paragraph.appendChild(doc.createTextNode(counterRow+". " + arRelevantText[0]));
                    subsection.appendChild(paragraph);
                    
                    paragraph2 = doc.createElement("P");
                    paragraph3 = doc.createElement("P");
                    
                    if(arRelevantText[5]==null)
                    {
                       try
                       {
                             paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                       +arRelevantText[1].substring(arRelevantText[1].indexOf("("),arRelevantText[1].length())
                        +" (with a total of "+arRelevantText[1].substring(0,arRelevantText[1].indexOf("("))+" participants)."
                            +"Pooled, these found the relative effect to be"+ arRelevantText[2].substring(arRelevantText[2].indexOf("RR")+2,arRelevantText[2].indexOf("(")-1)+
                            " (RR, CI "+arRelevantText[2].substring(arRelevantText[2].indexOf("(")+1,arRelevantText[2].indexOf(")"))+"). "
                            + "Put another way,if the risk of this outcome was "+arRelevantText[3].substring(0,arRelevantText[3].length()-1)+" in the "+comparison6+ " group."));
                           
                       }
                       catch (Exception e)                               
                       {
                             arRelevantText[1]="(empty bla bla)"; 
                             arRelevantText[2]="RR bla (empty bla bla)"; 
                             arRelevantText[3]="(empty bla bla)"; 
                             
                             paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                       +arRelevantText[1].substring(arRelevantText[1].indexOf("("),arRelevantText[1].length())
                        +" (with a total of "+arRelevantText[1].substring(0,arRelevantText[1].indexOf("("))+" participants)."
                            +"Pooled, these found the relative effect to be"+ arRelevantText[2].substring(arRelevantText[2].indexOf("RR")+2,arRelevantText[2].indexOf("(")-1)+
                            " (RR, CI "+arRelevantText[2].substring(arRelevantText[2].indexOf("(")+1,arRelevantText[2].indexOf(")"))+"). "
                            + "Put another way,if the risk of this outcome was "+arRelevantText[3].substring(0,arRelevantText[3].length()-1)+" in the "+comparison6+ " group."));
                               
                       }
                       
                     
                    }
                    else if(arRelevantText[7]==null)
                    {
                        paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                       +arRelevantText[1].substring(arRelevantText[1].indexOf("("),arRelevantText[1].length())
                        +" (with a total of "+arRelevantText[1].substring(0,arRelevantText[1].indexOf("("))+" participants)."
                            +"Pooled, these found the relative effect to be"+ arRelevantText[2].substring(arRelevantText[2].indexOf("RR")+2,arRelevantText[2].indexOf("(")-1)+
                            " (RR, CI "+arRelevantText[2].substring(arRelevantText[2].indexOf("(")+1,arRelevantText[2].indexOf(")"))+"). "
                            + "Put another way,if the risk of this outcome was "+arRelevantText[3].substring(0,arRelevantText[3].length()-1)+" in the "+comparison6+ " group ("+arRelevantText[5]+") then "+arRelevantText[6].substring(0,arRelevantText[6].indexOf("("))+
                            " people the intervention group would have this outcome (although it could be anything from "+arRelevantText[6].substring(arRelevantText[6].indexOf("(")+1,arRelevantText[6].indexOf(")"))+" because of the imprecision in the estimate)."));
                        
                    }
                    else if(arRelevantText[10]==null)
                    {
                        paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                       +arRelevantText[1].substring(arRelevantText[1].indexOf("("),arRelevantText[1].length())
                        +" (with a total of "+arRelevantText[1].substring(0,arRelevantText[1].indexOf("("))+" participants)."
                            +"Pooled, these found the relative effect to be"+ arRelevantText[2].substring(arRelevantText[2].indexOf("RR")+2,arRelevantText[2].indexOf("(")-1)+
                            " (RR, CI "+arRelevantText[2].substring(arRelevantText[2].indexOf("(")+1,arRelevantText[2].indexOf(")"))+"). "
                            + "Put another way,if the risk of this outcome was "+arRelevantText[3].substring(0,arRelevantText[3].length()-1)+" in the "+comparison6+ " group ("+arRelevantText[5]+") then "+arRelevantText[6].substring(0,arRelevantText[6].indexOf("("))+
                            " people the intervention group would have this outcome (although it could be anything from "+arRelevantText[6].substring(arRelevantText[6].indexOf("(")+1,arRelevantText[6].indexOf(")"))+" because of the imprecision in the estimate). If "+
                            arRelevantText[7].substring(0,arRelevantText[7].length()-1)+" ("+arRelevantText[8]+")"+" then it would be "+arRelevantText[9].substring(0,arRelevantText[9].indexOf("(")-1)+" people in the intervention group (although, again it could be from "+
                            arRelevantText[9].substring(arRelevantText[9].indexOf("(")+1,arRelevantText[9].indexOf(")"))+")."));
                            
                                                
                        
                    }
                    else if(arRelevantText[13]==null)
                    {
                        if(arRelevantText[2].contains("RR"))
                        {
                        
                        paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                       +arRelevantText[1].substring(arRelevantText[1].indexOf("("),arRelevantText[1].length())
                        +" (with a total of "+arRelevantText[1].substring(0,arRelevantText[1].indexOf("("))+" participants)."
                            +"Pooled, these found the relative effect to be"+ arRelevantText[2].substring(arRelevantText[2].indexOf("RR")+2,arRelevantText[2].indexOf("(")-1)+
                            " (RR, CI "+arRelevantText[2].substring(arRelevantText[2].indexOf("(")+1,arRelevantText[2].indexOf(")"))+"). "
                            + "Put another way,if the risk of this outcome was "+arRelevantText[3].substring(0,arRelevantText[3].length()-1)+" in the "+comparison6+ " group ("+arRelevantText[5]+") then "+arRelevantText[6].substring(0,arRelevantText[6].indexOf("("))+
                            " people the intervention group would have this outcome (although it could be anything from "+arRelevantText[6].substring(arRelevantText[6].indexOf("(")+1,arRelevantText[6].indexOf(")"))+" because of the imprecision in the estimate). If "+
                            arRelevantText[7].substring(0,arRelevantText[7].length()-1)+" ("+arRelevantText[8]+")"+" then it would be "+arRelevantText[9].substring(0,arRelevantText[9].indexOf("(")-1)+" people in the intervention group (although, again it could be from "+
                            arRelevantText[9].substring(arRelevantText[9].indexOf("(")+1,arRelevantText[9].indexOf(")"))+") and, finally, if "+
                            arRelevantText[10].substring(0,arRelevantText[10].length()-1)+" ("+arRelevantText[11]+") "+arRelevantText[12].substring(0,arRelevantText[12].indexOf("(")-1)+" people would have this outcome if given the intervention ("+
                            arRelevantText[12].substring(arRelevantText[12].indexOf("(")+1,arRelevantText[12].indexOf(")"))+" – due to imprecision)."));
                        
                        
                        }
                        else
                        {
                             paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                       +arRelevantText[1].substring(arRelevantText[1].indexOf("("),arRelevantText[1].length())
                        +" (with a total of "+arRelevantText[1].substring(0,arRelevantText[1].indexOf("("))+" participants)."
                            +"Pooled, these found the relative effect to be"+ arRelevantText[2]+". "      
                            + "Put another way,if the risk of this outcome was "+arRelevantText[3].substring(0,arRelevantText[3].length()-1)+" in the "+comparison6+ " group ("+arRelevantText[5]+") then "+arRelevantText[6].substring(0,arRelevantText[6].indexOf("("))+
                            " people the intervention group would have this outcome (although it could be anything from "+arRelevantText[6].substring(arRelevantText[6].indexOf("(")+1,arRelevantText[6].indexOf(")"))+" because of the imprecision in the estimate). If "+
                            arRelevantText[7].substring(0,arRelevantText[7].length()-1)+" ("+arRelevantText[8]+")"+" then it would be "+arRelevantText[9].substring(0,arRelevantText[9].indexOf("(")-1)+" people in the intervention group (although, again it could be from "+
                            arRelevantText[9].substring(arRelevantText[9].indexOf("(")+1,arRelevantText[9].indexOf(")"))+") and, finally, if "+
                            arRelevantText[10].substring(0,arRelevantText[10].length()-1)+" ("+arRelevantText[11]+") "+arRelevantText[12].substring(0,arRelevantText[12].indexOf("(")-1)+" people would have this outcome if given the intervention ("+
                            arRelevantText[12].substring(arRelevantText[12].indexOf("(")+1,arRelevantText[12].indexOf(")"))+" – due to imprecision)."));
                        }
                        
                        
                    }
                    
                    if(arRelevantText[17]!=null&&!arRelevantText[17].equals("empty")
                            &&!arRelevantText[17].equals("-")&&!arRelevantText[17].equals("See comment"))
                    {
                    StringBuffer sbFootnotes = new StringBuffer();
                        
                        if(arRelevantText[17].contains("very"))
                        { 
                            String numbers = arRelevantText[17].substring(arRelevantText[17].indexOf("very low")+8,arRelevantText[17].length());
                            String [] arSingleNumbers = numbers.split(",");
                            int numberFootnote=0;
                           
                            for(int a=0;a<arSingleNumbers.length;a++)
                            {
                                numberFootnote = Integer.parseInt(arSingleNumbers[a]);
                                if(a==arSingleNumbers.length-2)
                                {
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+" and ");
                                }
                                else if(a==arSingleNumbers.length-1)
                                {
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+".");
                                }
                                else
                                {                                    
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+", ");
                                }
                               
                            }
                            
                        
                            paragraph3.appendChild(doc.createTextNode("These data were graded as being of ‘very low’ quality because of issues relating to "+sbFootnotes.toString()+" This means we have to be very uncertain about the estimate."));
                                
                        }
                        else if(arRelevantText[17].contains("low"))
                        { 
                            String numbers = arRelevantText[17].substring(arRelevantText[17].indexOf("low")+3,arRelevantText[17].length());
                            System.out.println("numbers: "+numbers);
                            String [] arSingleNumbers = numbers.split(",");
                            int numberFootnote=0;
                           
                            for(int a=0;a<arSingleNumbers.length;a++)
                            {
                                numberFootnote = Integer.parseInt(arSingleNumbers[a]);
                                if(a==arSingleNumbers.length-2)
                                {
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+" and ");
                                }
                                else if(a==arSingleNumbers.length-1)
                                {
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+".");
                                }
                                else
                                {                                    
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+", ");
                                }
                               
                            }
                            
                        
                            paragraph3.appendChild(doc.createTextNode("These data were graded as being of ‘low’ quality because of issues relating to "+sbFootnotes.toString()+" This " 
                                    + "means that further research is very likely to have an important impact on our confidence in the estimate of effect and is likely to change the estimate."));
                                
                        }
                         else if(arRelevantText[17].contains("moderate"))
                        { 
                            String numbers = arRelevantText[17].substring(arRelevantText[17].indexOf("moderate")+8,arRelevantText[17].length());
                            String [] arSingleNumbers = numbers.split(",");
                            int numberFootnote=0;
                           
                            for(int a=0;a<arSingleNumbers.length;a++)
                            {
                                numberFootnote = Integer.parseInt(arSingleNumbers[a]);
                                if(a==arSingleNumbers.length-2)
                                {
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+" and ");
                                }
                                else if(a==arSingleNumbers.length-1)
                                {
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+".");
                                }
                                else
                                {                                    
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+", ");
                                }
                               
                            }
                            
                        
                            paragraph3.appendChild(doc.createTextNode("These data were graded as being of ‘moderate’ quality because of issues relating to "+sbFootnotes.toString()+" This " 
                                    + "means that further research is likely to have an important impact on our confidence in the estimate of effect and may change the estimate."));
                                
                        }
                          else if(arRelevantText[17].contains("high"))
                        { 
                            String numbers = arRelevantText[17].substring(arRelevantText[17].indexOf("high")+4,arRelevantText[17].length());
                            String [] arSingleNumbers = numbers.split(",");
                            int numberFootnote=0;
                           
                            for(int a=0;a<arSingleNumbers.length;a++)
                            {
                                numberFootnote = Integer.parseInt(arSingleNumbers[a]);
                                if(a==arSingleNumbers.length-2)
                                {
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+" and ");
                                }
                                else if(a==arSingleNumbers.length-1)
                                {
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+".");
                                }
                                else
                                {                                    
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+", ");
                                }
                               
                            }
                            
                        
                            paragraph3.appendChild(doc.createTextNode("These data were graded as being of ‘high’ quality because of issues relating to "+sbFootnotes.toString()+" This " 
                                    + "means that further research is very unlikely to change our confidence in the estimate of effect."));
                                
                        }
                    }
                    
                    
                    subsection.appendChild(paragraph2);
                    subsection.appendChild(paragraph3);
                    
                    
                    for(int a=0;a<arRelevantText.length;a++)
                    {
                    System.out.println("array text: "+a+" text "+arRelevantText[a]);                   
                    arRelevantText[a]=null;
                    
                    }
                    
                    }
                    
                    
                    
                }
            }
            
            paragraph = doc.createElement("P");
            subsection.appendChild(paragraph);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*--- End of HAL generated text "+formatter.format(currentTime)+" ---*"));
            paragraph.appendChild(marker);
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
            cleaner.deleteSpecialChars(filepath);
 
            System.out.println("Done");
            return true;
                
            
          
	  } catch (Exception ex) {
		System.out.println(ex);
                ex.printStackTrace();
                return false;
	  }
    }
    else if(language.equalsIgnoreCase("german"))
    {
     try {
           
            String filepath =file;
            Document doc = getDocument(filepath);
            
           // int anzahl = doc.getElementsByTagName("EXTENSIONS").getLength();
            NodeList nl = doc.getElementsByTagName("SOF_TABLE");
            
            Node sof_results = doc.getElementsByTagName("SUMMARY_OF_RESULTS").item(0);
            
            //Remove old Summary of main results
            removeAllChildren(sof_results);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date();  
            
            paragraph = doc.createElement("P");
            sof_results.appendChild(paragraph);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ Start des von HAL generierten Textes "+formatter.format(currentTime)+" ------*  Hinweis: nur von der SoF Tabelle."));
            paragraph.appendChild(marker);
            
            for (int i = 0; i < nl.getLength(); i++){
                Element comp =  (Element)doc.getElementsByTagName("SOF_TABLE").item(i);
                String comparison = comp.getElementsByTagName("TITLE").item(0).getTextContent().trim();
                System.out.println("COMPARISON "+(i+1)+": "+comparison);
                
                subsection = doc.createElement("SUBSECTION");
                sof_results.appendChild(subsection);
 
                  heading = doc.createElement ("HEADING");
                  heading.appendChild(doc.createTextNode("COMPARISON "+(i+1)+": "+comparison));
                  subsection.appendChild(heading);
                  Attr attr = doc.createAttribute("LEVEL");
                  attr.setValue("3");
                  heading.setAttributeNode(attr);
                
                NodeList nl1 = comp.getElementsByTagName("TR");
                paragraph = doc.createElement("P");
                paragraph.appendChild(doc.createTextNode("Please see "));
                subsection.appendChild(paragraph);
                Element link = doc.createElement("LINK");
                link.appendChild(doc.createTextNode("Summary of findings table "+(i+1)));
                paragraph.appendChild(link);
                attr = doc.createAttribute("REF");
                if (i < 9){
                     attr.setValue("SOF-0"+(i+1));
                }
                else {
                     attr.setValue("SOF-"+(i+1));
                }
                link.setAttributeNode(attr);
                Attr attr1 = doc.createAttribute("TYPE");
                attr1.setValue("SOF");
                link.setAttributeNode(attr1);
                paragraph.appendChild((doc.createTextNode(".")));
                subsection.appendChild(paragraph);
                
                for (int j = 5; j < nl1.getLength(); j++) {
                    Element tr = (Element) comp.getElementsByTagName("TR").item(j);
                    Element td0 = (Element)tr.getElementsByTagName("TD").item(0);
                    String outcome = td0.getElementsByTagName("P").item(0).getTextContent().trim();
                    if (outcome.startsWith("*The basis for the")) {
                        break;
                    }
                    else { 
                    Element td3 = (Element)tr.getElementsByTagName("TD").item(3);
                    String [] effect = new String [2];
                    effect = td3.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                    Element td4 = (Element)tr.getElementsByTagName("TD").item(4);
                    String [] participants = new String [2];
                    participants = td4.getElementsByTagName("P").item(0).getTextContent().trim().split("(\\()");
                    Element td5 = (Element)tr.getElementsByTagName("TD").item(5);
                    String [] quality = new String [3];
                    quality = td5.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                    
                    paragraph = doc.createElement("P"); 
                   
                    
                    if (effect[0].equals("Not estimable")){
                        System.out.println("For '"+outcome+"' we did not find any study that reported this outcome in any usable form. Fuer -.- wurde keine Studie gefunden, die ueber den Outcome in irgendeiner verwendbarbaren Form berichtet haette.");
                        paragraph.appendChild(doc.createTextNode("For '"+outcome+"' we did not find any study that reported this outcome in any usable form.")); 
                        subsection.appendChild(paragraph);
                    }
                    else {
                        System.out.print("For the outcome of '"+outcome+"' (");
                        paragraph.appendChild(doc.createTextNode("For the outcome of ' Fuer den Outcome '"+outcome+"' (")); 
                        subsection.appendChild(paragraph);
                        if (participants.length  > 1) {
                            System.out.print(participants[1].replaceAll(" study\\)", "").replaceAll(" studies\\)", "")+" RCT(s), "+participants[0]+" participants, ");
                             paragraph.appendChild(doc.createTextNode(participants[1].replaceAll(" study\\)", "").replaceAll(" studies\\)", "")+" RCT(s), "+participants[0]+" participants, ")); 
                            subsection.appendChild(paragraph);
                        }
                        else {
                            System.out.print(participants[0]);
                             paragraph.appendChild(doc.createTextNode(participants[0])); 
                             subsection.appendChild(paragraph);
                        }
                        if (effect.length > 1) {
                            System.out.print(effect[0]+"CI "+effect[1].replaceAll("(\\()", "").replaceAll("(\\))", "")+")");   
                             paragraph.appendChild(doc.createTextNode(effect[0]+"CI "+effect[1].replaceAll("(\\()", "").replaceAll("(\\))", "")+")")); 
                             subsection.appendChild(paragraph);
                        }
                        else {
                            System.out.print(effect[0]);
                             paragraph.appendChild(doc.createTextNode(effect[0])); 
                            subsection.appendChild(paragraph);
                        }    
                        if (quality.length > 1){
                            System.out.print(" we found quality of data to be "+quality[1]);
                             paragraph.appendChild(doc.createTextNode(" we found quality of data to be Die Qualitaet der Daten war "+quality[1]+".")); 
                    subsection.appendChild(paragraph);
                        }
                        else {
                            System.out.print(quality[0]);  
                             paragraph.appendChild(doc.createTextNode(quality[0])); 
                            subsection.appendChild(paragraph);
                        }
                        Element td6 = (Element)tr.getElementsByTagName("TD").item(6);
                        String comment = td6.getElementsByTagName("P").item(0).getTextContent().trim();
                        if (!comment.equals("")){
                        System.out.println(" [Note comment: "+comment+"].");
                         paragraph.appendChild(doc.createTextNode(" [Note comment: Hinweis: "+comment+"].")); 
                        subsection.appendChild(paragraph);
                        }
                        
                    }
                    }
                }
            }
            
            paragraph = doc.createElement("P");
            subsection.appendChild(paragraph);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*--- Ende des von HAL generierten Textes "+formatter.format(currentTime)+" ---*"));
            paragraph.appendChild(marker);
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
            cleaner.deleteSpecialChars(filepath);
 
            System.out.println("Done");
            return true;
                
            
          
	  } catch (Exception ex) {
		System.out.println(ex);
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
            return null;
        }
    }
     public static void removeAllChildren(Node node)
{
  for (Node child; (child = node.getFirstChild()) != null; node.removeChild(child));
}
     
       public static boolean checkSOF (String file) {
         Document doc = getDocument(file);
         if(doc.getElementsByTagName("SOF_TABLES").item(0).hasChildNodes() == true)
             return true;
         else
             return false;
     }
     
    }
    

