package bap.training.com.equizlocal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import bap.training.com.equizlocal.R;

/**
 * Created by dvan on 2/13/2017.
 */

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.DataViewHolder> {
    private List<HashMap<String, String>> mListWords;

    public interface OnItemClick {
        void onClickItem(int position, int id);
    }

    private OnItemClick mOnItemClick;

    public RecyclerDataAdapter(List<HashMap<String, String>> listWords, OnItemClick listener) {
        this.mListWords = listWords;
        mOnItemClick = listener;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_vocabulary, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        String word = mListWords.get(position).get("word");
        holder.mTvWord.setText(word);
    }

    @Override
    public int getItemCount() {
        return mListWords == null ? 0 : mListWords.size();
    }

    /**
     * Data ViewHolder class.
     */
    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button mBtnSound;
        private TextView mTvWord;

        public DataViewHolder(View itemView) {
            super(itemView);
            mBtnSound = (Button) itemView.findViewById(R.id.btnSound);
            mTvWord = (TextView) itemView.findViewById(R.id.tvWord);
            mBtnSound.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSound:
                    mOnItemClick.onClickItem(getLayoutPosition(),0);
                    break;
                default:
                    mOnItemClick.onClickItem(getLayoutPosition(),1);
                    break;
            }
        }
    }
}
