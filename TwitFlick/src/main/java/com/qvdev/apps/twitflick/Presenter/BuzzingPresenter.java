package com.qvdev.apps.twitflick.Presenter;

import android.content.Intent;
import android.net.Uri;

import com.qvdev.apps.twitflick.Adapters.BuzzingListAdapter;
import com.qvdev.apps.twitflick.Model.BuzzingModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.BuzzingView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.com.qvdev.apps.twitflick.network.NetworkHelper;
import com.qvdev.apps.twitflick.listeners.onBuzzingItemClickedListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by dirkwilmer on 7/29/13.
 */
public class BuzzingPresenter implements onBuzzingItemClickedListener {

    private static final String FORCE_FULLSCREEN = "force_fullscreen";

    private BuzzingView mBuzzingView;
    private BuzzingModel mBuzzingModel;
    private BuzzingListAdapter mBuzzingListAdapter;

    public BuzzingPresenter(BuzzingView buzzingView) {
        mBuzzingView = buzzingView;
        mBuzzingModel = new BuzzingModel();

        init();
        getBuzzing();
    }

    private void init() {
        mBuzzingListAdapter = new BuzzingListAdapter(mBuzzingView.getActivity(), R.layout.buzzing_list_item, mBuzzingModel);
        mBuzzingListAdapter.setOnBuzzingItemClicked(this);
        mBuzzingView.setAdapter(mBuzzingListAdapter);

    }


    private void getBuzzing() {
        //TODO::Think of a general Twitlfick api
        NetworkHelper networkHelper = new NetworkHelper(this);
        URL url = null;
        try {
            url = new URL("" + mBuzzingView.getString(R.string.base_url) + mBuzzingView.getString(R.string.api_url) + mBuzzingView.getString(R.string.buzzing_url) + mBuzzingView.getString(R.string.buzzing_retrieve_count) + mBuzzingView.getString(R.string.buzzing_retrieve_limit));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        networkHelper.execute(new URL[]{url});
    }

    public void refresh(List<Buzzing> result) {
        mBuzzingModel.getBuzzing().clear();
        mBuzzingModel.getBuzzing().addAll(result);
        mBuzzingListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTrailerClicked(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.putExtra(FORCE_FULLSCREEN, true);
        mBuzzingView.startActivity(intent);
    }

    @Override
    public void onLikeClicked(int position) {
        Buzzing buzzing = mBuzzingModel.getBuzzing().get(position);
        String likeText = mBuzzingView.getString(R.string.share_like, buzzing.getName(), (int) buzzing.getID());
        share(likeText);
    }

    @Override
    public void onHateClicked(int position) {
        Buzzing buzzing = mBuzzingModel.getBuzzing().get(position);
        String hateText = mBuzzingView.getString(R.string.share_hate, buzzing.getName(), (int) buzzing.getID());
        share(hateText);
    }

    private void share(String shareText) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        mBuzzingView.startActivity(Intent.createChooser(sendIntent, mBuzzingView.getResources().getText(R.string.send_to)));
    }
}
