package com.arlen.frame.view.account.service;


import com.arlen.frame.view.account.model.Account;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Arlen on 2016/12/21 16:33.
 */
public interface UserService {

    /**
     * 获取用户账号汇总信息
     */
    @GET("v2/account/summary")
    Observable<Account> loadAccountSummary();

}
