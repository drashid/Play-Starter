package com.github.drashid.metric;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

import redis.clients.jedis.Jedis;

import com.github.drashid.service.impl.RedisService;
import com.github.drashid.utils.JsonUtils;
import com.github.drashid.utils.MachineUtils;
import com.google.common.collect.Maps;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Timer;

@Singleton
public class MetricGateway {

  private static final String SERVER_REGISTRY_KEY = "_servers_";

  private static final String TIMER_HASH_KEY = "_timers_";

  private static final String MACHINE_CODE = MachineUtils.getMachineCode();

  @Inject
  private RedisService redis;

  public void pingServer() {
    Jedis conn = redis.getConnection();
    try {
      Map<String, String> map = Maps.newHashMap();
      map.put(MACHINE_CODE, String.valueOf(System.currentTimeMillis()));
      conn.hmset(SERVER_REGISTRY_KEY, map);
    } finally {
      redis.returnConnection(conn);
    }
  }

  public void pushMetrics() {
    Jedis conn = redis.getConnection();
    try {
      Map<String, TimerInfo> timerMap = Maps.newHashMap();
      for (Entry<MetricName, Metric> entry : Metrics.defaultRegistry().allMetrics().entrySet()) {
        if (entry.getValue() instanceof Timer) {
          timerMap.put(getFullName(entry.getKey()), new TimerInfo(MACHINE_CODE, (Timer)entry.getValue()));
        }
      }
      Map<String, String> nodeTimerMap = Maps.newHashMap();
      nodeTimerMap.put(MACHINE_CODE, JsonUtils.encode(timerMap));
      conn.hmset(TIMER_HASH_KEY, nodeTimerMap);
    } finally {
      redis.returnConnection(conn);
    }
  }

  public Map<String, JsonNode> getMetrics() throws JsonProcessingException, IOException {
    Jedis conn = redis.getConnection();
    try {
      String[] keys = conn.hkeys(TIMER_HASH_KEY).toArray(new String[]{});
      List<String> values = conn.hmget(TIMER_HASH_KEY, keys);
      Map<String, JsonNode> toRet = new HashMap<String, JsonNode>(keys.length);
      for (int i = 0; i < keys.length; i++) {
        toRet.put(keys[i], JsonUtils.MAPPER.readTree(values.get(i)));
      }
      return toRet;
    } finally {
      redis.returnConnection(conn);
    }
  }

  private static String getFullName(MetricName name) {
    return name.getGroup() + "." + name.getType() + "." + name.getName();
  }

}