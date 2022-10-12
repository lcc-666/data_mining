package KNN;
import java.util.ArrayList;
/**
 * KNN实现的方法，此算法相当于K=1，最后找到离测试点最近的某点p，p属于哪一类，测试点就属于哪一类
 * @author Administrator
 *
 */
public class KnnMain {
  public double getPointLength(ArrayList<PointBean> list,PointBean bb){
    int b_x=bb.getX();
    int b_y=bb.getY();
    double temp=(b_x -list.get(0).getX())*(b_x -list.get(0).getX())+
        (b_y -list.get(0).getY())*(b_y -list.get(0).getY());
    // 找出最小的距离
    for(int i=1;i<list.size();i++){
      if(temp<((b_x -list.get(i).getX())*(b_x -list.get(i).getX())+
          (b_y -list.get(i).getY())*(b_y -list.get(i).getY()))){
        temp=(b_x -list.get(i).getX())*(b_x -list.get(i).getX())+
            (b_y -list.get(i).getY())*(b_y -list.get(i).getY());
      }
    }
    return Math.sqrt(temp);
  }
  /**
   * 获取长度，找出最小的一个进行归类
   * @param list1
   * @param list2
   * @param list3
   * @param bb
   */
  public void getContent(ArrayList<PointBean> list1,ArrayList<PointBean> list2,
      ArrayList<PointBean> list3,PointBean bb){
    double A=getPointLength(list1,bb);//与A类点中的最近距离
    double B=getPointLength(list2,bb);//与B类点中的最近距离
    double C=getPointLength(list3,bb);//与C类点中的最近距离
    //做出比较
    if(A>B){
      if(B>C){
        System.out.println("这个点:"+bb.getX()+" , "+bb.getY()+" " +"属于C");
      }else {
        System.out.println("这个点:"+bb.getX()+" , "+bb.getY()+" " +"属于B");
      }
    }else {
      if(A>C){
        System.out.println("这个点:"+bb.getX()+" , "+bb.getY()+" " +"属于C");
      }else {
        System.out.println("这个点:"+bb.getX()+" , "+bb.getY()+" " +"属于A");
      }
    }
  }
}