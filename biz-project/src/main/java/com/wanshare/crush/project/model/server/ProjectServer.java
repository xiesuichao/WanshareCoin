package com.wanshare.crush.project.model.server;

import com.wanshare.crush.project.model.bean.Project;
import com.wanshare.crush.project.model.bean.ProjectDetail;
import com.wanshare.crush.project.model.bean.ProjectMarket;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * @author xiesuichao
 * @date 2018/6/20
 */
public interface ProjectServer {

    @GET("project-center/projects")
    Observable<Project> getProjectList(@Query("page") int page,
                                       @Query("sortKey") String sortKey,
                                       @Query("limit") int limit);

    @GET("project-center/projects/{id}")
    Observable<ProjectDetail> getProjectDetail(@Path("id") String id);

    @GET("project-center/projects/{id}/markets")
    Observable<ProjectMarket> getProjectMarketList(@Path("id") String id,
                                                   @Query("page") int page);


}
