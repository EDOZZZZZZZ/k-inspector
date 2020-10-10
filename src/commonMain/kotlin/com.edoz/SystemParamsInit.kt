package com.edoz

import com.edoz.obj.FFmpegInstance

/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/9
 */

var ffmpegInstanceStorage = mutableMapOf<String, FFmpegInstance>() // <pid, instance>

val fetchCommand = if (system == "windows") {
    "tasklist /FI \"IMAGENAME eq " + taskName + "\""
} else if (system == "linux") {
    "ps -ef|grep $taskName"
} else {
    ""
}

fun killCommand(pid: String): String {
    if (system == "windows") {
        return "taskkill /f /pid $pid"
    } else if (system == "linux") {
        return "kill -9 $pid"
    } else {
        return ""
    }
}