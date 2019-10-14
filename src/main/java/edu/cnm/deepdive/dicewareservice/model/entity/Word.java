package edu.cnm.deepdive.dicewareservice.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

@Entity
public class Word {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "word_id",nullable = false,updatable = false)
  private long id;

  @Column(nullable = false,updatable = false)
  private String word;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "passphrase_id",nullable = false,updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Passphrase passphrase;

  public void setWord(String word) {
    this.word = word;
  }

  public void setPassphrase(Passphrase passphrase) {
    this.passphrase = passphrase;
  }

  public long getId() {
    return id;
  }

  public String getWord() {
    return word;
  }

  public Passphrase getPassphrase() {
    return passphrase;
  }
}
