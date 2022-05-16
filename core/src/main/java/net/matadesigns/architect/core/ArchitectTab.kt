package net.matadesigns.architect.core

import android.os.Bundle
import androidx.compose.ui.graphics.vector.ImageVector

interface ArchitectTab {
    val id: String
    val title: String
    val start: ArchitectScreen
    val icon: ImageVector
    var navState: Bundle
}