package com.soul.bonny.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PreviewActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button cancle_photo,ok_photo;
    public final static String IMAGEURI_CODE="imageUri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initView();
        showImg();
    }

    void  initView(){
        imageView=findViewById(R.id.iv_phtot);
        cancle_photo=findViewById(R.id.cancle_photo);
        ok_photo=findViewById(R.id.ok_photo);
        cancle_photo.setOnClickListener(onClickListener);
        ok_photo.setOnClickListener(onClickListener);
    }

    private void showImg(){
        Intent intent = getIntent();
        Uri imageUri = Uri.parse(intent.getStringExtra(IMAGEURI_CODE));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath(), options);
        imageView.setImageBitmap(rotateToDegrees(bitmap,90));
    }


    /**
     * 图片旋转
     * @param tmpBitmap
     * @param degrees
     * @return
     */
    public static Bitmap rotateToDegrees(Bitmap tmpBitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degrees);
        return  Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight(), matrix,
                        true);
    }


    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.cancle_photo:
                    finish();
                    break;
                case R.id.ok_photo:

                    break;
            }
        }
    };
}
