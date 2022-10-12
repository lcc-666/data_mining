package com.id3.node;
import java.util.HashMap;
import java.util.Map;

public class TreeNode {

    private String nodeName;
    private Map<String,Attributes> attributes;
    private double gain;

    public double getGain() {
        return gain;
    }
    public void setGain(double gain) {
        this.gain = gain;
    }
    public String getNodeName() {

        return nodeName;
    }
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    public Map<String, Attributes> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, Attributes> attributes) {

        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "TreeNode [nodeName=" + nodeName + ", attributes=" + attributes
                + ", gain=" + gain + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((attributes == null) ? 0 : attributes.hashCode());
        long temp;
        temp = Double.doubleToLongBits(gain);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((nodeName == null) ? 0 : nodeName.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TreeNode other = (TreeNode) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        if (Double.doubleToLongBits(gain) != Double
                .doubleToLongBits(other.gain))
            return false;
        if (nodeName == null) {
            if (other.nodeName != null)
                return false;
        } else if (!nodeName.equals(other.nodeName))
            return false;
        return true;
    }

}


class Attributes{

    private String attrName;
    private TreeNode nextNode;
    private String leafName;
    private int attrNum;
    private double h;
    Map<String, Integer> resultNum = new HashMap<String, Integer>();

    public String getLeafName() {
        return leafName;
    }
    public void setLeafName(String leafName) {
        this.leafName = leafName;
    }
    public Map<String, Integer> getResultNum() {
        return resultNum;
    }
    public void setResultNum(Map<String, Integer> resultNum) {
        this.resultNum = resultNum;
    }
    public double getH() {
        return h;
    }
    public void setH(double h) {
        this.h = h;
    }
    public String getAttrName() {
        return attrName;
    }
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    public TreeNode getNextNode() {
        return nextNode;
    }
    public void setNextNode(TreeNode nextNode) {
        this.nextNode = nextNode;
    }
    public int getAttrNum() {
        return attrNum;
    }
    public void setAttrNum(int attrNum) {
        this.attrNum = attrNum;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((attrName == null) ? 0 : attrName.hashCode());
        result = prime * result + attrNum;
        long temp;
        temp = Double.doubleToLongBits(h);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((leafName == null) ? 0 : leafName.hashCode());
        result = prime * result
                + ((nextNode == null) ? 0 : nextNode.hashCode());
        result = prime * result
                + ((resultNum == null) ? 0 : resultNum.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Attributes other = (Attributes) obj;
        if (attrName == null) {
            if (other.attrName != null)
                return false;
        } else if (!attrName.equals(other.attrName))
            return false;
        if (attrNum != other.attrNum)
            return false;
        if (Double.doubleToLongBits(h) != Double.doubleToLongBits(other.h))
            return false;
        if (leafName == null) {
            if (other.leafName != null)
                return false;
        } else if (!leafName.equals(other.leafName))
            return false;
        if (nextNode == null) {
            if (other.nextNode != null)
                return false;
        } else if (!nextNode.equals(other.nextNode))
            return false;
        if (resultNum == null) {
            if (other.resultNum != null)
                return false;
        } else if (!resultNum.equals(other.resultNum))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Attributes [attrName=" + attrName + ", nextNode=" + nextNode
                + ", leafName=" + leafName + ", attrNum=" + attrNum + ", h="
                + h + ", resultNum=" + resultNum + "]";
    }

}