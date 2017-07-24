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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import static revmanhalcochranemode.Abstract.removeAllChildren;

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
    private boolean breaker=false;
    private boolean checkSecondColumn = false;
    
    
    public SOFDiscussion (String lang)
            {
            language = lang;
            }    
   
         public boolean main (String file) 
    {
        Element paragraph = null;
        Element marker = null;
        Element subsection = null;
        Element subsection2 = null;
        Element subsection3 = null;
        Element heading = null;
        Element heading2 = null;
        Element heading3 = null;
        Complex1 cleaner = new Complex1(null); 
       String filepath =file;
       Document doc = getDocument(filepath);


    if(language.equalsIgnoreCase("English"))
        {
        
            try {
           
           // int anzahl = doc.getElementsByTagName("EXTENSIONS").getLength();
            NodeList nl = doc.getElementsByTagName("SOF_TABLE");
            
            Node sof_results = doc.getElementsByTagName("SUMMARY_OF_RESULTS").item(0);
            
            //Remove old Summary of main results
            removeAllChildren(sof_results);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date();  
            
            heading3 = doc.createElement("HEADING");
            Attr attr3 = doc.createAttribute("LEVEL");
            attr3.setValue("2");
            heading3.setAttributeNode(attr3);

            
            paragraph = doc.createElement("P");
            //sof_results.appendChild(paragraph);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ Start of HAL generated text "+formatter.format(currentTime)+" ------*     PLEASE NOTE: Solely from SoF tables."));
            paragraph.appendChild(marker);
            
            subsection3=doc.createElement("SUBSECTION");
            subsection3.appendChild(heading3);
            subsection3.appendChild(paragraph);
            sof_results.appendChild(subsection3);
            
            for (int i = 0; i < nl.getLength(); i++)
            {
                if(breaker==true)
                {
                    break;
                }
                counterRow=0;
                Element comp =  (Element)doc.getElementsByTagName("SOF_TABLE").item(i);
                String comparison = comp.getElementsByTagName("TITLE").item(0).getTextContent().trim();
                String footnote = comp.getElementsByTagName("FOOTNOTES").item(0).getTextContent().trim();
                
                System.out.println("footnote: "+(i+1)+": "+footnote);
                
               // String [] arFootnotes=footnote.split(footnote.substring(footnote.indexOf(file), i)u);
                String [] arFootnotes;
                arFootnotes =footnote.split("\n");
               // String [] arFootnotes=footnote.split("\n");
                try
                {
                 System.out.println(arFootnotes[2]);
                }
                catch(Exception e)
                {
                   arFootnotes =footnote.split(".\\d "); 
                }
                 
                
                for(int t=0;t<arFootnotes.length;t++)
                {
                    
                    if(arFootnotes[t].isEmpty())
                    {
                        
                    }
                    else if(!arFootnotes[t].contains(":"))
                    {
                        int l=i+1;
                        t=t+1;
                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon. (Footnote "+t+" in table "+l+")");
                        final JDialog d = pane.createDialog(null, "ERROR");
                        d.setLocation(450, 430);
                        d.setVisible(true);
                        breaker=true;
                        break;
                    }
                    else if(arFootnotes[t].substring(0,2).matches("\\d ")) 
                   {
                       System.out.println("in digit dinna");
                       arFootnotes[t]= arFootnotes[t].substring(2, arFootnotes[t].length());  
                   }
                   else if(arFootnotes[t].substring(0,2).matches("\\d\\d"))
                   {
                       arFootnotes[t]= arFootnotes[t].substring(3, arFootnotes[t].length()); 
                   }
                   
                   
                    System.out.println("arFootnotes: "+arFootnotes[t]);
                }
                
        
                
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
                 String stColumnFourTwo="";
                 String stColumnFourThree="";
                 String stColumnFive="";
                 String stColumnFiveTwo="";
                 String stColumnFiveThree="";
                 
                 int counter=5;
                 String comparison6;
                 
                 try
                 {
                  comparison6 = trStart.getTextContent().toString().substring(trStart.getTextContent().toString().indexOf("Comparison:")
                         ,trStart.getTextContent().toString().length());
                 }
                 catch (Exception e)
                   {
                       comparison6 = trStart.getTextContent().toString().substring(trStart.getTextContent().toString().indexOf("Intervention:")
                         ,trStart.getTextContent().toString().length());
                       int l = i+1;
                       final JOptionPane pane = new JOptionPane("In table " + l + ", no COMPARISON information was found. Instead, the INTERVENTION text was used.");
                       final JDialog d = pane.createDialog(null, "WARNING");
                       d.setLocation(450, 430);
                       d.setVisible(true);
                        
                   
                   }
                 String[]arRelevantText = new String[20];
                 
                 comparison6= comparison6.replaceAll("Comparison: ", "");
                 arRelevantText[4]=comparison6; 
                 System.out.println("trStart: "+comparison6);
                 
                
                for (int j = 5; j < nl1.getLength(); j++) 
                {         
                    
                 stColumnOne="";
                 stColumnTwo="";
                 stColumnThree="";
                 stColumnFour="";
                 stColumnFourTwo="";
                 stColumnFourThree="";
                 stColumnFiveTwo="";
                 stColumnFiveThree="";
                 stColumnFive="";
                    
                    Element tr2 = (Element) comp.getElementsByTagName("TR").item(j);                    
                    
                    Element testNextRow = (Element) comp.getElementsByTagName("TR").item(j);  // for checking whether there are 7 columns or 6; includes the first case
                    String stContentNextRow = testNextRow.getTextContent().toString();
                    stContentNextRow = stContentNextRow.replaceAll("\n", "");
                    
                    System.out.println("STCONTENT: "+stContentNextRow);
                    
                     try
                        {   if(stContentNextRow.contains("The mean"))
                            
                            {                             
                             System.out.println("in if 2 dinna");
                             checkSecondColumn=true;
                             
                            }
                           else if(stContentNextRow.substring(0,stContentNextRow.indexOf("R")).contains("Low"))
                            {
                                
                                checkSecondColumn=false;
                                
                             /*   System.out.println(" vor substring: "+stContentNextRow.substring(stContentNextRow.indexOf("low")+3,stContentNextRow.indexOf("low")+4));
                                if((stContentNextRow.substring(stContentNextRow.indexOf("low")+3,stContentNextRow.indexOf("low")+4).matches("\\w") 
                                       || stContentNextRow.substring(stContentNextRow.indexOf("low")+3,stContentNextRow.indexOf("low")+4).matches("-")) 
                                        &&!stContentNextRow.substring(stContentNextRow.indexOf("low")+3,stContentNextRow.indexOf("low")+4).equals("R")
                                        &&!stContentNextRow.substring(stContentNextRow.indexOf("low")+3,stContentNextRow.indexOf("low")+4).equals("H"))
                                        {
                                            System.out.println(" vor zweitem if "+stContentNextRow.substring(stContentNextRow.indexOf("low")+3,stContentNextRow.indexOf("R")));
                                            if(stContentNextRow.substring(stContentNextRow.indexOf("low")+3,stContentNextRow.indexOf("R")).contains("Low"))
                                            {
                                                System.out.println(" in zweiter dinna");
                                                checkSecondColumn=false;
                                            }
                                            else
                                            {
                                                checkSecondColumn=true;
                                            }
                                        
                                        }
                                else
                                {
                                checkSecondColumn=false;
                                } */
                            }
                            
                        else if(stContentNextRow.substring(0,stContentNextRow.indexOf("R")).contains("Moderate")
                            ||stContentNextRow.substring(0,stContentNextRow.indexOf("R")).contains("High") ||
                                 stContentNextRow.substring(0,stContentNextRow.indexOf("R")).contains("Study population")||
                                 stContentNextRow.substring(0,stContentNextRow.indexOf("R")).contains("Medium risk population"))
                         {                             
                             System.out.println("in if 1 dinna"+stContentNextRow.substring(0,stContentNextRow.indexOf("R")));
                             checkSecondColumn=false;
                             
                         }                         
                         else if(stContentNextRow.substring(0,stContentNextRow.indexOf("R")).contains("See") )
                         {
                             System.out.println("in new if dinna");
                             if(stContentNextRow.contains("studies"))
                             {
                                 checkSecondColumn = true;
                             }
                             else
                             {
                                 checkSecondColumn = false;
                             }
                         }
                         else
                         {
                             System.out.println("in else dinna");
                             checkSecondColumn=true;
                         }
                        }
                        catch (Exception e)
                        {
                          
                            checkSecondColumn=false;
                        }
                    
                    
                    
                    NodeList nl2 = comp.getElementsByTagName("TD");
                    String attributeTD1 ="";
                    String attributeTD2 ="";
                    Element tdTest = null;
                    boolean justThreeColumns = false;
                    
                    for(int j2=0; j2< nl2.getLength();j2++)
                    {
                        tdTest = (Element)tr2.getElementsByTagName("TD").item(j2);
                        String test = "bal blab wetter ist schoen";
                       try
                       {
                          boolean test2 = test.substring(0, test.indexOf("wetter")).contains("blab");
                            attributeTD1 = tdTest.getAttribute("ROWSPAN");
                            attributeTD2 = tdTest.getAttribute("COLSPAN");                            
                            System.out.println("this is attribute tdTest: "+attributeTD1+" tdTest2:"+attributeTD2+"test: "+test2);
                            
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
                         //   arColumnFour = td4.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                            System.out.println("column 4: "+arColumnFour[0]);                           
                            stColumnFour=td4.getElementsByTagName("P").item(0).getTextContent();
                            
                            if (arColumnFour[0] != null) {
                          if(!arColumnFour[1].isEmpty())  
                           {
                               stColumnFourTwo=arColumnFour[1];
                               System.out.println("column 4.1: "+arColumnFour[1]); 
                           }
                           else if(!arColumnFour[2].isEmpty())  
                           {
                               stColumnFourThree=arColumnFour[2];
                                System.out.println("column 4.2: "+arColumnFour[2]); 
                           }
                            }  
                        }
                          
                        Element td5 = null;   
                        String [] arColumnFive = new String [3];
                       
                        if ((Element)tr2.getElementsByTagName("TD").item(5)!=null)
                        {
                             td5 = (Element)tr2.getElementsByTagName("TD").item(5);                                        
                             arColumnFive = td5.getElementsByTagName("P").item(0).getTextContent().trim().split("\n");
                             System.out.println("column 5: "+arColumnFive[0]);
                              stColumnFive=arColumnFive[0];                           
                            
                               if (arColumnFive[0] != null && arColumnFive.length>1) 
                               {
                               if(!arColumnFive[1].isEmpty())  
                           {
                               stColumnFiveTwo=arColumnFive[1];
                               System.out.println("column 5.2: "+arColumnFive[1]); 
                           }
                           else if(!arColumnFive[2].isEmpty())  
                           {
                               stColumnFiveThree=arColumnFive[2];
                                System.out.println("column 5.3: "+arColumnFive[2]); 
                           }
                        }
                        }
                        
                        paragraph = doc.createElement("P"); 
                   
                    
                        
                        if((Element)tr2.getElementsByTagName("TD").item(1)==null)
                        {
                            twoColumns = false;
                        }
                        else
                        {
                            twoColumns = true;
                        }
                        
                    }
                    int l=i+1;
                    
                     try
                    {
                        
                         arRelevantText[3]=arRelevantText[3].toLowerCase().replaceAll("\\d", "");
                        arRelevantText[7]=arRelevantText[7].toLowerCase().replaceAll("\\d", "");                       
                        arRelevantText[10]=arRelevantText[10].toLowerCase().replaceAll("\\d", "");
                        System.out.println("arSmall: "+arRelevantText[7] +" "+arRelevantText[10]);
                    }
                     catch(Exception e)
                     {
                         
                     }
                    
                    
                   try
                   {
                    if(firstTime==true)
                    {
                        
                     if(checkSecondColumn == true)
                     {
                    arRelevantText[0]=stColumnZero;
                    arRelevantText[1]= stColumnFour;
                    arRelevantText[2]=stColumnThree;
                    arRelevantText[3]="Study population";  
                    arRelevantText[5]= stColumnOne;
                    arRelevantText[6]= stColumnTwo;
                    arRelevantText[17]=stColumnFiveTwo;
                    arRelevantText[18]=stColumnFiveThree;
                    
                    
                    System.out.println("stFive: "+stColumnFive+" one: "+stColumnFiveTwo+" two "+stColumnFiveThree);
                 
                    for(int te=0;te<arRelevantText.length;te++)
                    {
                    System.out.println("in true, "+te+" "+arRelevantText[te]);
                    }
                     }
                     else
                     {
                    arRelevantText[0]=stColumnZero;
                    arRelevantText[1]=stColumnThree;
                    arRelevantText[2]=stColumnTwo;
                    arRelevantText[3]=stColumnOne.toLowerCase();
                    arRelevantText[17]=stColumnFour;
                    arRelevantText[18]=stColumnFourTwo;
                    arRelevantText[19]=stColumnFourThree;
                    System.out.print("arFour: "+stColumnFour +" two: "+stColumnFourTwo + " three "+stColumnFourThree);
                     }
                    
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
                
                }
                    catch(Exception e)
                    {
                       e.printStackTrace();
                       final JOptionPane pane = new JOptionPane("In table " + l +", a colon is missing in the outcome column. Please check! ");
                       final JDialog d = pane.createDialog(null, "ERROR");
                       d.setLocation(450, 430);
                       d.setVisible(true);
                       breaker=true;
                       break;
                        
                    }
                    
                    
                    
                    
                    for(int a=0;a<arRelevantText.length;a++)
                    {
                        
                    System.out.println("array text: "+a+" text "+arRelevantText[a]);
                    }
                    
                    Element testNextRow2 = (Element) comp.getElementsByTagName("TR").item(j+1);  
                    String stContentNextRow2 = testNextRow2.getTextContent().toString();
                    stContentNextRow2 = stContentNextRow2.replaceAll("\n", "");
                    
                    
                    System.out.println("ContentNext: "+stContentNextRow2);
                   
                    
                    if(stContentNextRow2.contains(":"))
                    {
                       
                         heading2 = doc.createElement("HEADING");
                         Attr attr2 = doc.createAttribute("LEVEL");
                         attr2.setValue("4");
                         heading2.setAttributeNode(attr2);
                     //   Element bold =doc.createElement("B");
                     //    Element italic =doc.createElement("I");
                         subsection2 = doc.createElement("SUBSECTION");
                        subsection2.appendChild(heading2);
                        
                        String wrongFormattedText="";
                        String partOne="";
                        
                        
                        if(arRelevantText[0].contains("Follow-up"))
                        {
                           partOne =  arRelevantText[0].substring(0,arRelevantText[0].indexOf("Follow-up")-1);
                           wrongFormattedText = arRelevantText[0].substring(arRelevantText[0].indexOf("Follow-up"), arRelevantText[0].length());
                           wrongFormattedText = wrongFormattedText.toLowerCase();
                        }
                        System.out.println("wrong formatted: "+wrongFormattedText);
                        firstTime = true;
                        counterRow++;
                        counter = 5;

                        if(wrongFormattedText.equals(""))
                        {
                            heading2.appendChild(doc.createTextNode(counterRow+". "+arRelevantText[0]+" "));                           
                            
                            
                        }
                        else
                        {
                            heading2.appendChild(doc.createTextNode(counterRow+". "+partOne+"; "+wrongFormattedText));
                     
                        }
                        
                        
                        
                        subsection.appendChild(subsection2);

                        paragraph2 = doc.createElement("P");
                        paragraph3 = doc.createElement("P");
                        
                        Element italic = doc.createElement("I");
                        Element italic2 = doc.createElement("I");
                        Element italic3 = doc.createElement("I");
                        
                        
                    
                if((arRelevantText[5]==null||arRelevantText[5].equals(""))&&checkSecondColumn==false)
                    {
                       try
                       {
                             paragraph2.appendChild(doc.createTextNode("We identified no studies reporting this important outcome. We have considered "
                                     + "if our suggestion of this as a key outcome was impractical but think, on reflection, "
                                     + "that the outcome of "+arRelevantText[0].substring(0,arRelevantText[0].indexOf(":"))  + " "
                                     + "for this comparison is not unreasonable to expect and diminishes the utility of the review by its absence."));
                           
                       }
                       catch (Exception e)                               
                       {
                             System.out.println(" in catch dinna "+arRelevantText[0]);
                             paragraph2.appendChild(doc.createTextNode("We identified no studies reporting this important outcome. We have considered "
                                     + "if our suggestion of this as a key outcome was impractical but think, on reflection, "
                                     + "that the outcome of "+arRelevantText[0].substring(0,arRelevantText[0].indexOf(":"))  + " "
                                     + "for this comparison is not unreasonable to expect and diminishes the utility of the review by its absence."));
                       }
                       
                     
                    }
                    
                    
                    else if(arRelevantText[7]==null&&!arRelevantText[3].equalsIgnoreCase("Study population"))  // case for array [1] has no brackets!!!
                    {
                        paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                       +arRelevantText[1].substring(arRelevantText[1].indexOf("(")+1,arRelevantText[1].length()-1)
                        +" (with a total of "+arRelevantText[1].substring(0,arRelevantText[1].indexOf("("))+" participants)."
                            +" Pooled, these found the relative effect to be"+ arRelevantText[2].substring(arRelevantText[2].indexOf("RR")+2,arRelevantText[2].indexOf("(")-1)+
                            " (RR, CI "+arRelevantText[2].substring(arRelevantText[2].indexOf("(")+1,arRelevantText[2].indexOf(")"))+"). "
                            + "Put another way, if the risk of this outcome was "));
                        
                               italic.appendChild(doc.createTextNode(arRelevantText[3]));
                               paragraph2.appendChild(italic);
                               paragraph2.appendChild(doc.createTextNode(" in the "+comparison6+ " group ("+arRelevantText[5]+") then "+arRelevantText[6].substring(0,arRelevantText[6].indexOf("("))+
                            " people the intervention group would have this outcome (although it could be anything from "+arRelevantText[6].substring(arRelevantText[6].indexOf("(")+1,arRelevantText[6].indexOf(")"))+" because of the imprecision in the estimate)."));
                        
                    }
                    else if(arRelevantText[3].equalsIgnoreCase("Study population"))
                    {
                        if(arRelevantText[6].contains("See"))
                        {
                            if (!arRelevantText[2].contains("(")) {
                                try
                                {
                                paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                                        + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                        + " (with a total of " + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + " participants)."));
                                }
                                catch (Exception e)
                                {
                                    i = i + 1;

                                    paragraph2.appendChild(doc.createTextNode("There is a special case, that can't be written. Please check the text of this outcome."));
                                    final JOptionPane pane = new JOptionPane("In table " + i + " there is special case, that can't be written. Please check the text of this outcome: " +"\n" + arRelevantText[0]);
                                    final JDialog d = pane.createDialog(null, "ERROR");
                                    d.setLocation(300, 430);
                                    d.setVisible(true);
                                    
                                }

                            }
                            else
                            {
                               
                                paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                                        + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                        + " (with a total of " + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + " participants)."
                                        + " Pooled, these found the relative effect to be " + arRelevantText[2].substring(arRelevantText[2].indexOf("R") -1, arRelevantText[2].indexOf("(") - 1)
                                        + " (RR, CI " + arRelevantText[2].substring(arRelevantText[2].indexOf("(") + 1, arRelevantText[2].indexOf(")")) + "). "));
                                          
                                
                            }
                        }
                        else //a new clause without the italic stuff
                        {
                            try
                            {
                                paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                                        + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                        + " (with a total of " + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + " participants)."
                                        + " Pooled, these found the relative effect to be" + arRelevantText[2].substring(arRelevantText[2].indexOf("RR") + 2, arRelevantText[2].indexOf("(") - 1)
                                        + " (RR, CI " + arRelevantText[2].substring(arRelevantText[2].indexOf("(") + 1, arRelevantText[2].indexOf(")")) + "). " + "Put another way, if the risk of this outcome was the same as that for the study populations in the " + comparison6 + " group (" + arRelevantText[5] + ") then " + arRelevantText[6].substring(0, arRelevantText[6].indexOf("("))
                                        + " people the intervention group would have this outcome (although it could be anything from " + arRelevantText[6].substring(arRelevantText[6].indexOf("(") + 1, arRelevantText[6].indexOf(")")) + " because of the imprecision in the estimate). "
                                        + "If "));
                                if(arRelevantText[7]!=null)
                                {
                                italic2.appendChild(doc.createTextNode(arRelevantText[7]));
                                paragraph2.appendChild(italic2);
                                }
                                else
                                {
                                 paragraph2.appendChild(doc.createTextNode("-NOTHING LIKE 'LOW','MODERATE' OR HIGH FOUND IN THE SECOND COLUMN-"));
                                }
                                System.out.println("italic dinna!!! "+italic2.toString());
                                if(checkSecondColumn==true)
                                {
                                    
                                }
                                else
                                {
                                paragraph2.appendChild(doc.createTextNode(" (" + arRelevantText[8] + ")" + " then it would be " + arRelevantText[9].substring(0, arRelevantText[9].indexOf("(") - 1) + " people in the intervention group (although, again it could be from "
                                        + arRelevantText[9].substring(arRelevantText[9].indexOf("(") + 1, arRelevantText[9].indexOf(")")) + ")."));
                                }
                                
                                }
                            catch (Exception e)
                            {
                                
                                if(!arRelevantText[2].equals(""))
                                {
                                    try
                                    {
                                paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                                        + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                        + " (with a total of " + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + " participants)."
                                        + " Pooled, these found the relative effect to be" + arRelevantText[2].substring(arRelevantText[2].indexOf("RR") + 2, arRelevantText[2].indexOf("(") - 1)
                                        + " (RR, CI " + arRelevantText[2].substring(arRelevantText[2].indexOf("(") + 1, arRelevantText[2].indexOf(")")) + "). " + "Put another way, if the risk of this outcome was the same as that for the study populations in the " + comparison6 + " group (" + arRelevantText[5] + ") then " + arRelevantText[6].substring(0, arRelevantText[6].indexOf("("))
                                        + " people the intervention group would have this outcome (although it could be anything from " + arRelevantText[6].substring(arRelevantText[6].indexOf("(") + 1, arRelevantText[6].indexOf(")")) + " because of the imprecision in the estimate)."));
                                
                                    }
                                    catch(Exception eta)
                                    {
                                    
                                    paragraph2.appendChild(doc.createTextNode("There is a special case, that can't be written. Please check the text of this outcome."));
                                        i=i+1;
                                        paragraph2.appendChild(doc.createTextNode("There is a special case, that can't be written. Please check the text of this outcome."));
                                    final JOptionPane pane = new JOptionPane("In table " + i + " there is special case, that can't be written. Please check the text of this outcome: "+"\n" + arRelevantText[0]);
                                    final JDialog d = pane.createDialog(null, "ERROR");
                                    d.setLocation(300, 430);
                                    d.setVisible(true);
                                         
                                    }
                                
                                
                                
                                }
                                else
                                {
                                    try
                                    {
                                        
                                        if(arRelevantText[6].contains("The mean"))
                                        {
                                            System.out.println(" before if the mean "+arRelevantText[6]);
                                            try {
                                                
                                                paragraph2.appendChild(doc.createTextNode("--PLEASE CHECK AND CORRECT THIS TEXT-- We identified " + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                                        + " reporting this important outcome. For the continuous measure of " + arRelevantText[0].substring(arRelevantText[0].indexOf("(") + 1, arRelevantText[0].indexOf(",")) + " the average score " + arRelevantText[6].substring(arRelevantText[6].indexOf("was"), arRelevantText[6].length()) + "."));
                                   

                                            } 
                                            catch (Exception v) {
                                                
                                                paragraph2.appendChild(doc.createTextNode("There is a special case (continuous), that can't be written. Please check the text of this outcome."));
                                               System.out.println("ERROR!!");
                                               
                                               int u =i+1;
                                                final JOptionPane pane = new JOptionPane("In table " + u + " there is a special case (continuous), that can't be written. Please check the text of this outcome: "+"\n" + arRelevantText[0]);
                                                final JDialog d = pane.createDialog(null, "ERROR");
                                                d.setLocation(300, 430);
                                                d.setVisible(true);
                                            }
                                        }
                                            
                                        else
                                        {
                                        paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                                        + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                        + " (with a total of " + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + " participants)."
                                        + " Pooled, these found the relative effect to be" + "NO MEASUREMENT FOUND!. " + "Put another way, if the risk of this outcome was the same as that for the study populations in the " + comparison6 + " group (" + arRelevantText[5] + ") then " + arRelevantText[6].substring(0, arRelevantText[6].indexOf("("))
                                        + " people the intervention group would have this outcome (although it could be anything from " + arRelevantText[6].substring(arRelevantText[6].indexOf("(") + 1, arRelevantText[6].indexOf(")")) + " because of the imprecision in the estimate)."));
                                        
                                                    
                                        }
                                    }
                                    
                                    catch (Exception ex)
                                    {
                                        i=i+1;
                                        paragraph2.appendChild(doc.createTextNode("There is a special case, that can't be written. Please check the text of this outcome."));
                                        final JOptionPane pane = new JOptionPane("In table " + i + " there is special case, that can't be written. Please check the text of this outcome: "+"\n" +arRelevantText[0]);
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(300, 430);
                                        d.setVisible(true);
                                    }
                                     
                                }
                            }
                        }
                       
                        
                    }
                        
                        
                        
                    else if(arRelevantText[10]==null)
                    {                         
              
                        paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                                + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                + " (with a total of " + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + " participants)."
                                + " Pooled, these found the relative effect to be" + arRelevantText[2].substring(arRelevantText[2].indexOf("RR") + 2, arRelevantText[2].indexOf("(") - 1)
                                + " (RR, CI " + arRelevantText[2].substring(arRelevantText[2].indexOf("(") + 1, arRelevantText[2].indexOf(")")) + "). "
                                + "Put another way, if the risk of this outcome was "));
                        italic.appendChild(doc.createTextNode(arRelevantText[3]));
                        paragraph2.appendChild(italic);

                        paragraph2.appendChild(doc.createTextNode(" in the " + comparison6 + " group (" + arRelevantText[5] + ") then " + arRelevantText[6].substring(0, arRelevantText[6].indexOf("("))
                                + " people the intervention group would have this outcome (although it could be anything from " + arRelevantText[6].substring(arRelevantText[6].indexOf("(") + 1, arRelevantText[6].indexOf(")")) + " because of the imprecision in the estimate). If "));
                        italic2.appendChild(doc.createTextNode(arRelevantText[7]));
                        paragraph2.appendChild(italic2);
                        paragraph2.appendChild(doc.createTextNode(" (" + arRelevantText[8] + ")" + " then it would be " + arRelevantText[9].substring(0, arRelevantText[9].indexOf("(") - 1) + " people in the intervention group (although, again it could be from "
                                + arRelevantText[9].substring(arRelevantText[9].indexOf("(") + 1, arRelevantText[9].indexOf(")")) + ")."));

                    }
                    
                    else if(arRelevantText[13]==null)
                    {
                      
                        
                        if(arRelevantText[2].contains("RR")||arRelevantText[2].contains("HR"))
                        {
                        
                            paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                                    + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                    + " (with a total of " + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + " participants)."
                                    + " Pooled, these found the relative effect to be" + arRelevantText[2].substring(arRelevantText[2].indexOf("\\wR") + 2, arRelevantText[2].indexOf("(") - 1)
                                    + " (RR, CI " + arRelevantText[2].substring(arRelevantText[2].indexOf("(") + 1, arRelevantText[2].indexOf(")")) + "). "
                                    + "Put another way, if the risk of this outcome was "));

                            italic.appendChild(doc.createTextNode(arRelevantText[3]));
                            paragraph2.appendChild(italic);

                            paragraph2.appendChild(doc.createTextNode(" in the " + comparison6 + " group (" + arRelevantText[5] + ") then " + arRelevantText[6].substring(0, arRelevantText[6].indexOf("("))
                                    + " people the intervention group would have this outcome (although it could be anything from " + arRelevantText[6].substring(arRelevantText[6].indexOf("(") + 1, arRelevantText[6].indexOf(")")) + " because of the imprecision in the estimate). If "));
                            italic2.appendChild(doc.createTextNode(arRelevantText[7]));
                            paragraph2.appendChild(italic2);
                            paragraph2.appendChild(doc.createTextNode(" (" + arRelevantText[8] + ")" + " then it would be " + arRelevantText[9].substring(0, arRelevantText[9].indexOf("(") - 1) + " people in the intervention group (although, again it could be from "
                                    + arRelevantText[9].substring(arRelevantText[9].indexOf("(") + 1, arRelevantText[9].indexOf(")")) + ") and, finally, if "));
                            italic3.appendChild(doc.createTextNode(arRelevantText[10]));
                            paragraph2.appendChild(italic3);
                            paragraph2.appendChild(doc.createTextNode(" (" + arRelevantText[11] + ") " + arRelevantText[12].substring(0, arRelevantText[12].indexOf("(") - 1) + " people would have this outcome if given the intervention ("
                                    + arRelevantText[12].substring(arRelevantText[12].indexOf("(")+1,arRelevantText[12].indexOf(")"))+" – due to imprecision)."));
                        
                        
                        }
                        else
                        {
                            paragraph2.appendChild(doc.createTextNode("For this outcome, of pre-stated key importance for summarising the effects within this comparison, we found "
                                    + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].length() - 1)
                                    + " (with a total of " + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + " participants)."
                                    + " Pooled, these found the relative effect to be" + arRelevantText[2] + ". "
                                    + "Put another way, if the risk of this outcome was "));
                            italic.appendChild(doc.createTextNode(arRelevantText[3]));
                            paragraph2.appendChild(italic);

                            paragraph2.appendChild(doc.createTextNode(" in the " + comparison6 + " group (" + arRelevantText[5] + ") then " + arRelevantText[6].substring(0, arRelevantText[6].indexOf("("))
                                    + " people the intervention group would have this outcome (although it could be anything from " + arRelevantText[6].substring(arRelevantText[6].indexOf("(") + 1, arRelevantText[6].indexOf(")")) + " because of the imprecision in the estimate). If "));
                            italic2.appendChild(doc.createTextNode(arRelevantText[7]));
                            paragraph2.appendChild(italic2);
                            paragraph2.appendChild(doc.createTextNode(" (" + arRelevantText[8] + ")" + " then it would be " + arRelevantText[9].substring(0, arRelevantText[9].indexOf("(") - 1) + " people in the intervention group (although, again it could be from "
                                    + arRelevantText[9].substring(arRelevantText[9].indexOf("(") + 1, arRelevantText[9].indexOf(")")) + ") and, finally, if "));
                            italic3.appendChild(doc.createTextNode(arRelevantText[10]));
                            paragraph2.appendChild(italic3);
                            paragraph2.appendChild(doc.createTextNode(" (" + arRelevantText[11] + ") " + arRelevantText[12].substring(0, arRelevantText[12].indexOf("(") - 1) + " people would have this outcome if given the intervention ("
                                    + arRelevantText[12].substring(arRelevantText[12].indexOf("(") + 1, arRelevantText[12].indexOf(")")) + " – due to imprecision)."));
                        }
                        
                        
                    }
                    
                    
                    
                    if(!arRelevantText[17].isEmpty()&&!arRelevantText[17].equals("empty")
                            &&!arRelevantText[17].equals("-")&&!arRelevantText[17].equals("See comment"))
                    {
                        
                        StringBuffer sbFootnotes = new StringBuffer();
                        
                        if(arRelevantText[17].contains("very"))
                        { 
                            String numbers = arRelevantText[17].substring(arRelevantText[17].indexOf("very low")+8,arRelevantText[17].length());
                            numbers=numbers.replace("\n", "");
                            String [] arSingleNumbers = numbers.split(",");
                            int numberFootnote=0;
                            System.out.println("numbers: "+numbers);
                           
                            for(int a=0;a<arSingleNumbers.length;a++)
                            {
                                  if(arSingleNumbers[a].isEmpty())
                                {
                                
                                }
                                  else
                                  {
                                numberFootnote = Integer.parseInt(arSingleNumbers[a]);
                                System.out.println("numberFootnote: "+numberFootnote+" length: "+arSingleNumbers.length +" arFoot:"+arFootnotes.length);
                                
                                if(a==arSingleNumbers.length-2)
                                {
                                    System.out.println("in length-2: "+ numberFootnote);
                                 try
                                    {   
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+" and ");
                                    }
                                 catch(Exception e)
                                 {
                                     final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                     final JDialog d = pane.createDialog(null, "ERROR");
                                     d.setLocation(450, 430);
                                     d.setVisible(true);
                                 }
                                    }
                                else if(a==arSingleNumbers.length-1)
                                {
                                    try
                                    {
                                         sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+".");    //error message, no colon in footnotes
                                    }
                                    catch(Exception e)
                                    {
                                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(450, 430);
                                        d.setVisible(true);
                                    }
                                
                                }
                                else
                                { 
                                    try
                                    {
                                     sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+", ");
                                    }
                                    catch(Exception e)
                                    {
                                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(450, 430);
                                        d.setVisible(true);
                                        
                                    }
                                
                                }
                                  }
                            }
                            
                            System.out.println("in very dinna");
                            paragraph3.appendChild(doc.createTextNode("These data were graded as being of ‘very low’ quality because of issues relating to "+sbFootnotes.toString()+" This means we have to be very uncertain about the estimate."));
                                
                        }
                        else if(arRelevantText[17].contains("low"))
                        { 
                            String numbers = arRelevantText[17].substring(arRelevantText[17].indexOf("low")+3,arRelevantText[17].length());
                            System.out.println("numbers: "+numbers);
                            numbers=numbers.replace("\n", "");
                            String [] arSingleNumbers = numbers.split(",");
                            int numberFootnote=0;
                           
                            
                            for(int a=0;a<arSingleNumbers.length;a++)
                            {
                                  if(arSingleNumbers[a].isEmpty())
                                {
                                
                                }
                                  else
                                  {
                                numberFootnote = Integer.parseInt(arSingleNumbers[a]);
                               
                                if(a==arSingleNumbers.length-2)
                                {
                                    System.out.println("in length-2: "+ numberFootnote);
                                 try
                                    {   
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+" and ");
                                    }
                                 catch(Exception e)
                                 {
                                     final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                     final JDialog d = pane.createDialog(null, "ERROR");
                                     d.setLocation(450, 430);
                                     d.setVisible(true);
                                 }
                                    }
                                else if(a==arSingleNumbers.length-1)
                                {
                                    try
                                    {
                                         sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+".");    //error message, no colon in footnotes
                                    }
                                    catch(Exception e)
                                    {
                                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(450, 430);
                                        d.setVisible(true);
                                    }
                                
                                }
                                else
                                { 
                                    try
                                    {
                                     sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+", ");
                                    }
                                    catch(Exception e)
                                    {
                                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(450, 430);
                                        d.setVisible(true);
                                        
                                    }
                                
                                }
                                  }
                            }
                            
                        
                            System.out.println("in low dinna");
                            paragraph3.appendChild(doc.createTextNode("These data were graded as being of ‘low’ quality because of issues relating to "+sbFootnotes.toString()+" This " 
                                    + "means that further research is very likely to have an important impact on our confidence in the estimate of effect and is likely to change the estimate."));
                                
                        }
                         else if(arRelevantText[17].contains("moderate"))
                        { 
                            String numbers = arRelevantText[17].substring(arRelevantText[17].indexOf("moderate")+8,arRelevantText[17].length());
                            numbers=numbers.replaceAll("\n", "");
                            String [] arSingleNumbers = numbers.split(",");
                            int numberFootnote=0;
                           
                            for(int a=0;a<arSingleNumbers.length;a++)
                            {
                                if(arSingleNumbers[a].isEmpty())
                                {
                                
                                }
                                else
                                {
                                numberFootnote = Integer.parseInt(arSingleNumbers[a]);
                                if(a==arSingleNumbers.length-2)
                                {
                                    System.out.println("in length-2: "+ numberFootnote);
                                 try
                                    {   
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+" and ");
                                    }
                                 catch(Exception e)
                                 {
                                     final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                     final JDialog d = pane.createDialog(null, "ERROR");
                                     d.setLocation(450, 430);
                                     d.setVisible(true);
                                 }
                                    }
                                else if(a==arSingleNumbers.length-1)
                                {
                                    try
                                    {
                                         sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+".");    //error message, no colon in footnotes
                                    }
                                    catch(Exception e)
                                    {
                                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(450, 430);
                                        d.setVisible(true);
                                    }
                                
                                }
                                else
                                { 
                                    try
                                    {
                                     sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+", ");
                                    }
                                    catch(Exception e)
                                    {
                                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(450, 430);
                                        d.setVisible(true);
                                        
                                    }
                                
                                }
                                  }
                            }
                        
                            System.out.println("in moderate dinna");
                            paragraph3.appendChild(doc.createTextNode("These data were graded as being of ‘moderate’ quality because of issues relating to "+sbFootnotes.toString()+" This " 
                                    + "means that further research is likely to have an important impact on our confidence in the estimate of effect and may change the estimate."));
                                
                        }
                          else if(arRelevantText[17].contains("high"))
                        { 
                            String numbers = arRelevantText[17].substring(arRelevantText[17].indexOf("high")+4,arRelevantText[17].length());
                            numbers=numbers.replace("\n", "");
                            String [] arSingleNumbers = numbers.split(",");
                            int numberFootnote=0;
                           
                            for(int a=0;a<arSingleNumbers.length;a++)
                            {
                                  if(arSingleNumbers[a].isEmpty())
                                {
                                
                                }
                                  else
                                  {
                                numberFootnote = Integer.parseInt(arSingleNumbers[a]);
                                if(a==arSingleNumbers.length-2)
                                {
                                    System.out.println("in length-2: "+ numberFootnote);
                                 try
                                    {   
                                 sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+" and ");
                                    }
                                 catch(Exception e)
                                 {
                                     final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                     final JDialog d = pane.createDialog(null, "ERROR");
                                     d.setLocation(450, 430);
                                     d.setVisible(true);
                                 }
                                    }
                                else if(a==arSingleNumbers.length-1)
                                {
                                    try
                                    {
                                         sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+".");    //error message, no colon in footnotes
                                    }
                                    catch(Exception e)
                                    {
                                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(450, 430);
                                        d.setVisible(true);
                                    }
                                
                                }
                                else
                                { 
                                    try
                                    {
                                     sbFootnotes.append(arFootnotes[numberFootnote-1].substring(0,arFootnotes[numberFootnote-1].indexOf(":"))+", ");
                                    }
                                    catch(Exception e)
                                    {
                                        final JOptionPane pane = new JOptionPane("Please check if each footnote contains a colon.");
                                        final JDialog d = pane.createDialog(null, "ERROR");
                                        d.setLocation(450, 430);
                                        d.setVisible(true);
                                        
                                    }
                                
                                }
                                  }
                            }
                            
                        
                            System.out.println("in high dinna");
                            paragraph3.appendChild(doc.createTextNode("These data were graded as being of ‘high’ quality because of issues relating to "+sbFootnotes.toString()+" This " 
                                    + "means that further research is very unlikely to change our confidence in the estimate of effect."));
                                
                        }
                    }
                    
                    
                    subsection2.appendChild(paragraph2);
                    subsection2.appendChild(paragraph3);
                    
                    
                    for(int a=0;a<arRelevantText.length;a++)
                    {
                    System.out.println("array text: "+a+" text "+arRelevantText[a]);                   
                    arRelevantText[a]=null;
                    
                    }
                    
                    }
                    
                    
                    
                }
            }
            
            if(breaker==false)
            {
            paragraph = doc.createElement("P");
            subsection2.appendChild(paragraph);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*--- End of HAL generated text "+formatter.format(currentTime)+" ---*"));
            paragraph.appendChild(marker);
            
            
            
            }
         
            
          
	  } catch (Exception ex) {
		System.out.println(ex);
                ex.printStackTrace();
                return false;
	  }
            
    }
    
    try{
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
           cleaner.deleteSpecialChars(filepath);
 
            System.out.println("Done");
            return true;
    }
    catch(Exception e)
    {
        System.out.println(e);
                e.printStackTrace();
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
    

