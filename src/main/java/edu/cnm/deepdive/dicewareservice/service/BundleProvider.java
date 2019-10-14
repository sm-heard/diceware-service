package edu.cnm.deepdive.dicewareservice.service;

import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BundleProvider implements WordProvider {

  private final ResourceBundle bundle;

  public BundleProvider(ResourceBundle bundle) {
    this.bundle = bundle;
  }

  @Override
  public Collection<String> words() {
    return bundle.keySet().parallelStream()
        .map(bundle::getString)
        .collect(Collectors.toList());
  }
}
