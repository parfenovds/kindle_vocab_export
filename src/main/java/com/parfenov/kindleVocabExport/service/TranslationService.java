package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.dto.CardLibretranslateDTO;
import com.parfenov.kindleVocabExport.dto.TranslationResponseLibretranslateDTO;
import com.parfenov.kindleVocabExport.entity.basic.Card;
import com.parfenov.kindleVocabExport.mapper.CardLibretranslateMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class TranslationService {

  private final RestTemplate restTemplate;
  private final CardLibretranslateMapper cardLibretranslateMapper;

  @Value("${libretranslate.url}")
  private String libreTranslateUrl;

  public TranslationService(RestTemplate restTemplate, CardLibretranslateMapper cardLibretranslateMapper) {
    this.restTemplate = restTemplate;
    this.cardLibretranslateMapper = cardLibretranslateMapper;
  }

  public Card translate(Card card, String targetLanguage) {
    CardLibretranslateDTO requestDto = cardLibretranslateMapper.mapFrom(card);
    requestDto.setTarget(targetLanguage);

    try {
      String url = libreTranslateUrl + "/translate";

      TranslationResponseLibretranslateDTO responseDto = restTemplate.postForObject(
          url,
          requestDto,
          TranslationResponseLibretranslateDTO.class
      );

      if (responseDto != null) {
        card.setTranslatedSentence(responseDto.getTranslatedText());
      }
      return card;

    } catch (HttpStatusCodeException e) {
      throw new RuntimeException("Translation failed", e);
    } catch (Exception e) {
      throw new RuntimeException("Translation failed", e);
    }
  }
}
