package com.wanshare.crush.account.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.contract.AuthorRealNameContract;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.model.bean.IndividualBean;
import com.wanshare.crush.account.model.bean.IndividualReq;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.AuthorRealNamePresenter;
import com.wanshare.crush.account.view.dialog.CardTypeDialog;
import com.wanshare.crush.account.view.dialog.PreviewImageDialog;
import com.wanshare.wscomponent.image.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 实名认证界面
 */
@Route(path = AccountArouterConstant.ACCOUNT_AUTHOR_REAL_NAME)
public class AuthorRealNameActivity extends BasePhotoActivity<AuthorRealNameContract.Presenter> implements AuthorRealNameContract.View {

    @BindView(R2.id.et_author_real_name)
    EditText mEtAuthorRealName;
    @BindView(R2.id.tv_author_real_name_area)
    TextView mTvAuthorRealNameArea;
    @BindView(R2.id.et_author_real_name_code)
    EditText mEtAuthorRealNameCode;
    @BindView(R2.id.iv_author_real_name_card_direct)
    ImageView mIvAuthorRealNameCardDirect;
    @BindView(R2.id.btn_author_real_name_direct)
    ImageView mBtnAuthorRealNameDirect;
    @BindView(R2.id.btn_author_real_name_direct_sample)
    TextView mTvAuthorRealNameDirectSample;
    @BindView(R2.id.iv_author_real_name_back)
    ImageView mIvAuthorRealNameBack;
    @BindView(R2.id.btn_author_real_name_back)
    ImageView mBtnAuthorRealNameBack;
    @BindView(R2.id.btn_author_real_name_back_sample)
    TextView mTvAuthorRealNameBackSample;
    @BindView(R2.id.iv_author_real_name_handheld_card)
    ImageView mIvAuthorRealNameHandheldCard;
    @BindView(R2.id.btn_author_real_name_handheld)
    ImageView mBtnAuthorRealNameHandheld;
    @BindView(R2.id.btn_author_real_name_handheld_sample)
    TextView mTvAuthorRealNameHandheldSample;
    @BindView(R2.id.btn_author_info_confirm)
    Button mBtnAuthorInfoConfirm;
    @BindView(R2.id.scroll_author_real_name)
    ScrollView mScrollAuthorRealName;
    @BindView(R2.id.iv_author_real_name_status)
    ImageView mIvAuthorRealNameStatus;
    @BindView(R2.id.tv_author_real_name_status)
    TextView mTvAuthorRealNameStatus;
    @BindView(R2.id.tv_author_real_name_status_reason)
    TextView mTvAuthorRealNameStatusReason;
    @BindView(R2.id.tv_author_real_name_status_name)
    TextView mTvAuthorRealNameStatusName;
    @BindView(R2.id.tv_author_real_name_status_type)
    TextView mTvAuthorRealNameStatusType;
    @BindView(R2.id.tv_author_real_name_status_code)
    TextView mTvAuthorRealNameStatusCode;
    @BindView(R2.id.ll_author_real_name_status_info)
    LinearLayout mLlAuthorRealNameStatusInfo;
    @BindView(R2.id.ll_author_real_name_status)
    LinearLayout mLlAuthorRealNameStatus;
    @BindView(R2.id.btn_author_real_name_reset)
    Button mBtnAuthorRealNameReset;
    @BindView(R2.id.btn_author_real_name_type)
    RelativeLayout mBtnAuthorRealNameType;
    @BindView(R2.id.tv_author_real_name_type)
    TextView mTvAuthorRealNameType;
    @BindView(R2.id.tv_author_real_name_direct_tips)
    TextView mTvAuthorRealNameDirectTips;
    @BindView(R2.id.tv_author_real_name_back_tips)
    TextView mTvAuthorRealNameBackTips;
    @BindView(R2.id.tv_author_real_name_handheld_tips)
    TextView mTvAuthorRealNameHandheldTips;

    public static final String AUTHOR_TYPE_NONE = "none";
    public static final String AUTHOR_TYPE_APPLIED = "applied";
    public static final String AUTHOR_TYPE_ACCEPTED = "accepted";
    public static final String AUTHOR_TYPE_REJECTED = "rejected";


    //none-未认证, applied-审核中, accepted-成功, rejected-已拒绝
    private String mStrStatus = "none";
    private int type = 1;
    private String mCounty = "";
    private String mName;
    private String mCode;
    //0 正面 1:反面 2:手持
    private int mType = 0;
    private String mStrDirectPath;
    private String mStrBackPath;
    private String mStrHandheldPath;

