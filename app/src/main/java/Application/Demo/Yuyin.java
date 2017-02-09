package Application.Demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gab.myapplication.R;

import java.util.Locale;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Yuyin extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private Button speechBtn; // 按钮控制开始朗读
    private TextView speechTxt; // 需要朗读的内容
    private TextToSpeech textToSpeech; // TTS对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yuyin);
        speechBtn = (Button) findViewById(R.id.speechButton);
        speechTxt = (TextView) findViewById(R.id.speechTextView);
        textToSpeech = new TextToSpeech(this, this); // 参数Context,TextToSpeech.OnInitListene
        speechBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                if (textToSpeech != null && !textToSpeech.isSpeaking()) {
                    textToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    textToSpeech.setSpeechRate(0.5f);
                    textToSpeech.speak(speechTxt.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
//                textToSpeech.speak(speechText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }

        });
    }

    /**
     * 用来初始化TextToSpeech引擎
     * status:SUCCESS或ERROR这2个值
     * setLanguage设置语言，帮助文档里面写了有22种
     * TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失。
     * TextToSpeech.LANG_NOT_SUPPORTED:不支持
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }
}
