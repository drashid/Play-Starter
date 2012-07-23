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
    optimize: "uglify",
    // closure: {
    //   CompilationLevel: "SIMPLE_OPTIMIZATION"
    // },
    //WHY DOESN'T THIS WORK!?
    // uglify: {
    //   //we require $<named> params in angularjs
    //   'no_mangle_functions': false,
    //   no_mangle: false,
    //   'nm': false,
    //   nfm: false
    // },

    //external lib definitions, also needed in the modules above
    mainConfigFile: '../public/js/global-config.js' 
})