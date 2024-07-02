package com.example.mobilefinalproject.view.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilefinalproject.R


@SuppressLint("ResourceAsColor")
@Composable
fun MyButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit,
    textColor:Color = Color.White,
    roundedCornerShape: Int = 16,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    paddingText:Int = 5
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(roundedCornerShape))
            ,
        colors = ButtonDefaults.buttonColors(
            colorResource(id = R.color.blue)
        )
    ) {
        Text(
            text = text,
            color = textColor,
            style = style,
            fontWeight = FontWeight.SemiBold,
            modifier= Modifier.padding(vertical = paddingText.dp)

        )
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun OutlineMyButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit,
    textColor:Color = MaterialTheme.colorScheme.onPrimaryContainer,
    roundedCornerShape: Int = 16,
    style: TextStyle = MaterialTheme.typography.titleMedium
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(roundedCornerShape)),
    ) {
        Text(
            text = text,
            color = textColor,
            style = style,
            fontWeight = FontWeight.SemiBold,
            modifier= Modifier.padding(vertical = 5.dp)
        )
    }
}

//@Preview
//@Composable
//fun PreviewMyButton() {
//    MyButton(
//        text = "Test Button",
//        modifier = Modifier,
//        onClick = {}
//    )
//}

@Preview
@Composable
fun PreviewOutlineMyButton() {
    OutlineMyButton(
        modifier = Modifier,
        text = "heloo",
        onClick = {}
    )
}