package swak.http.response

interface WritableResponse<out Body : Any> : Response<Body> {
    val writableBody:String
}