package com.example.learning_assignment1
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.learning_assignment1.ui.theme.LearningAssignment1Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LearningAssignment1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}

fun validName(name: String): Boolean{
    if (name.length>30) return false
    for (c in name) if (c !in 'A'..'Z' && c !in 'a'..'z' && c !=' ') return false
    return true
}
fun validPhone(phone: String): Boolean{
    if (phone.length != 10) return false
    for (c in phone) if (c !in '0'..'9') return false
    return true
}
fun validEmail(email: String): Boolean{
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun MainScreen(mainViewModel : MainViewModel){

    var name : String by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    val isDetailsValid by derivedStateOf{
        validName(name) && validPhone(phone) && validEmail(email)
    }

//    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(context){
        mainViewModel.readToLocal.collect{
            name = it.name
            phone= it.phone
            email = it.email
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.16f)
                .fillMaxWidth()
                .background(Color(0xFF6800EE))
        ) {
        }
        Image(painterResource(id = R.drawable.user) ,
            contentDescription = "UserLogo",

            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-40).dp)
                .size(Dp(80F))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(Dp(20F))
        ) {
            TextField(
                modifier = Modifier
                    .padding(Dp(0F), Dp(8F), Dp(0F), Dp(8F))
                    .fillMaxWidth()
                    .background(Color.White),

                value = name,
                onValueChange ={value->name = value},
                label = { Text(text = "Name\n")},
            )
            TextField(
                modifier = Modifier
                    .padding(Dp(0F), Dp(8F), Dp(0F), Dp(8F))
                    .fillMaxWidth(),
                value = phone,
                onValueChange ={value->phone = value},
                label = { Text(text = "Phone\n")},
            )

            TextField(
                modifier = Modifier
                    .padding(Dp(0F), Dp(8F), Dp(0F), Dp(8F))
                    .fillMaxWidth(),
                value = email,
                onValueChange ={value->email = value},
                label = { Text(text = "Email\n")},
            )

            Button(onClick = {
                mainViewModel.writeToLocal(name, phone, email)
                Toast.makeText(context,"Details updated Successfully",Toast.LENGTH_LONG).show()
            },
                enabled = isDetailsValid,
                modifier = Modifier
                    .padding(Dp(40F))) {
                Text(text = "Save details")

            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    LearningAssignment1Theme {
////       MainScreen(mainViewModel)
//    }
//}