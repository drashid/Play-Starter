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
    uglify: {
      //we require $<named> params in angularjs
      no_mangle_functions: true,
      no_mangle: true,
      nm: true,
      nfm: true
    },
    //move shim config to standalone file?
    mainConfigFile: '../public/js/admin-main.js'  
})