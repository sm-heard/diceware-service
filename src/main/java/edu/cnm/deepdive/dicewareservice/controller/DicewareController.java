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
import org.springframework.web.bind.annotation.DeleteMapping;
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
  public String[] get(@RequestParam(value = "length", defaultValue = "6") int length) {
    return generator.passphrase(length);
  }

  @PostMapping(value = "passphrases",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Passphrase post(@RequestBody Passphrase passphrase,
      @RequestParam(value = "length", defaultValue = "6") int length) {
    List<Word> words = passphrase.getWords();
    if (words.isEmpty()) {
      String[] dicewareWords = get(length);
      for (String dw : dicewareWords) {
        Word word = new Word();
        word.setWord(dw);
        words.add(word);
      }
    }
    for (Word word : words) {
      word.setPassphrase(passphrase);
    }
    return passphraseRepository.save(passphrase);
  }

  @GetMapping(value = "passphrases/{key:^\\D.*}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Passphrase get(@PathVariable("key") String key) {
    return passphraseRepository.getFirstByKey(key).get();
  }

  @GetMapping(value = "passphrases/{id:^\\d+$}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Passphrase get(@PathVariable("id") long id) {
    return passphraseRepository.findById(id).get();
  }

  @DeleteMapping(value = "passphrases/{id:^\\d+$}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") long id) {
    passphraseRepository.delete(get(id));
  }

  @GetMapping(value = "passphrases", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Passphrase> getAll() {
    return passphraseRepository.getAllByOrderByIdAsc();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public void badRequest() {}

}

