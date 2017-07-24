/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package revmanhalcochranemode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author msash9
 */
public class FrameEditTextTwoDrugs extends javax.swing.JFrame 
{
    private String stCondition;
    private String stInterventionDrugOne;
    private String stInterventionDrugTwo;
    FrameBackground frameBackground;
    private String path;
    private String stDrugNameOne="";
    private String stDrugNameTwo="";
    private String stConditionName="";
    private boolean activateMethods=false;
    

    /**
     * Creates new form FrameEditText
     */


    FrameEditTextTwoDrugs(String leftDrugOne,String leftDrugTwo, String right, FrameBackground fb, String p,boolean m) 
    {
         //To change body of generated methods, choose Tools | Templates.
    
         stCondition=right;
        stInterventionDrugOne=leftDrugOne; 
        stInterventionDrugTwo=leftDrugTwo;
        initComponents();
        frameBackground = fb;
        activateMethods=m;
        path = p;
        this.main(null);    
        this.setTitle("Edit Text");
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taInterventionDrugTwo = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        taCondition = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnReturn = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        taInterventionDrugOne = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        taInterventionDrugTwo.setColumns(20);
        taInterventionDrugTwo.setRows(5);
        jScrollPane1.setViewportView(taInterventionDrugTwo);

        taCondition.setColumns(20);
        taCondition.setRows(5);
        jScrollPane2.setViewportView(taCondition);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Interventions: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Condition:");

        btnReturn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/revmanhalcochranemode/back.png"))); // NOI18N
        btnReturn.setText("Return");
        btnReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReturnMouseClicked(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/revmanhalcochranemode/saveRedSmall.jpg"))); // NOI18N
        btnSave.setText("Save in CSV file");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });

