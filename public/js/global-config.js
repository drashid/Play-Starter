require({
  //shims for non-requirejs libraries
  shim: {
    'libs/underscore': { exports: '_' },
    'libs/angular': { exports: 'angular' },
    'libs/jquery': { exports: '$' },
    'libs/bootstrap-tooltip': ['libs/jquery'], 
    'libs/bootstrap-popover': ['libs/bootstrap-tooltip'],
    'libs/d3': { exports: 'd3' },
    'libs/nv.d3': { exports: 'nv', deps: ['libs/d3'] }
  }
})