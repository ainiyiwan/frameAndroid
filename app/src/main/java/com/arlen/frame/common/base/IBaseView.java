package com.arlen.frame.common.base;

/**
 * Created by Arlen on 2016/12/21 16:30.
 */
public interface IBaseView {

     void showLoadingView();

     void showErrorView(boolean show);

     void showEmptyView(boolean show);

     void showDataView();


}
