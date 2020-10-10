package com.edoz.obj

import kotlinx.serialization.Serializable

/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/7
 */

@Serializable
data class FFmpegInstance(val name: String,val createTime: String) {
    companion object {
        const val path = "/ffmpeg"
    }
}