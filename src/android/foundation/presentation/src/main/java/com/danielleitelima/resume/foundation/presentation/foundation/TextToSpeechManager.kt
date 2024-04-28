package com.danielleitelima.resume.foundation.presentation.foundation

import android.app.Activity
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale

class TextToSpeechManager : TextToSpeech.OnInitListener{
    private lateinit var tts: TextToSpeech

    fun init(activity: Activity) {
        tts = TextToSpeech(activity, this)
    }

    override fun onInit(status: Int) {
        tts.setLanguage(Locale.getDefault())
    }

    fun setLanguage(locale: Locale) {
        tts.setLanguage(locale)
    }

    fun startSpeaking(message: String, onStop: () -> Unit = {}) {
        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) {
                onStop()
            }

            override fun onError(utteranceId: String?) {
                onStop()
            }
        })
    }

    fun stopSpeaking() {
        tts.stop()
    }
}


