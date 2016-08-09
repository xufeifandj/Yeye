package com.angelatech.yeyelive.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angelatech.yeyelive.R;
import com.angelatech.yeyelive.TransactionValues;
import com.angelatech.yeyelive.activity.base.BaseActivity;
import com.angelatech.yeyelive.application.App;
import com.angelatech.yeyelive.model.RoomModel;
import com.angelatech.yeyelive.view.FrescoBitmapUtils;
import com.angelatech.yeyelive.view.GaussAmbiguity;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 直播结束页面
 */
public class LiveFinishActivity extends BaseActivity {
    public RoomModel roomModel;
    private ImageView face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_finish);
        Button btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        SimpleDraweeView img_head = (SimpleDraweeView) findViewById(R.id.img_head);
        TextView txt_barname = (TextView) findViewById(R.id.txt_barname);
        TextView txt_likenum = (TextView) findViewById(R.id.txt_likenum);
        face = (ImageView) findViewById(R.id.face);
        LinearLayout ly_live = (LinearLayout) findViewById(R.id.ly_live);
        TextView txt_live_num = (TextView) findViewById(R.id.txt_live_num);
        TextView txt_coin = (TextView) findViewById(R.id.txt_coin);
        TextView txt_live_time = (TextView) findViewById(R.id.txt_live_time);
        TextView ticke_num = (TextView) findViewById(R.id.ticke_num);
        ticke_num.setText(String.valueOf(App.ticke));
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            roomModel = (RoomModel) getIntent().getSerializableExtra(TransactionValues.UI_2_UI_KEY_OBJECT);
            img_head.setImageURI(Uri.parse(roomModel.getUserInfoDBModel().headurl));
            txt_barname.setText(roomModel.getUserInfoDBModel().nickname);
            txt_likenum.setText(String.valueOf(roomModel.getLikenum()));
            if (roomModel.getRoomType().equals(App.LIVE_HOST)) {
                ly_live.setVisibility(View.VISIBLE);
                txt_coin.setText(String.valueOf(roomModel.getLivecoin()));
                txt_live_num.setText(String.valueOf(roomModel.getLivenum()));
                txt_live_time.setText(String.format(getString(R.string.txt_live_time), roomModel.getLivetime()));
            } else {
                ly_live.setVisibility(View.INVISIBLE);
                FrescoBitmapUtils.getImageBitmap(LiveFinishActivity.this, roomModel.getUserInfoDBModel().headurl, new FrescoBitmapUtils.BitCallBack() {
                    @Override
                    public void onNewResultImpl(Bitmap bitmap) {
                        final Drawable drawable = GaussAmbiguity.BlurImages(bitmap, LiveFinishActivity.this);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                face.setImageDrawable(drawable);
                                face.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                LiveFinishActivity.this.finish();
                break;
        }
    }
}