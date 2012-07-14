package com.github.drashid.utils;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JsonUtils {

  public static final ObjectMapper MAPPER = new ObjectMapper();
  static {
    MAPPER.setVisibility(JsonMethod.FIELD, Visibility.NONE)
      .setVisibility(JsonMethod.GETTER, Visibility.ANY)
      .setVisibility(JsonMethod.IS_GETTER, Visibility.ANY);
    
    MAPPER.setSerializationConfig(MAPPER.getSerializationConfig()
        .without(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS)
        .with(SerializationConfig.Feature.AUTO_DETECT_GETTERS));
  }

}
