package com.github.drashid.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;

public class JsonUtils {

  private static class TimeUnitSerializer extends JsonSerializer<TimeUnit> {

    @Override
    public void serialize(TimeUnit value, JsonGenerator gen, SerializerProvider provider) throws IOException,
        JsonProcessingException {
      String jsonVal = null;
      switch(value) {
        case DAYS:
          jsonVal = "day";
          break;
        case HOURS:
          jsonVal = "hour";
          break;
        case MILLISECONDS: 
          jsonVal = "ms";
          break;
        case MICROSECONDS:
          jsonVal = "microsec";
          break;
        case NANOSECONDS:
          jsonVal = "ns";
          break;
        case MINUTES: 
          jsonVal = "min";
          break;
        case SECONDS:
          jsonVal = "s";
          break;
      }
      gen.writeString(jsonVal);
    }
    
    @Override
    public Class<TimeUnit> handledType() {
      return TimeUnit.class;
    }
  }

  public static final ObjectMapper MAPPER = new ObjectMapper();
  static {
    MAPPER.setVisibility(JsonMethod.FIELD, Visibility.NONE)
      .setVisibility(JsonMethod.GETTER, Visibility.ANY)
      .setVisibility(JsonMethod.IS_GETTER, Visibility.ANY);
  
    MAPPER.setSerializationConfig(MAPPER.getSerializationConfig()
      .without(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS)
      .with(SerializationConfig.Feature.AUTO_DETECT_GETTERS));
  
    SimpleModule module = new SimpleModule("drashid", new Version(1, 0, 0, null));
    module.addSerializer(new TimeUnitSerializer());
    MAPPER.registerModule(module);
  }

  public static String encode(Object o) {
    try {
      return MAPPER.writeValueAsString(o);
    } catch (Exception e) {
      throw new RuntimeException("Json encoding exception", e);
    }
  }

}
