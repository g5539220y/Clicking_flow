package cn.haihan520.mp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import cn.haihan520.mp_bean.PageViewbean;

public class PageViewTable {
     static class PageViewMapper extends Mapper<LongWritable, Text, Text, PageViewbean> 
     {
    	 protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, PageViewbean>.Context context)
    			throws IOException, InterruptedException {              
              Text k = new Text();
    		  String line = value.toString();
              String[] fields = line.split("\t");
              k.set(fields[0]);   
              PageViewbean v = new PageViewbean(fields[2], fields[1], 0," ", fields[3],fields[0]);
              context.write(k, v);             
    	}
     }
     static class PageViewReducer extends Reducer<Text, PageViewbean, PageViewbean,NullWritable>
     {
    	 @Override
    	protected void reduce(Text key, Iterable<PageViewbean> value,
    			Reducer<Text, PageViewbean, PageViewbean, NullWritable>.Context context)
    			throws IOException, InterruptedException 
    	 {
    		  ArrayList<PageViewbean> sl = new ArrayList<PageViewbean>();
    		  for(PageViewbean v:value)
    		  {
    			  PageViewbean pvb = new PageViewbean();
    			  try {
					BeanUtils.copyProperties(pvb, v);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
    			  sl.add(pvb);
    		  }
    		  Collections.sort(sl, new Comparator<PageViewbean>() {
				@Override
				public int compare(PageViewbean o1, PageViewbean o2) {
			        try {
						Date d1 = toDate(o1.getTime());
						Date d2 = toDate(o2.getTime());
						return d1.compareTo(d2);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return 0;
				}});
    	    int step=1;
    	    long timediff;
    	    PageViewbean v=null;
    	    String uuid = UUID.randomUUID().toString();
    	    for(int i = 0 ;i<sl.size();i++)
    	    {
    	    	if(1 == sl.size())
    	    	{
    	    		v = sl.get(i);
    	    		v.setSession(uuid);
    	    		v.setStep(step);
    	    		v.setView_time("60");
    	    		context.write(v, null);
    	    	}
    	    	
    	    	if(i==0) {continue;}    	    
    try {
    	        timediff = Sub_time(sl.get(i).getTime(),sl.get(i-1).getTime());
				if( timediff< 30*60*1000)
				{
					v = sl.get(i-1);
    	    		v.setSession(uuid);   	    		
    	    		v.setStep(step);
    	    		timediff = Sub_time(sl.get(i).getTime(),v.getTime());
    	    		String timedd = String.valueOf(timediff/1000);
    	    		v.setView_time(timedd);
					context.write(v, null);	
					step++;
				}else {
					v = sl.get(i-1);
    	    		v.setSession(uuid);    	    	
    	    		v.setStep(step);
    	    		timediff = Sub_time(sl.get(i).getTime(),v.getTime());
    	    		String timedd = String.valueOf(timediff/1000);
    	    		v.setView_time(timedd);
					context.write(v, null);	
					step =1;
    	    		uuid = UUID.randomUUID().toString();
				}	
				if(i == sl.size()-1)
				{
					v = sl.get(i);
    	    		v.setSession(sl.get(i-1).getSession());
    	    		v.setStep(sl.get(i-1).getStep()+1);
    	    		timediff = Sub_time(v.getTime(), sl.get(0).getTime());
    	    		String timedd = String.valueOf(timediff/1000);
    	    		v.setView_time(timedd);
					context.write(v, null);	
				}
         } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}    	       	    	
    	    }
    	 }
    	 
    	  public Date toDate(String date) throws ParseException
          {
        	SimpleDateFormat SD_OUT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);   
    		return SD_OUT.parse(date);
        	  
          }
          public String DatetoString(Date date) throws ParseException
          {
        	SimpleDateFormat SD_OUT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);   
    		return SD_OUT.format(date);
        	  
          }
          public long Sub_time(String s1,String s2) throws ParseException
          {
        	  Date d1 = toDate(s1);
        	  Date d2 = toDate(s2);
        	  return d1.getTime()-d2.getTime();
          }
     } 
     public static void main(String[] args) throws Exception {
    	 Configuration conf = new Configuration();
    	 	Job job = Job.getInstance(conf);
    	 	job.setJarByClass(PageViewTable.class);
    	 	job.setMapperClass(PageViewMapper.class);
    	 	job.setReducerClass(PageViewReducer.class);
    	 	job.setMapOutputKeyClass(Text.class);
    	 	job.setMapOutputValueClass(PageViewbean.class);
    	 	job.setOutputKeyClass(Text.class);
    	 	job.setOutputValueClass(NullWritable.class);
    	     FileInputFormat.setInputPaths(job, new Path(args[0]));
    	     Path outpath = new Path(args[1]);
    	     FileSystem fs = FileSystem.get(conf);
    	     if(fs.exists(outpath))
    	     {
    	     	fs.delete(outpath, true);
    	     }
    	     FileOutputFormat.setOutputPath(job, new Path(args[1]));
    	     boolean res = job.waitForCompletion(true);
    	     System.exit(res?0:1);
	}  
}