/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package revmanhalcochranemode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Collections;
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

/**
 *
 * @author msash9
 */


public class Description 
{
    
     private String revMan;
    private String csv;
    private String grade;
    private Vector vScales = new Vector();
    private Vector vExportedCsv = new Vector();
    private Element subsectionSub;
    private Element subsectionSub1;
    private Element subsectionSub2;
    private Element subsectionSub3;
    
    private int counterSubpoint1=1;
    private int counterSubpoint2=1;
    private int counterSubpoint3=1;
    private int counterSubpoint4=1;
    
    private int counterGeneral=0;
    
    private int counterFirstPoint1=1;
    private int counterFirstPoint2=1;
    private int counterFirstPoint3=1;
    private int counterFirstPoint4=1;
    
    
    private boolean lastEntry=false;
    private boolean firstTime = true;
    private boolean firstTime1 = true;
    private boolean firstTime2 = true;
    private boolean firstTime3 = true;
    private boolean nameScale = false;
    private boolean abbr = false;
    
    
    
    public Description (String revManFile, String csvFile)            
    {
            revMan = revManFile;
            csv=csvFile;
            this.readExportedCSV();
            this.readScales();
    }
            
    public boolean main()
    {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        
             Document doc = getDocument(revMan);
       
             
             boolean onlyOneTime=true;
             String textCsv;
             Vector vDataTable = new Vector();
             StringBuffer sbNotInCSV = new StringBuffer();
             
             for(int b=0;b<vScales.size();b++)
                                        {
                                        System.out.println("All Scales: "+vScales.elementAt(b));
                                        }
             
             for(int a=47;a<vExportedCsv.size();a++)   //start new
             {
                 String [] arCsvExport = vExportedCsv.elementAt(a).toString().split(";");
                 int zero = Integer.parseInt(arCsvExport[0]);
                 int one =Integer.parseInt(arCsvExport[1]);
                 int two =Integer.parseInt(arCsvExport[2]);            
                 

                if( zero > 0 && one > 0 && two >= 0 && arCsvExport[3].contains(":"))
                {
                 textCsv = arCsvExport[3].toString().substring(arCsvExport[3].indexOf(":"),arCsvExport[3].length());  // end new
                 
		
                     for(int c=2;c<vScales.size();c=c+32)   //was 22
                             {
                                System.out.println("scales bla: "+vScales.elementAt(c).toString()+" c "+c+" size: "+vScales.size());
                                String word="";
                                boolean fromHere=false;
                                boolean alreadyInCsv=false;
                                   try
                                    {  
                                        for (int h = 0; h < textCsv.length(); h++) 
                                        {
                                            
                                            if (textCsv.substring(h,h+1).equals(","))
                                            {
                                                break;
                                            }
                                            else
                                            {                                                
                                                 
                                                if(textCsv.substring(h,h+1).equals("("))
                                                {      
                                                    h=h+1;
                                                    word=word+textCsv.substring(h,h+1);    
                                                    fromHere=true;                                                    
                                                }
                                                else if(fromHere==true)
                                                {
                                                    word=word+textCsv.substring(h,h+1);   
                                                                                                       
                                                }
                                                else 
                                                {
                                                    
                                                }
                                                
                                            }
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                   
                                    for(int i=3;i<vScales.size();i=i+32)        // was 22 
                                    {      
                                        
                                        
                                        if(vScales.elementAt(i-3).toString().trim().equalsIgnoreCase("REF-SZ"))
                                        {
                                            break;
                                        }
                                     //   System.out.println("in already: "+word+"vScales: "+vScales.elementAt(i).toString());
                                        if((vScales.elementAt(i).toString().contains(word)
                                                || word.contains(vScales.elementAt(i).toString()))&&!vScales.elementAt(i).toString().isEmpty())
                                        {                                            
                                            alreadyInCsv = true;
                                        }
                                    }
                                
                                    System.out.println("already boolean: "+alreadyInCsv);
                                
                                if(alreadyInCsv==true && textCsv.contains(vScales.elementAt(c+1).toString()) && !vScales.elementAt(c+1).toString().equals("") )  // abbreviation
                                {                                    
                                  
                                        for(int d =0;d<vDataTable.size();d++)
                                        {  
                                            if(vDataTable.elementAt(d).toString().contains(vScales.elementAt(c).toString()))
                                                {
                                                    onlyOneTime=false;
                                                break;
                                                }
                                                else
                                                {
                                                    onlyOneTime=true;
                                                }                                           
                                        }
                                   
                                        if(onlyOneTime==true)
                                        {                                           
                                                
                                                 StringBuffer sbReference = new StringBuffer();
                                                 for(int e =c+4;e<c+20;e++)
                                                 {
                                                  System.out.println("in for sb: "+ vScales.elementAt(e) +" for scale: "+vScales.elementAt(c));
                                                  sbReference.append(vScales.elementAt(e)+"#");
                                                 }
                                                 vDataTable.addElement(vScales.elementAt(c-1)+"#"+vScales.elementAt(c)+"#"+vScales.elementAt(c+1)+"#"+vScales.elementAt(c+3)+"#"+vScales.elementAt(c+2)+"#"+sbReference.toString()); 
                                        }
                                        System.out.println("vDate Table django: "+vScales.elementAt(c-1)+"#"+vScales.elementAt(c)+"#"+vScales.elementAt(c+1)+"#"+vScales.elementAt(c+3)+"#"+vScales.elementAt(c+2));
                                       
                             }
                                else
                                {
                                   
                                        if (sbNotInCSV.toString().contains(word)) 
                                        {

                                        } 
                                        else if(alreadyInCsv==false)
                                        {
                                            sbNotInCSV.append(word+"#");
                                        }
                                       
                                       System.out.println("sbNot: "+sbNotInCSV.toString());
                                      
                                  
                                   
                                }
                                
                       
                                }	

             
                    }
             
            
         /*    for(int a=0;a<nl.getLength();a++)
             {
                 Element incl =  (Element)doc.getElementsByTagName("INCLUDED_CHAR").item(a);
                stCharOutcomes = incl.getElementsByTagName("CHAR_OUTCOMES").item(0).getTextContent().trim();
                System.out.println("stChar: "+stCharOutcomes +"length: "+nl.getLength());
                
                
                         String [] arOutcome = stCharOutcomes.split(" ");
                         
                         
                         for(int b=0;b<arOutcome.length;b++)
                         {
                             System.out.println("arOutcome: "+arOutcome[b].toString());
                             
                             for(int c=3;c<vScales.size();c=c+6)
                             {
                                System.out.println("scales: "+vScales.elementAt(c).toString());
                                
                                if(arOutcome[b].contains(vScales.elementAt(c).toString()))
                                    {
                                        for(int d =0;d<vDataTable.size();d++)
                                        {
                                            if(vDataTable.elementAt(d).toString().contains(vScales.elementAt(c).toString()))
                                            {
                                                onlyOneTime=false;
                                                break;
                                            }
                                            else
                                            {
                                                onlyOneTime=true;
                                            }
                                            
                                        }
                                            
                                        
                                        if(onlyOneTime==true)
                                        {
                                            vDataTable.addElement(vScales.elementAt(c-2)+"#"+vScales.elementAt(c-1)+"#"+vScales.elementAt(c)+"#"+vScales.elementAt(c+1)+"#"+vScales.elementAt(c+2)); 
                                           
                                        }
                                        
                                        
                                    }
                            
                             }
                          
                         }
          */
             }
            
            for(int i=0;i<vDataTable.size();i++)
            {
            System.out.println("dataTable: "+vDataTable.elementAt(i).toString());
            }
             
        
        Vector vAllReferences = new Vector();
        Node tag = doc.getElementsByTagName("INCLUDED_STUDIES_DESCR").item(0);
        
        Element subsection = doc.createElement("SUBSECTION");
        Element heading = doc.createElement("HEADING");

        subsection.appendChild(heading);
        Attr attr = doc.createAttribute("LEVEL");
        attr.setValue("4");
        heading.setAttributeNode(attr);
        heading.appendChild(doc.createTextNode("6. Outcome measures"));
        
        Element paragraph3 = doc.createElement("P");   // ADD TEXT (TEXT CAN ONLY BE ADDED IN P; MARKER IS PART OF PARAGRAPG3; PARAGRAPH3 IS PART OF INTERVENTION; INTERVENTION IS MAIN NODE)    
        Element marker = doc.createElement("MARKER");   // MARKER IS NEEDED TO ADD TEXT)
        marker.appendChild(doc.createTextNode("*------ Start of HAL generated text " + formatter.format(currentTime) + " ------* "));
        paragraph3.appendChild(marker);
        subsection.appendChild(paragraph3);

     //   Element paragraph = doc.createElement("P");
     //   paragraph.appendChild(doc.createTextNode(""));

     //   subsection.appendChild(paragraph);
        tag.appendChild(subsection);

        Collections.sort(vDataTable);
         String [] arSplittedText = new String[21];
         
         System.out.println("datatable size: "+vDataTable.size());
         
         if(vDataTable.size()==0)
         {
             Element paragraph = doc.createElement("P");
             paragraph.appendChild(doc.createTextNode("There was no scale found in the exported CSV file."));
             subsection.appendChild(paragraph);
             paragraph = doc.createElement("P");
             Element marker2 = doc.createElement("MARKER");
             marker2.appendChild(doc.createTextNode("*--- End of HAL generated text " + formatter.format(currentTime) + " ---*"));
             paragraph.appendChild(marker2);
             subsection.appendChild(paragraph);  
         }
        
        for (int c = 0; c < vDataTable.size(); c++) 
        {
            
            System.out.println("vector: " + vDataTable.elementAt(c).toString());
            
            if(c==vDataTable.size()-1)
            {
                lastEntry=true;
            }
            
        String [] arSplittedText2 = vDataTable.elementAt(c).toString().split("#");
        
         for(int f=0;f<21;f++)
            {
                arSplittedText[f]="";                
                
                try
                {
                    arSplittedText[f]=arSplittedText2[f];
                }
                catch(Exception e)
                {
                
                }
            }
         
         
        for(int d=0;d<arSplittedText.length;d++)
        {
       
            System.out.println("array splitted: "+arSplittedText[d]);
        }
        
             if(arSplittedText[0].equalsIgnoreCase("Mental state scales"))
             {
                 if(firstTime==true)
                 {
                     counterGeneral=counterGeneral+1;
                     
                     subsectionSub = doc.createElement("SUBSECTION");
                     Element heading2 = doc.createElement("HEADING");                     
                     subsectionSub.appendChild(heading2);
                     Attr attr2 = doc.createAttribute("LEVEL");
                     attr2.setValue("5");
                     heading2.setAttributeNode(attr2);
                     heading2.appendChild(doc.createTextNode("6."+counterGeneral+" "+arSplittedText[0]));

                     firstTime = false;
                     counterFirstPoint1 = counterGeneral;
                     
                 }
                 
                 Element paragraph2 = doc.createElement("P");
                 Element italic = doc.createElement("I");
                 italic.appendChild(doc.createTextNode("6."+counterFirstPoint1+"."+counterSubpoint1+" "+arSplittedText[1]+" - "+arSplittedText[2]));
                 paragraph2.appendChild(italic);
                 paragraph2.appendChild(doc.createTextNode(" ("));

                 Element link = doc.createElement("LINK");
                 Attr attrLink1 = doc.createAttribute("REF");
                 Attr attrLink2 = doc.createAttribute("TYPE");
                 String stAttrLink1 = arSplittedText[4].replaceAll(" ", "-");
                 attrLink1.setValue("REF-" + stAttrLink1);
                 attrLink2.setValue("REFERENCE");
                 link.setAttributeNode(attrLink1);
                 link.setAttributeNode(attrLink2);
                 link.appendChild(doc.createTextNode(arSplittedText[4]));
                 paragraph2.appendChild(link); 

                 paragraph2.appendChild(doc.createTextNode(")"));
                 Element br = doc.createElement("BR");
                 paragraph2.appendChild(br);
                 paragraph2.appendChild(doc.createTextNode(arSplittedText[3]));

                 subsectionSub.appendChild(paragraph2);
                 
                 
                 
                 if(lastEntry==true)
                 {
                     String []arNotInCsvSplit= sbNotInCSV.toString().split("#");
                     
                     Element paragraph4=doc.createElement("P");
                     Element italic2 = doc.createElement("I");
                     Element bold = doc.createElement("B");
                     bold.appendChild(doc.createTextNode("These scales are in the 'Data and Analysis' but were not found in the scales-CSV-file:"));
                     Element br3 = doc.createElement("BR"); 
                     paragraph4.appendChild(bold);
                     paragraph4.appendChild(br3);
                     paragraph4.appendChild(italic2);
                     
                     
                     for(int y=0;y<arNotInCsvSplit.length;y++)
                     {
                          italic2.appendChild(doc.createTextNode(arNotInCsvSplit[y]));
                          Element br2 = doc.createElement("BR"); 
                          italic2.appendChild(br2);
                     }
                     
                    
                     
                     
                     Element paragraph = doc.createElement("P");
                     Element marker2 = doc.createElement("MARKER");
                     marker2.appendChild(doc.createTextNode("*--- End of HAL generated text " + formatter.format(currentTime) + " ---*"));
                     paragraph.appendChild(marker2);
                     subsectionSub.appendChild(paragraph4);
                     subsectionSub.appendChild(paragraph);                     
                 }
                 subsection.appendChild(subsectionSub);            
                 counterSubpoint1=counterSubpoint1+1;
                 
                 

             }
             else if(arSplittedText[0].equalsIgnoreCase("Global state scales"))
             {
                   if(firstTime1==true)
                 {
                     counterGeneral=counterGeneral+1;
                     
                     subsectionSub1 = doc.createElement("SUBSECTION");
                     Element heading2 = doc.createElement("HEADING");

                     subsectionSub1.appendChild(heading2);
                     Attr attr2 = doc.createAttribute("LEVEL");
                     attr2.setValue("5");
                     heading2.setAttributeNode(attr2);
                     heading2.appendChild(doc.createTextNode("6."+counterGeneral+" "+arSplittedText[0]));

                     firstTime1 = false;
                     counterFirstPoint2 = counterGeneral;
                     
                 }
                 
                 Element paragraph2 = doc.createElement("P");
                 Element italic = doc.createElement("I");
                 italic.appendChild(doc.createTextNode("6."+counterFirstPoint2+"."+counterSubpoint1+" "+arSplittedText[1]+" - "+arSplittedText[2]));
                 paragraph2.appendChild(italic);
                 paragraph2.appendChild(doc.createTextNode(" ("));

                 Element link = doc.createElement("LINK");
                 Attr attrLink1 = doc.createAttribute("REF");
                 Attr attrLink2 = doc.createAttribute("TYPE");
                 String stAttrLink1 = arSplittedText[4].replaceAll(" ", "-");
                 attrLink1.setValue("REF-" + stAttrLink1);
                 attrLink2.setValue("REFERENCE");
                 link.setAttributeNode(attrLink1);
                 link.setAttributeNode(attrLink2);
                 link.appendChild(doc.createTextNode(arSplittedText[4]));
                 paragraph2.appendChild(link);

                 paragraph2.appendChild(doc.createTextNode(")"));
                 Element br = doc.createElement("BR");
                 paragraph2.appendChild(br);
                 paragraph2.appendChild(doc.createTextNode(arSplittedText[3]));
                 

                 subsectionSub1.appendChild(paragraph2);                 
                 
                 if(lastEntry==true)
                 {
                      String []arNotInCsvSplit= sbNotInCSV.toString().split("#");
                     
                     Element paragraph4=doc.createElement("P");
                     Element italic2 = doc.createElement("I");
                     Element bold = doc.createElement("B");
                     bold.appendChild(doc.createTextNode("These scales are in the 'Data and Analysis' but were not found in the scales-CSV-file:"));
                     Element br3 = doc.createElement("BR"); 
                     paragraph4.appendChild(bold);
                     paragraph4.appendChild(br3);
                     paragraph4.appendChild(italic2);
                     
                     
                     for(int y=0;y<arNotInCsvSplit.length;y++)
                     {
                          italic2.appendChild(doc.createTextNode(arNotInCsvSplit[y]));
                          Element br2 = doc.createElement("BR"); 
                          italic2.appendChild(br2);
                          System.out.println("arSplit: "+arNotInCsvSplit[y]);
                     }
                     
                     Element paragraph = doc.createElement("P");
                     Element marker2 = doc.createElement("MARKER");
                     marker2.appendChild(doc.createTextNode("*--- End of HAL generated text " + formatter.format(currentTime) + " ---*"));
                     paragraph.appendChild(marker2);
                     subsectionSub1.appendChild(paragraph4);
                     subsectionSub1.appendChild(paragraph);                     
                 }
                 subsection.appendChild(subsectionSub1);                 
                 counterSubpoint1=counterSubpoint1+1;
                 

             }
             else if(arSplittedText[0].equalsIgnoreCase("Adverse effects scales"))
             {
                   if(firstTime2==true)
                 {
                     counterGeneral=counterGeneral+1;
                     
                     subsectionSub2 = doc.createElement("SUBSECTION");
                     Element heading2 = doc.createElement("HEADING");

                     subsectionSub2.appendChild(heading2);
                     Attr attr2 = doc.createAttribute("LEVEL");
                     attr2.setValue("5");
                     heading2.setAttributeNode(attr2);
                     heading2.appendChild(doc.createTextNode("6."+counterGeneral+" "+arSplittedText[0]));

                     firstTime2 = false;
                     counterFirstPoint3 = counterGeneral;
                     
                 }
                 
                 Element paragraph2 = doc.createElement("P");
                 Element italic = doc.createElement("I");
                 italic.appendChild(doc.createTextNode("6."+counterFirstPoint3+"."+counterSubpoint3+" "+arSplittedText[1]+" - "+arSplittedText[2]));
                 paragraph2.appendChild(italic);
                 paragraph2.appendChild(doc.createTextNode(" ("));

                 Element link = doc.createElement("LINK");
                 Attr attrLink1 = doc.createAttribute("REF");
                 Attr attrLink2 = doc.createAttribute("TYPE");
                 String stAttrLink1 = arSplittedText[4].replaceAll(" ", "-");
                 attrLink1.setValue("REF-" + stAttrLink1);
                 attrLink2.setValue("REFERENCE");
                 link.setAttributeNode(attrLink1);
                 link.setAttributeNode(attrLink2);
                 link.appendChild(doc.createTextNode(arSplittedText[4]));
                 paragraph2.appendChild(link); 

                 paragraph2.appendChild(doc.createTextNode(")"));
                 Element br = doc.createElement("BR");
                 paragraph2.appendChild(br);
                 paragraph2.appendChild(doc.createTextNode(arSplittedText[3]));

                 subsectionSub2.appendChild(paragraph2);
                 
                 if(lastEntry==true)
                 {
                      String []arNotInCsvSplit= sbNotInCSV.toString().split("#");
                     
                     Element paragraph4=doc.createElement("P");
                     Element italic2 = doc.createElement("I");
                     Element bold = doc.createElement("B");
                     bold.appendChild(doc.createTextNode("These scales are in the 'Data and Analysis' but were not found in the scales-CSV-file:"));
                     Element br3 = doc.createElement("BR"); 
                     paragraph4.appendChild(bold);
                     paragraph4.appendChild(br3);
                     paragraph4.appendChild(italic2);
                     
                     
                     for(int y=0;y<arNotInCsvSplit.length;y++)
                     {
                          italic2.appendChild(doc.createTextNode(arNotInCsvSplit[y]));
                          Element br2 = doc.createElement("BR"); 
                          italic2.appendChild(br2);
                     }
                     
                     Element paragraph = doc.createElement("P");
                     Element marker2 = doc.createElement("MARKER");
                     marker2.appendChild(doc.createTextNode("*--- End of HAL generated text " + formatter.format(currentTime) + " ---*"));
                     paragraph.appendChild(marker2);
                     subsectionSub2.appendChild(paragraph4);
                     subsectionSub2.appendChild(paragraph);
                     
                 }
                 subsection.appendChild(subsectionSub2);
                 counterSubpoint3=counterSubpoint3+1;
             }
             else if(arSplittedText[0].equalsIgnoreCase("Quality of Life"))
             {
                if(firstTime3==true)
                 {
                     counterGeneral=counterGeneral+1;
                     
                     subsectionSub3 = doc.createElement("SUBSECTION");
                     Element heading2 = doc.createElement("HEADING");
                     subsectionSub3.appendChild(heading2);
                     Attr attr2 = doc.createAttribute("LEVEL");
                     attr2.setValue("5");
                     heading2.setAttributeNode(attr2);
                     heading2.appendChild(doc.createTextNode("6."+counterGeneral+" "+arSplittedText[0]));

                     firstTime3 = false;
                     counterFirstPoint4 = counterGeneral;
                     
                 }
                 
                 Element paragraph2 = doc.createElement("P");
                 Element italic = doc.createElement("I");
                 italic.appendChild(doc.createTextNode("6."+counterFirstPoint4+"."+counterSubpoint4+" "+arSplittedText[1]+" - "+arSplittedText[2]));
                 paragraph2.appendChild(italic);
                 paragraph2.appendChild(doc.createTextNode(" ("));

                 Element link = doc.createElement("LINK");
                 Attr attrLink1 = doc.createAttribute("REF");
                 Attr attrLink2 = doc.createAttribute("TYPE");
                 String stAttrLink1 = arSplittedText[4].replaceAll(" ", "-");
                 attrLink1.setValue("REF-" + stAttrLink1);
                 attrLink2.setValue("REFERENCE");
                 link.setAttributeNode(attrLink1);
                 link.setAttributeNode(attrLink2);
                 link.appendChild(doc.createTextNode(arSplittedText[4]));
                 paragraph2.appendChild(link); 

                 paragraph2.appendChild(doc.createTextNode(")"));
                 Element br = doc.createElement("BR");
                 paragraph2.appendChild(br);
                 paragraph2.appendChild(doc.createTextNode(arSplittedText[3]));

                 subsectionSub3.appendChild(paragraph2);
                 

                 
                 
                 if(lastEntry==true)
                 {
                      String []arNotInCsvSplit= sbNotInCSV.toString().split("#");
                     
                     Element paragraph4=doc.createElement("P");
                     Element italic2 = doc.createElement("I");
                     Element bold = doc.createElement("B");
                     bold.appendChild(doc.createTextNode("These scales are in the 'Data and Analysis' but were not found in the scales-CSV-file:"));
                     Element br3 = doc.createElement("BR"); 
                     paragraph4.appendChild(bold);
                     paragraph4.appendChild(br3);
                     paragraph4.appendChild(italic2);
                     
                     
                     for(int y=0;y<arNotInCsvSplit.length;y++)
                     {
                          italic2.appendChild(doc.createTextNode(arNotInCsvSplit[y]));
                          Element br2 = doc.createElement("BR"); 
                          italic2.appendChild(br2);
                     }
                     
                     Element paragraph = doc.createElement("P");
                     Element marker2 = doc.createElement("MARKER");
                     marker2.appendChild(doc.createTextNode("*--- End of HAL generated text " + formatter.format(currentTime) + " ---*"));
                     paragraph.appendChild(marker2);
                     subsectionSub3.appendChild(paragraph4);
                     subsectionSub3.appendChild(paragraph);                     
                 }
                 subsection.appendChild(subsectionSub3);
                 counterSubpoint4=counterSubpoint4+1;
             }
       
                System.out.println("in counterRef: "+arSplittedText[4]);
                String type ="";
                Node tag2 = doc.getElementsByTagName("ADDITIONAL_REFERENCES").item(0);
                Element reference = doc.createElement("REFERENCE");
                
                
                tag2.appendChild(reference);                 
                
                String stReference="REF-"+arSplittedText[4].toString().trim().replace(" ", "-");
                String stName = arSplittedText[4];
                
                
                for(int w = 0;w<vAllReferences.size();w++)   
                {
                     if(vAllReferences.elementAt(w).toString().equals(stReference))
                     {
                         stReference= stReference+"a";
                         stName = stName+"a";
                     }
                }
                
                System.out.println("stName: "+stName+" st Ref: "+stReference);
                
                     Attr attr2 = doc.createAttribute("ID");
                     attr2.setValue(stReference);
                     reference.setAttributeNode(attr2);
                     
                     vAllReferences.addElement(stReference);
                     
                     Attr attr3 = doc.createAttribute("NAME");
                     attr3.setValue(stName);
                     reference.setAttributeNode(attr3);
                     
                     Attr attr4 = doc.createAttribute("TYPE");
                     if(arSplittedText[5].equalsIgnoreCase("Conference proceedings"))
                     {
                         type = "CONFERENCE_PROC";
                     }
                     else if(arSplittedText[5].equalsIgnoreCase("Journal article"))
                     {
                         type = "JOURNAL_ARTICLE";
                     }
                     else if(arSplittedText[5].equalsIgnoreCase("Section of Book")||arSplittedText[5].equalsIgnoreCase("Book"))
                     {
                         type = "BOOK_SECTION";
                     }
                      else if(arSplittedText[5].equalsIgnoreCase("Other"))
                     {
                         type = "OTHER";
                     }                     
                     
                     attr4.setValue(type);
                     reference.setAttributeNode(attr4);                     
               
                    
                     System.out.println("before tags: "+arSplittedText[16]);
                     
                      if(!arSplittedText[6].equals(""))
                      {
                          Element au = doc.createElement("AU");
                          au.appendChild(doc.createTextNode(arSplittedText[6].trim()));
                          reference.appendChild(au);
                      }
                      if(!arSplittedText[7].equals(""))
                      {
                          Element ti = doc.createElement("TI");
                          ti.appendChild(doc.createTextNode(arSplittedText[7].trim()));
                          reference.appendChild(ti);
                      }
                      if(!arSplittedText[8].equals(""))
                      {
                          Element to = doc.createElement("TO");
                          to.appendChild(doc.createTextNode(arSplittedText[8].trim()));
                          reference.appendChild(to);
                      }
                      if(!arSplittedText[9].equals(""))
                      {
                          Element so = doc.createElement("SO");
                          so.appendChild(doc.createTextNode(arSplittedText[9].trim()));
                          reference.appendChild(so);
                      }
                      if(!arSplittedText[10].equals(""))
                      {
                          Element yr = doc.createElement("YR");
                          yr.appendChild(doc.createTextNode(arSplittedText[10].trim()));
                          reference.appendChild(yr);
                      }
                        if(!arSplittedText[11].equals(""))
                      {
                          Element en = doc.createElement("EN");
                          en.appendChild(doc.createTextNode(arSplittedText[11].trim()));
                          reference.appendChild(en);
                      }
                      if(!arSplittedText[12].equals(""))
                      {
                          Element pb = doc.createElement("PB");
                          pb.appendChild(doc.createTextNode(arSplittedText[12].trim()));
                          reference.appendChild(pb);
                      }
                       if(!arSplittedText[13].equals(""))
                      {
                          Element cy = doc.createElement("CY");
                          cy.appendChild(doc.createTextNode(arSplittedText[13].trim()));
                          reference.appendChild(cy);
                      }
                      if(!arSplittedText[14].equals(""))
                      {
                          Element vl = doc.createElement("VL");
                          vl.appendChild(doc.createTextNode(arSplittedText[14].trim()));
                          reference.appendChild(vl);
                      }
                      if(!arSplittedText[15].equals(""))
                      {
                          Element no = doc.createElement("NO");
                          no.appendChild(doc.createTextNode(arSplittedText[15]));
                          reference.appendChild(no);
                      }
                      if(!arSplittedText[16].equals(""))
                      {
                          Element pg = doc.createElement("PG");
                          pg.appendChild(doc.createTextNode(arSplittedText[16].trim()));
                          reference.appendChild(pg);
                      }
                      if(!arSplittedText[17].equals(""))
                      {
                          Element ed = doc.createElement("ED");
                          ed.appendChild(doc.createTextNode(arSplittedText[17].trim()));
                          reference.appendChild(ed);
                      }
                       if(!arSplittedText[18].equals(""))
                      {
                          Element md = doc.createElement("MD");
                          md.appendChild(doc.createTextNode(arSplittedText[18].trim()));
                          reference.appendChild(md);
                      }
                       if(!arSplittedText[19].equals(""))
                      {
                          Element md = doc.createElement("NT");
                          md.appendChild(doc.createTextNode(arSplittedText[19].trim()));
                          reference.appendChild(md);
                      }
                     
                    
                    
                      if(!arSplittedText[20].equals(""))
                      {
                          Element identifiers = doc.createElement("IDENTIFIERS");
                          
                          Element identifier = doc.createElement("IDENTIFIER");
                          String typeID="";
                          
                          Attr attr5 = doc.createAttribute("TYPE");
                          Attr attr6 = doc.createAttribute("VALUE");                          
                          for (int p = 0; p < arSplittedText[20].length(); p++) 
                          {
                              if (arSplittedText[20].substring(p, p + 1).equals(" ")) 
                              {
                                  break;
                              } 
                              else 
                              {
                                  typeID = typeID + arSplittedText[20].substring(p, p + 1);
                              }
                          }
                          System.out.println("typeID: "+typeID);
                          
                          attr5.setValue(typeID);
                          attr6.setValue(arSplittedText[20].substring(typeID.length()+4, arSplittedText[20].length()));
                          
                          identifier.setAttributeNode(attr5);
                          identifier.setAttributeNode(attr6);
                          identifiers.appendChild(identifier);
                          reference.appendChild(identifiers);
                        
                      }
                      else if(arSplittedText[20].equals(""))
                      {
                          Element identifier = doc.createElement("IDENTIFIERS");
                               reference.appendChild(identifier);
                               System.out.println("in else identifiers");
                      }
      
          
      }
             
          try 
             {
                
                 TransformerFactory transformerFactory = TransformerFactory.newInstance();
                 Transformer transformer;

                 transformer = transformerFactory.newTransformer();
                 DOMSource source = new DOMSource(doc);
                 StreamResult result = new StreamResult(new File(revMan));   
                 transformer.transform(source, result);
                 
                 Complex1 cleaner = new Complex1(null);
                 cleaner.deleteSpecialChars(revMan);
                 System.out.println("DONE!");
                 return true;
             } 
             catch (Exception e) 
             {
                 e.printStackTrace();
                 return false;
             }
        
    }
 public void readExportedCSV()    
     {
         try
         {
                    FileReader fr = new FileReader(csv);
                    BufferedReader br = new BufferedReader(fr);                    
                    String line = br.readLine();
                    
                    String [] arr = line.split(";");
                        if (arr.length != 46) 
                        {
                       final JOptionPane pane = new JOptionPane("Chosen file has not the suitable structure: number of colums are not matching. Please export it again or choose another file.");
                       final JDialog d = pane.createDialog(null, "ERROR");
                       d.setLocation(450, 430);
                       d.setVisible(true);                        
                        }
                        else
                        {
                            vExportedCsv.addElement(line);
                            while((line=br.readLine())!= null)
                            {                         
                                    vExportedCsv.addElement(line);                       
                            }
                        }
                     
                     br.close();
                     fr.close();
                    }
              
        catch (Exception e) 
        {

        }
     
  
     }
 

 public void readScales()
    {
     try        
        {           

                    StringBuffer sbAllText = new StringBuffer();

                    String pathCSV = System.getProperty("user.dir");
                                      

                  pathCSV = pathCSV +"\\drugsScalesConditionsReferencesCSV.csv";  //for final version!!!!
                    
                   // pathCSV = pathCSV +"\\src\\revmanhalcochranemode\\drugsScalesConditionsReferencesCSV.csv"; 
                    
                    System.out.println("das ist pfad "+pathCSV);
                    BufferedReader br = new BufferedReader(new FileReader(pathCSV));

                    int counter = 0;
                    String newLine = br.readLine();

                    while(newLine!=null)
                    {
                        sbAllText.append(newLine);
                        System.out.println("line: "+counter+" "+newLine);
                        counter = counter+1;
                        newLine = br.readLine();
                    }

                    String [] arSpreadSheet = sbAllText.toString().split(";");
                    System.out.println("DJANGO " + counter);
                    br.close();
                    
                    
                    counter = 0;                   
                    
                    for(int i=0;i<arSpreadSheet.length;i++)
                    {
                        if(arSpreadSheet[i].equalsIgnoreCase("id-ref"))
                        {
                            
                            for(int j=i;j<arSpreadSheet.length;j++)
                            {
                                counter=counter+1;
                                System.out.println("scale nr: "+j+" "+arSpreadSheet[j]);
                                
                               
                               vScales.addElement(arSpreadSheet[j]);
                           
                            //   if(counter==22)  // THIS IS WRONG!!!!! was 22
                             //  {
                             //     j = j+8;  // was 13                                    
                              //     counter=0;
                             // }
                             }
                        }
                    }
                    
                    for(int a=0;a<vScales.size();a++)
                    {
                        System.out.println("scale nr2: "+a+" "+vScales.elementAt(a));
                    }
                    
    
    }
     catch(Exception e)
     {
         e.printStackTrace();
     }
 
  }

 private static Document getDocument(String file) 
    {
        try 
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
}
    
    