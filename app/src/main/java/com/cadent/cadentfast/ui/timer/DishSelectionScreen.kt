package com.cadent.cadentfast.ui.timer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cadent.cadentfast.catalog.Catalog
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.ui.theme.Charcoal
import com.cadent.cadentfast.ui.theme.Parchment
import com.cadent.cadentfast.ui.theme.ParchmentDim

@Composable
fun DishSelectionScreen(
    onDishChosen: (Dish) -> Unit,
    onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)

    val haptics = LocalHapticFeedback.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Charcoal)
            .systemBarsPadding(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Tonight's table",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 28.sp),
                        color = Parchment,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "What are you fasting toward?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ParchmentDim,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }

            items(Catalog.wagyu, key = { it.id }) { dish ->
                DishCard(
                    dish = dish,
                    onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        onDishChosen(dish)
                    },
                )
            }
        }
    }
}

@Composable
private fun DishCard(dish: Dish, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
    ) {
        // The dish hero, sized like a magazine photograph -- tall enough to be
        // the protagonist of the card.
        DishHero(
            dish = dish,
            // A small head-start on the ripening so the card isn't dead-cold;
            // the full warming is reserved for the timer screen.
            progress = 0.25f,
            breathing = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = dish.name,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp, lineHeight = 26.sp),
                color = Parchment,
            )
            Text(
                text = dish.description,
                style = MaterialTheme.typography.bodyMedium,
                color = ParchmentDim,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${dish.portion} · approx. ${dish.approxCalories} kcal",
                style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 1.sp),
                color = ParchmentDim,
            )
        }
    }
}
