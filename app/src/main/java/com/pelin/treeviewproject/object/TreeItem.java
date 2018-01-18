package com.pelin.treeviewproject.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pelin on 1/17/2018.
 */

public class TreeItem {
    private String identifier;
    private String title;
    private List<TreeItem> item = new ArrayList<>();

    private boolean hasChild = false;
    private boolean showChildren = false;
    private int level = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<TreeItem> getItem() {
        return item;
    }

    public void setItem(List<TreeItem> item) {
        this.item = item;
    }

    public boolean isHasChild() {
        return item.size() > 0;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public boolean isShowChildren() {
        return showChildren;
    }

    public void setShowChildren(boolean showChildren) {
        this.showChildren = showChildren;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
