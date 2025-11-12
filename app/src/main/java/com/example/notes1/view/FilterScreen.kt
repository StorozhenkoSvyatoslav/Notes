package com.example.notes1.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes1.ui.theme.blue
import com.example.notes1.ui.theme.dark_violet
import com.example.notes1.ui.theme.green
import com.example.notes1.ui.theme.greyDark
import com.example.notes1.ui.theme.greyWhite
import com.example.notes1.ui.theme.orange
import com.example.notes1.ui.theme.pink
import com.example.notes1.ui.theme.red
import com.example.notes1.ui.theme.rose
import com.example.notes1.ui.theme.violet
import com.example.notes1.ui.theme.yellow

@Composable
fun FilterScreen(
    paddingValues: PaddingValues,
    selectedColor: Int? = null,
    onColorSelected: (Int) -> Unit = {},
    onResetFilter: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
        ) {
            Text(text = "Filter by color", Modifier, fontSize = 24.sp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ColorItem(red, selectedColor == red.toArgb()) { onColorSelected(red.toArgb()) }
                ColorItem(orange, selectedColor == orange.toArgb()) { onColorSelected(orange.toArgb()) }
                ColorItem(yellow, selectedColor == yellow.toArgb()) { onColorSelected(yellow.toArgb()) }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ColorItem(green, selectedColor == green.toArgb()) { onColorSelected(green.toArgb()) }
                ColorItem(blue, selectedColor == blue.toArgb()) { onColorSelected(blue.toArgb()) }
                ColorItem(pink, selectedColor == pink.toArgb()) { onColorSelected(pink.toArgb()) }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ColorItem(violet, selectedColor == violet.toArgb()) { onColorSelected(violet.toArgb()) }
                ColorItem(dark_violet, selectedColor == dark_violet.toArgb()) { onColorSelected(dark_violet.toArgb()) }
                ColorItem(rose, selectedColor == rose.toArgb()) { onColorSelected(rose.toArgb()) }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ColorItem(greyWhite, selectedColor == greyWhite.toArgb()) { onColorSelected(greyWhite.toArgb()) }
                ColorItem(greyDark, selectedColor == greyDark.toArgb()) { onColorSelected(greyDark.toArgb()) }
                Spacer(modifier = Modifier.size(width = 100.dp, height = 40.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ResetButton(
                    modifier = Modifier.weight(1f),
                    onClick = onResetFilter
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    FilterScreen(paddingValues = PaddingValues())
}


@Composable
fun ColorItem(color: Color, isSelected: Boolean = false, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 40.dp)
            .clickable(onClick = onClick)
            .background(color = color, shape = RoundedCornerShape(20.dp))
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            ),
    )
}

@Composable
fun ResetButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(end = 8.dp)
            .clickable(onClick = onClick)
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
            .padding(vertical = 10.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Reset", color = Color.Black, fontWeight = FontWeight.Bold)
    }
}
