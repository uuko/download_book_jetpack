package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Artical
import com.example.myapplication.repository.BookDownloadRepository
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.view.BookDownloadActivity
import com.example.myapplication.viewmodel.BookDownloadViewModel
import com.example.myapplication.viewmodel.BookDownloadViewModelFactory

class MainActivity : ComponentActivity() {
    private val factory = BookDownloadViewModelFactory(BookDownloadRepository())
    private val viewModel: BookDownloadViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.parseNovelAllBooksAndSave("https://czbooks.net/n/cpgb24p")
//        viewModel.bookAllSize.observe(this) {
//            viewModel.parseNovelOneBookAndSave(it.list)
//        }
//
//        viewModel.articalData.observe(this) {
//            Log.e("onCreate", "articalData: ${it.title}")
//
////            viewModel.getAritcal()
//        }


        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp(viewModel)
//                    Greeting("aaa")
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MyApp(viewModel: BookDownloadViewModel) {
    detectingStatus(viewModel)
}

@Composable
fun detectingStatus(viewModel: BookDownloadViewModel) {
    var shouldShowOnboarding by rememberSaveable {
        mutableStateOf(true)
    }

    if (shouldShowOnboarding) {
        OnboardingScreen(OnContinueClicked = {
            shouldShowOnboarding = false

        }, viewModel)

    } else {
        Conversation(
            List(1000) { "$it" }
        )
    }

}

@Composable
fun OnboardingScreen(OnContinueClicked: () -> Unit, viewModel: BookDownloadViewModel) {
    val artical: Artical by viewModel.articalData.observeAsState(
        Artical(
            "",
            "",
            "",
            "",
            "",
            listOf()
        )
    )
    val articalData = remember {
        mutableStateOf(artical)
    }


//    Surface() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
//        val _artical: Artical by  viewModel.articalData.observeAsState(Artical("aaaaaa", "", "aaaaa", "", listOf()))
////        val artical :Artical by rememberSaveable  {
////            viewModel.articalData.observeAsState(Artical("aaaaaa", "", "aaaaa", "", listOf()))
////        }
        Log.e("onCreate", "OnboardingScreen: ${artical.title}")
//        Text(_artical.author + "!!!")
        Text(text = articalData.component1().title, color = Color.White)
        Button(
            onClick = OnContinueClicked,
            modifier = Modifier.padding(vertical = 24.dp)
        ) {
            Text(text = "Continue")

        }
        val context = LocalContext.current
        Button(onClick = {
            context.startActivity(
                Intent(
                    context,
                    BookDownloadActivity::class.java
                )
            )
        }) {
            Text(text = "Jump")
        }

//        var text by remember { mutableStateOf ("Prev Text") }
//        Log.e("onCreate", "OnboardingScreen: $text" )
//        Text(text)
//        Button(onClick = { text = "Updated Text" }){
//            Text("Update The Text")
//        }

    }

//    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    OnboardingScreen({}, viewModel = BookDownloadViewModel(BookDownloadRepository()))
}


@Composable
fun Greeting(name: String) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding = if (expanded) 48.dp else 8.dp
    Log.e("Greeting", "Greeting: $expanded")
    Row(modifier = Modifier.padding(all = 8.dp)) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "aaaa",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )


        Column(
            modifier = Modifier
                .padding(bottom = extraPadding)
                .weight(1f)
        ) {
            Text(text = name, color = MaterialTheme.colors.secondaryVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name)
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }

        OutlinedButton(
            onClick = { expanded = !expanded }
        ) {
            Text(if (expanded) "Show less" else "Show more")
        }
    }


}


@Composable
fun Conversation(messages: List<String> = List(1000) { "$it" }) {
    LazyColumn {
        items(messages) { message ->
            Greeting(name = message)
        }
    }
}

object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Colleague",
            "Test...Test...Test..."
        ),
        Message(
            "Colleague",
            "List of Android versions:\n" +
                    "Android KitKat (API 19)\n" +
                    "Android Lollipop (API 21)\n" +
                    "Android Marshmallow (API 23)\n" +
                    "Android Nougat (API 24)\n" +
                    "Android Oreo (API 26)\n" +
                    "Android Pie (API 28)\n" +
                    "Android 10 (API 29)\n" +
                    "Android 11 (API 30)\n" +
                    "Android 12 (API 31)\n"
        ),
        Message(
            "Colleague",
            "I think Kotlin is my favorite programming language.\n" +
                    "It's so much fun!"
        ),
        Message(
            "Colleague",
            "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Colleague",
            "Hey, take a look at Jetpack Compose, it's great!\n" +
                    "It's the Android's modern toolkit for building native UI." +
                    "It simplifies and accelerates UI development on Android." +
                    "Less code, powerful tools, and intuitive Kotlin APIs :)"
        ),
        Message(
            "Colleague",
            "It's available from API 21+ :)"
        ),
        Message(
            "Colleague",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Colleague",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Colleague",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Colleague",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Colleague",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Colleague",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Colleague",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}

@Preview
@Composable
fun PreviewConversation() {
    Conversation(
        List(1000) { "$it" }
    )

}

@Preview
@Composable
fun PreviewMessageCard() {
    Greeting("Android")
}






