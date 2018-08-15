package com.soul.bonny.demo;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CameraActivity extends CameraBaseActivity implements SurfaceHolder.Callback{

    SurfaceView mPreview;
    private Button btn_photo,btn_arrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initView();
        initCallback();
        super.onCreate(savedInstanceState);
    }

    void initCallback(){
        mPreview.setOnClickListener(onClickListener);
        preInitData(mPreview,this);
        btn_photo.setOnClickListener(onClickListener);

    }

    void initView(){
        context=this;
        mPreview=findViewById(R.id.surfaceview);
        btn_photo=findViewById(R.id.btn_photo);
        btn_arrow=findViewById(R.id.btn_arrow);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCamera == null){
            mCamera = initCamera();
            startPreview(mCamera,mHolder);
        }
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.surfaceview:
                    autoFocus();
                    break;
                case R.id.btn_photo:
                    setParameters(mPictureCallback);
                    break;

                    /**向下箭头监听**/
                case R.id.btn_arrow:

                    break;
            }
        }
    };

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                FileOutputStream fos = new FileOutputStream(mFile);
                fos.write(data);
                fos.close();
                Uri imageUri = Uri.fromFile(mFile);
                Intent intent = new Intent(context,PreviewActivity.class);
                intent.putExtra(PreviewActivity.IMAGEURI_CODE,imageUri.toString());
                startActivity(intent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startPreview(mCamera,surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //先关闭,再开启
        mCamera.stopPreview();
        startPreview(mCamera,surfaceHolder);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
