package com.example.segmentedcontrolproject.ui.views

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.segmentedcontrolproject.ui.theme.MediumPadding
import com.example.segmentedcontrolproject.ui.theme.SegmentedControlProjectTheme
import com.example.segmentedcontrolproject.ui.theme.SmallPadding

@Composable
fun SegmentedControl(
    modifier: Modifier = Modifier, // modifier for widget root container
    segmentList: List<String>, // list of data
    selectedSegment: String, // currently selected segment
    defaultColor: Color, // default segment color
    selectedColor: Color, // selected segment color
    useTextWidth: Boolean = false, // use text width for selection indicator
    onSegmentSelected: (String) -> Unit // segment selection callback
) {
    val density = LocalDensity.current.density

    var selectedSegmentX by remember { mutableStateOf(-1f) }
    var selectorWidth by remember { mutableStateOf(0f) }
    var selectorHeight by remember { mutableStateOf(0f) }
    var isSelectorMaxWidth by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(all = SmallPadding)
            .background(defaultColor, MaterialTheme.shapes.medium)
    ) {
        val x = animateFloatAsState(
            targetValue = selectedSegmentX,
            label = "Move selection indicator to selected value",
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        val selectorWidthAnimated = animateFloatAsState(
            targetValue = selectorWidth,
            label = "Move selection indicator to selected value",
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val padding = SmallPadding.toPx() * density
                    val width = selectorWidthAnimated.value + (
                        if (useTextWidth && !isSelectorMaxWidth)
                            padding * 2
                        else
                            0f
                    )
                    val height = selectorHeight

                    drawRoundRect(
                        color = selectedColor,
                        topLeft = Offset(
                            x.value - (if (useTextWidth && !isSelectorMaxWidth) padding else 0f),
                            0f,
                        ),
                        size = Size(width, height),
                        cornerRadius = CornerRadius(
                            x = MediumPadding.toPx(),
                            y = MediumPadding.toPx()
                        ),
                    )
                }
        ) {
            for (segment in segmentList) {
                SegmentedControlItem(
                    modifier = Modifier.weight(1f),
                    text = segment,
                    isSelected = segment == selectedSegment,
                    useTextWidth = useTextWidth,
                    onClickAction = { x, width, isMaxWidth ->
                        if (segment != selectedSegment) {
                            selectedSegmentX = x
                            if (width.toFloat() != selectorWidth) {
                                selectorWidth = width.toFloat()
                                isSelectorMaxWidth = isMaxWidth
                            }
                            onSegmentSelected(segment)
                        }
                    },
                    onGloballyPositionedCallback = {
                        if (selectorWidth == 0f || segment == selectedSegment) {
                            selectorWidth = it.first.toFloat()
                            isSelectorMaxWidth = it.fourth
                        }
                        if (selectorHeight == 0f || segment == selectedSegment) {
                            selectorHeight = it.second.toFloat()
                        }
                        if (selectedSegmentX == -1f || segment == selectedSegment) {
                            selectedSegmentX = it.third
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentedControlPreview() {
    SegmentedControlProjectTheme {
        var selectedSegment by remember { mutableStateOf("Hello") }
        SegmentedControl(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            segmentList = listOf("Hello", "There", "World"),
            selectedSegment = selectedSegment,
            defaultColor = MaterialTheme.colorScheme.surface,
            selectedColor = MaterialTheme.colorScheme.primary,
            onSegmentSelected = {
                selectedSegment = it
            }
        )
    }
}