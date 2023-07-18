package com.nebula.commons.utils.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TreeNode implements Serializable {
    private static final long serialVersionUID = -82271382494327331L;
    protected Long id;
    protected Long parentId;
    protected Integer sort;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void add(TreeNode node){
        children.add(node);
    }
}
