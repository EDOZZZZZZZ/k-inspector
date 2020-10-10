package com.edoz

import com.edoz.obj.FFmpegInstance
import com.edoz.routes.registerAuthorityRouting
import com.edoz.routes.registerFFmpegRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.*
import java.util.regex.Pattern

//val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
//    ConnectionString("$it?retryWrites=false")
//}
//
//val client = if (connectionString != null) KMongo.createClient(connectionString) else KMongo.createClient()
//val database = client.getDatabase(connectionString?.database ?: "shoppingList")
//val collection = database.getCollection<ShoppingListItem>()

fun read(inputStream: InputStream, out: PrintStream): List<String> {
    var retList: MutableList<String> = mutableListOf()
    try {
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            line?.let { retList.add(it) }
//            out.println(line)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return retList
}

fun readProcessOutput(process: Process) {
    read(process.inputStream, System.out)
    read(process.errorStream, System.err)
}

fun main() {
    embeddedServer(Netty, host = jvmHost, port = jvmPort) {
        Something.timerTaskHandler()
        install(DefaultHeaders)
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        registerFFmpegRouting()
        registerAuthorityRouting()
        if (system == "windows") {
            windowsQueryProcess()
        }
        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }
        }
    }.start(wait = true)
}

fun queryProcess(): Unit {
    val fetchCommand = if (system == "windows") {
        windowsQueryProcess()
    } else if (system == "linux") {
        linuxQueryProcess()
    } else {

    }
}

fun windowsQueryProcess(): Unit {
    val p = Runtime.getRuntime().exec(fetchCommand)
    var taskList = read(p.inputStream, System.out)
    p.waitFor()
    p.destroy()
    if (taskList.size >= 4) {
        taskList = taskList.subList(3, taskList.size)
        for (str in taskList) {
            val pid = str.split(Pattern.compile("\\s+"), 3)[1]
            ffmpegInstanceStorage.putIfAbsent(pid, FFmpegInstance(pid, System.currentTimeMillis().toString()))
        }
    } else {
        ffmpegInstanceStorage.clear()
    }
}

fun windowsKillProcess(pid: String): Unit {
    Runtime.getRuntime().exec(killCommand(pid)).waitFor()
    ffmpegInstanceStorage.remove(pid)
}

fun linuxQueryProcess(): Unit {
    val p = Runtime.getRuntime().exec(fetchCommand)
    var taskList = read(p.inputStream, System.out)
    p.waitFor()
    p.destroy()
    for(str in taskList) {
        val pid = str.split(Pattern.compile("\\s+"))
        for((i, part) in pid.withIndex()) {
            println("$i:$part")
        }
    }
}