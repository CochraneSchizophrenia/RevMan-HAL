/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package revmanhalcochranemode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author msabs1
 */
public class Backup {
    
    private String filepath;
    private String frame;
    
    public Backup(String file, String f)
    {
        filepath = file;
        frame = f;
    }
    
    
    public void main()
    {
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd_'at'_HH-mm");  // get the time and date and save it in the statistic file
        Date currentTime = new Date(); 
                
         File inF = new File(filepath);
         File outF = new File(filepath.substring(0, filepath.indexOf(".")) + "_Backup"+frame+"_"+formatter2.format(currentTime)+".rm5");
         try
         {
         copyFile(inF, outF);
         final JOptionPane pane = new JOptionPane("A Backup was created!");
         final JDialog d = pane.createDialog(null, "CLUE");
         d.setLocation(450, 430);
         d.setVisible(true);
         }
         catch(Exception e)
         {
            final JOptionPane pane = new JOptionPane("A Backup COULD NOT be created!");
            final JDialog d = pane.createDialog(null, "ERROR");
            d.setLocation(450, 430);
            d.setVisible(true);
         }
    
    }
    
     public static void copyFile(File in, File out) throws IOException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel(); 
        inChannel.transferTo(0, inChannel.size(), outChannel);
         }
    
}
