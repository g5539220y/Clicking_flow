package cn.haihan520.mp_bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Ï´Ï´Ï´£¡
 * @¸ßÈ¦·ÖÎö
 *
 */
public class STbean_Rush {
	 public static SimpleDateFormat SD_IN = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
	 public static SimpleDateFormat SD_OUT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);  

	 public static STbean rush(String line)
{
	   STbean V =new STbean();  
   	   String[] fields = line.split(" ");
   	   V.setSession("Null");
   	   V.setIPaddress(fields[0]);
   	   String time = STbean_Rush.time_Format(fields[3].substring(1));
       V.setTime(time);
       if(fields[6].equals("400")||fields[6].equals("/")) {V.setUrl("error_url");}else{V.setUrl(fields[6]);}         
       if(fields.length>11) {	  
       StringBuffer sb = new StringBuffer();
       for(int i=11;i<fields.length;i++)
       {
       	   sb.append(fields[i]);
       }   
       	   V.setReferal(sb.toString());
   	   }else {
   		   V.setReferal("null_Referal");
   	   }
   	   return V;
}
	 
      public static String time_Format(String time)
      {
   	   try {
			return SD_OUT.format(SD_IN.parse(time));
		} catch (ParseException e) {
			return "error_time";
		}    	   
      }
      
      
}
