a **light framework** to start an asynchronous http server  
written in kotlin, based on undertow & rxjava

# status
in development

# everything is code
Swak's philosophy is to keep everything easily doable from code.

Support of configuration file will be supported but will always be a secondary way to configure your server.

swak api aims to be **simple**, **flexible** and **safe typed**

# where 'swak' comes from
well... we all know kotlin coders got the swak

# usage

## starting the server
to start a http server on port 80, you simply write
```kotlin
SwakServer().start()
```
Doing this will throw a `NoRouteProvided` exception: swak refuse to start a server doing nothing.

## hello world
to start a http server answering "hello world" on "/hi"
```kotlin
SwakServer {
    handle("/hi", GET) { Single.just(Response(body = "hello world")) }
}.start()
```

## path param
swak also support path param:
```kotlin
SwakServer {
    handle("/hi/{me}", GET) { request ->
        Single.just(Response(body = "hello ${request.pathParams["me"]}"))
    }
}.start()
```

