package kz.kasip.works.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.kasip.data.entities.Work
import kz.kasip.designcore.theme.CardBackground

@Composable
fun WorkCard(
    work: Work,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        colors = CardDefaults.elevatedCardColors()
            .copy(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(
                text = work.name,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = work.description)
            Text(
                modifier = Modifier.align(Alignment.End),
                text = work.price,
                fontSize = 18.sp
            )
        }
    }

}