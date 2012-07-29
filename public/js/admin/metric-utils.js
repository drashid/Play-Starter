define(['libs/underscore'], function(_){
  
  function averageObjs(objs, funcsByKey){
    var size = _.size(objs);
    if(size == 0){
      return {};
    }

    var result = {};
    var keys = _.keys(objs[0]);
    _.each(keys, function(key){
        if(_.has(funcsByKey, key)){ //specific user function
          result[key] = funcsByKey[key]( _.pluck(objs, key) );
        } else if(_.isNumber(objs[0][key])) { //average by default
          result[key] = average( _.pluck(objs, key) );
        } else {
          result[key] = objs[0][key];
        }
      });
    return result;
  };

  function average(vals){
    return sum(vals) / _.size(vals);
  }

  function sum(vals){
    return _.reduce(vals, function(memo, num){ return memo + num; }, 0);
  }

  return {
    averageObjs: averageObjs,
    sum: sum,
    average: average
  }
});