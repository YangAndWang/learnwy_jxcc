package com.learnwy.util.json;

import com.learnwy.model.SysMenu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-05.
 */
public class MenuTreeJson {
    static class Node {
        SysMenu data;
        Node parent;
        LinkedList<Node> children;

        public Node() {
            children = new LinkedList<>();
        }
    }

    ArrayList<Node> trees = new ArrayList<>();

    public static String getSysMenuJson(Node tree, StringBuffer sb) {
        SysMenu s = tree.data;
        sb.append("[ ");
        sb.append(s.getSysMenuId()).append(",\"");
        sb.append(s.getDisplayName()).append("\",\"");
        sb.append(s.getPath()).append("\",");
        sb.append(s.getParentId());
        if (tree.children.size() > 0) {
            sb.append(",");
            sb.append("[ ");
            for (Node n : tree.children) {
                getSysMenuJson(n, sb);
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append("]");
        }
        sb.append("]");
        return sb.toString();
    }

    public static String getSysMenuJson(List<SysMenu> sysMenus) {
        Node r = simpleTree(sysMenus);
        StringBuffer sb = new StringBuffer();
        return getSysMenuJson(r, sb);
    }

    public static Node simpleTree(List<SysMenu> sysMenus) {
        Node root;
        root = new Node();
        root.data = new SysMenu(0, "", "/", 0);
        simpleTree(sysMenus, root);
        return root;
    }

    private static void simpleTree(List<SysMenu> sysMenus, Node parent) {
        LinkedList<Integer> removeIndexs = new LinkedList<>();
        int i = 0;
        long pid = parent.data.getSysMenuId();
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getParentId() == pid) {
                removeIndexs.add(i);
                Node newNode = new Node();
                newNode.data = sysMenu;
                newNode.parent = parent;
                parent.children.add(newNode);
            }
        }
        while (removeIndexs.size() > 0) {
            sysMenus.remove(removeIndexs.getLast());
            removeIndexs.removeLast();
        }
        if (sysMenus.size() > 0) {
            for (Node n : parent.children) {
                simpleTree(sysMenus, n);
            }
        }
    }

    public boolean add(SysMenu sysMenu) {
        boolean ok = false;
        for (Node r : this.trees) {
            ok = add(r, sysMenu);
            if (ok) {
                break;
            }
        }
        if (ok) {
            sort();
        } else {
            Node newTree = new Node();
            newTree.data = sysMenu;
            trees.add(newTree);
        }
        merge();
        return ok;
    }

    private void sort() {
        int i = 0;
        Node c, p;
        for (i = 0; i < this.trees.size(); i++) {
            c = this.trees.get(i);
            p = c.parent;
            while (p != null) {
                c = p;
                p = c.parent;
            }
            this.trees.set(i, c);
        }
    }

    @Deprecated
    private void merge() {
        boolean change = false;
        if (change) {
            merge();
        }
    }

    private boolean mergeNode(Node r1, Node r2) {
        boolean ok = false;

        return ok;
    }


    private boolean add(Node r, SysMenu sysMenu) {
        boolean ok = false;
        if (r.data.getParentId() == sysMenu.getSysMenuId()) {
            Node newNode = new Node();
            newNode.data = sysMenu;
            newNode.children.add(r);
            r.parent = newNode;
            ok = true;
        } else {
            ok = addChild(r, sysMenu);
        }
        return ok;
    }

    private boolean addChild(Node r, SysMenu sysMenu) {
        boolean ok = false;
        if (sysMenu.getParentId() == r.data.getSysMenuId()) {
            Node newNode = new Node();
            newNode.data = sysMenu;
            newNode.parent = r;
            r.children.add(newNode);
            return true;
        }
        for (Node n : r.children) {
            if (addChild(n, sysMenu)) {
                return true;
            }
        }
        return ok;
    }
}
