package com.github.drashid.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Application;

import com.github.drashid.utils.json.JsonUtils;
import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule {

  private static final Logger LOG = LoggerFactory.getLogger(ConfigModule.class);

  private static final String CONFIG_ROOT_PACKAGE = "com.github.drashid.config";

  private final Environment env;
  
  private final InputStream configStream;
  
  public ConfigModule(Application app) {
    this.env = Environment.valueOf(app);    
    String configFile = getConfigFileForEnv(env, app);    
    this.configStream = app.resourceAsStream(configFile);
    
    LOG.info("Loading configuration from {}", configFile);
  }
  
  public ConfigModule(Environment env, String configFile) throws FileNotFoundException {
    this.env = env;
    this.configStream = new FileInputStream(new File(configFile));
    
    LOG.info("Loading configuration from {}", configFile);
  }
  
  @Override
  protected void configure() {
    //Bind environment
    bind(Environment.class).toInstance(env);
    
    //Bind config classes
    Reflections reflections = new Reflections(CONFIG_ROOT_PACKAGE);
    Set<Class<?>> clsz = reflections.getTypesAnnotatedWith(Config.class);
    LOG.info("Loading configuration for classes {}", clsz);

    JsonNode json;
    try {
      json = JsonUtils.toJson(configStream);
    } catch (Exception e) {
      LOG.error("Error loading configuration json file {}", configStream);
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

  private String getConfigFileForEnv(Environment env, Application app) {
    return app.configuration().getString("config." + env.name());
  }

  private <E> void bindConfig(Class<E> cls, JsonNode json) throws JsonParseException, JsonMappingException, IOException {
    E configData = JsonUtils.toObject(json.get(cls.getAnnotation(Config.class).value()), cls);
    bind(cls).toInstance(configData);
  }

}
