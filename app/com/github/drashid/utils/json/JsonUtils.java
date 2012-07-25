package com.github.drashid.utils.json;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.type.TypeReference;

public class JsonUtils {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.setVisibility(JsonMethod.FIELD, Visibility.NONE).setVisibility(JsonMethod.GETTER, Visibility.ANY)
        .setVisibility(JsonMethod.IS_GETTER, Visibility.ANY);

    MAPPER
        .setSerializationConfig(MAPPER.getSerializationConfig()
            .without(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS)
            .with(SerializationConfig.Feature.AUTO_DETECT_GETTERS));

    SimpleModule module = new SimpleModule("drashid", new Version(1, 0, 0, null));
//    module.addSerializer(new TimeUnitSerializer());
    MAPPER.registerModule(module);
  }

  public static String toJsonString(Object o) throws JsonGenerationException, JsonMappingException, IOException {
    return MAPPER.writeValueAsString(o);
  }

  public static String toJsonString(Object o, TypeReference<?> typedWriter) throws JsonGenerationException, JsonMappingException, IOException {
    return MAPPER.writerWithType(typedWriter).writeValueAsString(o);
  }
  
  public static JsonNode toJson(Object o) {
    return MAPPER.valueToTree(o);
  }

  public static <E> E toObject(JsonNode json, Class<E> cls) throws JsonParseException, JsonMappingException, IOException {
    return MAPPER.readValue(json, cls);
  }

  public static <E> E toObject(String json, Class<E> cls) throws JsonParseException, JsonMappingException, IOException {
    return MAPPER.readValue(json, cls);
  }

  public static <E> E toObject(String json, TypeReference<E> cls) throws JsonParseException, JsonMappingException, IOException {
    return MAPPER.readValue(json, cls);
  }
  
  public static <E> JsonNode toJson(InputStream in) throws JsonProcessingException, IOException {
    return MAPPER.readTree(in);
  }

}
