import kotlinext.js.jsObject
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RProps
import react.child
import react.dom.form
import react.dom.input
import react.functionalComponent
import react.useState

/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/9
 */

external interface InputProps : RProps {
    var onSubmit: (String) -> Unit
}

val InputComponent = functionalComponent<InputProps> { props ->
    val (text, setText) = useState("")

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()
        setText("")
        props.onSubmit(text)
    }

    val changeHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setText(value)
    }

    form {
        attrs.onSubmitFunction = submitHandler
        input(InputType.password) {
            attrs.onChangeFunction = changeHandler
            attrs.value = text
        }
    }
}