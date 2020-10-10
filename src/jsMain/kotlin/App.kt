/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/1
 */

import com.edoz.obj.FFmpegInstance
import kotlinext.js.jsObject
import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.html.js.onClickFunction


private val scope = MainScope()

val App = functionalComponent<RProps> { _ ->
    val (ffmpegInstanceMap, setFfmpegInstanceMap) = useState(emptyMap<String, FFmpegInstance>())
    val (currentIndex, setCurrentIndex) = useState("")

    useEffect(dependencies = listOf()) {
        scope.launch {
            while (true) {
                setFfmpegInstanceMap(getFFmpegList())
                delay(5000)
            }
        }
    }

    h1() {
        +"Produced By E-Doz"
    }
    h3() {
        +"CurrentIndex: ${currentIndex}"

    }
    ul {
        ffmpegInstanceMap.forEach { item ->
            li {
                key = item.toString()
                +"[${item.key}] ${item.value} "
                attrs.onClickFunction = {
                    setCurrentIndex(item.key)
                    console.log(item.key)

                }
            }
        }
    }
    child(SwitchComponent, props = jsObject {
        onClick = { input ->
            console.log(input)
        }
    })

    child(
        InputComponent,
        props = jsObject {
            onSubmit = { input ->
                val tempIndex = currentIndex
                scope.launch {
                    val flag = checkAuthority(input)
                    console.log("flag:$flag")
                    if (flag) {
                        async {
                            delFFmpeg(tempIndex)
                        }.await()
                        setCurrentIndex("")
                        setFfmpegInstanceMap(getFFmpegList())
                    } else {
                        setCurrentIndex("hehe")
                    }
                }
            }
        }
    )

}