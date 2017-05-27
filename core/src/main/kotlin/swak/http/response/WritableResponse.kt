package swak.http.response

interface WritableResponse<out Body> : Response<Body> {
    val writableBody:String
}