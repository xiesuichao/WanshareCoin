package com.wanshare.crush.account.presenter;

import com.wanshare.common.base.BaseApplication;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.UploadBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import top.zibin.luban.Luban;
import com.wanshare.wscomponent.http.ApiClient;

public class UploadTask {

    public UploadTask() {
    }

    public Single<List<UploadBean>> subscribe(List<String> images) {
        return Observable.fromIterable(images)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String url) throws Exception {
                        return compress(url);
                    }
                })
                .map(new Function<File, UploadBean>() {
                    @Override
                    public UploadBean apply(File file) throws Exception {
                        return upload(file);
                    }
                })
                .collect(new Callable<List<UploadBean>>() {
                    @Override
                    public List<UploadBean> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<List<UploadBean>, UploadBean>() {
                    @Override
                    public void accept(List<UploadBean> list, UploadBean bean) throws Exception {
                        list.add(bean);
                    }
                });



    }

    private UploadBean upload(File file) throws IOException {
        MultipartBody.Part body = null;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        } else {
            body = MultipartBody.Part.createFormData("file", "");
        }
        Call<UploadBean> call = ApiClient.getInstance().
                create(AccountServer.class).uploadFileSn(body);
        return call.execute().body();
    }

    private File compress(String url) {
        File file = null;
        try {
            file = Luban.with(BaseApplication.getInstance().getApplicationContext()).get(url);
        } catch (IOException e) {
            e.printStackTrace();
            file = new File(url);
        }
        return file;
    }


}
