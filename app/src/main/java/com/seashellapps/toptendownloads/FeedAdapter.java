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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FeedEntry currentApp = applications.get(position);

        viewHolder.tvTitle.setText(currentApp.getTitle());
        viewHolder.tvCategory.setText(currentApp.getCategory());
        viewHolder.tvDescription.setText(currentApp.getDescription());

        return convertView;
    }

    private class ViewHolder {
        final TextView tvTitle;
        final TextView tvCategory;
        final TextView tvDescription;

        public ViewHolder(View v) {
            this.tvTitle = v.findViewById(R.id.tvTitle);
            this.tvCategory = v.findViewById(R.id.tvCategory);
            this.tvDescription = v.findViewById(R.id.tvDescription);
        }
    }
}
