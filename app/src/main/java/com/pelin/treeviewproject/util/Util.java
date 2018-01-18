package com.pelin.treeviewproject.util;

import android.content.Context;
import com.pelin.treeviewproject.object.TreeItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pelin on 1/17/2018.
 */

public class Util {

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<TreeItem> addorRemoveSubList(List<TreeItem> mainList, List<TreeItem> subList, int position, boolean isAdd) {
        List<TreeItem> newList;
        if (isAdd) {
            newList = addSubList(mainList, subList,position);
        } else {
            newList = removeSubList(mainList, subList, position);
        }
        return newList;
    }

    public static List<TreeItem> addSubList(List<TreeItem> mainList, List<TreeItem> subList, int position) {
        List<TreeItem> newList = new ArrayList<>();
        for(int i = 0; i < mainList.size(); i ++) {
            newList.add(mainList.get(i));
            TreeItem item = mainList.get(position);
            item.setShowChildren(true);
            if(i == position) {
                for (int j = 0; j < subList.size(); j++) {
                    TreeItem child = subList.get(j);
                    child.setLevel(item.getLevel() + 1);
                    newList.add(child);
                }
            }
        }
        return newList;
    }

    public static List<TreeItem> removeSubList(List<TreeItem> mainList, List<TreeItem> subList,int position) {
        for(int i = 0; i < mainList.size(); i ++) {
            if(i == position) {
                TreeItem item = mainList.get(position);

                for (int j = 0; j < subList.size(); j++) {
                    TreeItem child = mainList.get(position + 1);
                    mainList.remove(position + 1);
                    if (child.isHasChild() && child.isShowChildren()) {
                        child.setShowChildren(false);
                        removeSubList(mainList, child.getItem(), position);
                    }
                }
                item.setShowChildren(false);
            }
        }
        return mainList;
    }
}
