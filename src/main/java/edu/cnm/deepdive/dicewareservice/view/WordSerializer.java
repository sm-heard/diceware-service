package edu.cnm.deepdive.dicewareservice.view;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import edu.cnm.deepdive.dicewareservice.model.entity.Word;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class WordSerializer {

  public static class Serializer extends JsonSerializer<Word> {

    @Override
    public void serialize(Word word, JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeString(word.getWord());
    }

  }

  public static class Deserializer extends JsonDeserializer<Word> {

    @Override
    public Word deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {
      Word word = new Word();
      word.setWord(jsonParser.readValueAs(String.class));
      return word;
    }
  }

}
