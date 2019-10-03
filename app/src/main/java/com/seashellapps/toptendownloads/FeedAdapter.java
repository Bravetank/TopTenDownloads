package com.seashellapps.toptendownloads;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;


import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<FeedEntry> applications;

    public FeedAdapter(Context context, int resource, List<FeedEntry> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.applications = applications;
    }


    @Override
    public int getCount() {
        return applications.size();
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        View view = layoutInflater.inflate(layoutResource, parent, false);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvCategory = view.findViewById(R.id.tvCategory);
        TextView tvDescription = view.findViewById(R.id.tvDescription);

        FeedEntry currentApp = applications.get(position);

        tvTitle.setText(currentApp.getTitle());
        tvCategory.setText(currentApp.getCategory());
        tvDescription.setText(currentApp.getDescription());

        return view;
    }



}
