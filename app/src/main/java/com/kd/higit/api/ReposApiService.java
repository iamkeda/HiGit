package com.kd.higit.api;

import com.kd.higit.bean.FileOrDirContent;
import com.kd.higit.bean.Repository;
import com.kd.higit.bean.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by KD on 2016/6/24.
 */
public interface ReposApiService {
    @GET("/{path}")
    Call<FileOrDirContent> get(@Path("path") String path);

    @GET("/repos/{owner}/{name}")
    Call<Repository> get(@Path("owner") String owner, @Path("name") String repo);

    @GET("/repos/{owner}/{name}/contents")
    Call<List<FileOrDirContent>> contents(@Path("owner") String owner, @Path("name") String repo);

    @GET("/repos/{owner}/{name}/contents")
    Call<List<FileOrDirContent>> contentsByRef(@Path("owner") String owner, @Path("name") String repo, @Query("ref") String ref);

    @GET("/repos/{owner}/{name}/readme")
    Call<FileOrDirContent> readme(@Path("owner") String owner, @Path("name") String repo);

    @GET("/repos/{owner}/{name}/readme")
    Call<FileOrDirContent> readme(@Path("owner") String owner, @Path("name") String repo, @Query("ref") String ref);

    @GET("/repos/{owner}/{name}/contents{path}")
    Call<List<FileOrDirContent>> contents(@Path("owner") String owner, @Path("name") String repo, @Path("path") String path);

    @GET("/repos/{owner}/{name}/contents/{path}")
    Call<List<FileOrDirContent>> contentsByRef(@Path("owner") String owner, @Path("name") String repo, @Path("path") String path, @Query("ref") String ref);

    @GET("/repos/{owner}/{name}/contributors")
    Call<List<User>> contributors(@Path("owner") String owner, @Path("name") String repo);

    @GET("/repos/{owner}/{name}/contributors")
    Call<List<User>> contributors(@Path("owner") String owner, @Path("name") String repo, @Query("page") int page);

    @GET("/repos/{owner}/{name}/stargazers")
    Call<List<User>> stargazers(@Path("owner") String owner, @Path("name") String repo);

    @GET("/repos/{owner}/{name}/stargazers")
    Call<List<User>> stargazers(@Path("owner") String owner, @Path("name") String repo, @Query("page") int page);

    @GET("/repos/{owner}/{name}/collaborators")
    Call<List<User>> collaborators(@Path("owner") String owner, @Path("name") String repo);

    @GET("/repos/{owner}/{name}/collaborators")
    Call<List<User>> collaborators(@Path("owner") String owner, @Path("name") String repo, @Query("page") int page);

    //@GET("/repos/{owner}/{name}/compare/{base}...{head}")
    //CompareCommit compareCommits(@Path("owner") String owner, @Path("name") String repo, @Path("base") String base, @Path("head") String head);

    @DELETE("/repos/{owner}/{name}")
    Call<Object> delete(@Path("owner") String owner, @Path("name") String repo);

    //@GET("/repos/{owner}/{name}/events")
    //List<GithubEvent> events(@Path("owner") String owner, @Path("name") String repo);


}
