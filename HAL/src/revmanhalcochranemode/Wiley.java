/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package revmanhalcochranemode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 
import java.util.Date; 


public class Wiley {
    
   
  public static String main(String file) {
             
      boolean noTotals = false;  
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
            String readfile = file;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
            String str = dateFormat.format(new Date());
            FileReader fr = new FileReader(readfile);
            BufferedReader br = new BufferedReader(fr);
            String filename = file.substring(0,file.lastIndexOf("\\"))+"\\CopyEdit_"+str+".html";
            File writefile = new File (filename);
            FileWriter fw = new FileWriter(writefile, false);

            String zeile = br.readLine();
            zeile = br.readLine();
            
            while( zeile  != null  )
            {
              arr = zeile.split(";");
              
              if (Integer.parseInt(arr[1])== 0 && Integer.parseInt(arr[2])== 0) {  //Columns B and C equals 0?
                  
                  fw.write("<h2>COMPARISON "+arr[0]+": "+arr[3]+"</h2>");
                  zeile = br.readLine();
              }
              else
                  if (Integer.parseInt(arr[1])!= 0 && Integer.parseInt(arr[2])== 0 ) {  // Column B not equals 0 but Column C equals 0?
                        
                      analysisnr = arr[0]+"."+arr[1];
                      if (!arr[4].equals("")) {             //Column E not empty?
                         
                                                
                              
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
                               
                              } 
                              fw.write("<h3>"+arr[0]+"."+arr[1]+" "+arr[3]+"</h3>");
                              zeile = br.readLine();
                         
                      } else {
                            count2 = 0;
                            fw.write("<h5>INDIVIDUAL RCT RESULTS</h5>");
                            while (arr.length > 44 && arr[4].equals("")) {
                              studyname2 = arr[3];
                              fw.write(arr[3]+" (n="+(Integer.parseInt(arr[18])+Integer.parseInt(arr[22]))+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0+")");
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
                                fw.write("<h5>COMPOSITE RESULT</h5>");
                                if (count2 == 1) {
                                   fw.write("(1 RCT, n="+total2+", "+effectMeasure2Short+", "+analysisModel2+", "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+") ");
                                    
                                }
                                else {
                                    fw.write("("+count+" RCTs, n="+total+", "+effectMeasure2Short+", "+analysisModel2+", "+effectEst2+" CI "+ciStart2+" to "+ciEnd2+", Analysis "+analysisnr+") ");
                                }
                                fw.write(" (Chi1="+chi1+"; df="+df1+"; P="+p1+"; I2="+i1+"%)");
                
                      }
                  }
                  else
                      if (Integer.parseInt(arr[1])!= 0 && Integer.parseInt(arr[2])!= 0 && noTotals == false){       //Column B and C not equals 0
                          
                          if (!arr[4].equals("")){                  // Column E not empty
                          
                            
                        fw.write("<h4>"+arr[0]+"."+arr[1]+"."+arr[2]+" "+ arr[3]+"</h4>");
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
                           
                      // fw.write("Total analysis: " +"(n="+total+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0 +") "+"<br/>");
                            zeile = br.readLine();
                          }
                          
                          else {
                            count = 0;
                            fw.write("<h5>INDIVIDUAL RCT RESULTS</h5>");
                            while (arr.length > 44 && arr[4].equals("")){
                                
                          fw.write(arr[3]+" (n="+(Integer.parseInt(arr[18])+Integer.parseInt(arr[22]))+", RR "+Math.round(Double.parseDouble(arr[25])*100)/100.0+" CI "+Math.round(Double.parseDouble(arr[27])*100)/100.0+" to "+Math.round(Double.parseDouble(arr[28])*100)/100.0+") </br>");
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
                                fw.write("<h5>COMPOSITE RESULT</h5>");
                                if (count == 1) {
                                   fw.write("(1 RCT, n="+total+", "+effectMeasureShort+", "+analysisModel+", "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analysis "+analysisnr+") ");
                                    
                                }
                                else {
                                    fw.write("("+count+" RCTs, n="+total+", "+effectMeasureShort+", "+analysisModel+", "+effectEst+" CI "+ciStart+" to "+ciEnd+", Analysis "+analysisnr+") ");
                                }
                                fw.write(" (Chi2="+chi2+"; df="+df2+"; P="+p2+"; I2="+i2+"%)"+"<br/>");
                            
                           //     System.out.println (">>Total study count: "+count); 
                      
                            
                          }
                              
                      }
                      else if(noTotals == true){
                          fw.write("*--- Missing data in this subsection. Data was not totaled. ---*"+"<br/>");
                          zeile = br.readLine();
                      }
            }

           
            System.out.println("Done");
            br.close();
            fr.close();
            fw.flush();
            fw.close();
            return filename;
       
        }
            
        catch (Exception e){
            System.out.println(e);
            return "";
        }
  }
}
    
  

  

