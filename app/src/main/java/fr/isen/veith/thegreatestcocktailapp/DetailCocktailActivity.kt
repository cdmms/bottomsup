package fr.isen.veith.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.isen.veith.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.veith.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class DetailCocktailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val drinkID = intent.getStringExtra("drinkID") ?: ""

        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text(
                            text = getString(R.string.title_activity_detail_cocktail),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
                        )
                    }
                ) { innerPadding ->
                    DetailCocktailScreen(Modifier.padding(innerPadding), drinkID)
                }
            }
        }
    }
}
