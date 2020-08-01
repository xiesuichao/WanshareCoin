package com.wanshare.crush.account.view;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.contract.AuthorInfoContract;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.AuthorInfoPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_ACCEPTED;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 认证信息界面
 * */
@Route(path = AccountArouterConstant.ACCOUNT_AUTHOR_INFO)
public class AuthorInfoActivity extends BaseActivity<AuthorInfoContract.Presenter> implements AuthorInfoContract.View {
    @BindView(R2.id.tv_author_info_personal)
    TextView mTvAuthorInfoPersonal;
    @BindView(R2.id.tv_author_info_personal_status)
    TextView mTvAuthorInfoPersonalStatus;
    @BindView(R2.id.iv_author_info_personal_status)
    ImageView mIvAuthorInfoPersonalStatus;
    @BindView(R2.id.btn_author_info_personal)
    RelativeLayout mBtnAuthorInfoPersonal;
    @BindView(R2.id.tv_author_info_company)
    TextView mTvAuthorInfoCompany;
    @BindView(R2.id.tv_author_info_company_status)
    TextView mTvAuthorInfoCompanyStatus;
    @BindView(R2.id.iv_author_info_company_status)
    ImageView mIvAuthorInfoCompanyStatus;
    @BindView(R2.id.btn_author_info_company)
    RelativeLayout mBtnAuthorInfoCompany;

    private String mStrPersonalStatus = "none";
    private String mStrCompanyStatus = "none";
    private String mStrAuthorType = "";
    private AccountInfoBean.CertificationAuditBean mAuditBean;

    @Override
    protected AuthorInfoContract.Presenter getPresenter() {
        return new AuthorInfoPresenter(this);
    }

    @Override
    protected void initData() {
        parseStatus();
        mPresenter.getAccountInfo();
    }

    private void parseStatus(){

        AccountInfoBean bean = AccountCacheManager.getInstance().getAccountInfoBean();

        if (bean == null) {
            return;
        }

        mAuditBean = bean.getCertificationAudit();

        if (mAuditBean == null) {
            return;
        }
        mStrAuthorType = mAuditBean.getCertificationType();
        if ("company".equals(mStrAuthorType)) {
            mStrCompanyStatus = mAuditBean.getCertificationStatus();
        }else{
            mStrPersonalStatus = mAuditBean.getCertificationStatus();
        }
        changeView();
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_author_message);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_author_info;
    }

    @OnClick({R2.id.btn_author_info_personal, R2.id.btn_author_info_company})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_author_info_personal) {
            if (!mPresenter.isEnter(mStrCompanyStatus)) {
                return;
            }
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_AUTHOR_REAL_NAME).
                    withString("status", mStrPersonalStatus).
                    navigation(this, 1);
            changeView();

        } else if (i == R.id.btn_author_info_company) {
            if (!mPresenter.isEnter(mStrPersonalStatus)) {
                return;
            }
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_AUTHOR_COMPANY).
                    withString("status", mStrCompanyStatus).
                    navigation(this, 1);
            changeView();

        }
    }

    private void setCompanyStatusBg(String status){
        int[] companyConfigs = mPresenter.getStatusConfig(status);
        mTvAuthorInfoCompanyStatus.setText(companyConfigs[0]);
        mTvAuthorInfoCompanyStatus.setTextColor(getResources().getColor(companyConfigs[1]));
        int img = companyConfigs[2];
        mIvAuthorInfoCompanyStatus.setVisibility(img == 0 ? View.GONE : View.VISIBLE);
        if (img > 0) {
            mIvAuthorInfoCompanyStatus.setBackgroundResource(img);
        }
    }

    private void setPersonalStatusBg(String status){
        int[] companyConfigs = mPresenter.getStatusConfig(status);
        mTvAuthorInfoPersonalStatus.setText(companyConfigs[0]);
        mTvAuthorInfoPersonalStatus.setTextColor(getResources().getColor(companyConfigs[1]));
        int img = companyConfigs[2];
        mIvAuthorInfoPersonalStatus.setVisibility(img == 0 ? View.GONE : View.VISIBLE);
        if (img > 0) {
            mIvAuthorInfoPersonalStatus.setBackgroundResource(img);
        }
    }

    private void changeView(){
        setCompanyStatusBg(mStrCompanyStatus);
        setPersonalStatusBg(mStrPersonalStatus);
        hideAuthor();
        showStatusView();
    }

    /**
     * 根据状态修改左边字体颜色
     * */
    private void hideAuthor(){
        if (mPresenter.isEnter(mStrPersonalStatus) && mPresenter.isEnter(mStrCompanyStatus)) {
            mTvAuthorInfoCompany.setTextColor(getResources().getColor(R.color.color_gray_dark2));
            mTvAuthorInfoPersonal.setTextColor(getResources().getColor(R.color.color_gray_dark2));
        }else if (!mPresenter.isEnter(mStrPersonalStatus)){
            mTvAuthorInfoCompany.setTextColor(getResources().getColor(R.color.color_gray_dark));
            mTvAuthorInfoPersonal.setTextColor(getResources().getColor(R.color.color_gray_dark2));
        } else if (!mPresenter.isEnter(mStrCompanyStatus)) {
            mTvAuthorInfoCompany.setTextColor(getResources().getColor(R.color.color_gray_dark2));
            mTvAuthorInfoPersonal.setTextColor(getResources().getColor(R.color.color_gray_dark));
        }
    }

    /**
     * 根据状态隐藏某一项认证，用户只能认证一项
     * 认证成功之后，隐藏未认证的另一项
     * */
    private void showStatusView(){
        if (AUTHOR_TYPE_ACCEPTED.equals(mStrPersonalStatus)) {
            mBtnAuthorInfoCompany.setVisibility(View.GONE);
            mBtnAuthorInfoPersonal.setVisibility(View.VISIBLE);
        } else if (AUTHOR_TYPE_ACCEPTED.equals(mStrCompanyStatus)) {
            mBtnAuthorInfoCompany.setVisibility(View.VISIBLE);
            mBtnAuthorInfoPersonal.setVisibility(View.GONE);
        }else {
            mBtnAuthorInfoCompany.setVisibility(View.VISIBLE);
            mBtnAuthorInfoPersonal.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void resultAccountInfo(AccountInfoBean bean) {
        parseStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mPresenter.getAccountInfo();
        }
    }
}
