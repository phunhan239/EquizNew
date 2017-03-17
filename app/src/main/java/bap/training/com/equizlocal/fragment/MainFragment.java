package bap.training.com.equizlocal.fragment;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import bap.training.com.equizlocal.R;
import bap.training.com.equizlocal.adapter.RecyclerDataAdapter;
import bap.training.com.equizlocal.controller.VocabularyController;

/**
 * Created by dvan on 2/15/2017.
 */
public class MainFragment extends Fragment implements RecyclerDataAdapter.OnItemClick {
    private List<HashMap<String, String>> mLists;
    private RecyclerDataAdapter mRecyclerDataAdapter;
    private VocabularyController mVocabularyController;
    private static final String CURRENT_POSITION = "position";
    private MediaPlayer mMediaPlayer = null;

    public static MainFragment sInstance(int position) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CURRENT_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);

        mVocabularyController = VocabularyController.sGetInstance();
        mVocabularyController.init(container.getContext());

        LinearLayoutManager llm = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);

        switch (getArguments().getInt(CURRENT_POSITION)) {
            case 1:
                mVocabularyController.loadSingleWordFromJson();
                mLists = mVocabularyController.getListSingleWords();
                break;
            case 2:
                mVocabularyController.loadPhraseFromJson();
                mLists = mVocabularyController.getListPhrases();
                break;
        }
        //adapter
        mRecyclerDataAdapter = new RecyclerDataAdapter(mLists, this);
        mRecyclerView.setAdapter(mRecyclerDataAdapter);
        //add view
        ((ViewPager) container).addView(layout);

        return layout;
    }

    @Override
    public void onStop() {
        super.onStop();
        mLists.clear();
    }

    @Override
    public void onClickItem(int position, int id) {
        if (id == 0) {
            playSoundCurrent(position);
        } else {
            addFragmentContent(new ContainerWordFragment(mLists, position));
        }
    }

    private void playSoundCurrent(int position) {
        String filename = mLists.get(position).get("sound").trim();
        mMediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getContext().getAssets().openFd(filename);
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer.release();
            }
        });
    }

    protected void addFragmentContent(Fragment fragment) {
        if (fragment != null) {
            FragmentManager mFragmentManager = getFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.add(R.id.containerFragment, fragment);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        }
    }
}