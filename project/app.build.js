//Relative to project directory where r.js lives
({
    appDir: "../public",
    baseUrl: "js",
    dir: "../public/r-build",
    modules: [
        {
            name: "admin-main"
        }
    ], 
    paths: {
      "lib/angular": "lib/angular"
    },
    optimize: "none",
    //WHY DOESN'T THIS WORK!?
    // uglify: {
    //   //we require $<named> params in angularjs
    //   'no_mangle_functions': false,
    //   no_mangle: false,
    //   'nm': false,
    //   nfm: false
    // },
    //move shim config to standalone file?
    mainConfigFile: '../public/js/admin-main.js'  
})