package com.pelin.treeviewproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pelin.treeviewproject.R;
import com.pelin.treeviewproject.adapter.TreeViewAdapter;
import com.pelin.treeviewproject.object.TreeItem;
import com.pelin.treeviewproject.util.Const;
import com.pelin.treeviewproject.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.imageViewLeft)
    ImageView imageViewLeft;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.imageViewRight)
    ImageView imageViewRight;

    private TreeViewAdapter treeViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initListeners();
        getDataFromJson();
    }

    private void initListeners() {
        imageViewLeft.setOnClickListener(this);
        imageViewRight.setOnClickListener(this);
    }

    private void getDataFromJson() {
        try {
            JSONObject obj = new JSONObject(Util.loadJSONFromAsset(this, "tree.txt"));
            JSONArray jsonArray = obj.getJSONObject("tableofcontents").getJSONArray("item");

            Gson gson = new Gson();
            TreeItem[] itemList = gson.fromJson(String.valueOf(jsonArray), TreeItem[].class);
            treeViewAdapter = new TreeViewAdapter(Arrays.asList(itemList), MainActivity.this);
            listView.setAdapter(treeViewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        textViewTitle.setText(title);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageViewLeft:
                treeViewAdapter.navigate(Const.LEFT);
                break;

            case R.id.imageViewRight:
                treeViewAdapter.navigate(Const.RIGHT);
                break;

            default:
                break;
        }
    }

    public void scrollToPos(int pos) {
        listView.smoothScrollToPosition(pos);
    }

    public void setVisibilityOfNavigateButtons(int selectedItemPosition) {
        imageViewLeft.setVisibility(selectedItemPosition == 0 ? View.GONE: View.VISIBLE);
        imageViewRight.setVisibility(selectedItemPosition == listView.getAdapter().getCount() - 1 ? View.GONE: View.VISIBLE);
    }
}
