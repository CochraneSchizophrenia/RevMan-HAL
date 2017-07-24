package revmanhalcochranemode;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import revmanhalcochranemode.Complex1;
import static revmanhalcochranemode.SOFDiscussion.removeAllChildren;


public class Abstract
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
    private Vector vLastEntries = new Vector();
    
    //test
    
    
    public Abstract (String lang)
            {
            language = lang;
            }
    
    public boolean main (String file) 
    {
        Element paragraph = null;
        Element marker = null;

       
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
            NodeList nl3 = doc.getElementsByTagName("INCLUDED_CHAR");
            
            String tempWholeText="";
            String onlyParticipants="";
            int counterStudies =0;
            int totalNumberParticipants=0;
            int yearMin=2100;
            int yearMax=1900;
            int tempAtt=0;
            
            Node abs_results = doc.getElementsByTagName("ABS_RESULTS").item(0);
            
            //Remove old Summary of main results
            removeAllChildren(abs_results);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
            Date currentTime = new Date();  
            
            paragraph = doc.createElement("P");            
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*------ Start of HAL generated text "+formatter.format(currentTime)+" ------*     PLEASE NOTE: Solely from SoF tables."));
            paragraph.appendChild(marker);
            abs_results.appendChild(paragraph);
            
            
            
            for(int i2 = 0;i2<nl3.getLength();i2++)
            {
                Element included =  (Element)doc.getElementsByTagName("INCLUDED_CHAR").item(i2);
                 
                String attIncluded=included.getAttribute("STUDY_ID");
                System.out.println("attIncl "+attIncluded);
                String cleanerAtt = attIncluded.substring(0,attIncluded.lastIndexOf("-"));
                try
                {
                tempAtt= Integer.parseInt(attIncluded.substring(attIncluded.lastIndexOf("-")+1,attIncluded.lastIndexOf("-")+5));
                }
                catch (Exception e)
                {
                    try
                    {
                        tempAtt= Integer.parseInt(cleanerAtt.substring(cleanerAtt.length()-4,cleanerAtt.length()));
                    }
                    catch (Exception ex)
                    {
                        final JOptionPane pane = new JOptionPane("Unfortunately it was not possible to get the years out of the tables. Please check them manually. ");
                       final JDialog d = pane.createDialog(null, "ERROR");
                       d.setLocation(450, 430);
                       d.setVisible(true);
                       
                    }
                }
                        
                
                if(tempAtt<yearMin)
                {
                    yearMin=tempAtt;
                }
                else if(tempAtt>yearMax)
                {
                    yearMax=tempAtt;
                }
                
                
                tempWholeText = included.getElementsByTagName("CHAR_PARTICIPANTS").item(0).getTextContent().trim();                 
                
                
                
                try
                {
                              
                    tempWholeText=tempWholeText.replaceAll(" ", "");
                    System.out.println("temp WholeText "+tempWholeText);
                    String nUnclean1=tempWholeText.substring(tempWholeText.indexOf("N="), tempWholeText.indexOf("N=")+9);         
                     System.out.println("unclean1  "+nUnclean1);
                     
                    String nClean=nUnclean1.substring(nUnclean1.indexOf("N=")+2,nUnclean1.indexOf("."));                 
                    int n = Integer.parseInt(nClean);
                    totalNumberParticipants=totalNumberParticipants+n;
                    System.out.println("n=: "+n);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.out.println("Fehler at entry: "+i2);
                    int l =i2+1;
                    final JOptionPane pane = new JOptionPane("Please check if the number of participants in the 'characteristics of included studies' table no "+l+"\n"+" is in the right format (e.g. N = any numbers, a dot after that -->N=546.)! ");
                       final JDialog d = pane.createDialog(null, "ERROR");
                       d.setLocation(450, 430);
                       d.setVisible(true);
                }                        
                counterStudies = counterStudies+1; 
                
               
            }
          
             System.out.println("year Max: "+yearMax+" year Min: "+yearMin+" counterStudies "+counterStudies+" totalNumber: "+totalNumberParticipants);
             Element paragraph3 = doc.createElement("P");
             paragraph3.appendChild(doc.createTextNode("We identified "+counterStudies+" relevant studies (total "
                     + "number of participants="+totalNumberParticipants+") published between "+yearMin+" and "+yearMax+"."));
             abs_results.appendChild(paragraph3);
             
            for (int i = 0; i < nl.getLength(); i++)
            {
                if(breaker==true)
                {
                    break;
                }
                counterRow=0;
                Element comp =  (Element)doc.getElementsByTagName("SOF_TABLE").item(i);
                String comparison = comp.getElementsByTagName("TITLE").item(0).getTextContent().trim();
                
                
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
                
                 String[]arRelevantText = new String[20];
                 
                for (int j = 5; j < nl1.getLength(); j++) 
                {         
                    
                 stColumnOne="";
                 stColumnTwo="";
                 stColumnThree="";
                 
                    
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
                    arRelevantText[17]=stColumnFive;
                    
                    if(arRelevantText[17].contains("low")||arRelevantText[17].contains("high")
                           || arRelevantText[17].contains("moderate"))
                    {
                  try
                        {
                       String []arSpecialCase = arRelevantText[17].split("\n");
                       stColumnFourTwo = arSpecialCase[1];
                       System.out.println("special case: "+arSpecialCase[1]);
                        }
                        catch (Exception e)
                        {
                            if(stColumnFour.contains("very low"))
                            {
                                stColumnFourTwo="very low";
                            }
                            else if(stColumnFour.contains("low") && !stColumnFour.contains("very"))
                            {
                                 stColumnFourTwo="low";
                            }
                            else if(stColumnFour.contains("moderate"))
                            {
                                 stColumnFourTwo="moderate";
                            }
                            else if(stColumnFour.contains("high"))
                            {
                                 stColumnFourTwo="high";
                            }
                        }
                    }
                    if(stColumnFourTwo.isEmpty())
                    {
                        stColumnFourTwo="NO INFORMATION";
                    }
                    if(stColumnFour.isEmpty())
                    {
                       stColumnFour="NO INFORMATION";
                    }
                       
                     System.out.println("ar6: "+arRelevantText[6]);
                    
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
                   
                    
                    if(arRelevantText[17].contains("low")||arRelevantText[17].contains("high")
                           || arRelevantText[17].contains("moderate"))
                    {
                        try
                        {
                       String []arSpecialCase = arRelevantText[17].split("\n");
                       stColumnFourTwo = arSpecialCase[1];
                       System.out.println("special case: "+arSpecialCase[1]);
                        }
                        catch (Exception e)
                        {
                            if(stColumnFour.contains("very low"))
                            {
                                stColumnFourTwo="very low";
                            }
                            else if(stColumnFour.contains("low") && !stColumnFour.contains("very"))
                            {
                                 stColumnFourTwo="low";
                            }
                            else if(stColumnFour.contains("moderate"))
                            {
                                 stColumnFourTwo="moderate";
                            }
                            else if(stColumnFour.contains("high"))
                            {
                                 stColumnFourTwo="high";
                            }
                        }
                    }
                    if(stColumnFourTwo.isEmpty())
                    {
                        stColumnFourTwo="NO INFORMATION";
                    }
                    if(stColumnFour.isEmpty())
                    {
                       stColumnFour="NO INFORMATION";
                    }
                    
                    
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
                       
                      
                        
                        String wrongFormattedText="";
                        String partOne="";
                        
                        
                        if(arRelevantText[0].contains("Follow-up"))
                        {
                           partOne =  arRelevantText[0].substring(0,arRelevantText[0].indexOf("Follow-up")-1);
                           wrongFormattedText = arRelevantText[0].substring(arRelevantText[0].indexOf("Follow-up"), arRelevantText[0].length());
                           wrongFormattedText = wrongFormattedText.toLowerCase();
                           arRelevantText[0]=partOne+"; "+wrongFormattedText;
                        }
                        
                        System.out.println("wrong formatted: "+wrongFormattedText);
                        firstTime = true;
                        counterRow++;
                        counter = 5;


                        paragraph2 = doc.createElement("P");
                        paragraph3 = doc.createElement("P");
                        
                        Element italic = doc.createElement("I");
                        
                        
                       
                        
                        
                    
                if((arRelevantText[5]==null||arRelevantText[5].equals(""))&&checkSecondColumn==false)
                    {
                       try
                       {
                          vLastEntries.addElement(arRelevantText[0].substring(0,arRelevantText[0].indexOf(":")));
                          
                       }
                       catch (Exception e)                               
                       {                           
                    
                        paragraph2.appendChild(doc.createTextNode("THERE WAS AN ERROR IN THE TABLE! (Please check e.g. for colons in the outcome column)"));
                             
                       }
                       
                     
                    }
                    
                     else if(arRelevantText[6].contains("mean"))
                        {
                            String values ="";
                            try 
                            {                                
                                    values = arRelevantText[6].substring(arRelevantText[6].indexOf(".") - 1, arRelevantText[6].length());
                                
                            }
                            catch (Exception e)
                            {
                                values ="NO INFORMATION";
                            }
                            
                            if(arRelevantText[1].contains("("))
                            {
                            
                            paragraph2.appendChild(doc.createTextNode("For the continuous measure of '"
                                            + arRelevantText[0] + "' the mean was "+ values
                                            + "; " + arRelevantText[1].substring(arRelevantText[1].indexOf("(") + 1, arRelevantText[1].indexOf(" ")) + " RCT, n=" + arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + ", quality "));

                                    italic.appendChild(doc.createTextNode(stColumnFourTwo));

                                    paragraph2.appendChild(italic);
                                    paragraph2.appendChild(doc.createTextNode(")."));
                            
                            }
                            else
                            {
                                 paragraph2.appendChild(doc.createTextNode("For the continuous measure of '"
                                            + arRelevantText[0] + "' the mean was "+ values
                                            + "; " +"NO INFORMATION ABOUT RCT AND PARTICIPANTS, quality "));

                                    italic.appendChild(doc.createTextNode(stColumnFourTwo));

                                    paragraph2.appendChild(italic);
                                    paragraph2.appendChild(doc.createTextNode(")."));
                                
                            }

                        
                        }
                      
                    else if(arRelevantText[3].equalsIgnoreCase("Study population"))
                    {
                        String relativeEffect="";
                        String values ="";
                        if(arRelevantText[2].contains("RR"))
                        {
                            relativeEffect = "RR";
                            values=arRelevantText[2].substring(arRelevantText[2].indexOf(".") -1, arRelevantText[2].length()-1);
                        }
                        else if(arRelevantText[2].contains("HR"))
                        {
                            relativeEffect = "HR";
                            values=arRelevantText[2].substring(arRelevantText[2].indexOf(".") -1, arRelevantText[2].length()-1);
                        }
                        else 
                        {
                            relativeEffect = "NOTHING FOUND";
                           
                        }
                       
                        if(arRelevantText[1].contains("("))
                        {
                        paragraph2.appendChild(doc.createTextNode("For the binary outcome of '"
                                + arRelevantText[0]+ "' the "+relativeEffect+" was " +values
                                +", "+arRelevantText[1].substring(arRelevantText[1].indexOf("(")+1, arRelevantText[1].indexOf(" "))+" RCT, n="+arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + ", quality "));
                        }
                        else
                        {
                         paragraph2.appendChild(doc.createTextNode("For the binary outcome of '"
                                + arRelevantText[0]+ "' the "+relativeEffect+" was " +values
                                +", NO INFORMATION ABOUT RCT AND NUMBER OF PARTICIPANTS, quality "));
                        }
                      
                        italic.appendChild(doc.createTextNode(stColumnFourTwo));
                       
                        
                        paragraph2.appendChild(italic);
                        paragraph2.appendChild(doc.createTextNode(")."));

                    }
                    
                       
                       
                    else if(arRelevantText[7]==null||arRelevantText[10]==null||arRelevantText[13]==null)
                    {  String relativeEffect="";
                        String values ="";
                        if(arRelevantText[2].contains("RR"))
                        {
                            relativeEffect = "RR";
                            values=arRelevantText[2].substring(arRelevantText[2].indexOf(".") -1, arRelevantText[2].length()-1);
                        }
                        else if(arRelevantText[2].contains("HR"))
                        {
                            relativeEffect = "HR";
                            values=arRelevantText[2].substring(arRelevantText[2].indexOf(".") -1, arRelevantText[2].length()-1);
                        }
                        else 
                        {
                            relativeEffect = "NOTHING FOUND";
                           
                        }
                       
                        paragraph2.appendChild(doc.createTextNode("For the binary outcome of '"
                                + arRelevantText[0]+ "' the "+relativeEffect+" was " +values
                                +", "+arRelevantText[1].substring(arRelevantText[1].indexOf("(")+1, arRelevantText[1].indexOf(" "))+" RCT, n="+arRelevantText[1].substring(0, arRelevantText[1].indexOf("(")) + ", quality "));
                        
                        italic.appendChild(doc.createTextNode(stColumnFourTwo));
                        
                        paragraph2.appendChild(italic);
                        paragraph2.appendChild(doc.createTextNode(")."));

                    }
                
                    abs_results.appendChild(paragraph2);
                    abs_results.appendChild(paragraph3);
                    
                                   
                     
                    
                    for(int a=0;a<arRelevantText.length;a++)
                    {
                                  
                    arRelevantText[a]=null;
                    
                    }
                        
                    }
                    
                    
                    }
                    
                    
                    
                }
            if(breaker==false)
            {
                StringBuffer sbAllHowever = new StringBuffer(); 
              
                for (int it = 0; it < vLastEntries.size(); it++) 
                        {
                            if(it==vLastEntries.size()-2)
                            {
                            sbAllHowever.append(vLastEntries.elementAt(it).toString()+"' and '");
                            System.out.println("counterLast: "+it);
                            }
                            else if (it==vLastEntries.size()-1)
                            {
                                sbAllHowever.append(vLastEntries.elementAt(it).toString()+"' ");
                            }
                             else
                            {
                                sbAllHowever.append(vLastEntries.elementAt(it).toString()+"', '");
                            }
                        }
                   
                    if(vLastEntries.isEmpty())
                    {
                        
                    }
                    else
                    {
                            paragraph2 = doc.createElement("P");                           
                            
                            paragraph2.appendChild(doc.createTextNode(" However, for the key outcomes of '"
                                    + sbAllHowever.toString() + " "
                                    + "we found that no trial had  reported any usable data. "));
                            abs_results.appendChild(paragraph2);
                    }
                    
                    
            paragraph = doc.createElement("P");
            abs_results.appendChild(paragraph);
            marker = doc.createElement("MARKER");
            marker.appendChild(doc.createTextNode("*--- End of HAL generated text "+formatter.format(currentTime)+" ---*"));
            paragraph.appendChild(marker);            
            
            
            }
            }
            
              catch (Exception ex) 
                  {
		System.out.println(ex);
                ex.printStackTrace();
                return false;
	  }
           
    }
    
    try
    {
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