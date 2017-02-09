package Application.StatusBarColor;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.gab.myapplication.R;
import Application.widger.DensityUtil;
import Application.widger.MessageUtils;

import java.util.Timer;

public class PlanThreeActivity extends BaseActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener, View.OnTouchListener {
    private VideoView mVv;
    private View mRlVv;
    private GestureDetector mGestureDetector;
    private AudioManager mAudioManager;
    //    private int mStreamVolume;
    private static final String VIDEO_TAG = "video_sample";
    private int mPlayingPos;
    private int mNetworkState = 0;//当前网络状态 0-不可用 1-wifi 2-mobile
    private int mLastLoadLength = -1;//断网/onStop前缓存的位置信息(ms)
    private Uri mVideoUri;
    private static final String SCHEME_HTTP = "http";
    private Timer mCheckPlayingProgressTimer;
    private int deltaTime = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_three);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        debugLog("onCreate mPlayingPos = " + mPlayingPos);
//        initData();
        initView();
    }

    private void initData() {
        try {
            int screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            int bright = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);

            if (screenMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
            }
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
            setScreenBrightness(0);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setVoiceVolume(float value) {
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int flag = value > 0 ? -1 : 1;
        currentVolume += flag * 0.1 * maxVolume;
        // 对currentVolume进行限制
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
    }

    /**
     * 设置当前屏幕亮度值 0--255，并使之生效
     */
    private void setScreenBrightness(float value) {
        Window mWindow = getWindow();
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        int flag = value > 0 ? -1 : 1;

        mParams.screenBrightness += flag * 20 / 255.0F;
        if (mParams.screenBrightness >= 1) {
            mParams.screenBrightness = 1;
        } else if (mParams.screenBrightness <= 0.1) {
            mParams.screenBrightness = 0.1f;
        }
        mWindow.setAttributes(mParams);

        // 保存设置的屏幕亮度值
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) value);
    }

    private void initView() {
        // mRlVv容器用于全屏旋转,使视频宽高比保持不变
        mRlVv = findViewById(R.id.rl_vv);
        mVv = (VideoView) findViewById(R.id.vv);
        // 播放本地文件
        mVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.shuai_dan_ge);
        mVv.setVideoURI(mVideoUri);

        //添加播放控制条并设置视频源
        mVv.setMediaController(new MediaController(this));
        mLastLoadLength = -1;
        mPlayingPos = -1;

        mVv.setOnPreparedListener(this);
        mVv.setOnErrorListener(this);
        mVv.setOnCompletionListener(this);

        //播放按钮
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNetworkState == 0) {
                    if (SCHEME_HTTP.equalsIgnoreCase(mVideoUri.getScheme())) {
                        if (mPlayingPos >= mLastLoadLength - deltaTime) {
                            MessageUtils.showAlertDialog(PlanThreeActivity.this, "提示", getResources().getString(R.string.network_error), null);
                            return;
                        }
                    }
                }

                mVv.start();
                //mVv.requestFocus();
            }
        });
        //横竖屏切换
        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //mStreamVolume = mAudioManager.getStreamVolume(mAudioManager.STREAM_MUSIC);
        mGestureDetector = new GestureDetector(this, mGestureListener);
        mRlVv.setOnTouchListener(this);

    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float v = e2.getY() - e1.getY();
            Log.d(VIDEO_TAG, "v = " + v);
            if (Math.abs(v) > 10) {
                setScreenBrightness(v);
                setVoiceVolume(v);
            }
//            final double FLING_MIN_VELOCITY = 0.5;
//            final double FLING_MIN_DISTANCE = 0.5;
//
//            if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
//                setScreenBrightness(20);
//            }
//            if (e1.getY() - e2.getY() < FLING_MIN_DISTANCE && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
//                setScreenBrightness(-20);
//            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            return false;
        }
    };

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //        MessageUtils.showToast(MainActivity.this, "播放时发生错误 " + what + " mPlayingPos = " + mPlayingPos);
        debugLog("onError what = " + what + " mPlayingPos = " + mPlayingPos);
        if (SCHEME_HTTP.equalsIgnoreCase(mVideoUri.getScheme()) && mNetworkState == 0) {
            MessageUtils.showAlertDialog(this, "提示", getResources().getString(R.string.network_error), null);
        } else {
            restartPlayVideo();
        }
        return true;
    }

    private void restartPlayVideo() {
        //todo 添加 progressBar 体验好点
        if (mCheckPlayingProgressTimer != null) {
            mCheckPlayingProgressTimer.cancel();
            mCheckPlayingProgressTimer = null;
        }
        mVv.setVideoURI(mVideoUri);
        mVv.start();
        mVv.seekTo(mPlayingPos);

        mLastLoadLength = -1;
        mPlayingPos = 0;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //        mVv.start();
        debugLog("onPrepared currPos = " + mVv.getCurrentPosition());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        MessageUtils.showToast(PlanThreeActivity.this, "播放完毕");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mVv == null) {
            return;
        }
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mRlVv.getLayoutParams().height = (int) width;
            mRlVv.getLayoutParams().width = (int) height;
        } else {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 200.f);
            mRlVv.getLayoutParams().height = (int) height;
            mRlVv.getLayoutParams().width = (int) width;
        }
    }

    @Override
    protected void onPause() {
        mPlayingPos = mVv.getCurrentPosition();
        mVv.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        debugLog("onStop mPlayingPos0 = " + mPlayingPos + " -- " + mLastLoadLength);
        if (mVv.isPlaying() || mVv.canPause()) {
            mVv.stopPlayback();
        }
        debugLog("onStop mPlayingPos1 = " + mPlayingPos + " -- " + mLastLoadLength);
        mLastLoadLength = 0;
        super.onStop();
        debugLog("onStop mPlayingPos = " + mPlayingPos + " -- " + mLastLoadLength);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        debugLog("onDestroy");
        if (mCheckPlayingProgressTimer != null) {
            mCheckPlayingProgressTimer.cancel();
            mCheckPlayingProgressTimer = null;
        }

        Window mWindow = getWindow();
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        mParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        mWindow.setAttributes(mParams);
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            finish();
        } else {
            finish();
        }
    }

    public void debugLog(String msg) {
        Log.d(this.getClass().getName(), msg);
    }
}

