package com.nebula.commons.utils.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TreeUtil {

    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public static <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {

        List<T> trees = new ArrayList<T>();

        for (T treeNode : treeNodes) {

            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            for (T it : treeNodes) {
                if (it.getParentId().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<TreeNode>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public static <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (((Long) Long.parseLong(String.valueOf(root))).equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<TreeNode>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 构建树并支持排序
     * @param treeNodes
     * @param root
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> List<T> buildWithSort(List<T> treeNodes, Object root) {
        List<T> tree = treeNodes.stream().filter(node -> node.getParentId().longValue() == Long.parseLong(String.valueOf(root))).map(curent -> {
            curent.setChildren((List<TreeNode>) findChildrenWithSort(curent, treeNodes));
            return curent;
        }).sorted((resource1, resource2) -> {
            return resource1.getSort() - resource2.getSort();
        }).collect(Collectors.toList());
        return tree;
    }

    /**
     * 递归查找子节点
     * @param parent
     * @param all
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> List<T> findChildrenWithSort(T parent, List<T> all) {
        List<T> children = all.stream().filter(curent -> curent.getParentId().longValue() == parent.getId().longValue()).map(curent -> {
            curent.setChildren((List<TreeNode>) findChildrenWithSort(curent, all));
            return curent;
        }).sorted((resource1, resource2) -> {
            return resource1.getSort() - resource2.getSort();
        }).collect(Collectors.toList());
        return children;
    }

}
