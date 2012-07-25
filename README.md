Play 2 Starter Project 
=========
_(currently in development)_

This project is meant to get the ball rolling on new projects.  It is built upon [Play 2](http://www.playframework.org/documentation/2.0/JavaHome) and the backend uses [Google Guice](http://code.google.com/p/google-guice/) extensively for dependency injection.  It also has integrated support for [Yammer Metrics](http://metrics.codahale.com/) allowing you to easily annotate your API endpoints and other methods to receive metering/timing information, as well as set up health checks (e.g. connections to databases, etc).  To better view this information, it currently comes with an administrative console ([heroku demo here](http://play-2-starter-demo.herokuapp.com/admin), if I haven't broken it while developing).  The metrics data is pushed to Redis on a regular interval from each node, but could be set up to push to other sources (e.g. MongoDB).  The admin dashboard is written using [AngularJS](http://angularjs.org/), [RequireJS](http://requirejs.org/), and [Twitter Bootstrap](http://twitter.github.com/bootstrap/).

Documentation will come later, don't get your hopes up too soon on that front -- but good code "documents itself," or so people who never write documentation tell me.

Installation
--------

1. [Install Play 2](http://www.playframework.org/documentation/2.0/Installing)
2. Install [Redis](http://redis.io/) by the means most appropriate for your OS (e.g. Homebrew on Mac)
3. Clone this project to your favorite directory

Running
-------

1. Launch the Redis server (most likely by running `redis-server` on the command line) and leave it running
2. From the root of the project directory where you cloned the code, launch `play run` to start the application in DEVELOPMENT mode

From here you can navigate your browser to localhost at the /admin endpoint (e.g. `http://localhost:9000/admin`) to view the dashboard.  You may need to wait a few seconds and refresh your browser for metric data to come in, as it is only synced every 5 seconds.
