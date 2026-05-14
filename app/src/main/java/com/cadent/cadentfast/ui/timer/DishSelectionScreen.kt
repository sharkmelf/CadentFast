package com.cadent.cadentfast.ui.timer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cadent.cadentfast.catalog.Catalog
import com.cadent.cadentfast.catalog.Cuisine
import com.cadent.cadentfast.catalog.DietaryTag
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.ui.theme.Charcoal
import com.cadent.cadentfast.ui.theme.CharcoalRaised
import com.cadent.cadentfast.ui.theme.Copper
import com.cadent.cadentfast.ui.theme.Parchment
import com.cadent.cadentfast.ui.theme.ParchmentDim

/**
 * One menu. Food is food.
 *
 * Vertical scrolling list of full-width dish cards in the curator's editorial
 * sequence. Filter chips at the top — cuisine on one row, dietary register on
 * another — toggleable, multiple may be active. The picker defaults to the
 * smart-default chip pre-activation hinted by [initialDietaryFilters]; the
 * caller derives that from the upcoming break-fast's time of day.
 *
 * Tapping a dish fires a soft lock-confirm haptic and routes back via
 * [onDishChosen]. The system back button (or the back gesture) routes via
 * [onBack].
 */
@Composable
fun DishSelectionScreen(
    onDishChosen: (Dish) -> Unit,
    onBack: () -> Unit,
    initialDietaryFilters: Set<DietaryTag> = emptySet(),
    headerTitle: String = "Tonight's table",
    headerSubtitle: String = "What are you fasting toward?",
) {
    BackHandler(onBack = onBack)

    var activeCuisines by remember { mutableStateOf<Set<Cuisine>>(emptySet()) }
    var activeTags by remember { mutableStateOf<Set<DietaryTag>>(initialDietaryFilters) }

    val haptics = LocalHapticFeedback.current

    val filteredDishes = remember(activeCuisines, activeTags) {
        Catalog.filter(activeCuisines, activeTags)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Charcoal)
            .systemBarsPadding(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = 24.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                ) {
                    Text(
                        text = headerTitle,
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 28.sp),
                        color = Parchment,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = headerSubtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ParchmentDim,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            item {
                FilterChipRows(
                    activeCuisines = activeCuisines,
                    activeTags = activeTags,
                    onCuisineToggle = { cuisine ->
                        activeCuisines = activeCuisines.toggle(cuisine)
                    },
                    onTagToggle = { tag ->
                        activeTags = activeTags.toggle(tag)
                    },
                )
            }

            if (filteredDishes.isEmpty()) {
                item {
                    EmptyFilterState(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                    )
                }
            } else {
                items(filteredDishes, key = { it.id }) { dish ->
                    DishCard(
                        dish = dish,
                        onClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            onDishChosen(dish)
                        },
                        modifier = Modifier.padding(horizontal = 24.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterChipRows(
    activeCuisines: Set<Cuisine>,
    activeTags: Set<DietaryTag>,
    onCuisineToggle: (Cuisine) -> Unit,
    onTagToggle: (DietaryTag) -> Unit,
) {
    val cuisineScroll = rememberScrollState()
    val tagScroll = rememberScrollState()
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(cuisineScroll)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Cuisine.entries.forEach { cuisine ->
                FilterChip(
                    label = cuisine.displayName,
                    active = cuisine in activeCuisines,
                    onClick = { onCuisineToggle(cuisine) },
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(tagScroll)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DietaryTag.entries.forEach { tag ->
                FilterChip(
                    label = tag.displayName,
                    active = tag in activeTags,
                    onClick = { onTagToggle(tag) },
                    // Slightly different visual weight so the user reads
                    // dietary tags as secondary to cuisine.
                    accent = Copper.copy(alpha = 0.65f),
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    active: Boolean,
    onClick: () -> Unit,
    accent: Color = Copper,
) {
    val bg = if (active) accent.copy(alpha = 0.18f) else Color.Transparent
    val borderColor = if (active) accent else Parchment.copy(alpha = 0.25f)
    val textColor = if (active) Parchment else ParchmentDim
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(bg)
            .border(width = 0.75.dp, color = borderColor, shape = CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 12.sp,
                letterSpacing = 0.5.sp,
            ),
            color = textColor,
        )
    }
}

@Composable
private fun DishCard(
    dish: Dish,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CharcoalRaised)
            .clickable(onClick = onClick),
    ) {
        // Hero gradient. A small head-start on the ripening so the card isn't
        // dead-cold; the full warming is reserved for the timer screen.
        DishHero(
            dish = dish,
            progress = 0.25f,
            breathing = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = dish.name,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 22.sp,
                    lineHeight = 26.sp,
                ),
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
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                ),
                color = ParchmentDim,
            )
        }
    }
}

@Composable
private fun EmptyFilterState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "The kitchen has nothing in that register tonight.",
            style = MaterialTheme.typography.bodyMedium,
            color = ParchmentDim,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Loosen a filter to see more.",
            style = MaterialTheme.typography.bodyMedium,
            color = ParchmentDim,
            textAlign = TextAlign.Center,
        )
    }
}

private fun <T> Set<T>.toggle(item: T): Set<T> =
    if (item in this) this - item else this + item
