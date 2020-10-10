import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RProps
import react.dom.form
import react.dom.input
import react.functionalComponent
import react.useState

/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/10
 */

external interface SwitchProps : RProps {
    var onClick: (Boolean) -> Unit
}

val SwitchComponent = functionalComponent<SwitchProps> { props ->
    val (switch, setSwitch) = useState(false)

    val changeHandler: (Event) -> Unit = {

        setSwitch(!switch)
    }

    input(InputType.checkBox) {
        attrs.onChangeFunction = changeHandler
        props.onClick(switch)
    }

}