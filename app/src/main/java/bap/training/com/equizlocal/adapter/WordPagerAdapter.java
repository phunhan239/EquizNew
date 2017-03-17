package bap.training.com.equizlocal.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import bap.training.com.equizlocal.R;

/**
 * Created by dvan on 2/14/2017.
 */

public class WordPagerAdapter extends PagerAdapter {
    private List<HashMap<String, String>> mListWords;
    //phrase
    private static final String WORD = "word";
    private static final String VIETNAMESE = "vietnamese";
    private static final String MEAN = "mean";
    private static final String EXAMPLE = "example";
    private static final String SOUND = "sound";
    //single world
    private static final String VOICE = "voice";
    private static final String M_NOUN = "mNoun";
    private static final String M_VERB = "mVerb";
    private static final String M_ADJ = "mAdjective";
    private static final String S_NOUN = "sNoun";
    private static final String S_VERB = "sVerb";
    private static final String S_ADJ = "sAdjective";

    public WordPagerAdapter(List<HashMap<String, String>> mListWords) {
        this.mListWords = mListWords;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(container.getContext());
        View rootView = mLayoutInflater.inflate(R.layout.fragment_word_layout, container, false);
        //set widgets
        TextView mTvWord = (TextView) rootView.findViewById(R.id.tvWord);
        TextView mTvMean = (TextView) rootView.findViewById(R.id.tvMean);
        TextView mTvVietnamese = (TextView) rootView.findViewById(R.id.tvVietnamese);
        TextView mTvNoun = (TextView) rootView.findViewById(R.id.tvNoun);
        TextView mTvVerb = (TextView) rootView.findViewById(R.id.tvVerb);
        TextView mTvAdjective = (TextView) rootView.findViewById(R.id.tvAdjective);
        //get arguments
        String word = mListWords.get(position).get(WORD).trim();
        String vietnamese = mListWords.get(position).get(VIETNAMESE).trim();
        //set text
        mTvWord.setText(word);
        mTvVietnamese.setText("Dịch sang tiếng việt:" + vietnamese);
        //add view
        ((ViewPager) container).addView(rootView);
        return rootView;
    }

    @Override
    public int getCount() {
        return mListWords == null ? 0 : mListWords.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}