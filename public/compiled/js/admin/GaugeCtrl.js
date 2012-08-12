define(["controller/controllers","libs/underscore","libs/nv.d3","admin/metric-utils"],function(e,t,n,r){e.controller("GaugeCtrl",["$scope","$http",function(i,s){i.loadMetrics=function(){s.get("/api/admin/metrics/fetch").success(function(e){i.fetchedMetrics=t.flatten(e),i.averagedMetrics=r.averageMetrics(i.fetchedMetrics),_loadGraph()})},_isGauge=function(e){return t.has(e,"type")&&e.type==="GAUGE"},_formatDataForGraph=function(e){var n=[];return t.each(e,function(e,t){_isGauge(e)&&n.push({value:e.value,label:e.name})}),[{key:"Gauge",values:n}]},_loadGraph=function(){n.addGraph(function(){var e=n.models.multiBarHorizontalChart().x(function(e){return e.label}).y(function(e){return e.value}).margin({top:30,right:20,bottom:50,left:175}).showControls(!1).showLegend(!1).tooltips(!1).showValues(!0);return e.yAxis.tickFormat(d3.format(",.2f")),d3.select("#chart1 svg").datum(_formatDataForGraph(i.averagedMetrics)).transition().duration(500).call(e),n.utils.windowResize(e.update),e})},i.loadMetrics()}])})