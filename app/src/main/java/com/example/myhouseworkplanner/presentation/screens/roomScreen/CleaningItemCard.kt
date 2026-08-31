package com.example.myhouseworkplanner.presentation.screens.roomScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhouseworkplanner.domain.model.CleanStatus
import com.example.myhouseworkplanner.domain.model.CleaningItem
import com.example.myhouseworkplanner.domain.model.IntervalUnit

@Composable
fun CleaningItemCard(
    item: CleaningItem,
    onCleanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (statusColor, statusText) = when (item.status) {
        CleanStatus.CLEAN -> Pair(Color(0xFF4CAF50), "Чисто")
        CleanStatus.DUE_SOON -> Pair(Color(0xFFFFB300), "Скоро пора")
        CleanStatus.NEEDS_CLEANING -> Pair(Color(0xFFFF9800), "Пора убрать")
        CleanStatus.OVERDUE -> Pair(Color(0xFFE53935), "Просрочено")
    }

    val intervalText = when (item.intervalUnit) {
        IntervalUnit.HOURS -> "Раз в ${item.intervalValue} ч."
        IntervalUnit.DAYS -> "Раз в ${item.intervalValue} дн."
        IntervalUnit.WEEKS -> "Раз в ${item.intervalValue} нед."
        IntervalUnit.MONTHS -> "Раз в ${item.intervalValue} мес."
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, statusColor.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(
            containerColor = statusColor.copy(alpha = 0.08f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = item.icon.ifEmpty { "🧽" }, fontSize = 28.sp)
                Column {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$intervalText • $statusText",
                        style = MaterialTheme.typography.bodySmall,
                        color = statusColor
                    )
                }
            }

            FilledIconButton(
                onClick = onCleanClick,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = statusColor
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Отметить уборку",
                    tint = Color.White
                )
            }
        }
    }
}