a **light framework** to start an asynchronous http server
written in kotlin with coroutine.  
Currently, swak can be used on top of undertow.

# status
in development

# everything is code
Swak's philosophy is to keep everything easily doable from code.

Support of configuration file will be supported but will always be a secondary way to configure your server.

swak api aims to be **simple**, **flexible** and **safe typed**

# usage

## starting the server
to start a http server on port 80, you simply write
```kotlin
SwakServer(UndertowEngine()).start()
```

## hello world
to start a http server answering "hello world" on "/hi"
```kotlin
SwakServer {
    handle("/hi", GET) { SimpleResponse(body = "hello world") }
}.start()
```

## path param
swak also support path param:
```kotlin
SwakServer {
    handle("/hi/{me}", GET) { request ->
        SimpleResponse(body = "hello ${request.pathParams["me"]}")
    }
}.start()
```

