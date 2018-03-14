package com.ecp.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TreeNode
 * @Description 树结点泛型类
 * @author Administrator
 * @Date 2017年5月18日 上午8:57:31
 * @version 1.0.0
 * @param <T>
 * http://blog.csdn.net/lc375660910/article/details/52386321
 */
public class TreeNode<T> {  
  
    private T entity;  
    private boolean hasChildren = false;  
    private List<TreeNode<T>> children ;  	
  
  
    /** 
     * 初始TreeNode 
     * @param entity 被注入的更新内容 
     */  
    public TreeNode(T entity){  
        this.entity = entity;  
    }  
  
    /** 
     * 返回TreeNode包含的实例 
     * @return 
     */  
    public T getEntity(){  
        return entity;  
    }  
  
    /** 
     * 添加子节点,被添加的子节点将放置在最后 
     * @param t 
     */  
    public void addChildren(TreeNode<T> t){  
        if(children == null)  
            children = new ArrayList<TreeNode<T>>();  
        if (!children.contains(t)) {  
            hasChildren = children.add(t); //如果没有子节点并添加子节点成功，则标注为true  
        } else {  
            children.remove(t);  
            children.add(t);  
        }  
    }  
  
    /** 
     * 添加子节点,被添加的子节点将放置在指定位置，如果指定位置不存在则被放置在最后 
     * @param t 
     * @param index 指定位置 
     */  
    public void addChildrenAt(TreeNode<T> t, int index){  
        if(children == null)  
            addChildren(t);  
        else if(!children.contains(t)) {  
            if(index<0||index>children.size())  
                children.add(t);  
            else  
                children.add(index, t);  
            hasChildren = !hasChildren ? true : false; //如果没有子节点并添加子节点成功，则标注为true  
        }  
    }  
  
    /** 
     * 查看是否存在子列点 
     * @return 
     */  
    public boolean hasChildren(){  
        return hasChildren;  
    }  
  
    /** 
     * 设置存在子节点 
     */  
    public void setHasChildren(boolean has){  
        this.hasChildren = has;  
    }  
  
    /** 
     * 返回子列表 
     * @return 
     */  
    public List<TreeNode<T>> getChildren(){  
        if(children!=null && !children.isEmpty())  
            return children;  
        return null;  
    }  
  
    /** 
     * 得到指定位置的节点信息 
     * @param number 指定位置，如果指定位置不存在将抛出异常 
     * @return 返回指定位置对应的内容 
     */  
    public TreeNode<T> getChildAt(int number){  
        if(children != null && !children.isEmpty()) {  
            return children.get(number);  
        }  
        return null;  
    }  
  
    @Override  
    public boolean equals(Object obj) {  
        if(obj == null && entity == null)   //both null  
            return true;  
        if(obj != null && entity != null && obj instanceof TreeNode){ // both not null  
            TreeNode t = (TreeNode) obj;  
            return entity.equals(t.getEntity());  
        }  
        return false;   //one of them is null or obj is not a instance of TreeNode  
    }  
  
}  
