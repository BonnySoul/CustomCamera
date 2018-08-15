package com.soul.bonny.demo;

import android.Manifest;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.IOException;

import rx.functions.Action1;

/**
 * Created by so on 2018/8/15.
 */
public class CameraBaseActivity extends AppCompatActivity {

    protected File mFile;
    protected SurfaceHolder mHolder;
    protected Camera mCamera;
    protected String path;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions.getInstance(context).request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){
                            mCamera = initCamera();
                            startPreview(mCamera, mHolder);
                        }
                    }
                });
    }


    protected void preInitData(SurfaceView mPreview, SurfaceHolder.Callback mCallback) {
        String path = "/sdcard/temp.png";
        mFile = new File(path);
        mHolder = mPreview.getHolder();
        mHolder.addCallback(mCallback);
    }


    /**
     * 初始化相机对象
     *
     * @return
     */
    protected Camera initCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }


    /**
     * 开始预览相机内容
     */
    protected void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            //将Camera预览角度进行调整
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置相机参数
     */
    protected void setParameters(Camera.PictureCallback mPictureCallback) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(1200, 800);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.takePicture(null, null, mPictureCallback);
    }


    /**
     * 相机聚焦
     */
    protected void autoFocus() {
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {

            }
        });
    }

    /**
     * 释放相机资源
     */
    protected void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

}
