package com.example.madmidexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madmidexam.ui.theme.MadMidExamTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MadMidExamTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}



data class TransactionData(
    val title: String,
    val amount: String,
    val transactionType: String
)

var TransactionList = mutableListOf<TransactionData>()

fun addTransaction(title: String, amount:String, transactionType: String){
    TransactionList.add(TransactionData(title, amount, transactionType))
}


@Composable
fun MyApp(){



    val navController = rememberNavController()

    LaunchedEffect(true){
        delay(3000)
        navController.navigate("main")
    }

    NavHost(navController = navController, startDestination = "SplashScreen") {

        composable(route = "SplashScreen") { Splasher() }

        composable(route = "main"){ MainScreen(navController)}
        composable(route = "transaction"){ AddTransaction(navController) }
        composable(route = "summary"){SummaryPage(navController)}

    }

}


@Composable
fun Splasher() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Expense Manager",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Add icons below the text
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Favorite",
                tint = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



@Composable
fun MainScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text ="Expense Manager", fontSize = 30.sp)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { navController.navigate("transaction") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add Transaction")
        }

        Button(
            onClick = { navController.navigate("summary") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("View Summary")
        }
    }
}

@Composable
fun AddTransaction(navController: NavController) {

    var Title = remember{ mutableStateOf("") }
    var Amount = remember{ mutableStateOf("") }
    var TransactionType = remember{ mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween , horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier.padding(20.dp)){
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Title")
            TextField(value = Title.value, onValueChange = {Title.value = it})
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Amount")
            TextField(value = Amount.value, onValueChange = {Amount.value = it})
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Transaction (expense/income)")
            TextField(value = TransactionType.value, onValueChange = {TransactionType.value = it})
        }

        Button(
            onClick = {  addTransaction(Title.value, Amount.value, TransactionType.value)
                navController.navigate("main") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Add Transaction")
        }
    }

}



@Composable
fun SummaryPage(navController: NavController){

    var totalExpense = 0
    var totalIncome = 0

     TransactionList.forEach{transactionData -> (if(transactionData.transactionType == "expense")totalExpense += (transactionData.amount.toInt())) }
     TransactionList.forEach{transactionData ->  (if(transactionData.transactionType == "income")totalIncome += (transactionData.amount.toInt())) }


    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier.padding(20.dp)) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .shadow(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Incomes")
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Favorite",
                            tint = Color.Green,
                            modifier = Modifier.padding(2.dp)
                        )

                    }
                    Text(text = "R$ ${totalIncome}", fontSize = 26.sp)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .shadow(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Expense")
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Favorite",
                            tint = Color.Red,
                            modifier = Modifier.padding(2.dp)
                        )

                    }
                    Text(text = "R$ ${totalExpense}", fontSize = 26.sp)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .shadow(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Total")
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Favorite",
                            tint = Color.Yellow,
                            modifier = Modifier.padding(2.dp)
                        )

                    }
                    Text(text = "R$ ${totalIncome - totalExpense}", fontSize = 26.sp)
                }
            }

            Column {
                Text(text = "Last Transactions", fontSize= 20.sp, modifier = Modifier.padding(20.dp))

                Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                    Column {
                        TransactionList.forEach { item ->
                            Text(text = "${item.transactionType}")
                        }


                    }
                        Column {
                            TransactionList.forEach { item ->
                                Text(text = "${item.amount}")
                            }
                        }


            }
        }
        }
        Button(
            onClick = { navController.navigate("main") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Main Screen")
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MadMidExamTheme {
        MyApp()
    }
}