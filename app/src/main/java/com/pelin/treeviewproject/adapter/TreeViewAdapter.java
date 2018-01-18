package com.pelin.treeviewproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pelin.treeviewproject.R;
import com.pelin.treeviewproject.activity.MainActivity;
import com.pelin.treeviewproject.object.TreeItem;
import com.pelin.treeviewproject.util.Const;
import com.pelin.treeviewproject.util.Util;

import java.util.List;

/**
 * Created by pelin on 1/17/2018.
 */

public class TreeViewAdapter extends ArrayAdapter<TreeItem> {
    private List<TreeItem> itemList;
    private Context context;

    private TreeItem selectedItem;
    private int selectedItemPosition = -1;

    public TreeViewAdapter(List<TreeItem> itemList, Context ctx) {
        super(ctx, R.layout.layout_tree_item, itemList);

        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public TreeItem getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_tree_item, parent, false);
        }

        final TreeItem item = itemList.get(position);

        ImageView imageViewExpandCollapse = (ImageView) convertView.findViewById(R.id.imageViewExpandCollapse);
        TextView textTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
        textTitle.setText(item.getTitle());
        if(item.isHasChild()) {
            imageViewExpandCollapse.setVisibility(View.VISIBLE);
            if(item.isShowChildren()) {
                imageViewExpandCollapse.setImageResource(R.drawable.ic_remove_black_18dp);
            } else {
                imageViewExpandCollapse.setImageResource(R.drawable.ic_add_black_18dp);
            }
        } else {
            imageViewExpandCollapse.setVisibility(View.INVISIBLE);
        }

        LinearLayout linearLayoutContainer = (LinearLayout) convertView.findViewById(R.id.linearLayoutContainer);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);


        int marginLeft = (int) context.getResources().getDimensionPixelSize(R.dimen.general_margin);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayoutContainer.getLayoutParams();
        layoutParams.setMargins(marginLeft * (item.getLevel() + 1), marginLeft, marginLeft, marginLeft);
        linearLayoutContainer.setLayoutParams(layoutParams);

        linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getItem()!= null) {
                        selectedItem = item;
                        selectedItemPosition = position;
                        itemList = Util.addorRemoveSubList(itemList, item.getItem(), position, !item.isShowChildren());
                        ((MainActivity) context).setVisibilityOfNavigateButtons(selectedItemPosition);
                        notifyDataSetChanged();
                    }

                }
            });

        if(selectedItem!= null) {
            if(item.getIdentifier().equals(selectedItem.getIdentifier())) {
                linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorSelected));
            } else {
                linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorNormal));
            }
            ((MainActivity) context).setTitle(selectedItem.getTitle());
        }

        return convertView;
    }


    public void navigate(int direction) {
        switch (direction) {
            case Const.LEFT:
                if(selectedItemPosition > 0) {
                    selectedItemPosition -= 1;
                }
                break;

            case Const.RIGHT:
                if(selectedItemPosition < itemList.size()) {
                    selectedItemPosition += 1;
                }
                break;

            default:
                break;
        }

        selectedItem = itemList.get(selectedItemPosition);
        if(selectedItem.isHasChild() && !selectedItem.isShowChildren()) {
            itemList = Util.addorRemoveSubList(itemList, selectedItem.getItem(), selectedItemPosition, !selectedItem.isShowChildren());
        }

        ((MainActivity) context).setVisibilityOfNavigateButtons(selectedItemPosition);
        ((MainActivity) context).scrollToPos(selectedItemPosition);
        notifyDataSetChanged();
    }
}