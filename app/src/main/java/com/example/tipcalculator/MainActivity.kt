package com.example.tipcalculator


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.BoringLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    TipCalculatorLayout()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorLayout() {

    var amountInput by remember { mutableStateOf("")}
    var tipAmountInput by remember { mutableStateOf("") }

    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercentage = tipAmountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercentage)


    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            value = amountInput,
            onValueChange = { amountInput = it},
            message = stringResource(R.string.bill_amount),
            imeAction = ImeAction.Next,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        EditNumberField(
            value = tipAmountInput,
            onValueChange = {tipAmountInput = it},
            message = stringResource(R.string.tip_amount),
            imeAction = ImeAction.Done,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it},
//            modifier = Modifier.padding(bottom = 32.dp)

        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(top = 32.dp)
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun RoundTheTipRow(
    roundUp : Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier= Modifier

){
    Row(
        modifier =modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(R.string.round_up_tip),
        )

        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String)->Unit,
    message: String,
    imeAction: ImeAction,
    modifier: Modifier = Modifier
){
   TextField(
       value = value,
       onValueChange = onValueChange,
       singleLine = true,
       label = { Text(message)},
       keyboardOptions = KeyboardOptions.Default.copy(
           keyboardType = KeyboardType.Number,
           imeAction = imeAction
       ),

       modifier=modifier,
   )
}

private fun calculateTip(amount: Double, tipPercent: Double, /*roundUp: Boolean*/): String {
    val tip = tipPercent / 100 * amount
//    if (roundUp) {
//        tip = kotlin.math.ceil(tip)
//    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun TipTimeLayoutPreview() {
    TipCalculatorTheme {
        TipCalculatorLayout()
    }
}