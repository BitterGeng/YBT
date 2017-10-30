package com.sinosoft.service.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PubFun {

	 public static String getCurrentDate()
	    {
	        String pattern = "yyyy-MM-dd";
	        SimpleDateFormat df = new SimpleDateFormat(pattern);
	        Date today = new Date();
	        String tString = df.format(today);
	        return tString;
	    }

	    /**
	     * 得到当前系统时间 author: YT
	     * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	     */
	    public static String getCurrentTime2()
	    {
	        String pattern = "HHmmss";
	        SimpleDateFormat df = new SimpleDateFormat(pattern);
	        Date today = new Date();
	        String tString = df.format(today);
	        return tString;
	    }
	    /**
	     * 得到当前系统时间 author: YT
	     * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	     */
	    public static String getCurrentTime()
	    {
	        String pattern = "HH:mm:ss";
	        SimpleDateFormat df = new SimpleDateFormat(pattern);
	        Date today = new Date();
	        String tString = df.format(today);
	        return tString;
	    }
	    
	    
	    
	    public static String getCurrentDate2()
	    {
	        String pattern = "yyyyMMdd";
	        SimpleDateFormat df = new SimpleDateFormat(pattern);
	        Date today = new Date();
	        String tString = df.format(today);
	        return tString;
	    }
	    
	    public static Date getdate(Date creDate) {
	   	 String mydate="";
	   	 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	   	 mydate=sf.format(creDate);
	   	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	         String dateString =mydate;
	         Date date1=new Date();
	   		try {
	   			date1 = df.parse(dateString);
	   		} catch (ParseException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		} 
	   	
	   	return date1;
	   }
	
	    
	    
	    public static Date getDate(String date){
	    	Date date1=null;
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
				date1 = sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date1;
	    	
	    }
	    
	    

	    public static String  getDate(Date date){
	    	String date1=null;
			
				try {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					date1 = sdf.format(date);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			return date1;
	    	
	    }
	    
	    /**
	     * 如果一个字符串数字中小数点后全为零，则去掉小数点及零
	     * @param Value String
	     * @return String
	     */
	    public static String getInt(String Value)
	    {
	        if (Value == null)
	        {
	            return null;
	        }
	        String result = "";
	        boolean mflag = true;
	        int m = 0;
	        m = Value.lastIndexOf(".");
	        if (m == -1)
	        {
	            result = Value;
	        }
	        else
	        {
	            for (int i = m + 1; i <= Value.length() - 1; i++)
	            {
	                if (Value.charAt(i) != '0')
	                {
	                    result = Value;
	                    mflag = false;
	                    break;
	                }
	            }
	            if (mflag)
	            {
	                result = Value.substring(0, m);
	            }
	        }
	        return result;
	    }
	    
	    
	    
}
