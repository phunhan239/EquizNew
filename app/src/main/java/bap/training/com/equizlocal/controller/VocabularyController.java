package bap.training.com.equizlocal.controller;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bap.training.com.equizlocal.utlis.Utils;

/**
 * Created by dvan on 2/13/2017.
 */

public class VocabularyController {
    private static VocabularyController sInstance = null;
    private static final String ASSET_FILE_PHRASE = "phrase.json";
    private static final String ASSET_FILE_SINGLE_WORD = "singleword.json";
    private List<HashMap<String, String>> mListPhrases = new ArrayList<>();
    private List<HashMap<String, String>> mListSingleWords = new ArrayList<>();
    private static final String TAG = "nhandev";
    private Context mContext;

    public static VocabularyController sGetInstance() {
        if (sInstance == null) {
            sInstance = new VocabularyController();
        }
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public List<HashMap<String, String>> getListPhrases() {
        if (mListPhrases == null) {
            mListPhrases = new ArrayList<>();
        }
        return mListPhrases;
    }

    public List<HashMap<String, String>> getListSingleWords() {
        if (mListSingleWords == null) {
            mListSingleWords = new ArrayList<>();
        }
        return mListSingleWords;
    }

    public void loadSingleWordFromJson() {
        loadSingleWordFromJson(ASSET_FILE_SINGLE_WORD);
    }

    private void loadSingleWordFromJson(String jsonFile) {
        try {
            String jsonString = Utils.readAssetJSONFile(mContext, jsonFile);
            //getting object vocabulary
            JSONObject mJsonObject = new JSONObject(jsonString);
            //getting array vocabularies
            JSONArray words = mJsonObject.getJSONArray("words");
            for (int i = 0; i < words.length(); i++) {
                JSONObject vocabulary = words.getJSONObject(i);
                String word = vocabulary.getString("word");
                String voice = vocabulary.getString("voice");
                String vietnamese = vocabulary.getString("vietnamese");

                //mean object
                JSONObject meanObj = new JSONObject(vocabulary.getString("mean"));
                String mNoun = meanObj.getString("noun");
                String mVerb = meanObj.getString("verb");
                String mAdjective = meanObj.getString("adjective");
                //synonym object
                JSONObject synonymObj = new JSONObject(vocabulary.getString("synonym"));
                String sNoun = synonymObj.getString("noun");
                String sVerb = synonymObj.getString("verb");
                String sAdjective = synonymObj.getString("adjective");

                String example = vocabulary.getString("example");
                String sound = vocabulary.getString("sound");

                HashMap<String, String> object = new HashMap<>();
                object.put("word", word);
                object.put("voice", voice);
                object.put("vietnamese", vietnamese);
                object.put("mNoun", mNoun);
                object.put("mVerb", mVerb);
                object.put("mAdjective", mAdjective);
                object.put("sNoun", sNoun);
                object.put("sVerb", sVerb);
                object.put("sAdjective", sAdjective);
                object.put("example", example);
                object.put("sound", sound);
                //add to mList
                mListSingleWords.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPhraseFromJson() {
        loadPhraseFromJson(ASSET_FILE_PHRASE);
    }

    private void loadPhraseFromJson(String jsonFile) {
        try {
            String jsonString = Utils.readAssetJSONFile(mContext, jsonFile);
            //getting object vocabulary
            JSONObject mJsonObject = new JSONObject(jsonString);
            //getting array vocabularies
            JSONArray phrases = mJsonObject.getJSONArray("phrases");
            for (int i = 0; i < phrases.length(); i++) {
                JSONObject phrase = phrases.getJSONObject(i);
                String word = phrase.getString("word");
                String vietnamese = phrase.getString("vietnamese");
                String mean = phrase.getString("mean");
                String example = phrase.getString("example");
                String sound = phrase.getString("sound");

                HashMap<String, String> object = new HashMap<>();
                object.put("word", word);
                object.put("vietnamese", vietnamese);
                object.put("mean", mean);
                object.put("example", example);
                object.put("sound", sound);
                //add to mList
                mListPhrases.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
