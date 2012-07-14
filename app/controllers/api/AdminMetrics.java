package controllers.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.github.drashid.api.Async;
import com.github.drashid.utils.JsonUtils;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.annotation.Timed;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Timer;

@Async
public class AdminMetrics extends ApiOp {

  @Override
  @Timed
  public Result call() {
    List<MetricInfo> metricNames = new ArrayList<MetricInfo>();
    for (Entry<MetricName, Metric> metric : Metrics.defaultRegistry().allMetrics().entrySet()) {
      metricNames.add(MetricInfo.valueOf(metric.getKey(), metric.getValue()));
    }
    JsonNode node = JsonUtils.MAPPER.valueToTree(metricNames);
    return ok(node);
  }

  private static class MetricInfo {
    
    private String key;
    
    private Map<String, Object> data = new HashMap<String, Object>();

    private MetricInfo() { }

    public static MetricInfo valueOf(MetricName name, Metric metric) {
      MetricInfo info = new MetricInfo();
      info.key = name.getGroup() + "." + name.getType() + "." + name.getName();
      if(metric instanceof Timer){
        info.data.put("metric", metric);
      }
      
      return info;
    }
    
    public Map<String, Object> getData() {
      return data;
    }
    
    public String getKey() {
      return key;
    }
  }

}
