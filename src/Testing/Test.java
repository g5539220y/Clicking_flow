package Testing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Test {
	  public  static Date toDate(String date) throws ParseException
      {
    	SimpleDateFormat SD_OUT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);   
		return SD_OUT.parse(date);
    	  
      }
      public static String DatetoString(Date date) throws ParseException
      {
    	SimpleDateFormat SD_OUT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);   
		return SD_OUT.format(date);
    	  
      }
      public static long Sub_time(String s1,String s2) throws ParseException
      {
    	  Date d1 = toDate(s1);
    	  Date d2 = toDate(s2);
    	  return d1.getTime()-d2.getTime();
      }
    public static void main(String[] args) throws ParseException {
    	String a = "1";
    	int b=Integer.parseInt(a);
    	System.out.println(b==1);
	}
}
