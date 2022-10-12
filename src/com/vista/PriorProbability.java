package com.vista;

/**
 * 先验概率计算
 * <h3>先验概率计算</h3>
 * P(c<sub>j</sub>)=N(C=c<sub>j</sub>)<b>/</b>N <br>
 * 其中，N(C=c<sub>j</sub>)表示类别c<sub>j</sub>中的训练文本数量；
 * N表示训练文本集总数量。
 */

public class PriorProbability
{
    private static TrainingDataManager tdm =new TrainingDataManager();

    /**
     * 先验概率
     * @param c 给定的分类
     * @return 给定条件下的先验概率
     */
    public static float calculatePc(String c)
    {
        float ret = 0F;
        float Nc = tdm.getTrainingFileCountOfClassification(c);//返回训练文本集中在给定分类下的训练文本数目
        float N = tdm.getTrainingFileCount();//返回训练文本集中所有的文本数目
        ret = Nc / N;
        return ret;
    }
}