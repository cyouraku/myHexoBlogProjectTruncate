package sample.searchArticle.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.ansj.app.crf.Model;
import org.ansj.app.crf.model.CRFModel;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.dic.LearnTool;
import org.ansj.domain.Nature;
import org.ansj.domain.NewWord;
import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.IOUtil;

public class AnsjSegUtil {

	private static final Logger logger = Logger.getLogger(AnsjSegUtil.class.getName());

	private static String testStr = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!";

	public static void main(String[] args) throws InterruptedException {

		testFindKeyword();
	}

	private static void dispEquals(){
		logger.info("round 1");
		testEquals("","");
		logger.info("round 2");
		testEquals(null,"");
		logger.info("round 3");
		testEquals("",null);
	}

	private static void testEquals(String input, String cons){
		if(input.equals(cons)){
			System.out.println("OK");
		}else{
			System.out.println("NG");
		}
	}

	private static void testTreeSplitDemo() throws Exception{
        /**
         * 词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
         */
        String dic = "中国\t1\tzg\n人名\t2\n中国人民\t4\n人民\t3\n孙健\t5\nCSDN\t6\njava\t7\njava学习\t10\n";
        Forest forest = Library.makeForest(new BufferedReader(new StringReader(dic)));
        /**
         * 删除一个单词
         */
        Library.removeWord(forest, "中国");
        /**
         * 增加一个新词
         */
        Library.insertWord(forest, "中国人");
        String content = "中国人名识别是中国人民的一个骄傲.孙健人民在CSDN中学到了很多最早iteye是java学习笔记叫javaeye但是java123只是一部分";
        GetWord udg = forest.getWord(content);
        String temp = null;
        while ((temp = udg.getFrontWords()) != null)
            System.out.println(temp + "\t\t" + udg.getParam(1) + "\t\t" + udg.getParam(2));
	}

	private static void testFindKeyword(){
	    String title = "维基解密否认斯诺登接受委内瑞拉庇护";
	    String content = "有俄罗斯国会议员，9号在社交网站推特表示，美国中情局前雇员斯诺登，已经接受委内瑞拉的庇护，不过推文在发布几分钟后随即删除。俄罗斯当局拒绝发表评论，而一直协助斯诺登的维基解密否认他将投靠委内瑞拉。　　俄罗斯国会国际事务委员会主席普什科夫，在个人推特率先披露斯诺登已接受委内瑞拉的庇护建议，令外界以为斯诺登的动向终于有新进展。　　不过推文在几分钟内旋即被删除，普什科夫澄清他是看到俄罗斯国营电视台的新闻才这样说，而电视台已经作出否认，称普什科夫是误解了新闻内容。　　委内瑞拉驻莫斯科大使馆、俄罗斯总统府发言人、以及外交部都拒绝发表评论。而维基解密就否认斯诺登已正式接受委内瑞拉的庇护，说会在适当时间公布有关决定。　　斯诺登相信目前还在莫斯科谢列梅捷沃机场，已滞留两个多星期。他早前向约20个国家提交庇护申请，委内瑞拉、尼加拉瓜和玻利维亚，先后表示答应，不过斯诺登还没作出决定。　　而另一场外交风波，玻利维亚总统莫拉莱斯的专机上星期被欧洲多国以怀疑斯诺登在机上为由拒绝过境事件，涉事国家之一的西班牙突然转口风，外长马加略]号表示愿意就任何误解致歉，但强调当时当局没有关闭领空或不许专机降落。";
		Collection<Keyword> result = findKeyWords(title,content);
		for(Keyword kw : result){
			System.out.println("name = " + kw.getName() + " freq = " + kw.getFreq() + " score = " + kw.getScore());
		}
	}

	private static void addNewWordDemo(){
        // 增加新词,中间按照'\t'隔开
//        UserDefineLibrary.insertWord("ansj中文分词", "userDefine", 1000);
        Result terms = ToAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!");
        System.out.println("增加新词例子:" + terms);
        // 删除词语,只能删除.用户自定义的词典.
//        UserDefineLibrary.removeWord("ansj中文分词");
        terms = ToAnalysis.parse("我觉得ansj中文分词是一个不错的系统!我是王婆!");
        System.out.println("删除用户自定义词典例子:" + terms);
	}

	private static void learnToolDemo(){
        //构建一个新词学习的工具类。这个对象。保存了所有分词中出现的新词。出现次数越多。相对权重越大。
        LearnTool learnTool = new LearnTool() ;
        Forest forest = new Forest();
        //进行词语分词。也就是nlp方式分词，这里可以分多篇文章
//        NlpAnalysis.parse("说过，社交软件也是打着沟通的平台，让无数寂寞男女有了肉体与精神的寄托。", learnTool) ;
//        NlpAnalysis.parse("其实可以打着这个需求点去运作的互联网公司不应只是社交类软件与可穿戴设备，还有携程网，去哪儿网等等，订房订酒店多好的寓意", learnTool) ;
//        NlpAnalysis.parse("张艺谋的卡宴，马明哲的戏",learnTool) ;
        NlpAnalysis.parse("说过，社交软件也是打着沟通的平台，让无数寂寞男女有了肉体与精神的寄托。", forest) ;
        NlpAnalysis.parse("其实可以打着这个需求点去运作的互联网公司不应只是社交类软件与可穿戴设备，还有携程网，去哪儿网等等，订房订酒店多好的寓意", forest) ;
        NlpAnalysis.parse("张艺谋的卡宴，马明哲的戏",forest) ;
		for (String str : forest.getParams()) {
			logger.info(str);
		}
//        new NewWord(String,Nature,Double);
        //取得学习到的topn新词,返回前10个。这里如果设置为0则返回全部
        System.out.println(learnTool.getTopTree(10));

        //只取得词性为Nature.NR的新词
        System.out.println(learnTool.getTopTree(10,Nature.NR));
	}

