package cn.haihan520.mp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import cn.haihan520.mp_bean.VisitPagebean;

public class VisitepageTable {
      static class VisitMapper extends Mapper<LongWritable, Text, Text, VisitPagebean>
      {
    	@Override
    	protected void map(LongWritable key, Text value,
    			Mapper<LongWritable, Text, Text, VisitPagebean>.Context context)
    			throws IOException, InterruptedException {
    	     String line = value.toString();
    	     String[] fields = line.split("\t");
    	     Text k = new Text();
    	     k.set(fields[0]);
    	     /*  private String Session;
    	     private String Start_Time;
    	     private String End_time;
    	     private String Start_page;
    	     private String End_page;
    	     private String Pages_num;
    	     private String IPaddress;
    	     private String step;*/
    	     VisitPagebean v = new VisitPagebean(fields[0], fields[2],"End_time",fields[5],"End_pages","pages_num", fields[1], fields[3]);
    	     context.write(k, v);    	     
    	}  
      }
      static class VisitReducer extends Reducer<Text, VisitPagebean, NullWritable,VisitPagebean>
      {
    	  @Override
    	protected void reduce(Text key, Iterable<VisitPagebean> value,
    			Reducer<Text, VisitPagebean, NullWritable, VisitPagebean>.Context context)
    			throws IOException, InterruptedException 
    	{
            ArrayList<VisitPagebean> beans = new ArrayList<VisitPagebean>();            
            for(VisitPagebean vpb:value)
            {
            	VisitPagebean bean = new VisitPagebean();
            	try 
            	{
					BeanUtils.copyProperties(bean,vpb);
				} catch (Exception e) {
			        e.printStackTrace();
				}
            	beans.add(bean);
            }
            Collections.sort(beans, new Comparator<VisitPagebean>() {

				@Override
				public int compare(VisitPagebean o1, VisitPagebean o2) {
					int a = Integer.parseInt(o1.getStep());
					int b = Integer.parseInt(o2.getStep());
					return a>b?1:-1;
				}
			});
            VisitPagebean values = new VisitPagebean(beans.get(0).getSession(), beans.get(0).getStart_Time(), beans.get(beans.size()-1).getStart_Time(), beans.get(0).getStart_page(), beans.get(beans.size()-1).getStart_page(), beans.get(beans.size()-1).getStep(), beans.get(beans.size()-1).getIPaddress(), " ");
            context.write(null, values);
            
            
      }
    
     
  }
      public static void main(String[] args) throws Exception 
      {
    	Configuration conf = new Configuration();
  	 	Job job = Job.getInstance(conf);
  	 	job.setJarByClass(VisitepageTable.class);
  	 	job.setMapperClass(VisitMapper.class);
  	 	job.setReducerClass(VisitReducer.class);
  	 	job.setMapOutputKeyClass(Text.class);
  	 	job.setMapOutputValueClass(VisitPagebean.class);
  	 	job.setOutputKeyClass(NullWritable.class);
  	 	job.setOutputValueClass(VisitPagebean.class);
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
