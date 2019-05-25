package com.svtcc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author yangjiaying
 * @create 2019-05-17 下午3:40
 * @email 1296813487@qq.com
 */
public class ZPmain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取Job对象
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        //2.设置jar加载路径
        job.setJarByClass(ZPmain.class);

        //3.设置map和Reduce类
        job.setMapperClass(ZPMapper.class);
        job.setReducerClass(ZPReducer.class);

        //4.设置map输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //5.设置最终数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //6.设置出入路径和输出路径
        FileInputFormat.setInputPaths(job,new Path("/home/rain/Test/input/"));
        FileOutputFormat.setOutputPath(job,new Path("/home/rain/Test/output/"));

        //7.提交job
        boolean result = job.waitForCompletion(true);
    }
}