        taInterventionDrugOne.setColumns(20);
        taInterventionDrugOne.setRows(5);
        jScrollPane3.setViewportView(taInterventionDrugOne);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Interventions: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24))
                        .addComponent(jLabel3))))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnReturn)
                        .addGap(338, 338, 338)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseClicked
        
         this.dispose();
       FrameBackground fb = new FrameBackground(path,activateMethods);
       fb.setVisible(true);
       fb.validate();
        
    }//GEN-LAST:event_btnReturnMouseClicked

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        
        try
        {
             
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd_'at'_HH-mm");  // get the time and date and save it in the statistic file
            Date currentTime = new Date();

            String pathCSV = System.getProperty("user.dir");

            //  pathCSV = pathCSV +"\\drugDataNewCSVWithSpace.csv";  //for final version!!!!
            pathCSV = pathCSV + "\\src\\revmanhalcochranemode\\InterventionsConditionsScalesCSV.csv";

            
            int counterLetters =0;
            
            
            for(int y=path.length();y>=0;y--)
            {             
                
                if(path.substring(y-1, y).equals("\\"))
                {                    
                    break;                    
                }               
                
                counterLetters=counterLetters+1;                
               
                
            }
            String newPath = path.substring(0,path.length()-counterLetters)+"InterventionsConditionsScalesCSV";
            
          
            
            
            File inF = new File(pathCSV);
            File outF = new File(newPath + "_Backup_" + formatter2.format(currentTime) + ".csv");
            
            copyFile(inF, outF);

             
             System.out.println("path: "+pathCSV);
            Reader fr = new InputStreamReader(new FileInputStream(pathCSV), "ISO-8859-1");
            BufferedReader br = new BufferedReader(fr);
            Writer fw = new OutputStreamWriter(new FileOutputStream(pathCSV+"newFile.csv"),"ISO-8859-1");
            
            
           // taIntervention.setText(taIntervention.getText().substring(1,taIntervention.getText().length()));
            
            
            String line="";
           
            
            while((line=br.readLine())!= null)
            {
              
                System.out.println("substring line: "+line.substring(12,18)+" substring taIntervention: "+taInterventionDrugTwo.getText().substring(0,6));
                   
                if(line.substring(2,3).equalsIgnoreCase("a"))
                {
                    if(line.trim().substring(11,17).equalsIgnoreCase(stDrugNameOne.substring(0,6)))
                    {
                        System.out.println("this is line: "+line);
                        line = line.substring(0,11).toString() +";"+ stDrugNameOne+taInterventionDrugOne.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                    }
                    
                    if(line.trim().substring(11,17).equalsIgnoreCase(stDrugNameTwo.substring(0,6)))
                    {
                        System.out.println("this is line: "+line);
                        line = line.substring(0,11).toString() + stDrugNameTwo+taInterventionDrugTwo.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                    }
                }
                else if(line.substring(3,4).equalsIgnoreCase("a"))
                {
                    
                    if(line.substring(9,10).equals("8"))
                    {
                         if(line.trim().substring(14,20).equalsIgnoreCase(stDrugNameOne.substring(0,6)))
                        {
                        System.out.println("this is line: "+line);
                        line = line.substring(0,11).toString() +";"+ stDrugNameOne+taInterventionDrugOne.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                        }
                        
                        
                        if(line.trim().substring(14,20).equalsIgnoreCase(stDrugNameTwo.substring(0,6)))
                        {
                        System.out.println("this is line: "+line);
                        line = line.substring(0,14).toString() + stDrugNameTwo+ taInterventionDrugTwo.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                        }
                    }
                    else
                    {
                         if(line.trim().substring(12,18).equalsIgnoreCase(stDrugNameOne.substring(0,6)))
                        {
                        System.out.println("this is line olan: "+line);
                        line = line.substring(0,11).toString() +";"+ stDrugNameOne+taInterventionDrugOne.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                        }
                        
                        
                        if(line.trim().substring(12,18).equalsIgnoreCase(stDrugNameTwo.substring(0,6)))
                        {
                        System.out.println("this is line: "+line);
                        line = line.substring(0,12).toString()+ stDrugNameTwo + taInterventionDrugTwo.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                        }
                    }
                }
                 else if(line.substring(4,5).equalsIgnoreCase("a"))
                {
                    if(line.substring(10,13).equalsIgnoreCase("2 0"))
                    {
                         if(line.trim().substring(17,23).equalsIgnoreCase(stDrugNameOne.substring(0,6)))
                        {
                        System.out.println("this is line: "+line);
                        line = line.substring(0,11).toString() +";"+ stDrugNameOne+taInterventionDrugOne.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                        }   
                        
                        
                        if(line.trim().substring(17,23).equalsIgnoreCase(stDrugNameTwo.substring(0,6)))
                        {
                        System.out.println("this is line: "+line);
                        line = line.substring(0,17).toString()+ stDrugNameTwo + taInterventionDrugTwo.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                        }
                    }
                    else
                    {
                         if(line.trim().substring(14,20).equalsIgnoreCase(stDrugNameOne.substring(0,6)))
                         {
                         System.out.println("this is line: "+line);
                         line = line.substring(0,11).toString()+";"+ stDrugNameOne+taInterventionDrugOne.getText().replaceAll("\n", ";")+";;;";
                         System.out.println("this is line 2: "+line);
                         }
                         
                        if(line.trim().substring(14,20).equalsIgnoreCase(stDrugNameTwo.substring(0,6)))
                        {
                        System.out.println("this is line: "+line);
                        line = line.substring(0,14).toString()+ stDrugNameTwo + taInterventionDrugTwo.getText().replaceAll("\n", ";")+";;;";
                        System.out.println("this is line 2: "+line);
                        }
                     }
                    
                    
                }
                  System.out.println("condition, sub: "+line.trim().substring(0, 8)+" name: "+stConditionName.substring(0, 6));
                    
                    if (line.trim().substring(2, 8).equalsIgnoreCase(stConditionName.substring(0, 6))) {
                        System.out.println("this is line condition: " + line);
                        line = line.substring(0, 2).toString() + stConditionName + taCondition.getText().replaceAll("\n", ";")+";;;;;;;;;;;;;;;;;;;;;;;;;;;;;";
                        System.out.println("this is line 2: " + line);
                       
                }
                
                
                
                fw.append(line);
                fw.append("\n");
               
            }
            
            fw.close();
            fr.close();
            
           
            
     File file2 = new File(pathCSV);        
     boolean success = file2.delete();        
     System.out.println(success);        
     file2 = new File(pathCSV+"newFile.csv");        
     file2.renameTo(new File(pathCSV));            
                        
                        
            
            
            
                        
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
    //    FrameBackground frame = new FrameBackground(path);
    //    this.dispose();
        
        
    }//GEN-LAST:event_btnSaveMouseClicked

   public void main (String[]args)
   {
       
       /*
       StringBuffer sbWordIntervention = new StringBuffer();
       StringBuffer sbWordCondition = new StringBuffer();
       boolean endOfLine=false;
       
       
        for (int a = 0; a <= stIntervention.length(); a++) 
        {
          // System.out.println("das ist word if: " + sbWordIntervention + " " + a + " length " + stIntervention.length());

           if(a%190==0&&a>0)
           {
               System.out.println("in modulo 190 dinna "+a);
               endOfLine=true;
           }
           if (a == stIntervention.length() || stIntervention.substring(a, a + 1).equals(" ")&&endOfLine==true) 
           {
               sbWordIntervention.append("\n");
               endOfLine=false;
           }        
           else       
           {
              sbWordIntervention.append(stIntervention.substring(a, a + 1));            
           }       
    
        }
        
        endOfLine=false;
        
      for (int a = 0; a <= stCondition.length(); a++) 
        {
          // System.out.println("das ist word if: " + sbWordCondition + " " + a + " length " + stCondition.length());

           if(a%190==0&&a>0)
           {
               System.out.println("in modulo 190 dinna "+a);
               endOfLine=true;
           }
           if (a == stCondition.length() || stCondition.substring(a, a + 1).equals(" ")&&endOfLine==true) 
           {
               sbWordCondition.append("\n");
               endOfLine=false;
           }        
           else       
           {
              sbWordCondition.append(stCondition.substring(a, a + 1));            
           }      

        }    
        */
       
       
       
        String[] arCondition = stCondition.split(";");
       stConditionName=arCondition[1];
     
        jLabel2.setText("<html><body>Condition: <b>" +stConditionName+"</b></body></html>");
        
        for (int i = 2; i < arCondition.length; i++) {
           System.out.println("ar: " + arCondition[i]);
           taCondition.setText(taCondition.getText() + "\n" + arCondition[i]);
       }
       
       
        
       String[] arInterventionDrugOne = stInterventionDrugOne.split(";");
        
        stDrugNameOne = arInterventionDrugOne[0];
        jLabel1.setText("<html><body>Intervention 1: <b>" +stDrugNameOne+"</b></body></html>");
        
        for(int i=1;i<arInterventionDrugOne.length;i++)
        {
        System.out.println("ar: "+arInterventionDrugOne[i]);
        taInterventionDrugOne.setText(taInterventionDrugOne.getText()+"\n" +arInterventionDrugOne[i]);
        }
       
       
       
       
       
        String[] arInterventionDrugTwo = stInterventionDrugTwo.split(";");
        
        stDrugNameTwo = arInterventionDrugTwo[0];
        jLabel3.setText("<html><body>Intervention 2: <b>"+stDrugNameTwo+"</b></body></html>");
        
        for(int i=1;i<arInterventionDrugTwo.length;i++)
        {
        System.out.println("ar 2: "+arInterventionDrugTwo[i]);
        taInterventionDrugTwo.setText(taInterventionDrugTwo.getText()+"\n" +arInterventionDrugTwo[i]);
        }
         
     //    String[] arIntervention = stIntervention.split(";");
        
    //    for(int i=0;i<arIntervention.length;i++)
    //    {
    //    System.out.println("ar: "+arIntervention[i]);
    //    taIntervention.setText(taIntervention.getText()+"\n" +arIntervention[i]);
    //    }
      
         
          
         
         taInterventionDrugOne.setCaretPosition(0);
         taInterventionDrugTwo.setCaretPosition(0);
         taCondition.setCaretPosition(0);
   }
   
   public static void copyFile(File in, File out) throws IOException {     // copy the file for a backup
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea taCondition;
    private javax.swing.JTextArea taInterventionDrugOne;
    private javax.swing.JTextArea taInterventionDrugTwo;
    // End of variables declaration//GEN-END:variables
}
