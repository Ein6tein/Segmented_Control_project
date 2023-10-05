package com.example.segmentedcontrolproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.segmentedcontrolproject.ui.theme.LargePadding
import com.example.segmentedcontrolproject.ui.theme.SegmentedControlProjectTheme
import com.example.segmentedcontrolproject.ui.views.SegmentedControl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SegmentedControlProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var selected1 by rememberSaveable { mutableStateOf("One and only") }
                    var selected2 by rememberSaveable { mutableStateOf("One") }
                    var selected3 by rememberSaveable { mutableStateOf("One") }
                    var selected5 by rememberSaveable { mutableStateOf("One") }

                    Column {
                        SegmentedControl(
                            modifier = Modifier.height(56.dp),
                            segmentList = listOf("One and only"),
                            selectedSegment = selected1,
                            defaultColor = MaterialTheme.colorScheme.background,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            onSegmentSelected = {
                                selected1 = it
                            }
                        )
                        Spacer(modifier = Modifier.height(LargePadding))
                        SegmentedControl(
                            modifier = Modifier.height(56.dp),
                            segmentList = listOf("One", "Two"),
                            selectedSegment = selected2,
                            defaultColor = MaterialTheme.colorScheme.background,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            onSegmentSelected = {
                                selected2 = it
                            }
                        )
                        Spacer(modifier = Modifier.height(LargePadding))
                        SegmentedControl(
                            modifier = Modifier.height(56.dp),
                            segmentList = listOf("One", "Two", "Three"),
                            selectedSegment = selected3,
                            defaultColor = MaterialTheme.colorScheme.background,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            onSegmentSelected = {
                                selected3 = it
                            }
                        )
                        Spacer(modifier = Modifier.height(LargePadding))
                        SegmentedControl(
                            modifier = Modifier.height(56.dp),
                            segmentList = listOf("One", "Two", "Sixteen", "Four"),
                            selectedSegment = selected5,
                            defaultColor = MaterialTheme.colorScheme.background,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            useTextWidth = true,
                            onSegmentSelected = {
                                selected5 = it
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    SegmentedControlProjectTheme {
        MainActivity()
    }
}