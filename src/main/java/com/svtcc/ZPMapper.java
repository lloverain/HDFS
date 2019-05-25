package com.svtcc;
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author yangjiaying
 * @create 2019-05-11 下午12:03
 * @email 1296813487@qq.com
 */
public class ZPMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
    Text k = new Text();
    IntWritable v = new IntWritable();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取一行数据
        String line = value.toString();
        //2.清洗数据
//        String elstr = ZPUtil.zpstr(line);
        String[] split = line.split(",");
        if (split.length<14){
        }else {

            split[4] = split[4].replaceAll(" ","");
            String job_info = split[9];
            Analyzer analyzer = new PaodingAnalyzer();
            StringReader reader = new StringReader(job_info);
            TokenStream ts = analyzer.tokenStream(job_info, reader);
            Token t = null;
            try {
                t = ts.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (t != null) {
                String ci = t.termText();
                k.set(ci);
                v.set(1);
                context.write(k,v);
                try {
                    t = ts.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //3.写出
//            k.set(elstr);
//            context.write(k,NullWritable.get());

        }
    }
}
