package com.vista;
//对语料分词
import java.io.IOException;
import jeasy.analysis.MMAnalyzer;

/**
 * 中文分词器，因为对文本进行分类时，需要计算文本中每个词在各类别中出现的概率，所以需要对文本进行分词，用到了分词器
 */
public class ChineseSpliter
{
    /**
     * 对给定的文本进行中文分词
     * @param text 给定的文本
     * @param splitToken 用于分割的标记,如"|"
     * @return 分词完毕的文本
     */
    public static String split(String text,String splitToken)
    {
        String result = null;
        MMAnalyzer analyzer = new MMAnalyzer();  //中文分词工具
        try
        {
            result = analyzer.segment(text, splitToken);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
