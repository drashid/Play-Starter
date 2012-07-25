package com.github.drashid.config;

import java.io.IOException;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Play;

import com.github.drashid.utils.json.JsonUtils;
import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule {

  private static final Logger LOG = LoggerFactory.getLogger(ConfigModule.class);

  private static final String CONFIG_ROOT_PACKAGE = "com.github.drashid.config";

  @Override
  protected void configure() {
    //Bind environment
    Environment env = Environment.valueOf(Play.application());
    bind(Environment.class).toInstance(env);
    
    String configFile = getConfigFile(env);
    
    //Bind config classes
    Reflections reflections = new Reflections(CONFIG_ROOT_PACKAGE);
    Set<Class<?>> clsz = reflections.getTypesAnnotatedWith(Config.class);
    LOG.info("Loading configuration for classes {} from {}", clsz, configFile);

    JsonNode json;
    try {
      json = JsonUtils.toJson(Play.application().resourceAsStream(configFile));
    } catch (Exception e) {
      LOG.error("Error loading configuration json file {}", configFile);
      throw new InvalidConfigurationException("Error loading json config", e);
    }

    for (Class<?> cls : clsz) {
      LOG.info("Loading configuration for {}", cls);
      try {
        bindConfig(cls, json);
      } catch (Exception e) {
        LOG.error("Error configuring class {}", cls, e);
        throw new InvalidConfigurationException("Exception while loading configuration for " + cls.getName(), e);
      }
    }
  }

  private String getConfigFile(Environment env) {
    return Play.application().configuration().getString("config." + env.name());
  }

  private <E> void bindConfig(Class<E> cls, JsonNode json) throws JsonParseException, JsonMappingException, IOException {
    E configData = JsonUtils.toObject(json.get(cls.getAnnotation(Config.class).value()), cls);
    bind(cls).toInstance(configData);
  }

}
