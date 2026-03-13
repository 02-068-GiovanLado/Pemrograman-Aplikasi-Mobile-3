package com.example.myprofile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors

/**
 * COMPOSABLE 3 — ProfileCard
 * Card section generik dengan header (icon + judul) dan
 * slot konten yang fleksibel menggunakan trailing lambda.
 *
 * Penggunaan:
 *   ProfileCard(title = "Bio", icon = Icons.Filled.Person) {
 *       Text("isi konten di sini")
 *   }
 */
@Composable
fun ProfileCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ── Section Header ────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = AppColors.Primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryDark
                )
            }

            HorizontalDivider(color = AppColors.Divider, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // ── Slot Konten ───────────────────────────────────
            content()
        }
    }
}