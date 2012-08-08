define(["controller/controllers","libs/underscore","libs/nv.d3","admin/metric-utils"],function(e,t,n,r){e.controller("MeterCtrl",["$scope","$http",function(i,s){i.loadMetrics=function(){s.get("/api/admin/metrics/fetch").success(function(e){i.fetchedMetrics=t.flatten(e),i.averagedMetrics=r.averageMetrics(i.fetchedMetrics),i.metrics=_chooseMetrics(),_loadGraph()})},i.sortBy=function(e){r.sortBy(i,e,"sortField","sortOrder"),_showSortArrow(e)},i.showID=function(){return i.averageNodes?"hide-id":"show-id"},i.isMeter=function(e){return t.has(e,"type")&&(e.type==="TIMER"||e.type==="METER")},_showSortArrow=function(e){i.sortArrow=i.sortOrder?"▼":"▲",$("th > span.sortArrow").css("visibility","hidden"),$("th#"+e+" > span.sortArrow").css("visibility","visible")},_chooseMetrics=function(){return i.averageNodes?i.averagedMetrics:i.fetchedMetrics},_formatDataForGraph=function(e){var n=[];return t.each(e,function(e,t){i.isMeter(e)&&n.push({x:t,y:e.count,metricName:e.name})}),[{key:"Count",values:n}]},_loadGraph=function(){n.addGraph(function(){var e=n.models.multiBarChart();return e.showControls(!1),e.showLegend(!1),e.tooltipContent(function(e,t,n,r,i){return"<h3>"+r.point.metricName+"</h3>"+"<p>"+n+"</p>"}),e.xAxis.axisLabel("Metrics").tickFormat(d3.format(",f")),e.yAxis.axisLabel("Count").tickFormat(d3.format(",.1f")),d3.select("#chart1 svg").datum(_formatDataForGraph(i.averagedMetrics)).transition().duration(250).call(e),n.utils.windowResize(e.update),e})},i.$watch("averageNodes",function(e){i.metrics=_chooseMetrics()}),i.averageNodes=!0,i.loadMetrics(),i.sortBy("count"),i.sortOrder=!0,_showSortArrow("count")}])})