package swak.http.response

import swak.http.MutableHeaders

interface Response<out Body> {
    val status: Code
    val headers: MutableHeaders
    val body: Body
}