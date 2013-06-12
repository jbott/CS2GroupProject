/**
 * @(#)IO.java
 * This file is designed to allow HCRHS students to collect information from the user during Computer Science 1 and
 * Computer Science 2.
 * @author Mr. Twisler
 * @version 1.10 2010/11/17
 *		*Added temporary fix to let /t work for all input/output
 */

import javax.swing.JOptionPane;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class IO {
	public static String getString(String text)
	{
		text = fixTabs(text);
		String ans =JOptionPane.showInputDialog(null,text,"HCRHS",
                    JOptionPane.QUESTION_MESSAGE);
        if(ans == null)
             return "";
        return ans;
	}
	
	/* Returns int typed by the user
	*  If they do not type in a valid int, it will return 0.*/
	public static int getInt(String text)
	{
		text = fixTabs(text);
		try{
			return Integer.parseInt(JOptionPane.showInputDialog(null,text,"HCRHS",
                    JOptionPane.QUESTION_MESSAGE));
		}
        catch (NumberFormatException e)
		{	return 0;}
	}    
    
    /* Returns int typed by the user
	*  If they do not type in a valid int, it will return 0.*/
	public static double getDouble(String text)
	{
		text = fixTabs(text);
		try{
			return Double.parseDouble(JOptionPane.showInputDialog(null,text,"HCRHS",
                    JOptionPane.QUESTION_MESSAGE));
		}
        catch (NumberFormatException e)
		{	return 0;}
	}
	
	public static boolean getBoolean(String text)
	{
		text = fixTabs(text);
		int n = JOptionPane.showOptionDialog(null,text,"HCRHS",
    	JOptionPane.YES_NO_OPTION,
    	JOptionPane.QUESTION_MESSAGE,
    	null,
    	new Object[]{"True","False"},
    	1);
		return (n == 0);
	}
	
	public static char getChar(String text)
	{
		text = fixTabs(text);
		return JOptionPane.showInputDialog(null,text,"HCRHS",
                    JOptionPane.QUESTION_MESSAGE).charAt(0);
	}
	
	public static void showMsg(String text, int fontSize)
	{
		/*try
	    {
    		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
    	} catch (Exception e) {	}
		*/
		text = fixTabs(text);
		
		//javax.swing.JLabel switchFont = new javax.swing.JLabel(text);
	    //switchFont.setFont(new java.awt.Font("Monospaced", 0/*java.awt.Font.BOLD*/, fontSize));
	    //JOptionPane.showMessageDialog( null, switchFont, "HCRHS", JOptionPane.PLAIN_MESSAGE );
		
		JOptionPane.showMessageDialog(null,text,"HCRHS",JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void showMsg(String text)
	{
		showMsg(text,14);
	}
	
	public static void showMsg(Object obj)
	{
		showMsg(obj.toString());
	}

	//	Swing has a bug that does not show /t correctly.  This method is a crude workaround.
	private static String fixTabs(String text)
	{
		Scanner s = new Scanner(text).useDelimiter("\\t");
		text ="";
		while (s.hasNext())
			text+=s.next()+"     ";
		return text;
	}

	/*********************************Format material*************************************************************
	  * lPad/rPad/center are all used to add whitespace to the sides of a String
	  * format is overloaded to accept Strings/double/int
	  * this material was heavily based of the work of Mrs. Sainz
	  ************************************************************************************************************/
	public static String lPad(String s, int w)
	{
		String newS = "";
		if (s.length() > w)
		{
			newS = s.substring(0, w - 1);
			return newS;
		}
		else
		{
			String out = s;
			for (int i = 0; i < w - s.length(); i++)
				out = " "+out;
			return out;
		}
	}

	public static String rPad(String s, int w)
	{
		String newS = "";
		if (s.length() > w)
		{
			newS = s.substring(0, w - 1);
			return newS;
		}
		else
		{
			String out = s;
			for (int i = 0; i < w - s.length(); i++)
				out += " ";
			return out;
		}
	}

	public static String center(String s, int w)
	{
		String newS = "";
		if (s.length() > w)
			newS = s.substring(0, w - 1);
		else
		{
			int lLen = (w - s.length())/2 + s.length();
			newS = lPad(s, lLen);
			newS = rPad(newS, w);
		}
		return newS;
	}

	public static String format(char justification, int maxWidth, String s)
	{
		if (justification == 'l' || justification == 'L')
		{
			return rPad(s, maxWidth);
		}
		else if (justification == 'r' || justification == 'R')
		{
			return lPad(s, maxWidth);
		}
		else if (justification == 'c' || justification == 'C')
		{
			return center(s, maxWidth);
		}
		else return s;
	}

	public static String format(char justification, int maxWidth, int num)
	{
		java.text.NumberFormat fmtNum = java.text.NumberFormat.getInstance();
		String s = fmtNum.format(num);
		return format(justification, maxWidth, s);
	}

	public static String format(char justification, int maxWidth, double num,
	                            int decimals)
	{
		String dec="";
		for(int c=0;c<decimals;c++)
			dec+="0";
    	java.text.NumberFormat fmtNum = new java.text.DecimalFormat("#,##0."+dec);
		String s = fmtNum.format(num);
		return format(justification, maxWidth, s);
	}	
	//*****************************end Format material************************************************************* 
	//*********************************Fancy expirmental stuff*****************************************************
	public static String choice( String... options )
    {
        String s = (String)JOptionPane.showInputDialog(null,
                    "Pick one of the following",
                    "HCRHS",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    null);

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
    		return s;
		}
		return "";
        
    } 
    /*Need the following imports at the top of the file
     *import java.util.Scanner;
	 *import java.io.File;
	 *import java.io.FileNotFoundException;
     **/
    public static String readFile(String fileName) {
       String ans ="";
       try {
       	
         Scanner scanner = new Scanner(new File(fileName));
         scanner.useDelimiter(System.getProperty("line.separator")); 
         while (scanner.hasNext()) {
           ans += scanner.next()+"\n";
         }
         scanner.close();
       } catch (FileNotFoundException e) {
         e.printStackTrace();
       }
       return ans;
     }
     
   /* Need to import
    *import java.io.File;
	*import java.io.FileNotFoundException;
	*import java.io.FileWriter;  
	*/
   public static void writeFile(String fileName, String data)
   {
   	try{
   		FileWriter fw = new FileWriter(fileName, true);
   		fw.write(data);
   		fw.close();
   	}catch(java.io.IOException e) {
         e.printStackTrace();
    }
   }
}