package com.svtcc;

import com.huaban.analysis.jieba.JiebaSegmenter;
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjiaying
 * @create 2019-05-11 下午1:13
 * @email 1296813487@qq.com
 */
public class test {
    public static void main(String[] args) {
        Analyzer analyzer = new PaodingAnalyzer();
        String indexStr = "职责描述：1、 基于机器学习/深度学习开发医疗领域的智能应用；2、 结合业务数据特点，持续优化模型算法；3、 追踪业内前沿技术，结合业务特点，探索将前沿的算法技术应用于实际业务。任职要求：1、计算机或相关专业硕士以上学历；3年以上机器学习研究或开发经验；2、熟悉C++/JAVA/Python中的一种或多种编程语言；具备Linux环境开发经验；3、熟练掌握常用的机器学习和深度学习算法，有NLP经验者优先；4、优秀的分析和解决问题能力，对挑战性问题充满激情；良好的团队合作精神，较强的沟通能力 。 ";
        StringReader reader = new StringReader(indexStr);
        TokenStream ts = analyzer.tokenStream(indexStr, reader);
        Token t = null;
        try {
            t = ts.next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (t != null) {
            System.out.print(t.termText() + " ");
            try {
                t = ts.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void demo(){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String[] sentences =  new String[] {"职责描述：1、 基于机器学习/深度学习开发医疗领域的智能应用；2、 结合业务数据特点，持续优化模型算法；3、 追踪业内前沿技术，结合业务特点，探索将前沿的算法技术应用于实际业务。任职要求：1、计算机或相关专业硕士以上学历；3年以上机器学习研究或开发经验；2、熟悉C++/JAVA/Python中的一种或多种编程语言；具备Linux环境开发经验；3、熟练掌握常用的机器学习和深度学习算法，有NLP经验者优先；4、优秀的分析和解决问题能力，对挑战性问题充满激情；良好的团队合作精神，较强的沟通能力 。"};
        for (String sentence : sentences) {
            System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH).toString());
        }
        String a = "职责描述：1、 基于机器学习/深度学习开发医疗领域的智能应用；2、 结合业务数据特点，持续优化模型算法；3、 追踪业内前沿技术，结合业务特点，探索将前沿的算法技术应用于实际业务。任职要求：1、计算机或相关专业硕士以上学历；3年以上机器学习研究或开发经验；2、熟悉C++/JAVA/Python中的一种或多种编程语言；具备Linux环境开发经验；3、熟练掌握常用的机器学习和深度学习算法，有NLP经验者优先；4、优秀的分析和解决问题能力，对挑战性问题充满激情；良好的团队合作精神，较强的沟通能力 。";
        List<String> b = segmenter.sentenceProcess(a);
        for(String c : b){
            System.out.println(c);
        }
    }
}