	private static void testParse(){
		System.out.println(ToAnalysis.parse(testStr));
		// Expect as below:
		// 欢迎/v,使用/v,ansj/en,_,seg/en,,,(,ansj/en,中文/nz,分词/n,),在/p,这里/r,如果/c,你/r,遇到/v,什么/r,问题/n,都/d,可以/v,联系/v,我/r,./m,我/r,一定/d,尽我所能/l,./m,帮助/v,大家/r,./m,ansj/en,_,seg/en,更快/d,,,更/d,准/a,,,更/d,自由/a,!

		//parseString 2:
		testStr = "结婚的和尚未结婚的";
		System.out.println(parseStrInput(testStr));
		System.out.println(parseStrInputWithOutNature(testStr));

		//parseString 2:
		testStr = "结合成分子时";
		System.out.println(parseStrInput(testStr));
		System.out.println(parseStrInputWithOutNature(testStr));

		//交叉歧义（多种切分交织在一起）：内塔内亚胡说的/确实/在理
		testStr = "内塔内亚胡说的确实在理";
		System.out.println(parseStrInput(testStr));
		System.out.println(parseStrInputWithOutNature(testStr));
		testStr = "内塔内亚说的确实在理";
		System.out.println(parseNlp(testStr));

		//组合歧义（不同情况下切分不同）：这个人/手上有痣、我们公司人手
		testStr = "这个人手上有痣、我们公司人手";
		System.out.println(parseStrInput(testStr));
		System.out.println(parseStrInputWithOutNature(testStr));

		//真歧义（几种切分都可以）：乒乓球拍/卖/完了、乒乓球/拍卖/完了
		testStr = "乒乓球拍卖完了、乒乓球拍卖完了";
		System.out.println(parseStrInput(testStr));
		System.out.println(parseStrInputWithOutNature(testStr));

		//synonyms test
		testStr = "我家外戚和远房亲戚是一个意思吗？";
		System.out.println(parseStrInput(testStr));
		System.out.println(parseStrInputWithOutNature(testStr));

		//亲家母亲家公
		testStr = "亲家母亲家公";
		System.out.println(parseStrInput(testStr));
		System.out.println(parseStrInputWithOutNature(testStr));
	}

	/**
	 * accurate parse with nature
	 * @param str
	 * @return
	 */
	public static String parseStrInput(String str){
		return ToAnalysis.parse(str).toString();
	}

	/**
	 * accurate parse without nature
	 * @param str
	 * @return
	 */
	public static String parseStrInputWithOutNature(String str){
		return ToAnalysis.parse(str).toStringWithOutNature();
	}

	/**
	 * nlp parse with nature
	 * @param str
	 * @return
	 */
	public static String parseNlp(String str){
		return NlpAnalysis.parse(str).toString();
	}

	/**
	 * Write trained result to file
	 * /home/ansj/temp/learnTool.snap
	 * @param filePath
	 * @param learnTool
	 */
	public static void writeTrainedResultToFile(String filePath, LearnTool learnTool){
		  List<Entry<String, Double>> topTree = learnTool.getTopTree(0);
		  StringBuilder sb = new StringBuilder();
		  for (Entry<String, Double> entry : topTree) {
		      sb.append(entry.getKey() + "\t" + entry.getValue()+"\n");
		  }
		  IOUtil.Writer(filePath, IOUtil.UTF8, sb.toString());
		  sb = null;
	}

	/**
	 * Read trained result from file
	 * /home/ansj/temp/learnTool.snap
	 * @param filePath
	 * @throws UnsupportedEncodingException
	 */
	public static LearnTool readTrainedResultFrFile(String filePath) throws UnsupportedEncodingException{
		  LearnTool learnTool = new LearnTool() ;
		  HashMap<String, Double> loadMap = IOUtil.loadMap(filePath, IOUtil.UTF8, String.class, Double.class);
		  for (Entry<String, Double> entry : loadMap.entrySet()) {
		      learnTool.addTerm(new NewWord(entry.getKey(), Nature.NW, entry.getValue())) ;
		      learnTool.active(entry.getKey()) ;
		  }
		  //Debug:
		  logger.info(String.format("Top 10 new words = %s",learnTool.getTopTree(10).toString()));
		  return learnTool;
	}


	/**
	 * Find keywords from title and content
	 * @param title
	 * @param content
	 * @return
	 */
	public static Collection<Keyword> findKeyWords(String title, String content){
	    KeyWordComputer kwc = new KeyWordComputer(5);
		return kwc.computeArticleTfidf(title, content);
	}


	public static void trainCrfModel() throws Exception{
		Model model = CRFModel.load("D:/tmp/sn/model.txt");
		model.writeModel("D:/tmp/sn/crf.model");
	}



}
