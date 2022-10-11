package rule;

import java.util.*;

public class Apriori {
    private final static int SUPPORT =2;
    private final static double CONFIDENCE=0.7;
    private final static String ITEM_SPLIT=";";
    private final static String CON="->";
    private final static List<String> translist= new ArrayList<>();

    static {
        translist.add("1;2;5;");
        translist.add("2;4;");
        translist.add("2;3;");
        translist.add("1;2;4;");
        translist.add("1;3;");
        translist.add("2;3;");
        translist.add("1;3;");
        translist.add("1;2;3;5;");
        translist.add("1;2;3;");



    }
    public Map<String,Integer> getFC(){
        Map<String, Integer> frequentCollectionMap = new HashMap<>(getItem1FC());
        Map<String, Integer> itemkFcMap = new HashMap<>(getItem1FC());
        while (itemkFcMap.size() != 0){
            Map<String,Integer> candidateCollection=getCandidateCollection(itemkFcMap);
            Set<String> ccKeySet=candidateCollection.keySet();

            for (String trans:translist){
                for (String candidate:ccKeySet){
                    boolean flag=true;
                    String[] candidateItems=candidate.split(ITEM_SPLIT);
                    for (String candidateItem:candidateItems){
                        if (!trans.contains(candidateItem + ITEM_SPLIT)){
                            flag=false;
                            break;
                        }
                    }
                    if(flag){
                        Integer count=candidateCollection.get(candidate);
                        candidateCollection.put(candidate,count+1);
                    }
                }
            }
            itemkFcMap.clear();
            for (String candidate:ccKeySet){
                Integer count=candidateCollection.get(candidate);
                if (count>=SUPPORT){
                    itemkFcMap.put(candidate,count);
                }
            }
            frequentCollectionMap.putAll(itemkFcMap);
        }
        return frequentCollectionMap;

    }

    private Map<String, Integer> getCandidateCollection(Map<String, Integer> itemkFcMap) {
        Map<String,Integer> candidateCollection= new HashMap<>();
        Set<String> itemkSet1=itemkFcMap.keySet();
        Set<String> itemkSet2=itemkFcMap.keySet();
        for (String itemk1:itemkSet1){
            for (String itemk2:itemkSet2){
                String[] tmp1=itemk1.split(ITEM_SPLIT);
                String[] tmp2=itemk2.split(ITEM_SPLIT);
                String c="";
                if (tmp1.length==1){
                    if (tmp1[0].compareTo(tmp2[0])<0){
                        c=tmp1[0]+ITEM_SPLIT+tmp2[0]+ITEM_SPLIT;
                    }
                }else {
                    boolean flag=true;
                    for (int i=0;i<tmp1.length-1;i++){
                        if (!tmp1[i].equals(tmp2[i])){
                            flag=false;
                            break;
                        }
                    }
                    if(flag && (tmp1[tmp1.length-1].compareTo(tmp2[tmp2.length-1])<0)){
                        c=itemk1+tmp2[tmp2.length-1]+ITEM_SPLIT;
                    }
                }
                boolean hashInfrequentSubSet =false;
                if (!c.equals("")){
                    String[] tmpC =c.split(ITEM_SPLIT);
                    for (int i=0;i<tmpC.length;i++){
                        StringBuilder subC= new StringBuilder();
                        for (int j=0;j<tmpC.length;j++){
                            if (i!=j){
                                subC.append(tmpC[j]).append(ITEM_SPLIT);
                            }
                        }
                        if (itemkFcMap.get(subC.toString())==null){
                            hashInfrequentSubSet=true;
                            break;
                        }
                    }
                }else {
                    hashInfrequentSubSet=true;
                }
                if(!hashInfrequentSubSet){
                    candidateCollection.put(c,0);
                }
            }
        }
        return candidateCollection;
    }

    private Map<String, Integer> getItem1FC() {
        Map<String,Integer> sItem1FcMap= new HashMap<>();
        Map<String,Integer> rItem1FcMap= new HashMap<>();
        for (String trans:translist){
            String[] items=trans.split(ITEM_SPLIT);
            for (String item:items){
                sItem1FcMap.merge(item + ITEM_SPLIT, 1, Integer::sum);
            }
        }
        Set<String> keySet=sItem1FcMap.keySet();
        for (String key:keySet){
            Integer count=sItem1FcMap.get(key);
            if (count>=SUPPORT){
                rItem1FcMap.put(key,count);
            }
        }
        return rItem1FcMap;
    }
    public Map<String, Double> getRelationRules(Map<String, Integer> frequentCollectionMap) {
        Map<String,Double> relationRules= new HashMap<>();
        Set<String> keySet=frequentCollectionMap.keySet();
        for (String key:keySet){
            double countAll =frequentCollectionMap.get(key);
            String[] keyItems =key.split(ITEM_SPLIT);
            if (keyItems.length>1){
                List<String> source= new ArrayList<>();
                Collections.addAll(source,keyItems);
                List<List<String>> result= new ArrayList<>();
                buildSubSet(source,result);
                for (List<String> itemList:result){
                    if(itemList.size()<source.size()){
                        List<String> otherList= new ArrayList<>();
                        for (String sourceItem:source){
                            if (!itemList.contains(sourceItem)){
                                otherList.add(sourceItem);
                            }
                        }
                        StringBuilder reasonStr= new StringBuilder();
                        StringBuilder resultStr= new StringBuilder();
                        for (String item:itemList){
                            reasonStr.append(item).append(ITEM_SPLIT);
                        }
                        for (String item:otherList){
                            resultStr.append(item).append(ITEM_SPLIT);
                        }
                        double countReason=frequentCollectionMap.get(reasonStr.toString());
                        double itemConfidence=countAll/countReason;
                        if (itemConfidence>=CONFIDENCE){
                            String rule=reasonStr+CON+resultStr;
                            relationRules.put(rule,itemConfidence);
                        }
                    }
                }
            }
        }
        return relationRules;
    }

    private void buildSubSet(List<String> sourceSet, List<List<String>> result) {
        if (sourceSet.size()==1){
            List<String> set = new ArrayList<>();
            set.add(sourceSet.get(0));
            result.add(set);
        }else if (sourceSet.size()>1){
            buildSubSet(sourceSet.subList(0,sourceSet.size()-1),result);
            int size=result.size();
            List<String> single = new ArrayList<>();
            single.add(sourceSet.get(sourceSet.size()-1));
            result.add(single);
            List<String> clone;
            for (int i=0;i<size;i++){
                clone = new ArrayList<>(result.get(i));
                clone.add(sourceSet.get(sourceSet.size()-1));
                result.add(clone);
            }
        }
    }


    public static void main(String[] args) {
        Apriori apriori =new Apriori();
        Map<String,Integer> frequenCollectionMap=apriori.getFC();
        System.out.println("---------------存在强关联规则如下"+"-------");
        Set<String> fcKeySet=frequenCollectionMap.keySet();
        for (String fcKey:fcKeySet){
            System.out.println(fcKey+"    :      "+frequenCollectionMap.get(fcKey));
        }
        Map<String,Double> relationRuleMap=apriori.getRelationRules(frequenCollectionMap);



        System.out.println("---------------存在强关联规则如下"+"-------");
        Set<String> rrKeySet=relationRuleMap.keySet();
        for (String rrKey:rrKeySet){
            System.out.println(rrKey+"        :         "+relationRuleMap.get(rrKey));
        }
    }
}

