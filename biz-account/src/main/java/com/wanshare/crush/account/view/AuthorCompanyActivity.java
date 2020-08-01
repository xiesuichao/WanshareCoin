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
import com.wanshare.crush.account.contract.AuthorCompanyContract;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.model.bean.CompanyBean;
import com.wanshare.crush.account.model.bean.CompanyReq;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.AuthorCompanyPresenter;
import com.wanshare.wscomponent.image.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_ACCEPTED;
import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_APPLIED;
import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_NONE;
import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_REJECTED;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 企业认证界面
 * */
@Route(path = AccountArouterConstant.ACCOUNT_AUTHOR_COMPANY)
public class AuthorCompanyActivity extends BasePhotoActivity<AuthorCompanyContract.Presenter> implements AuthorCompanyContract.View {

    @BindView(R2.id.et_author_company_name)
    EditText mEtAuthorCompanyName;
    @BindView(R2.id.et_author_company_address_country)
    EditText mEtAuthorCompanyAddressCountry;
    @BindView(R2.id.et_author_company_address_area)
    EditText mEtAuthorCompanyAddressArea;
    @BindView(R2.id.et_author_company_address_detail)
    EditText mEtAuthorCompanyAddressDetail;
    @BindView(R2.id.iv_author_company_logo)
    ImageView mIvAuthorCompanyLogo;
    @BindView(R2.id.btn_author_company_logo_delete)
    ImageView mBtnAuthorCompanyLogoDelete;
    @BindView(R2.id.btn_author_company_logo)
    RelativeLayout mBtnAuthorCompanyLogo;
    @BindView(R2.id.et_author_company_uniform)
    EditText mEtAuthorCompanyUniform;
    @BindView(R2.id.et_author_company_registration_number)
    EditText mEtAuthorCompanyRegistrationNumber;
    @BindView(R2.id.et_author_company_organization_number)
    EditText mEtAuthorCompanyOrganizationNumber;
    @BindView(R2.id.iv_author_company_business)
    ImageView mIvAuthorCompanyBusiness;
    @BindView(R2.id.btn_author_company_business_delete)
    ImageView mBtnAuthorCompanyBusinessDelete;
    @BindView(R2.id.btn_author_company_business)
    RelativeLayout mBtnAuthorCompanyBusiness;
    @BindView(R2.id.et_author_company_contact_name)
    EditText mEtAuthorCompanyContactName;
    @BindView(R2.id.et_author_company_contact_phone)
    EditText mEtAuthorCompanyContactPhone;
    @BindView(R2.id.btn_author_company_agree)
    ImageView mBtnAuthorCompanyAgree;
    @BindView(R2.id.btn_author_company_protocol)
    TextView mBtnAuthorCompanyProtocol;
    @BindView(R2.id.btn_author_company_confirm)
    Button mBtnAuthorCompanyConfirm;
    @BindView(R2.id.scroll_author_company)
    ScrollView mScrollAuthorCompany;
    @BindView(R2.id.iv_author_company_status)
    ImageView mIvAuthorCompanyStatus;
    @BindView(R2.id.tv_author_company_status)
    TextView mTvAuthorCompanyStatus;
    @BindView(R2.id.tv_author_company_status_reason)
    TextView mTvAuthorCompanyStatusReason;
    @BindView(R2.id.tv_author_company_status_name)
    TextView mTvAuthorCompanyStatusName;
    @BindView(R2.id.tv_author_company_status_uniform_numbers)
    TextView mTvAuthorCompanyStatusUniformNumbers;
    @BindView(R2.id.tv_author_company_status_contact)
    TextView mTvAuthorCompanyStatusContact;
    @BindView(R2.id.tv_author_company_status_contact_phone)
    TextView mTvAuthorCompanyStatusContactPhone;
    @BindView(R2.id.ll_author_company_status_info)
    LinearLayout mLlAuthorCompanyStatusInfo;
    @BindView(R2.id.btn_author_company_reset)
    Button mBtnAuthorCompanyReset;
    @BindView(R2.id.ll_author_company_status)
    LinearLayout mLlAuthorCompanyStatus;

