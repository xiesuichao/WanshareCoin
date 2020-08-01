package com.wanshare.crush.account.view;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.mvp.IPresenter;
import com.wanshare.crush.account.R;
import com.wanshare.wscomponent.album.intent.PhotoPickerIntent;
import com.wanshare.wscomponent.album.select.PhotoPickerActivity;
import com.wanshare.wscomponent.album.select.SelectModel;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 选择照片基类
 * */
public abstract class BasePhotoActivity<P extends IPresenter> extends BaseActivity<P> {


    public void onStorage() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE, Permission.Group.CAMERA)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        gotoPhoto();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        showLongToast(getString(R.string.account_permission_refuse));
                    }
                })
                .start();
    }

    private void gotoPhoto() {
        new PhotoPickerIntent()
                .setSelectModel(SelectModel.SINGLE)
                .setShowCamera(true).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (data != null) {
                ArrayList<String> imageLists = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                if (imageLists != null && imageLists.size() > 0) {
                    resultAlbum(imageLists);
                }
            }
        }
    }

    public abstract void resultAlbum(ArrayList<String> list);
}
