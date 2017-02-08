package com.arlen.frame.view.account.presenter;

import com.arlen.frame.common.base.BasePresenter;
import com.arlen.frame.common.net.ReqDataCallBack;
import com.arlen.frame.view.account.model.Account;
import com.arlen.frame.view.account.service.UserService;
import com.arlen.frame.view.account.view.IUserView;

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
                if(isAttachView()) {
                    getView().showContentView(account);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
