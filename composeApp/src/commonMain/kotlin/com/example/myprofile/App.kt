package com.example.myprofile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.model.ProfileData
import com.example.myprofile.theme.AppColors
import com.example.myprofile.ui.InfoItem
import com.example.myprofile.ui.ProfileCard
import com.example.myprofile.ui.ProfileHeader
import com.example.myprofile.ui.SkillChip

@Composable
fun App() {
    // ── Data ──────────────────────────────────────────────────
    val profile = ProfileData(
        name = "Giovan Lado",
        title = "Data Engineer | ITERA",
        bio = "Mahasiswa Teknik Informatika Institut Teknologi Sumatera yang passionate dalam pengembangan aplikasi mobile cross-platform menggunakan Kotlin Multiplatform & Compose Multiplatform.",
        email = "giovan.123140068@student.itera.ac.id",
        phone = "+62 812-3456-7890",
        location = "Bandar Lampung, Indonesia",
        github = "github.com/02-068-GiovanLado",
        skills = listOf("Kotlin", "Compose", "Android", "KMP", "Coroutines", "MVVM", "Git")
    )

    var showContact by remember { mutableStateOf(true) }

    // ── Theme ─────────────────────────────────────────────────
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = AppColors.Primary,
            secondary = AppColors.Secondary,
            background = AppColors.Background
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColors.Background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                // ── 1. Profile Header ─────────────────────────
                ProfileHeader(
                    name = profile.name,
                    title = profile.title
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ── 2. Bio Card ───────────────────────────────
                ProfileCard(
                    title = "Tentang Saya",
                    icon = Icons.Filled.Person
                ) {
                    Text(
                        text = profile.bio,
                        fontSize = 14.sp,
                        color = AppColors.TextSecondary,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Justify
                    )
                }

                // ── 3. Kontak Card ────────────────────────────
                ProfileCard(
                    title = "Informasi Kontak",
                    icon = Icons.Filled.Info
                ) {
                    // Toggle show/hide dengan animasi
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showContact = !showContact }) {
                            Icon(
                                imageVector = if (showContact) Icons.Filled.Lock
                                else Icons.Filled.Search,
                                contentDescription = "Toggle kontak",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (showContact) "Sembunyikan" else "Tampilkan",
                                fontSize = 12.sp
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = showContact,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut()
                    ) {
                        Column {
                            InfoItem(
                                icon = Icons.Filled.Email,
                                label = "Email",
                                value = profile.email,
                                iconTint = AppColors.IconEmail
                            )
                            HorizontalDivider(color = AppColors.Background)
                            InfoItem(
                                icon = Icons.Filled.Phone,
                                label = "Phone",
                                value = profile.phone,
                                iconTint = AppColors.IconPhone
                            )
                            HorizontalDivider(color = AppColors.Background)
                            InfoItem(
                                icon = Icons.Filled.LocationOn,
                                label = "Location",
                                value = profile.location,
                                iconTint = AppColors.IconLocation
                            )
                            HorizontalDivider(color = AppColors.Background)
                            InfoItem(
                                icon = Icons.Filled.Star,
                                label = "GitHub",
                                value = profile.github,
                                iconTint = AppColors.IconGithub
                            )
                        }
                    }
                }

                // ── 4. Skills Card ────────────────────────────
                ProfileCard(
                    title = "Skill & Teknologi",
                    icon = Icons.Filled.Star
                ) {
                    profile.skills.chunked(3).forEach { rowSkills ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowSkills.forEach { skill -> SkillChip(skill = skill) }
                        }
                    }
                }

                // ── 5. Action Buttons ─────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Edit Profil", fontWeight = FontWeight.SemiBold)
                    }

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Bagikan", fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}