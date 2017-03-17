package bap.training.com.equizlocal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bap.training.com.equizlocal.R;

/**
 * Created by dvan on 1/27/2017.
 */
public class AnswerArrayAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private int mResource;
    private List<String> mListAnswers;

    public AnswerArrayAdapter(Context mContext, int mResource, List<String> mListAnswers) {
        super(mContext, mResource, mListAnswers);
        this.mContext = mContext;
        this.mResource = mResource;
        this.mListAnswers = mListAnswers;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(mResource, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvAbcd = (TextView) convertView.findViewById(R.id.tvAbcd);
            mViewHolder.mTvAnswer = (TextView) convertView.findViewById(R.id.tvAnswerItem);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            mViewHolder.mTvAbcd.setText("A");
        } else if (position == 1) {
            mViewHolder.mTvAbcd.setText("B");
        } else if (position == 2) {
            mViewHolder.mTvAbcd.setText("C");
        } else if (position == 3) {
            mViewHolder.mTvAbcd.setText("D");
        }
        String mAnswer = mListAnswers.get(position);
        mViewHolder.mTvAnswer.setText(mAnswer.substring(2).trim().toString());
        return convertView;
    }

    private class ViewHolder {
        public TextView mTvAbcd;
        public TextView mTvAnswer;
    }
}
