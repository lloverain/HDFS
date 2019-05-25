package com.svtcc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author yangjiaying
 * @create 2019-05-17 下午3:39
 * @email 1296813487@qq.com
 */
public class ZPReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
    IntWritable v= new IntWritable();

    private IntWritable postion = new IntWritable(1); //存放名次
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //1.累加求和
        int sum=0;
        for(IntWritable value:values){
            sum+=value.get();
        }
        v.set(sum);
        //2.写出
        context.write(key,v);
    }
}
