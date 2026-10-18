package fr.isen.veith.thegreatestcocktailapp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BottomsUpDarkColors = darkColorScheme(
    primary = Purple40,
    onPrimary = BottomsUpText,
    secondary = PurpleGrey40,
    onSecondary = BottomsUpText,
    tertiary = Pink40,
    onTertiary = Color(0xFF080813),
    background = BottomsUpBackground,
    onBackground = BottomsUpText,
    surface = BottomsUpSurface,
    onSurface = BottomsUpText,
    surfaceVariant = Color(0xFF252333),
    onSurfaceVariant = Color(0xFFB9B6C9)
)

@Composable
fun TheGreatestCocktailAppTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Always use BottomsUp's authored palette; Android dynamic colors are intentionally disabled.
    MaterialTheme(
        colorScheme = BottomsUpDarkColors,
        typography = Typography,
        content = content
    )
}
