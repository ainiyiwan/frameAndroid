package com.arlen.frame.net.http;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.arlen.frame.view.AppContext;
import com.arlen.frame.BuildConfig;
import com.arlen.frame.common.utils.OsUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arlen on 2016/12/21 15:03.
 */
public class HttpProvider {

    private static Retrofit mRetrofit;
    private static HttpProvider mInstance;

    public static String API_SERVER_ADDRESS = "https://api.thy360.com:443";
    public static String DEBUG_SERVER_ADDRESS = "http://release.thy360.com:80";

    private static Context mAppContext;
    public static int mVersionCode; // eg:17
    public static String mVersionName; // eg:4.1.0
    public static String mImeI; // 设备号OsUtils

    public static final int CONNECT_TIMEOUT = 5;

    public  HttpProvider(){

        getPackageInfo();
        initOkHttp();
    }

    public static void init() {
        mInstance = new HttpProvider();
    }

    public static HttpProvider getInstance() {
        if (mInstance == null) {
            synchronized (HttpProvider.class) {
                if (mInstance == null) {
                    mInstance = new HttpProvider();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取版本信息
     */
    public static void getPackageInfo() {

        mAppContext = AppContext.getAppContext();

        // 获取版本信息
        final PackageManager packManager = mAppContext.getPackageManager();
        try {
            PackageInfo pi = packManager
                    .getPackageInfo(mAppContext.getPackageName(), 0);
            mVersionName = pi.versionName;
            mVersionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        mImeI = OsUtils.getUniqueId(mAppContext);
    }


    private static void wrapParamHeader(Request.Builder builder){
        builder.addHeader("versionCode", mVersionCode+"");
        builder.addHeader("mVersionCode", mVersionCode+"");
        builder.addHeader("mImeI", mImeI+"");
        builder.addHeader("system", "android");
        builder.addHeader("token","85ed4bbd-1e25-4f1b-8087-63a08575e0c8");
    }

    public static String getHttpPointByStatus() {
        return BuildConfig.DEBUG ? DEBUG_SERVER_ADDRESS : API_SERVER_ADDRESS;
    }

    /**
     * 初始化okhttp配置
     */
    private static void initOkHttp(){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        Interceptor headInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                wrapParamHeader(builder);
                return chain.proceed(builder.build());
            }
        };

        //OkHttp3打印
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        okHttpBuilder.addInterceptor(loggingInterceptor);

        if (!BuildConfig.DEBUG) {
            try {
                okHttpBuilder.sslSocketFactory(
                        SSLSocketFactory.
                                getSSLSocketFactory(mAppContext));
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
        }

        //设置缓存
        File httpCacheDirectory = new File(mAppContext.getCacheDir(), "zzkgcache");
        Cache httpCache = new Cache(httpCacheDirectory, 50 * 1024 * 1024);

        OkHttpClient client = okHttpBuilder.addInterceptor(headInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .cache(httpCache)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(getHttpPointByStatus())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 获取service对象
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }
}
