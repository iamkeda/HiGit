package com.kd.higit.api;

/**
 * Created by KD on 2016/6/18.
 */
public class UsersClient extends RetrofitNetwork {
    private UsersService usersService;

    private UsersClient() {
        usersService = ApiUrlRetrofit.getRetrofit().create(UsersService.class);
    }

    public static UsersClient getNewInstance() {
        return new UsersClient();
    }

    public void me() {
        execute(usersService.me());
    }

    public void userReposList(String username, String sort, int page){
        execute(usersService.userReposList(username, sort, page));
    }

    @Override
    public UsersClient setNetworkListener(NetworkListener networkListener) {
        return setNetworkListener(networkListener, this);
    }
}
