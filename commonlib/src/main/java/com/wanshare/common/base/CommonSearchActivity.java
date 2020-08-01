package com.wanshare.common.base;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.R;
import com.wanshare.common.R2;
import com.wanshare.common.constant.CommonArouterCanstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.widget.text.CustomSearchLayout;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.KeyBoardUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * 通用搜索交互封装
 * </br>
 * Date: 2018/9/3 11:54
 *
 * @author hemin
 */
@Route(path = CommonArouterCanstant.COMMON_SEARCH)
public class CommonSearchActivity extends BaseActivity implements SearchSuggestionsSelect {

    @BindView(R2.id.csl_search)
    CustomSearchLayout mCustomSearchLayout;
    private Fragment mKeywordsListFragment;
    private Fragment mResultListFragment;

    private Fragment mCurrentFragment;

    final SearchHandler mHandler = new SearchHandler(new WeakReference<CommonSearchActivity>(this));
    // 防止将点击的建议关键词设置到文本框的时候，回调中切换到关键词列表
    private boolean mShouldShowKeyList = true;

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void initIntent() {
        String keywordsListFragmentName = getIntent().getStringExtra(IntentConstant.EXTRA_KEYWORDS_FRAGMENT);
        if (TextUtils.isEmpty(keywordsListFragmentName)) {
            LogUtil.e("keywords_fragment name must not be empty or null");
            finish();
            return;
        }
        String resultListFragmentName = getIntent().getStringExtra(IntentConstant.EXTRA_RESULT_FRAGMENT);
        if (TextUtils.isEmpty(keywordsListFragmentName)) {
            LogUtil.e("result_fragment name must not be empty or null");
            finish();
            return;
        }
        try {
            mKeywordsListFragment = Fragment.instantiate(getContext(), keywordsListFragmentName);
            mResultListFragment = Fragment.instantiate(getContext(), resultListFragmentName);

            showResultListFragment();
            showKeywordListFragment();
        } catch (Fragment.InstantiationException ex) {
            LogUtil.ex(ex);
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.common_activity_search;
    }

    @Override
    protected void initView() {
        mCustomSearchLayout.setOnTextChangeListener(new CustomSearchLayout.OnInputChangeListener() {
            @Override
            public void textChange(Editable s) {
                if (!mShouldShowKeyList) {
                    return;
                }
                showKeywordListFragment();
                mHandler.postDelaySearch(s.toString());
            }
        });

        mCustomSearchLayout.setOnSearchCancelListener(new CustomSearchLayout.OnSearchCancelListener() {
            @Override
            public void cancel() {
                CommonSearchActivity.this.finish();
            }
        });

        mCustomSearchLayout.setOnKeyboardSearchClickListener(new CustomSearchLayout.OnKeyboardSearchClickListener() {
            @Override
            public void searchClick(String content) {
                showResultListFragment();
                search(content);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSearchSelect(String keyword) {
        showResultListFragment();
        mShouldShowKeyList = false;
        mCustomSearchLayout.setInputTest(keyword);
        mShouldShowKeyList = true;
        search(keyword);
        KeyBoardUtils.closeKeybord(mCustomSearchLayout,this);
    }

    private void search(String keyword) {
        if (mResultListFragment != null && mResultListFragment instanceof Searchable) {
            ((Searchable) mResultListFragment).onSearch(keyword);
        }
    }

    private void showFragment(Fragment fragment) {
        if (mCurrentFragment != fragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (mCurrentFragment != null) {
                fragmentTransaction.hide(mCurrentFragment);
            }

            if (!fragment.isAdded()) {
                fragmentTransaction.add(R.id.fl_content, fragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.show(fragment).commitAllowingStateLoss();
            }
            mCurrentFragment = fragment;
        }
    }

    private void showResultListFragment() {
        showFragment(mResultListFragment);
    }

    private void showKeywordListFragment() {
        showFragment(mKeywordsListFragment);
    }

    private void searchSuggestions(String keyword) {
        if (mKeywordsListFragment != null && mKeywordsListFragment instanceof Searchable) {
            ((Searchable) mKeywordsListFragment).onSearch(keyword);
        }
    }

    private static class SearchHandler extends Handler {
        static final int MSG_SEARCH = 1000;

        static final int DELAY_SECOND = 500;

        WeakReference<CommonSearchActivity> mActivity;

        public SearchHandler(WeakReference<CommonSearchActivity> activity) {
            super();
            this.mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SEARCH:
                    if (mActivity.get() != null) {
                        mActivity.get().searchSuggestions((String) msg.obj);
                    }
                    break;
            }
        }

        public void postDelaySearch(String keyword) {
            this.removeMessages(SearchHandler.MSG_SEARCH);

            Message msg = Message.obtain();
            msg.what = SearchHandler.MSG_SEARCH;
            msg.obj = keyword;
            this.sendMessageDelayed(msg, SearchHandler.DELAY_SECOND);
        }

        public void clearSearch() {
            this.removeMessages(SearchHandler.MSG_SEARCH);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.clearSearch();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
