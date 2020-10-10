import com.edoz.obj.FFmpegInstance
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.browser.window

/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/7
 */

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getFFmpegList(): Map<String, FFmpegInstance> {
    return jsonClient.get(endpoint + FFmpegInstance.path)
}

suspend fun delFFmpeg(pid: String): Unit {
    jsonClient.delete<Unit>(endpoint + FFmpegInstance.path + "/${pid}")
}

