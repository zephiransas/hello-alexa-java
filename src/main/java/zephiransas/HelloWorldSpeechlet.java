package zephiransas;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

public class HelloWorldSpeechlet implements SpeechletV2 {

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {

    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("あなたの名前を教えてください");
        return SpeechletResponse.newAskResponse(speech, createReprompt("私の名前は太郎です、などと話しかけてください"));
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        IntentRequest request = requestEnvelope.getRequest();
        Intent intent = request.getIntent();
        String intentName = intent != null ? intent.getName() : null;

        String message;
        if ("GreetingIntent".equals(intentName)) {
            Slot slot = intent.getSlot("name");
            message = String.format("こんにちは、%sさん", slot.getValue());
        } else {
            message = "もう一度やりなおしてください";
        }

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(message);
        return SpeechletResponse.newTellResponse(speech);
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {

    }

    private Reprompt createReprompt(String message) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(message);
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);
        return reprompt;
    }
}
