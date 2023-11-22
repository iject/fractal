import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.drawText
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class Complex(var re: Double, var im: Double){
    operator fun plus(c: Complex) : Complex{
        return Complex(this.re + c.re, this.im + c.im)
    }

    operator fun times(c: Complex) : Complex{
        var re1: Double
        var im1: Double

        re1 = this.re * c.re - this.im * c.im
        im1 = this.re * c.im + this.im * c.re
        return Complex(re1, im1)
    }

    fun abs(): Double{
        return Math.sqrt(re*re + im*im)
    }

    override fun toString(): String {
        return "$re + i$im"
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App(){
    var text by remember { mutableStateOf("Hello, World!") }

    val coroutineScope = rememberCoroutineScope()

    MaterialTheme {
        Canvas(modifier = Modifier.fillMaxSize(),
            onDraw = {
                var xMin = -2.5
                var xMax = 1.0
                var yMin = -1.0
                var yMax = 1.0
                var width = this.size.width.toInt()
                var height = this.size.height.toInt()

                for (i in 1..this.size.width.toInt()){
                    //coroutineScope.launch {
                        for (j in 1..height) {
                            var iterator = 0
                            var z1 = Complex(0.0, 0.0)
                            var c = Complex(
                                xMin + i * (xMax - xMin) / width,
                                yMin + j * (yMax - yMin) / height
                            )

                            while (z1.abs() < 2 && iterator < 1000) {
                                z1 = z1 * z1 + c
                                iterator += 1
                            }

                            var clr: Color
                            if (iterator == 1000) clr = Color.White
                            else clr = Color.Black

                            drawCircle(color = clr, radius = 3f, center = Offset(i.toFloat(), j.toFloat()))
                        //}
                    }
                }
            })
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }

    var a = Complex(1.0, 2.0)
    var b = Complex(3.0, 4.0)

    println(a)
    println(a + b)
    println(a * b)
    println(a.abs())

}
