package Application.widger;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.gab.myapplication.R;


public class VideoViewActivity extends AppCompatActivity {
    VideoView mVideoView;
    ViewGroup.LayoutParams mVideoViewLayoutParams;
    RelativeLayout mVideoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        mVideoView = (VideoView) this.findViewById(R.id.video_view);
        //mVideoView.setMediaController(new MediaController(this));

        mVideoLayout = (RelativeLayout) super.findViewById(R.id.voide_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int rot = getWindowManager().getDefaultDisplay().getRotation();
        if (rot == Surface.ROTATION_90 || rot == Surface.ROTATION_270) {
            mVideoViewLayoutParams = mVideoLayout.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mVideoLayout.setLayoutParams(layoutParams);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else if (rot == Surface.ROTATION_0) {
//            RelativeLayout.LayoutParams lp = new  RelativeLayout.LayoutParams(320,240);
//            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mVideoLayout.setLayoutParams(mVideoViewLayoutParams);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_start:
                Uri uri = Uri.parse("/mnt/sdcard/qq.mp4");
                mVideoView.setVideoURI(uri);
                mVideoView.start();
                break;

            case R.id.play_pause:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                } else {
                    mVideoView.start();
                }
                break;

            case R.id.play_stop:
                if (mVideoView.isPlaying()) {
                    mVideoView.suspend();
                }
                break;

            case R.id.test:
                //startActivity(new Intent(this, TestActivity.class));
            {
                if (true) {//设置RelativeLayout的全屏模式


                    View decorView = getWindow().getDecorView();
                    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                    decorView.setSystemUiVisibility(uiOptions);

                    ActionBar actionBar = getActionBar();
                    if (actionBar != null)
                        actionBar.hide();
                } else {

                }
            }
            break;
        }
    }
}
