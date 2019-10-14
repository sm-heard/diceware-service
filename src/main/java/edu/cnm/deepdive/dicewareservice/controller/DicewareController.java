package edu.cnm.deepdive.dicewareservice.controller;

import edu.cnm.deepdive.dicewareservice.model.dao.PassphraseRepository;
import edu.cnm.deepdive.dicewareservice.model.entity.Passphrase;
import edu.cnm.deepdive.dicewareservice.model.entity.Word;
import edu.cnm.deepdive.dicewareservice.service.PassphraseGenerator;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diceware")
public class DicewareController {

  private final PassphraseGenerator generator;
  private final PassphraseRepository passphraseRepository;

  @Autowired
  public DicewareController(PassphraseGenerator generator,
      PassphraseRepository passphraseRepository) {
    this.generator = generator;
    this.passphraseRepository = passphraseRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  String[] get(@RequestParam(value = "length", defaultValue = "6") int length) {
    return generator.passphrase(length);
  }

  @PostMapping(value = "passphrases",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  Passphrase post(@RequestBody Passphrase passphrase,
      @RequestParam(value = "length", defaultValue = "6") int length) {
    List<Word> words = passphrase.getWords();
    String[] dicewareWords = get(length);
    for (String dw : dicewareWords) {
      Word word = new Word();
      word.setWord(dw);
      word.setPassphrase(passphrase);
      words.add(word);
    }
    return passphraseRepository.save(passphrase);
  }

  @GetMapping(value = "passphrases/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  Passphrase get(@PathVariable("key") String key) {
    return passphraseRepository.getFirstByKey(key).get();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound(){}

}

