package com.awstraining.backend.business.notifyme.adapter;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.awstraining.backend.business.notifyme.NotifyMeDO;
import com.awstraining.backend.business.notifyme.Translator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranslatorImpl implements Translator {

    private static final Logger LOGGER = LogManager.getLogger(TranslatorImpl.class);

    // TODO: lab2
    //  1. Inject AWS AmazonTranslate from configuration TranslatorConfig.
    private final AmazonTranslate amazonTranslate;

    @Autowired
    public TranslatorImpl(AmazonTranslate amazonTranslate) {
        this.amazonTranslate = amazonTranslate;
    }
    
    @Override
    public String translate(NotifyMeDO notifyMeDO) {
        // TODO: lab2
        //  1. Create translate text request.
        //  2. Call translate.
        //  3. Log information about successful translated message.
        //  4. Return translated message
        TranslateTextRequest request = new TranslateTextRequest();
        request.setText(notifyMeDO.text());
        request.setSourceLanguageCode(notifyMeDO.sourceLc());
        request.setTargetLanguageCode(notifyMeDO.targetLc());
        TranslateTextResult result = amazonTranslate.translateText(request);
        LOGGER.info("'{}' translated to: '{}'", notifyMeDO.text(), result.getTranslatedText());
        return result.getTranslatedText();
    }
}
