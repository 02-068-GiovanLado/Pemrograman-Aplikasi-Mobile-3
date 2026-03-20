package com.example.myprofile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myprofile.theme.AppColors
import com.example.myprofile.theme.AppTheme
import com.example.myprofile.ui.EditProfileForm
import com.example.myprofile.ui.InfoItem
import com.example.myprofile.ui.ProfileCard
import com.example.myprofile.ui.ProfileHeader
import com.example.myprofile.ui.SkillChip
import com.example.myprofile.viewmodel.ProfileViewModel

@Composable
fun App(
    viewModel: ProfileViewModel = viewModel { ProfileViewModel() }
) {
    // ── Observe state dari ViewModel ──────────────────────────
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // ── Buat theme helper berdasarkan isDarkMode ──────────────
    val theme = AppTheme(isDark = uiState.isDarkMode)

    // ── MaterialTheme menyesuaikan dark/light ─────────────────
    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) {
            darkColorScheme(
                primary = AppColors.Primary,
                background = AppColors.BackgroundDark,
                surface = AppColors.SurfaceDark
            )
        } else {
            lightColorScheme(
                primary = AppColors.Primary,
                background = AppColors.BackgroundLight,
                surface = AppColors.SurfaceLight
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = theme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                // ── 1. Profile Header + Dark Mode Toggle ──────
                ProfileHeader(
                    name = uiState.name,
                    title = uiState.title,
                    isDarkMode = uiState.isDarkMode,
                    onToggleDarkMode = { viewModel.toggleDarkMode() }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ── 2. Snackbar sukses simpan ─────────────────
                AnimatedVisibility(
                    visible = uiState.saveSuccess,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF43A047)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "✅ Profil berhasil disimpan!",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // ── 3. Bio Card ───────────────────────────────
                ProfileCard(
                    title = "Tentang Saya",
                    icon = Icons.Filled.Person,
                    theme = theme
                ) {
                    Text(
                        text = uiState.bio,
                        fontSize = 14.sp,
                        color = theme.textSecondary,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Justify
                    )
                }

                // ── 4. Edit Profile Form (AnimatedVisibility) ─
                AnimatedVisibility(
                    visible = uiState.isEditMode,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    EditProfileForm(
                        editName = uiState.editName,
                        editBio = uiState.editBio,
                        onNameChange = { viewModel.onEditNameChange(it) },
                        onBioChange = { viewModel.onEditBioChange(it) },
                        onSave = { viewModel.saveProfile() },
                        onCancel = { viewModel.closeEditMode() },
                        theme = theme
                    )
                }

                // ── 5. Kontak Card ────────────────────────────
                ProfileCard(
                    title = "Informasi Kontak",
                    icon = Icons.Filled.Info,
                    theme = theme
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { viewModel.toggleContact() }) {
                            Icon(
                                imageVector = if (uiState.showContact) Icons.Filled.Lock
                                else Icons.Filled.Search,
                                contentDescription = "Toggle",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (uiState.showContact) "Sembunyikan" else "Tampilkan",
                                fontSize = 12.sp
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = uiState.showContact,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut()
                    ) {
                        Column {
                            InfoItem(
                                icon = Icons.Filled.Email,
                                label = "Email",
                                value = uiState.email,
                                iconTint = AppColors.IconEmail,
                                theme = theme
                            )
                            HorizontalDivider(color = theme.background)
                            InfoItem(
                                icon = Icons.Filled.Phone,
                                label = "Phone",
                                value = uiState.phone,
                                iconTint = AppColors.IconPhone,
                                theme = theme
                            )
                            HorizontalDivider(color = theme.background)
                            InfoItem(
                                icon = Icons.Filled.LocationOn,
                                label = "Location",
                                value = uiState.location,
                                iconTint = AppColors.IconLocation,
                                theme = theme
                            )
                            HorizontalDivider(color = theme.background)
                            InfoItem(
                                icon = Icons.Filled.Star,
                                label = "GitHub",
                                value = uiState.github,
                                iconTint = AppColors.IconGithub,
                                theme = theme
                            )
                        }
                    }
                }

                // ── 6. Skills Card ────────────────────────────
                ProfileCard(
                    title = "Skill & Teknologi",
                    icon = Icons.Filled.Star,
                    theme = theme
                ) {
                    uiState.skills.chunked(3).forEach { rowSkills ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowSkills.forEach { skill ->
                                SkillChip(skill = skill, theme = theme)
                            }
                        }
                    }
                }

                // ── 7. Action Buttons ─────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (uiState.isEditMode) viewModel.closeEditMode()
                            else viewModel.openEditMode()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.isEditMode)
                                Color(0xFF757575) else AppColors.Primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (uiState.isEditMode) "Tutup Edit" else "Edit Profil",
                            fontWeight = FontWeight.SemiBold
                        )
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