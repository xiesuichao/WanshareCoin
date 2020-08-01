package com.wanshare.crush.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.biz.project.ProjectArouterConstant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import wanshare.wscomponent.http.ApiClient;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiClient.init("http://result.eolinker.com/fAaSSgA91f369a3c9e27be33b8b90a40bdf57ac033c4c1e?uri=", new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        });


        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(getApplication());

        Button testBtn = findViewById(R.id.btn_test);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ProjectArouterConstant.PROJECT_PUBLICITY).navigation(MainActivity.this);

            }
        });



    }






}
