package com.arlen.frame.common.operation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.arlen.frame.common.model.BaseResult;
import com.arlen.frame.common.net.HttpProvider;
import com.arlen.frame.common.net.ReqCallBack;
import com.arlen.frame.common.net.ReqDataCallBack;
import com.arlen.frame.common.net.ReqSubscriber;
import com.arlen.frame.common.utils.DensityUtils;
import com.arlen.frame.common.utils.FileSizeUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Arlen on 2017/1/20 17:05.
 */
public class UploadImgOperation<T>{

    private Activity mContext;
    private HandleResult<T> mHandleResult;

    public UploadImgOperation(Activity context,HandleResult<T> mHandleResult){
        mContext = context;
        this.mHandleResult = mHandleResult;
    }

    public void uploadImage(final Uri uri) {
        if (uri == null)
            return;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                File file = new File(FileSizeUtil.getImageAbsolutePath(mContext,uri));
                BitmapFactory.Options options = FileSizeUtil.getBitmapOptions(file.getPath());
                int screenMax = Math.max(DensityUtils.getWindowWidth(mContext),
                        DensityUtils.getWindowHeight(mContext));
                int imgMax = Math.max(options.outWidth, options.outHeight);
                int inSimpleSize = 1;
                if (screenMax <= imgMax) {
                    inSimpleSize = Math.max(screenMax, imgMax) / Math.min(screenMax, imgMax);
                }
                return  FileSizeUtil.compressBitmap(mContext,
                        file.getAbsolutePath(),
                        Bitmap.CompressFormat.JPEG,
                        options.outWidth / inSimpleSize,
                        options.outHeight / inSimpleSize,
                        false);
            }

            @Override
            protected void onPostExecute(final String fileName) {
                final File imageBytes = new File(fileName);
                Map<String, RequestBody> map = new HashMap<>();
                if (imageBytes != null) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), imageBytes);
                    map.put("uploadedFile\"; filename=\"" + imageBytes.getName() + "", requestBody);
                }
                setObservable(HttpProvider.getInstance().create(ICommonService.class).uploadImage(map),
                        new ReqDataCallBack<BaseResult<String>>(){

                    @Override
                    public void onNext(BaseResult<String> result) {
                        super.onNext(result);
                        if (result.isSuccess()) {
                            mHandleResult.onSuccess((T)result.data);
                        }
                    }
                });
            }
        }.execute();
    }
    public Subscription setObservable(Observable observable, ReqCallBack mCallback) {
        return  observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ReqSubscriber(mCallback));
    }
}
