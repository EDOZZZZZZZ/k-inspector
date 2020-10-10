import io.ktor.client.request.*
import io.ktor.http.*

/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/10
 */


suspend fun checkAuthority(password: String): Boolean {
    return jsonClient.post("$endpoint/authority") {
        contentType(ContentType.Application.Json)
        body = password
    }
}