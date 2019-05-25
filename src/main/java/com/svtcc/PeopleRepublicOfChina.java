package com.svtcc;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import net.paoding.analysis.examples.gettingstarted.BoldFormatter;
import net.paoding.analysis.examples.gettingstarted.ContentReader;
import net.paoding.analysis.knife.Paoding;
import net.paoding.analysis.knife.PaodingMaker;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.TermPositionVector;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class PeopleRepublicOfChina {
	private static String FIELD_NAME = "content";
	private static String QUERY = "学习";

	public static void main(String[] args) throws Exception {
		if (args.length != 0) {
			QUERY = args[0];
		}
		// 将庖丁封装成符合Lucene要求的Analyzer规范
		Paoding paoding = PaodingMaker.make();
		Analyzer writerAnalyzer = PaodingAnalyzer.writerMode(paoding);
		
		//读取本类目录下的text.txt文件
		String content ="职责描述：1、 基于机器学习/深度学习开发医疗领域的智能应用；2、 结合业务数据特点，持续优化模型算法；3、 追踪业内前沿技术，结合业务特点，探索将前沿的算法技术应用于实际业务。任职要求：1、计算机或相关专业硕士以上学历；3年以上机器学习研究或开发经验；2、熟悉C++/JAVA/Python中的一种或多种编程语言；具备Linux环境开发经验；3、熟练掌握常用的机器学习和深度学习算法，有NLP经验者优先；4、优秀的分析和解决问题能力，对挑战性问题充满激情；良好的团队合作精神，较强的沟通能力 。";

		//接下来是标准的Lucene建立索引和检索的代码
		Directory ramDir = new RAMDirectory();
		IndexWriter writer = new IndexWriter(ramDir, writerAnalyzer);
		Document doc = new Document();
		Field fd = new Field(FIELD_NAME, content, Field.Store.YES,
				Field.Index.TOKENIZED, Field.TermVector.WITH_POSITIONS_OFFSETS);
		doc.add(fd);
		writer.addDocument(doc);
		writer.optimize();
		writer.close();

		IndexReader reader = IndexReader.open(ramDir);
		String queryString = QUERY;
		QueryParser parser = new QueryParser(FIELD_NAME, PaodingAnalyzer
				.queryMode(paoding));
		Query query = parser.parse(queryString);
		Searcher searcher = new IndexSearcher(ramDir);
		query = query.rewrite(reader);
		System.out.println("Searching for: " + query.toString(FIELD_NAME));
		Hits hits = searcher.search(query);

		BoldFormatter formatter = new BoldFormatter();
		Highlighter highlighter = new Highlighter(formatter, new QueryScorer(
				query));
		highlighter.setTextFragmenter(new SimpleFragmenter(50));
		for (int i = 0; i < hits.length(); i++) {
			String text = hits.doc(i).get(FIELD_NAME);
			int maxNumFragmentsRequired = 5;
			String fragmentSeparator = "...";
			TermPositionVector tpv = (TermPositionVector) reader
					.getTermFreqVector(hits.id(i), FIELD_NAME);
			TokenStream tokenStream = TokenSources.getTokenStream(tpv);
			String result = highlighter.getBestFragments(tokenStream, text,
					maxNumFragmentsRequired, fragmentSeparator);
			System.out.println("\n" + result);
		}
		reader.close();
	}
}
