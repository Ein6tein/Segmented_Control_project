package com.example.segmentedcontrolproject.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.segmentedcontrolproject.ui.theme.SegmentedControlProjectTheme
import com.example.segmentedcontrolproject.ui.theme.SmallPadding
import com.example.segmentedcontrolproject.util.Quadruple

@Composable
fun SegmentedControlItem(
    modifier: Modifier,
    text: String,
    isSelected: Boolean,
    useTextWidth: Boolean = false,
    onClickAction: (Float, Int, Boolean) -> Unit,
    onGloballyPositionedCallback: (Quadruple<Int, Int, Float, Boolean>) -> Unit,
) {
    var boxLayoutCoordinates: LayoutCoordinates? = null
    var textLayoutCoordinates: LayoutCoordinates? = null
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = SmallPadding)
            .clickable {
                val quad = getQuadruple(boxLayoutCoordinates, textLayoutCoordinates, useTextWidth)
                onClickAction(quad.third, quad.first, quad.fourth)
            }
            .onGloballyPositioned {
                boxLayoutCoordinates = it
                if (textLayoutCoordinates != null) {
                    onGloballyPositionedCallback(
                        getQuadruple(boxLayoutCoordinates, textLayoutCoordinates, useTextWidth)
                    )
                }
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .onGloballyPositioned {
                    textLayoutCoordinates = it
                    if (boxLayoutCoordinates != null) {
                        onGloballyPositionedCallback(
                            getQuadruple(boxLayoutCoordinates, textLayoutCoordinates, useTextWidth)
                        )
                    }
                },
            text = text,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

private fun getQuadruple(
    boxCoordinates: LayoutCoordinates?,
    textCoordinates: LayoutCoordinates?,
    shouldUseTextParams: Boolean
) : Quadruple<Int, Int, Float, Boolean> {
    return Quadruple(
        if (shouldUseTextParams) {
            textCoordinates!!.size.width
        } else {
            boxCoordinates!!.size.width
        },
        boxCoordinates!!.size.height,
        boxCoordinates.positionInParent().x +
            if (shouldUseTextParams) {
                textCoordinates!!.positionInParent().x
            } else {
                0f
            },
        boxCoordinates.size.width == textCoordinates!!.size.width
    )
}

@Preview(showBackground = true)
@Composable
fun SegmentedControlItemPreview() {
    SegmentedControlProjectTheme {
        SegmentedControlItem(
            modifier = Modifier,
            text = "Hello",
            isSelected = true,
            onClickAction = { _, _, _ -> /*TODO*/ },
            onGloballyPositionedCallback = { /*TODO*/ },
        )
    }
}