    private boolean mIsAgree;

    private String mStrStatus = "none";
    private CompanyReq mCompanyReq;

    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mStrStatus = bundle.getString("status", "none");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected AuthorCompanyContract.Presenter getPresenter() {
        return new AuthorCompanyPresenter(this);
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_author_company);

        showView();
    }

    /**
     * 是否认证
     */
    private boolean isAuthor() {
        return !(TextUtils.isEmpty(mStrStatus) || mStrStatus.equals("none"));
    }

    private void showView(){
        if (isAuthor()) {
            mScrollAuthorCompany.setVisibility(View.GONE);
            mLlAuthorCompanyStatus.setVisibility(View.VISIBLE);
            showStatusView();

        }else{
            mLlAuthorCompanyStatus.setVisibility(View.GONE);
            mScrollAuthorCompany.setVisibility(View.VISIBLE);
        }
    }

    private void showStatusView(){
        mIvAuthorCompanyStatus.setBackgroundResource(getIvStatus(mStrStatus));
        mTvAuthorCompanyStatus.setText(getTvStatus(mStrStatus));
        mTvAuthorCompanyStatusReason.setVisibility(isRefuse() ? View.VISIBLE : View.GONE);
        mBtnAuthorCompanyReset.setVisibility(isRefuse() ? View.VISIBLE : View.GONE);
        mLlAuthorCompanyStatusInfo.setVisibility(isRefuse() ? View.GONE : View.VISIBLE);
        if (isRefuse()) {
            AccountInfoBean info = AccountCacheManager.getInstance().getAccountInfoBean();
            if (info == null) {
                return;
            }
            AccountInfoBean.CertificationAuditBean bean = info.getCertificationAudit();
            mTvAuthorCompanyStatusReason.setText(getString(R.string.account_author_fail_reason,
                    bean == null ? "" : bean.getRejectedReason()));
        }else{
            mPresenter.queryCompany();
        }
    }

    private boolean isRefuse(){
        return AUTHOR_TYPE_REJECTED.equals(mStrStatus);
    }

    private int getTvStatus(String status) {
        int strStatus;
        if (AUTHOR_TYPE_REJECTED.equals(status)) {
            strStatus = R.string.account_author_fail;
        } else if (AUTHOR_TYPE_APPLIED.equals(status)) {
            strStatus = R.string.account_author_loading;
        }else if (AUTHOR_TYPE_ACCEPTED.equals(status)) {
            strStatus = R.string.account_author_success_pass;
        }else{
            strStatus = R.string.account_author_not;
        }
        return strStatus;
    }

    private int getIvStatus(String status) {
        int strStatus;
        if (AUTHOR_TYPE_REJECTED.equals(status)) {
            strStatus = R.drawable.ic_shshibai;
        } else if (AUTHOR_TYPE_APPLIED.equals(status)) {
            strStatus = R.drawable.ic_shenghe;
        }else if (AUTHOR_TYPE_ACCEPTED.equals(status)) {
            strStatus = R.drawable.ic_succ;
        }else{
            strStatus = R.drawable.ic_shenghe;
        }
        return strStatus;
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_author_company;
    }


    @OnClick({R2.id.btn_author_company_logo_delete, R2.id.btn_author_company_logo,
            R2.id.btn_author_company_business_delete, R2.id.btn_author_company_business,
            R2.id.btn_author_company_agree, R2.id.btn_author_company_protocol,
            R2.id.btn_author_company_confirm, R2.id.btn_author_company_reset})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_author_company_logo_delete) {
            mIvAuthorCompanyLogo.setImageBitmap(null);

        } else if (i == R.id.btn_author_company_logo) {
            mType = 0;
            onStorage();

        } else if (i == R.id.btn_author_company_business_delete) {
            mIvAuthorCompanyBusiness.setImageBitmap(null);

        } else if (i == R.id.btn_author_company_business) {
            mType = 1;
            onStorage();

        } else if (i == R.id.btn_author_company_agree) {
            mIsAgree = !mIsAgree;
            mBtnAuthorCompanyAgree.setSelected(mIsAgree);

        } else if (i == R.id.btn_author_company_protocol) {
        } else if (i == R.id.btn_author_company_confirm) {
            confirm();
        } else if (i == R.id.btn_author_company_reset) {
            mStrStatus = AUTHOR_TYPE_NONE;
            showView();

        }
    }

    private void confirm(){

        String companyName = mEtAuthorCompanyName.getText().toString();
        String companyCountyAddress = mEtAuthorCompanyAddressCountry.getText().toString();
        String companyAreaAddress = mEtAuthorCompanyAddressArea.getText().toString();
        String companyAddress = mEtAuthorCompanyAddressDetail.getText().toString();
        String companyOrganizationNumber = mEtAuthorCompanyOrganizationNumber.getText().toString();
        String companyRegistrationNumber = mEtAuthorCompanyRegistrationNumber.getText().toString();
        String companyUniform = mEtAuthorCompanyUniform.getText().toString();
        String companyContactName = mEtAuthorCompanyContactName.getText().toString();
        String companyContactPhone = mEtAuthorCompanyContactPhone.getText().toString();

        if (TextUtils.isEmpty(companyName) || TextUtils.isEmpty(companyCountyAddress)
                || TextUtils.isEmpty(companyAreaAddress) || TextUtils.isEmpty(companyAddress)
                || TextUtils.isEmpty(mStrLogoPath) || TextUtils.isEmpty(companyUniform)
                || TextUtils.isEmpty(mStrBusinessPath) || TextUtils.isEmpty(companyContactName)
                || TextUtils.isEmpty(companyContactPhone)){

            showLongToast(getString(R.string.account_author_confirm_tips));

            return;


        }

        mCompanyReq = new CompanyReq(companyName, "", companyAreaAddress,
                companyAddress, "", companyUniform,
                companyRegistrationNumber, companyOrganizationNumber,
                "", companyContactName, companyContactPhone);

        List<String> list = new ArrayList<>();
        list.add(mStrLogoPath);
        list.add(mStrBusinessPath);

        showLoading("");

        mPresenter.uploadFile(list);
    }

    // 0:Logo 1:营业执照
    private int mType;
    private String mStrLogoPath;
    private String mStrBusinessPath;

    @Override
    public void resultAlbum(ArrayList<String> list) {
        switch (mType) {
            case 0:
                mStrLogoPath = list.get(0);
                loadImage(mIvAuthorCompanyLogo, mStrLogoPath);
                break;
            case 1:
                mStrBusinessPath = list.get(0);
                loadImage(mIvAuthorCompanyBusiness, mStrBusinessPath);
                break;
        }
    }

    private void loadImage(ImageView image, String path){
        ImageLoader.with(this).load(new File(path)).into(image);
    }

    @Override
    public void onCompanyResult() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void resultUploadFile(List<String> list) {
        if (list.size() < 2) {
            hideLoading();
            showLongToast(getString(R.string.account_author_upload_fail));
            return;
        }
        if (mCompanyReq != null) {
            mCompanyReq.setLogo(list.get(0));
            mCompanyReq.setBusinessLicense(list.get(1));
            mPresenter.company(mCompanyReq);
        }
    }

    @Override
    public void resultQueryCompany(CompanyBean bean) {
        if (bean == null) {
            return;
        }
        mTvAuthorCompanyStatusName.setText(bean.getCompanyName());
        mTvAuthorCompanyStatusUniformNumbers.setText(bean.getSocialCode());
        mTvAuthorCompanyStatusContact.setText(bean.getUserName());
        mTvAuthorCompanyStatusContactPhone.setText(bean.getPhoneNumber());

    }
}
