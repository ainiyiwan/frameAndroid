package com.arlen.frame.view.account.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arlen.frame.R;
import com.arlen.frame.common.utils.ToastUtils;
import com.google.zxing.ICapture;
import com.google.zxing.Result;
import com.google.zxing.camera.CameraManager;
import com.google.zxing.decode.DecodeThread;
import com.google.zxing.utils.BeepManager;
import com.google.zxing.utils.CaptureActivityHandler;
import com.google.zxing.utils.InactivityTimer;

import java.lang.reflect.Field;

/**
 * 扫描二维码界面
 *
 * @author huangcancheng
 * @data: 2015年5月14日 上午10:23:52
 * @version: V
 */
public class ScanActivity extends FragmentActivity implements
        SurfaceHolder.Callback, ICapture {
    private static final String TAG = ScanActivity.class.getSimpleName();
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;
    private Button btnFlash;
    private Rect mCropRect = null;
    private boolean isHasSurface = false;
    private String resultUrl;
    private boolean isFlashing;

    public static void start(Context context) {
        Intent intent = new Intent(context, ScanActivity.class);
        context.startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        findViewById();
        initViews();

    }

    private void findViewById() {
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_layout);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        btnFlash = (Button) findViewById(R.id.btn_flash);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            btnFlash.setBackgroundResource(R.drawable.qr_btn_flash);
        } else {
            btnFlash.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        beepManager.init(getResources().openRawResourceFd(R.raw.beep));
        scanLine.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scan_line));
        btnFlash.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isFlashing) {
                    btnFlash.setBackgroundResource(R.drawable.qr_btn_flash);
                    cameraManager.offLight();
                } else {
                    btnFlash.setBackgroundResource(R.drawable.qr_btn_flash_off);
                    cameraManager.openLight();
                }
                isFlashing = !isFlashing;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager(getApplication());
        handler = null;
        if (isHasSurface) {
            initCamera(scanPreview.getHolder());
        } else {
            scanPreview.getHolder().addCallback(this);
        }
        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG,
                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSound();

        final String scanResult = (rawResult != null) ? (rawResult.getText()) : (null);
        if (TextUtils.isEmpty(scanResult)) {
            ToastUtils.toastShort("无法打开此链接！");
        } else {
            if (isTrustUrl(scanResult)) {
//                if (scanResult.startsWith("yaochufa://")) {
//                    ProtocolUtils.doProtocol(this, scanResult, null, true);
//                } else {
//                    final String webUrl = isWebSiteUrl(scanResult);
//                    if (webUrl != null) {
//                        if (webUrl.contains("yaochufa")) {
//                            Intent intent = new Intent(this, BannerWebViewActivity.class);
//                            intent.putExtra("url", webUrl);
//                            startActivity(intent);
//                        } else {
//                            startBrowser(webUrl);
//                        }
//                    } else {
//                        if (startIntent(scanResult) == false) {
//                            return;
//                        }
//                    }
//                }
            } else {
                final String webUrl = isWebSiteUrl(scanResult);
                if (webUrl != null) {
                    resultUrl = webUrl;
//                    SimpleYesNoDialogActivity.show(
//                            this,
//                            "二维码内容：",
//                            resultUrl,
//                            "该内容可能存在风险，是否继续打开？",
//                            "用浏览器打开",
//                            "取消"
//                    );
                    return; //OK
                } else {
                    if (startIntent(scanResult) == false) {
                        return;
                    }
                }
            }
        }
        doFinish();
    }


    private static final boolean isTrustUrl(String url) {
        return url.contains("yaochufa") || url.contains("weixin");
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager,
                        DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (Exception e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            ToastUtils.toastShort("打开相机失败，请检查是否允许程序使用相机权限");
            finish();
        }
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(ICapture.RESTART_PREVIEW, delayMS);
        }
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Rect getCropRect() {
        return mCropRect;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public CameraManager getCameraManager() {
        return cameraManager;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
//        if (reqCode == SimpleYesNoDialogActivity.REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                startBrowser(resultUrl);
//            }
//            doFinish();
//        } else if (reqCode == SimpleTextDialogActivity.REQUEST_CODE) {
//            doFinish();
//        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    //////////////////////////////////////////////////////////////////////////////////////////////

    private final void doFinish() {

    }

    private static String isWebSiteUrl(String content) {
        if (content == null || content.length() <= 0) {
            return null;
        }
        if (Patterns.WEB_URL.matcher(content).matches()) {
            return content;//URLUtil.guessUrl(content);
        }
        return null;
    }

    private final void startBrowser(String url) {
        try {
            Intent intent = new Intent();
            //intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            //intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
//            showToast("无法打开此链接！");
        }
    }


    private final boolean startIntent(String content) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
            startActivity(intent);
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            //showToast("无法打开此链接！");
//            SimpleTextDialogActivity.show(this, "二维码内容：", content);
        }

        return false;
    }


}