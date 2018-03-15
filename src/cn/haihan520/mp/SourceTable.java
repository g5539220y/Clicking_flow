package cn.haihan520.mp;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import cn.haihan520.mp_bean.STbean;
import cn.haihan520.mp_bean.STbean_Rush;


public class SourceTable {
	 static class STMapper extends Mapper<LongWritable, Text, Text, STbean>
     {
    	 @Override
    	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, STbean>.Context context)
    			throws IOException, InterruptedException {
    		 String line = value.toString();
    		 Text k =new Text();
    		 STbean values = STbean_Rush.rush(line);	
    		 k.set(values.getIPaddress());
        	     context.write(k, values);  
    	     }
    	}

     public static void main(String[] args) throws Exception {
   	  Configuration conf = new Configuration();
     	Job job = Job.getInstance(conf);
     	job.setJarByClass(SourceTable.class);
     	job.setMapperClass(STMapper.class);
     	job.setMapOutputKeyClass(Text.class);
     	job.setMapOutputValueClass(STbean.class);
        job.setNumReduceTasks(0);
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
