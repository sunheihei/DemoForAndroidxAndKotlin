package com.sunexample.demoforandroidxandkotlin.FitnessTest;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;

import java.io.File;
import java.util.Locale;

public class TTSUtils {
    //保存TTS文件到本地
    public static int wavToLocal(CharSequence input, TextToSpeech textToSpeech, File file) {
        if (input.length() > TextToSpeech.getMaxSpeechInputLength()) {
            input = input.subSequence(0, TextToSpeech.getMaxSpeechInputLength());
        }
        return textToSpeech.synthesizeToFile(input, null, file, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
    }

    //删除文字转成的语音文件
    public static int deleteWav(Context context, String title) {

        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;


        return resolver.delete(uri,
                MediaStore.Audio.Media.TITLE + " = ? ", new String[]{title});
    }

    public static Locale getLanguageLocale(int which) {
        switch (which) {
            case 1:
                return Locale.ENGLISH;
            case 2:
                return Locale.FRENCH;
            case 3:
                return Locale.GERMAN;
            case 4:
                return Locale.ITALIAN;
            case 5:
                return Locale.JAPANESE;
            case 6:
                return Locale.KOREAN;
            case 7:
                return Locale.CHINESE;
            case 8:
                return Locale.SIMPLIFIED_CHINESE;
            case 9:
                return Locale.TRADITIONAL_CHINESE;
            default:
                return Locale.US;
        }
    }
}
