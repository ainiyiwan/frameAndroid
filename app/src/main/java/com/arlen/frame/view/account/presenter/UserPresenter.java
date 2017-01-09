package com.arlen.frame.view.account.presenter;

import com.arlen.frame.net.http.ReqDataCallBack;
import com.arlen.frame.net.service.user.UserService;
import com.arlen.frame.view.account.model.Account;
import com.arlen.frame.view.account.view.IUserView;
import com.arlen.frame.view.base.BasePresenter;

/**
 * Created by Arlen on 2016/12/21 16:53.
 */
public class UserPresenter extends BasePresenter<IUserView> implements IUserPresenter<IUserView> {

    @Override
    public void loadAccount() {
        setObservable(createService(UserService.class).loadAccountSummary(),new ReqDataCallBack<Account>(){
            @Override
            public void onNext(Account account) {
                super.onNext(account);
                getView().showContentView(account);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
