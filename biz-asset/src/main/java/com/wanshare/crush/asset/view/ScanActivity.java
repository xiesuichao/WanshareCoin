package com.wanshare.crush.asset.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.uuzuche.lib_zxing.camera.CameraManager;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.contract.ScanContract;
import com.wanshare.wscomponent.PermissionCallback;
import com.wanshare.wscomponent.Permissions;
import com.wanshare.wscomponent.utils.ImageUtil;

import java.util.List;

/**
 * 二维码扫描页面
 *
 * @author wangdunwei
 */
@Route(path = AssetArouterConstant.SCAN)
public class ScanActivity extends BaseActivity<ScanContract.Presenter> implements ScanContract.View {
    public static final int REQUEST_SCAN = 1000;
    private static final int REQUEST_IMAGE = 1001;

    CaptureFragment mCaptureFragment = new CaptureFragment();

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_scan;
    }

    @Override
    protected void initView() {
        CameraManager.init(this);
        mMyToolbar.setRightButtonText(R.string.asset_gallery);
        mMyToolbar.setTitle(R.string.asset_take_a_scan);

        mMyToolbar.setOnBackButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScanResult("", false);
            }
        });

        mMyToolbar.setOnRightButtonTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraManager.get().stopPreview();
                mCaptureFragment.setAnalyzeCallback(null);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        ZXingLibrary.initDisplayOpinion(getContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Permissions.from(this, Manifest.permission.CAMERA)
                   .start(new PermissionCallback() {
                       @Override
                       public void yes(List<String> data) {

                           onGrantedCamera();
                       }

                       @Override
                       public void no(List<String> data) {
                           onDeniedCamera();
                       }
                   });
    }

    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            onScanResult(result, true);
        }

        @Override
        public void onAnalyzeFailed() {
            onScanResult("", false);
        }
    };


    // 成功回调的方法，用注解即可，里面的数字是请求时的 requestCode。
    private void onGrantedCamera() {
        // 申请权限成功，可以去做点什么了。
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(mCaptureFragment, R.layout.asset_layout_scan);
        mCaptureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.flScan, mCaptureFragment).commit();
    }

    // 失败回调的方法，用注解即可，里面的数字是请求时的 requestCode。
    private void onDeniedCamera() {
        // 申请权限失败，可以提醒一下用户。
        Toast.makeText(this, "获取拍照权限失败", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE) {
            if(data != null) {
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, data.getData()),
                            new CodeUtils.AnalyzeCallback() {
                                @Override
                                public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                                    onScanResult(result, true);
                                }

                                @Override
                                public void onAnalyzeFailed() {
                                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                                    onScanResult("", false);
                                }
                            });
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 返回扫描结果
     *
     * @param result  扫描结果
     * @param succeed 是否成功
     */
    private void onScanResult(String result, boolean succeed) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, succeed ? CodeUtils.RESULT_SUCCESS : CodeUtils.RESULT_FAILED);
        bundle.putString(CodeUtils.RESULT_STRING, result == null ? "" : result);
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }
}