    @Override
    protected AuthorRealNameContract.Presenter getPresenter() {
        return new AuthorRealNamePresenter(this);
    }

    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mStrStatus = bundle.getString("status", "none");
        }
    }

    @Override
    protected void initData() {
        setCounty();
    }

    private void setCounty() {
        AccountInfoBean bean = AccountCacheManager.getInstance().getAccountInfoBean();
        if (bean == null) {
            return;
        }
        mCounty = bean.getCountry();
        mTvAuthorRealNameArea.setText(mCounty);
    }


    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_author_real_name);

        showView();
    }

    private void showStatusView() {
        mIvAuthorRealNameStatus.setBackgroundResource(mPresenter.getIvStatus(mStrStatus));
        mTvAuthorRealNameStatus.setText(mPresenter.getTvStatus(mStrStatus));
        mTvAuthorRealNameStatusReason.setVisibility(mPresenter.isRefuse(mStrStatus) ? View.VISIBLE : View.GONE);
        mBtnAuthorRealNameReset.setVisibility(mPresenter.isRefuse(mStrStatus) ? View.VISIBLE : View.GONE);
        mLlAuthorRealNameStatusInfo.setVisibility(mPresenter.isRefuse(mStrStatus) ? View.GONE : View.VISIBLE);
        if (mPresenter.isRefuse(mStrStatus)) {
            AccountInfoBean info = AccountCacheManager.getInstance().getAccountInfoBean();
            if (info == null) {
                return;
            }
            AccountInfoBean.CertificationAuditBean bean = info.getCertificationAudit();
            mTvAuthorRealNameStatusReason.setText(getString(R.string.account_author_fail_reason,
                    bean == null ? "" : bean.getRejectedReason()));
        } else {
            mPresenter.queryIndividual();
        }
    }


    private void showView() {
        if (mPresenter.isAuthor(mStrStatus)) {
            mScrollAuthorRealName.setVisibility(View.GONE);
            mLlAuthorRealNameStatus.setVisibility(View.VISIBLE);
            showStatusView();
        } else {
            mLlAuthorRealNameStatus.setVisibility(View.GONE);
            mScrollAuthorRealName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_author_real_name;
    }


    @OnClick({R2.id.btn_author_real_name_direct, R2.id.btn_author_real_name_back,
            R2.id.btn_author_real_name_handheld, R2.id.btn_author_info_confirm,
            R2.id.btn_author_real_name_reset, R2.id.btn_author_real_name_type,
            R2.id.btn_author_real_name_direct_sample, R2.id.btn_author_real_name_back_sample,
            R2.id.btn_author_real_name_handheld_sample})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_author_real_name_direct) {
            mType = 0;
            onStorage();

        } else if (i == R.id.btn_author_real_name_back) {
            mType = 1;
            onStorage();

        } else if (i == R.id.btn_author_real_name_handheld) {
            mType = 2;
            onStorage();

        } else if (i == R.id.btn_author_info_confirm) {
            confirm();
        } else if (i == R.id.btn_author_real_name_reset) {
            mStrStatus = AUTHOR_TYPE_NONE;
            showView();

        } else if (i == R.id.btn_author_real_name_type) {
            showTypeDialog();
        } else if (i == R.id.btn_author_real_name_direct_sample) {
            showPreviewDialog(R.drawable.ic_sm_zm);
        } else if (i == R.id.btn_author_real_name_back_sample) {
            showPreviewDialog(R.drawable.ic_sm_bm);
        } else if (i == R.id.btn_author_real_name_handheld_sample) {
            showPreviewDialog(R.drawable.ic_sm_scqm);
        }
    }

    private void showPreviewDialog(int resourceId) {
        PreviewImageDialog directDialog = new PreviewImageDialog(getActivity());
        directDialog.setResourceId(resourceId);
        directDialog.show();
    }

    private void confirm() {

        mName = mEtAuthorRealName.getText().toString();
        mCode = mEtAuthorRealNameCode.getText().toString();

        if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mCode)
                || TextUtils.isEmpty(mStrDirectPath) || TextUtils.isEmpty(mStrBackPath)
                || TextUtils.isEmpty(mStrHandheldPath) || TextUtils.isEmpty(mCounty)) {

            showLongToast(getString(R.string.account_author_confirm_tips));

            return;
        }

        List<String> list = new ArrayList<>();
        list.add(mStrDirectPath);
        list.add(mStrBackPath);
        list.add(mStrHandheldPath);
        showLoading("");

        mPresenter.uploadFile(list);
    }

    @Override
    public void resultAlbum(ArrayList<String> list) {
        switch (mType) {
            case 0:
                mStrDirectPath = list.get(0);
                loadImage(mIvAuthorRealNameCardDirect, mStrDirectPath);
                break;
            case 1:
                mStrBackPath = list.get(0);
                loadImage(mIvAuthorRealNameBack, mStrBackPath);
                break;
            case 2:
                mStrHandheldPath = list.get(0);
                loadImage(mIvAuthorRealNameHandheldCard, mStrHandheldPath);
                break;
        }
    }

    private void loadImage(ImageView image, String path) {
        ImageLoader.with(this).load(new File(path)).into(image);
    }

    @Override
    public void onIndividualResult() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void resultUploadFile(List<String> list) {
        if (list.size() < 3) {
            hideLoading();
            showLongToast(getString(R.string.account_author_upload_fail));
            return;
        }
        mPresenter.individual(new IndividualReq(mCounty, mName, type + "", mCode,
                list.get(0), list.get(1), list.get(2)));
    }

    @Override
    public void resultQueryIndividual(IndividualBean bean) {
        if (bean == null) {
            return;
        }
        mTvAuthorRealNameStatusName.setText(bean.getName());
        mTvAuthorRealNameStatusCode.setText(bean.getNumber());
        mTvAuthorRealNameStatusType.setText(bean.getType());
    }

    private void showTypeDialog() {
        final CardTypeDialog dialog = new CardTypeDialog(this);
        dialog.setOnItemCardTypeListener(new CardTypeDialog.OnItemCardTypeListener() {
            @Override
            public void onSelect(int type, String card) {
                AuthorRealNameActivity.this.type = type;
                mTvAuthorRealNameType.setText(card);
                changeCardTips();
            }

        });
        dialog.show();
    }

    private void changeCardTips() {
        switch (type) {
            case 1:
                mTvAuthorRealNameDirectTips.setText(R.string.account_author_face_img_tips);
                mTvAuthorRealNameBackTips.setText(R.string.account_author_back_img_tips);
                mTvAuthorRealNameHandheldTips.setText(R.string.account_author_handheld_img_tips);
                break;
            case 2:
                mTvAuthorRealNameDirectTips.setText(R.string.account_author_face_img_pass_tips);
                mTvAuthorRealNameBackTips.setText(R.string.account_author_back_img_pass_tips);
                mTvAuthorRealNameHandheldTips.setText(R.string.account_author_handheld_img_pass_tips);
                break;
        }
    }

}

