//Relative to project directory where r.js lives
({
    appDir: "../public",
    baseUrl: "js",
    dir: "../public/compiled",
    modules: [
        {
            name: "admin-main"
        }
    ],
    optimize: "uglify",
    //external lib definitions, also needed in the modules above
    mainConfigFile: '../public/js/global-config.js' 
})