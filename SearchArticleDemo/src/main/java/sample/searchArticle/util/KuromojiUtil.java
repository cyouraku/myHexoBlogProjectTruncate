package sample.searchArticle.util;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;



public class KuromojiUtil {

	private static final Logger logger = Logger.getLogger(KuromojiUtil.class.getName());

	public static void main(String[] args) {

//		logger.info("testDemo() start!");
//		testDemo();
		logger.info("testAnalyzerModeNormal start!");
		testAnalyzerModeNormal();
	}

	public static void testDemo(){
        Tokenizer tokenizer = Tokenizer.builder().build();
        for (Token token : tokenizer.tokenize("寿司が食べたい。")) {
            System.out.println(token.getSurfaceForm() + "\t" + token.getAllFeatures());
        }
	}

	/**
	 * 分词模式
	 */
	public static void testAnalyzerModeNormal(){
		String word = "日本経済新聞でモバゲーの記事を読んだ。";
		Builder builder = Tokenizer.builder();

		// Normal
		Tokenizer normal = builder.build();
		List<Token> tokensNormal = normal.tokenize(word);
		disp(tokensNormal);

		// Search
//		builder.mode(Model.SEARCH);
//		Tokenizer search = builder.build();
//		List<Token> tokensSearch = search.tokenize(word);
//		disp(tokensSearch);

		// Extends
//		builder.mode(Mode.EXTENDED);
//		Tokenizer extended = builder.build();
//		List<Token> tokensExtended = extended.tokenize(word);
//		disp(tokensExtended);
	}


	public static void disp(List<Token> tokens){
		for (Token token : tokens) {
		    System.out.println("==================================================");
		    System.out.println("allFeatures : " + token.getAllFeatures());
		    System.out.println("partOfSpeech : " + token.getPartOfSpeech());
		    System.out.println("position : " + token.getPosition());
		    System.out.println("reading : " + token.getReading());
		    System.out.println("surfaceFrom : " + token.getSurfaceForm());
		    System.out.println("allFeaturesArray : " + Arrays.asList(token.getAllFeaturesArray()));
		    System.out.println("辞書にある言葉? : " + token.isKnown());
		    System.out.println("未知語? : " + token.isUnknown());
		    System.out.println("ユーザ定義? : " + token.isUser());
		}
	}

}
