package com.wanshare.crush.asset.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.contract.RechargeContract;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.model.bean.RechargeAddressBean;
import com.wanshare.crush.asset.presenter.RechargePersenter;
import com.wanshare.wscomponent.datamanager.FileCache;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.DateUtil;
import com.wanshare.wscomponent.utils.DensityUtil;
import com.wanshare.wscomponent.utils.FileUtil;
import com.wanshare.wscomponent.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 充币
 * </br>
 * Date: 2018/8/24 16:12
 *
 * @author hemin
 */
@Route(path = AssetArouterConstant.ASSET_RECHARGE_COIN)
public class RechargeActivity extends BaseActivity<RechargeContract.Presenter> implements RechargeContract.View {

    protected CompositeDisposable mCompositeDisposable;

    @BindView(R2.id.tv_coin_name)
    TextView mTvCoinName;

    @BindView(R2.id.tv_address)
    TextView mTvAddress;
    @BindView(R2.id.iv_qrcode)
    ImageView mIvQrCode;
    @BindView(R2.id.tv_save_qrcode)
    TextView mTvSaveQrCode;
    @BindView(R2.id.tv_explain)
    TextView mTvExplain;
    private CoinInfo mCoinInfo;

    @Override
    protected RechargeContract.Presenter getPresenter() {
        return new RechargePersenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_recharge;
    }

    @Override
    protected void initIntent() {
        mCoinInfo = (CoinInfo) getIntent().getSerializableExtra(IntentConstant.EXTRA_COIN_INFO);
        if (mCoinInfo == null) {
            LogUtil.d("RechargeActivity mCoinInfo is null");
            finish();
        }
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.asset_recharge);
        mTvCoinName.setText(mCoinInfo.getShortName() + "(" + mCoinInfo.getFullName() + ")");
    }

    @Override
    protected void initData() {
        mPresenter.getRechargeAddress(mCoinInfo.getId());
        mPresenter.getCoinInfo(mCoinInfo.getId());
    }

    @OnClick({R2.id.tv_address, R2.id.tv_save_qrcode})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_address) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText(null, mTvAddress.getText()));
            ToastUtil.showShort(getContext(), R.string.asset_copied_to_clipboard);
        } else if (id == R.id.tv_save_qrcode) {
            Disposable disposable = Observable.just(saveQRCode())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {

                        @Override
                        public void accept(String s) throws Exception {
                            if (TextUtils.isEmpty(s)) {
                                return;
                            }
                            ToastUtil.showLong(getApplicationContext(), getResources().getString(R.string.asset_save_qr_code_success));
                        }
                    });

            addDispose(disposable);
        }
    }

    private String saveQRCode() {
        //获取图片
        BitmapDrawable drawable = (BitmapDrawable) mIvQrCode.getDrawable();
        if (drawable == null) {
            return null;
        }
        //得到bitmap
        Bitmap bitmap = drawable.getBitmap();
        if (bitmap == null) {
            return null;
        }
        //创建文件
        String path = FileCache.getExternalFileImgPath(getContext());
        if (path == null) {
            path = FileCache.getInternalCacheImgPath(getContext());
        }
        File file = FileUtil.changeFile(path, DateUtil.getCurrentDate(DateUtil.FORMAT_DATE_UNDER_LINE) + File.pathSeparator + ".jpg");
        //保存至sdcard
        FileUtil.saveBitmap(bitmap, file.getAbsolutePath());
        //更新系统媒体库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), file.getPath(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
        return file.getPath();
    }

    @Override
    public void showRechargeAddress(RechargeAddressBean result) {
        if (result == null || TextUtils.isEmpty(result.getCoinAddress())) {
            return;
        }
        String address = result.getCoinAddress();
        mTvAddress.setText(result.getCoinAddress());

        int w = DensityUtil.dip2px(getActivity(), 150);
        Disposable disposable = Observable.just(CodeUtils.createImage(address, w, w, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) {
                        mIvQrCode.setImageBitmap(bitmap);
                    }
                });

        addDispose(disposable);
    }

    @Override
    public void showCoinInfo(CoinInfo coinInfo) {
        String mCurrency = coinInfo.getShortName();
        String limit = coinInfo.getConfirmationNumber() + "";
        mTvExplain.setText(getString(R.string.asset_recharge_explain_detail, mCurrency, mCurrency,
                mCurrency, mCurrency, mCurrency, limit));
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        //将所有 Disposable 放入集中处理
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保证 Activity 结束时取消所有正在执行的订阅
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        mCompositeDisposable = null;
    }
}
