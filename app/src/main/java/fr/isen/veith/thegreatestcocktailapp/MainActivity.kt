package fr.isen.veith.thegreatestcocktailapp

import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.veith.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import kotlin.random.Random

private val NightInk = Color(0xFF080813)
private val CardInk = Color(0xFF151421)
private val ElectricPink = Color(0xFFFF168B)
private val Ultraviolet = Color(0xFF7B61FF)
private val VegasLime = Color(0xFFB8FF3B)
private val MutedText = Color(0xFFB9B6C9)
private val LineColor = Color(0xFF363348)

private data class GamePrompt(
    val title: String,
    val prompt: String,
    val category: String,
    val tag: String
)

private val promptDeck = listOf(
    GamePrompt("Main character", "Give a 10-second acceptance speech for winning ‘Best Night Out’.", "DARE", "QUICK DARE"),
    GamePrompt("The toast", "Make a dramatic toast to the group using your best movie-trailer voice.", "DARE", "QUICK DARE"),
    GamePrompt("Vegas headline", "Invent a ridiculous headline describing tonight’s adventure.", "VEGAS", "VEGAS DECK"),
    GamePrompt("Sip or dare", "Take one small sip of your beverage, or choose water or a no-alcohol alternative.", "DRINK", "SIP OR DARE"),
    GamePrompt("Cheers remix", "Make up a two-line cheers rhyme. A sip is optional; any beverage counts.", "DRINK", "SIP OR DARE"),
    GamePrompt("Hydration flex", "Lead a dramatic water toast. Invite the group, but nobody has to join.", "DRINK", "SIP OR DARE"),
    GamePrompt("No-pressure pick", "Choose one: a small sip of your beverage, a water break, or a goofy question.", "DRINK", "SIP OR DARE"),
    GamePrompt("Truth time", "What is your most harmless but hilariously unpopular opinion?", "TRUTH", "TRUTH OR DARE"),
    GamePrompt("Red carpet", "Walk an imaginary red carpet and pose for three different paparazzi photos.", "DARE", "VEGAS DECK"),
    GamePrompt("Most likely to…", "Point to who is most likely to become friends with a stranger in a queue.", "VOTE", "MOST LIKELY TO"),
    GamePrompt("Vegas alter ego", "Give yourself a Vegas nickname and explain its origin.", "VEGAS", "VEGAS DECK"),
    GamePrompt("The impression", "Do a friendly impression of someone famous; the group guesses who it is.", "DARE", "QUICK DARE"),
    GamePrompt("Plot twist", "Tell a two-sentence story about tonight. The group adds one unexpected twist.", "WILD", "WILD CARD"),
    GamePrompt("Truth time", "What song would play when you make a grand entrance?", "TRUTH", "TRUTH OR DARE"),
    GamePrompt("Cheers or choice", "Choose a cheers, a water break, or a no-alcohol alternative—and give it a name.", "WILD", "WILD CARD"),
    GamePrompt("The hype person", "Give the player on your left an over-the-top, 5-second introduction.", "DARE", "QUICK DARE"),
    GamePrompt("Most likely to…", "Vote for who would be the best tour guide for a chaotic group trip.", "VOTE", "MOST LIKELY TO"),
    GamePrompt("Photo caption", "Invent a caption for the group’s imaginary album cover.", "VEGAS", "VEGAS DECK"),
    GamePrompt("Plot twist", "Swap roles with another willing player and introduce yourself as them—in a kind way.", "WILD", "WILD CARD"),
    GamePrompt("Truth time", "What small thing instantly makes a night out better for you?", "TRUTH", "TRUTH OR DARE"),
    GamePrompt("Dance break", "Make up a signature celebration move. Everyone who wants to can copy it.", "DARE", "QUICK DARE"),
    GamePrompt("The review", "Rate this imaginary party from one to ten, then deliver a dramatic review.", "VEGAS", "VEGAS DECK"),
    GamePrompt("Plot armor", "Invent the most absurd reason you would survive a reality-show finale.", "WILD", "WILD CARD"),
    GamePrompt("The side quest", "Give the group a harmless 20-second side quest everyone can try or skip.", "DARE", "QUICK DARE"),
    GamePrompt("Awards night", "Give someone a made-up award for a funny, kind reason.", "DARE", "QUICK DARE"),
    GamePrompt("Signature move", "Name your signature dance move and demonstrate it if you want.", "DARE", "QUICK DARE"),
    GamePrompt("Reverse review", "Review your own outfit like a brutally overdramatic fashion critic.", "VEGAS", "VEGAS DECK"),
    GamePrompt("The trailer", "Pitch tonight as a movie in one sentence. The group names the genre.", "WILD", "WILD CARD"),
    GamePrompt("Tiny flex", "Share a strangely specific skill you are proud of.", "TRUTH", "TRUTH OR DARE"),
    GamePrompt("Unexpected talent", "Tell the group a talent you wish you had, then attempt a 5-second demo.", "DARE", "QUICK DARE"),
    GamePrompt("Group slogan", "Create a slogan for this crew in ten seconds.", "VEGAS", "VEGAS DECK"),
    GamePrompt("The prediction", "Predict one harmless, funny thing this group will be talking about next week.", "VOTE", "MOST LIKELY TO"),
    GamePrompt("Never have I ever", "Never have I ever rehearsed a conversation in the mirror. Share the story if you have.", "NEVER", "NEVER HAVE I EVER"),
    GamePrompt("Never have I ever", "Never have I ever sent a message to the wrong chat. Tell the PG-rated version if you have.", "NEVER", "NEVER HAVE I EVER"),
    GamePrompt("Never have I ever", "Never have I ever pretended to know a song's lyrics. Bonus points for the fake lyrics.", "NEVER", "NEVER HAVE I EVER"),
    GamePrompt("Never have I ever", "Never have I ever forgotten someone's name seconds after hearing it.", "NEVER", "NEVER HAVE I EVER"),
    GamePrompt("Never have I ever", "Never have I ever laughed at a moment when I absolutely shouldn't have.", "NEVER", "NEVER HAVE I EVER"),
    GamePrompt("Never have I ever", "Never have I ever said 'I'm nearly there' while still getting ready.", "NEVER", "NEVER HAVE I EVER"),
    GamePrompt("Never have I ever", "Never have I ever practiced an argument in the shower and won.", "NEVER", "NEVER HAVE I EVER"),
    GamePrompt("Never have I ever", "Never have I ever taken more than ten photos to get one I liked.", "NEVER", "NEVER HAVE I EVER"),
    GamePrompt("Would you rather", "Have a personal theme song play whenever you enter a room, or have a laugh track follow your jokes?", "CHOICE", "WOULD YOU RATHER"),
    GamePrompt("Would you rather", "Be able to pause time for ten seconds or rewind one awkward sentence per day?", "CHOICE", "WOULD YOU RATHER"),
    GamePrompt("Would you rather", "Have your search history read aloud or your last ten emojis appear over your head?", "CHOICE", "WOULD YOU RATHER"),
    GamePrompt("Would you rather", "Always arrive twenty minutes early or always arrive five minutes late? Defend your choice.", "CHOICE", "WOULD YOU RATHER"),
    GamePrompt("Would you rather", "Have a wildly dramatic narrator describe your day or a sitcom audience react to it?", "CHOICE", "WOULD YOU RATHER"),
    GamePrompt("Would you rather", "Only communicate in movie quotes for a day or sing every text message?", "CHOICE", "WOULD YOU RATHER"),
    GamePrompt("Hot seat", "What is a tiny hill you will absolutely die on?", "HOTSEAT", "HOT SEAT"),
    GamePrompt("Hot seat", "What is your most irrational but harmless pet peeve?", "HOTSEAT", "HOT SEAT"),
    GamePrompt("Hot seat", "Which fictional character would be your worst roommate, and why?", "HOTSEAT", "HOT SEAT"),
    GamePrompt("Hot seat", "What is a skill everyone seems to have that you never learned?", "HOTSEAT", "HOT SEAT"),
    GamePrompt("Hot seat", "What is your most controversial food opinion? The crew may challenge it.", "HOTSEAT", "HOT SEAT"),
    GamePrompt("Hot seat", "What would your friends put on your personal warning label? Keep it kind.", "HOTSEAT", "HOT SEAT"),
    GamePrompt("Story chain", "Start a story with: 'The elevator opened, and everyone was wearing the same hat…' The next player adds one sentence.", "STORY", "STORY CHAIN"),
    GamePrompt("Story chain", "Start a story with: 'We knew the night was unusual when the pigeon spoke…' Pass it around one sentence at a time.", "STORY", "STORY CHAIN"),
    GamePrompt("Story chain", "Add one sentence to a group story that must include a missing pizza and a royal crown.", "STORY", "STORY CHAIN"),
    GamePrompt("Story chain", "Tell a story one word at a time. Stop after the story gets a ridiculous plot twist.", "STORY", "STORY CHAIN"),
    GamePrompt("Story chain", "The first person names a location; each player adds one sentence that makes the trip stranger.", "STORY", "STORY CHAIN"),
    GamePrompt("Story chain", "Invent a heist movie where the crew steals something completely useless.", "STORY", "STORY CHAIN"),
    GamePrompt("Lightning round", "In three seconds, name three things you would bring to a deserted island.", "LIGHTNING", "LIGHTNING ROUND"),
    GamePrompt("Lightning round", "Rapid fire: name three fictional characters you would invite to dinner.", "LIGHTNING", "LIGHTNING ROUND"),
    GamePrompt("Lightning round", "In five seconds, name three songs that could rescue a boring party.", "LIGHTNING", "LIGHTNING ROUND"),
    GamePrompt("Lightning round", "Name three things that are overrated. You have five seconds.", "LIGHTNING", "LIGHTNING ROUND"),
    GamePrompt("Lightning round", "Say three compliments for the group without repeating a word.", "LIGHTNING", "LIGHTNING ROUND"),
    GamePrompt("Lightning round", "Name three imaginary jobs you would be excellent at.", "LIGHTNING", "LIGHTNING ROUND"),
)

private val chaosTwists = listOf(
    "SPEED ROUND: the next answer must be given in five seconds.",
    "MOVIE TRAILER: say your next answer like a dramatic narrator.",
    "CREW HYPE: give another player a genuine, specific compliment.",
    "PLOT TWIST: add one absurd but harmless detail to the next story.",
    "SOUND EFFECTS: the group supplies sound effects for the next answer."
)

private enum class AppTab(val title: String, val symbol: String) {
    Play("Play", "🎲"), Dares("Dares", "⚡"), Vegas("Vegas", "✦"), Saved("Saved", "♡")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                BottomsUpApp()
            }
        }
    }
}

@Composable
private fun BottomsUpApp() {
    var selectedTab by rememberSaveable { mutableStateOf(AppTab.Play) }
    var selectedMode by rememberSaveable { mutableStateOf("Quick Dare") }
    var gameStarted by rememberSaveable { mutableStateOf(false) }
    var selectedPromptText by rememberSaveable { mutableStateOf<String?>(null) }
    var promptIndex by rememberSaveable { mutableIntStateOf(0) }
    var completedCount by rememberSaveable { mutableIntStateOf(0) }
    var passedCount by rememberSaveable { mutableIntStateOf(0) }
    var playerInput by rememberSaveable { mutableStateOf("") }
    val players = remember { mutableStateListOf("You", "Friend") }
    val savedPromptIndices = remember { mutableStateListOf<Int>() }
    val context = LocalContext.current
    val gamePreferences = remember(context) { context.getSharedPreferences("bottomsup_game", Context.MODE_PRIVATE) }
    val customPrompts = remember(gamePreferences) {
        mutableStateListOf<GamePrompt>().apply {
            gamePreferences.getStringSet("custom_prompts", emptySet()).orEmpty().sorted().forEach { savedText ->
                add(GamePrompt("Crew custom", savedText, "DARE", "CREW CUSTOM"))
            }
        }
    }
    val allPrompts = promptDeck + customPrompts.toList()

    val filteredPrompts = remember(selectedMode, customPrompts.size) {
        when (selectedMode) {
            "Truth or Dare" -> allPrompts.filter { it.category == "TRUTH" || it.category == "DARE" }
            "Sip or Dare" -> allPrompts.filter { it.category == "DRINK" }
            "Vegas Deck" -> allPrompts.filter { it.tag == "VEGAS DECK" || it.category == "VEGAS" }
            "Most Likely To" -> allPrompts.filter { it.category == "VOTE" }
            "Wild Card" -> allPrompts.filter { it.category == "WILD" }
            "Never Have I Ever" -> allPrompts.filter { it.category == "NEVER" }
            "Would You Rather" -> allPrompts.filter { it.category == "CHOICE" }
            "Hot Seat" -> allPrompts.filter { it.category == "HOTSEAT" }
            "Story Chain" -> allPrompts.filter { it.category == "STORY" }
            "Lightning Round" -> allPrompts.filter { it.category == "LIGHTNING" }
            else -> allPrompts
        }.ifEmpty { allPrompts }
    }
    val currentPrompt = allPrompts.firstOrNull { it.prompt == selectedPromptText }
        ?: filteredPrompts[promptIndex.mod(filteredPrompts.size)]

    fun pickNext() {
        selectedPromptText = null
        promptIndex = if (filteredPrompts.size > 1) {
            (promptIndex + Random.nextInt(1, filteredPrompts.size)) % filteredPrompts.size
        } else 0
    }

    Scaffold(
            containerColor = NightInk,
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().background(NightInk).padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Bottoms", fontSize = 27.sp, fontWeight = FontWeight.Black, color = Color.White)
                            Text("Up", fontSize = 27.sp, fontWeight = FontWeight.Black, color = ElectricPink)
                        }
                        Text("VEGAS PARTY GAME", fontSize = 10.sp, letterSpacing = 2.sp, color = MutedText)
                    }
                    Box(Modifier.size(44.dp).clip(RoundedCornerShape(15.dp)).background(CardInk), contentAlignment = Alignment.Center) {
                        Text("↗", color = VegasLime, fontSize = 27.sp, fontWeight = FontWeight.Black)
                    }
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF11101B), contentColor = Color.White) {
                    AppTab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = { Text(tab.symbol, fontSize = 19.sp) },
                            label = { Text(tab.title, fontSize = 11.sp) },
                            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                selectedIconColor = ElectricPink,
                                selectedTextColor = ElectricPink,
                                indicatorColor = ElectricPink.copy(alpha = 0.12f),
                                unselectedIconColor = MutedText,
                                unselectedTextColor = MutedText
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            when (selectedTab) {
                AppTab.Play -> PlayScreen(
                    modifier = Modifier.padding(innerPadding),
                    selectedMode = selectedMode,
                    onModeChange = { selectedMode = it; selectedPromptText = null; promptIndex = 0; gameStarted = false },
                    gameStarted = gameStarted,
                    onStart = { gameStarted = true; pickNext() },
                    prompt = currentPrompt,
                    player = players[(completedCount + passedCount).mod(players.size.coerceAtLeast(1))],
                    completedCount = completedCount,
                    passedCount = passedCount,
                    onDone = { completedCount++; pickNext() },
                    onSwap = { pickNext() },
                    onPass = { passedCount++; pickNext() },
                    onExit = { gameStarted = false },
                    onSave = {
                        val globalIndex = allPrompts.indexOf(currentPrompt)
                        if (globalIndex !in savedPromptIndices) savedPromptIndices.add(globalIndex)
                    },
                    isSaved = allPrompts.indexOf(currentPrompt) in savedPromptIndices,
                    players = players,
                    playerInput = playerInput,
                    onPlayerInput = { playerInput = it },
                    onAddPlayer = {
                        val cleaned = playerInput.trim()
                        if (cleaned.isNotEmpty() && cleaned !in players && players.size < 12) players.add(cleaned)
                        playerInput = ""
                    }
                )
                AppTab.Dares -> DaresScreen(
                    modifier = Modifier.padding(innerPadding),
                    prompts = allPrompts,
                    savedIndices = savedPromptIndices,
                    onToggleSave = { idx -> if (idx in savedPromptIndices) savedPromptIndices.remove(idx) else savedPromptIndices.add(idx) },
                    onAddPrompt = { rawText ->
                        val cleaned = rawText.trim()
                        if (cleaned.isNotEmpty() && customPrompts.size < 120 && allPrompts.none { it.prompt.equals(cleaned, ignoreCase = true) }) {
                            customPrompts.add(GamePrompt("Crew custom", cleaned, "DARE", "CREW CUSTOM"))
                            gamePreferences.edit().putStringSet("custom_prompts", customPrompts.map { it.prompt }.toSet()).apply()
                        }
                    }
                )
                AppTab.Vegas -> VegasScreen(Modifier.padding(innerPadding), onPlayVegas = { selectedMode = "Vegas Deck"; gameStarted = false; selectedTab = AppTab.Play })
                AppTab.Saved -> SavedScreen(
                    modifier = Modifier.padding(innerPadding),
                    prompts = allPrompts,
                    indices = savedPromptIndices,
                    onRemove = { savedPromptIndices.remove(it) },
                    onPlay = { idx ->
                        val prompt = allPrompts[idx]
                        selectedMode = when (prompt.tag) {
                            "VEGAS DECK" -> "Vegas Deck"
                            "TRUTH OR DARE" -> "Truth or Dare"
                            "MOST LIKELY TO" -> "Most Likely To"
                            "WILD CARD" -> "Wild Card"
                            "NEVER HAVE I EVER" -> "Never Have I Ever"
                            "WOULD YOU RATHER" -> "Would You Rather"
                            "HOT SEAT" -> "Hot Seat"
                            "STORY CHAIN" -> "Story Chain"
                            "LIGHTNING ROUND" -> "Lightning Round"
                            else -> "Quick Dare"
                        }
                        promptIndex = 0
                        selectedPromptText = prompt.prompt
                        gameStarted = true
                        selectedTab = AppTab.Play
                    }
                )
            }
        }
}

@Composable
private fun PlayScreen(
    modifier: Modifier,
    selectedMode: String,
    onModeChange: (String) -> Unit,
    gameStarted: Boolean,
    onStart: () -> Unit,
    prompt: GamePrompt,
    player: String,
    completedCount: Int,
    passedCount: Int,
    onDone: () -> Unit,
    onSwap: () -> Unit,
    onPass: () -> Unit,
    onExit: () -> Unit,
    onSave: () -> Unit,
    isSaved: Boolean,
    players: List<String>,
    playerInput: String,
    onPlayerInput: (String) -> Unit,
    onAddPlayer: () -> Unit
) {
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 18.dp, vertical = 8.dp)) {
        Text("LESS SCROLLING. MORE STORY.", color = VegasLime, fontSize = 10.sp, letterSpacing = 1.7.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(if (gameStarted) "YOUR TURN." else "SPIN. DARE.\nREPEAT.", color = Color.White, fontSize = 38.sp, lineHeight = 40.sp, fontWeight = FontWeight.Black)
        Spacer(Modifier.height(8.dp))
        Text(if (gameStarted) "Pass the phone. Let the next moment happen." else "The party game built for big Vegas energy.", color = MutedText, fontSize = 15.sp)
        Spacer(Modifier.height(18.dp))

        if (!gameStarted) {
            Text("CHOOSE YOUR GAME", color = MutedText, fontSize = 11.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Quick Dare", "Sip or Dare", "Truth or Dare", "Never Have I Ever", "Would You Rather", "Hot Seat", "Story Chain", "Lightning Round", "Vegas Deck", "Most Likely To", "Wild Card").forEach { mode ->
                    FilterChip(
                        selected = selectedMode == mode,
                        onClick = { onModeChange(mode) },
                        label = { Text(mode) },
                        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricPink.copy(alpha = 0.18f),
                            selectedLabelColor = Color.White,
                            labelColor = MutedText
                        )
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Card(colors = CardDefaults.cardColors(containerColor = CardInk), shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp)) {
                    Text("✦  TONIGHT'S ENERGY", color = ElectricPink, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(selectedMode.uppercase(), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                    Spacer(Modifier.height(6.dp))
                    Text("Fast rounds. Funny prompts. Your crew sets the vibe.", color = MutedText, fontSize = 14.sp)
                    Spacer(Modifier.height(18.dp))
                    Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(54.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = ElectricPink, contentColor = Color.White)) {
                        Text("START THE ROUND  →", fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    }
                }
            }
            Spacer(Modifier.height(22.dp))
            Text("ADD YOUR CREW", color = MutedText, fontSize = 11.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = playerInput,
                    onValueChange = onPlayerInput,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("Player name", color = MutedText) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = ElectricPink, unfocusedBorderColor = LineColor, cursorColor = ElectricPink)
                )
                Button(onClick = onAddPlayer, colors = ButtonDefaults.buttonColors(containerColor = Ultraviolet), shape = RoundedCornerShape(14.dp)) { Text("ADD") }
            }
            Spacer(Modifier.height(10.dp))
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                players.forEach { name ->
                    Surface(color = CardInk, shape = CircleShape) { Text(name, Modifier.padding(horizontal = 13.dp, vertical = 8.dp), fontSize = 12.sp, color = Color.White) }
                }
            }
        } else {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("UP NEXT: ${player.uppercase()}", color = VegasLime, fontSize = 11.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = onExit) { Text("END GAME", color = MutedText, fontSize = 11.sp) }
            }
            Spacer(Modifier.height(8.dp))
            Card(colors = CardDefaults.cardColors(containerColor = CardInk), shape = RoundedCornerShape(26.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✦  ${prompt.tag}  ✦", color = ElectricPink, fontSize = 11.sp, letterSpacing = 1.4.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(24.dp))
                    Box(Modifier.size(72.dp).clip(RoundedCornerShape(22.dp)).background(Ultraviolet.copy(alpha = 0.18f)), contentAlignment = Alignment.Center) {
                        Text(if (prompt.category == "TRUTH") "?" else if (prompt.category == "VOTE") "👀" else "⚡", color = VegasLime, fontSize = 37.sp, fontWeight = FontWeight.Black)
                    }
                    Spacer(Modifier.height(20.dp))
                    Text(prompt.title, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(12.dp))
                    Text(prompt.prompt, color = Color(0xFFF0EDF9), fontSize = 19.sp, lineHeight = 27.sp, textAlign = TextAlign.Center)
                    if ((completedCount + passedCount) > 0 && (completedCount + passedCount) % 4 == 0) {
                        Spacer(Modifier.height(14.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Ultraviolet.copy(alpha = 0.20f)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("⚡ CHAOS ROUND", color = VegasLime, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.4.sp)
                                Spacer(Modifier.height(5.dp))
                                Text(chaosTwists[((completedCount + passedCount) / 4 - 1).mod(chaosTwists.size)], color = Color.White, textAlign = TextAlign.Center, fontSize = 14.sp, lineHeight = 20.sp)
                            }
                        }
                    }
                    Spacer(Modifier.height(18.dp))
                    Surface(color = Ultraviolet.copy(alpha = 0.16f), shape = CircleShape) {
                        Text("YOUR GAME • YOUR RULES", Modifier.padding(horizontal = 13.dp, vertical = 7.dp), color = Color(0xFFC7B9FF), fontSize = 10.sp, letterSpacing = 1.sp)
                    }
                    Spacer(Modifier.height(22.dp))
                    Button(onClick = onDone, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(containerColor = ElectricPink)) {
                        Text("DONE — NEXT PLAYER", fontWeight = FontWeight.Black, letterSpacing = 0.5.sp)
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = onSwap, modifier = Modifier.weight(1f), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = Ultraviolet.copy(alpha = 0.22f), contentColor = Color.White)) { Text("↻ SWAP") }
                        Button(onClick = onPass, modifier = Modifier.weight(1f), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF252333), contentColor = Color.White)) { Text("PASS") }
                    }
                    TextButton(onClick = onSave) { Text(if (isSaved) "♥  SAVED TO FAVORITES" else "♡  SAVE THIS DARE", color = if (isSaved) ElectricPink else MutedText) }
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatPill("COMPLETED", completedCount.toString(), VegasLime)
                StatPill("PASSED", passedCount.toString(), MutedText)
                StatPill("PLAYERS", players.size.toString(), ElectricPink)
            }
        }
        Spacer(Modifier.height(20.dp))
        Text("21+ • Drinking is optional. Passing is always okay. Never drink and drive.", color = MutedText, fontSize = 11.sp, lineHeight = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
    }
}

@Composable
private fun StatPill(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontSize = 23.sp, fontWeight = FontWeight.Black)
        Text(label, color = MutedText, fontSize = 9.sp, letterSpacing = 1.sp)
    }
}

@Composable
private fun DaresScreen(
    modifier: Modifier,
    prompts: List<GamePrompt>,
    savedIndices: List<Int>,
    onToggleSave: (Int) -> Unit,
    onAddPrompt: (String) -> Unit
) {
    var customPromptText by rememberSaveable { mutableStateOf("") }
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        Text("THE DECK", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black)
        Text("Pick a card or make one that only your crew would think of.", color = MutedText, fontSize = 14.sp)
        Spacer(Modifier.height(16.dp))
        Card(colors = CardDefaults.cardColors(containerColor = CardInk), shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("MAKE IT YOURS", color = VegasLime, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.4.sp)
                Spacer(Modifier.height(6.dp))
                Text("Add a crew-only prompt", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = customPromptText,
                    onValueChange = { if (it.length <= 220) customPromptText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Write a funny, kind challenge…", color = MutedText) },
                    supportingText = { Text("${customPromptText.length}/220 · Saved on this device", color = MutedText) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = ElectricPink, unfocusedBorderColor = LineColor, cursorColor = ElectricPink)
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { val cleaned = customPromptText.trim(); if (cleaned.isNotEmpty()) { onAddPrompt(cleaned); customPromptText = "" } },
                    enabled = customPromptText.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricPink)
                ) { Text("＋ ADD TO OUR DECK", fontWeight = FontWeight.Black) }
                Spacer(Modifier.height(4.dp))
                Text("Custom dares are stored locally. Keep it fun, kind, and optional.", color = MutedText, fontSize = 11.sp, lineHeight = 16.sp)
            }
        }
        Spacer(Modifier.height(18.dp))
        prompts.forEachIndexed { index, prompt ->
            PromptListCard(prompt, saved = index in savedIndices, onSave = { onToggleSave(index) })
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun SavedScreen(modifier: Modifier, prompts: List<GamePrompt>, indices: List<Int>, onRemove: (Int) -> Unit, onPlay: (Int) -> Unit) {
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        Text("YOUR SAVED DECK", color = Color.White, fontSize = 31.sp, fontWeight = FontWeight.Black)
        Spacer(Modifier.height(6.dp))
        Text("Keep your favorites ready for the next round.", color = MutedText)
        Spacer(Modifier.height(16.dp))
        if (indices.isEmpty()) {
            EmptyState("♡", "Nothing saved yet", "Save a prompt from the Dares list or during a round.")
        } else {
            indices.toList().forEach { index ->
                if (index in prompts.indices) {
                    PromptListCard(prompts[index], saved = true, onSave = { onRemove(index) }, onPlay = { onPlay(index) })
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun PromptListCard(prompt: GamePrompt, saved: Boolean, onSave: () -> Unit, onPlay: (() -> Unit)? = null) {
    Card(colors = CardDefaults.cardColors(containerColor = CardInk), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(15.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(prompt.tag, color = ElectricPink, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                TextButton(onClick = onSave) { Text(if (saved) "♥ SAVED" else "♡ SAVE", color = if (saved) ElectricPink else MutedText) }
            }
            Text(prompt.title, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(prompt.prompt, color = MutedText, fontSize = 14.sp, lineHeight = 20.sp)
            if (onPlay != null) {
                Spacer(Modifier.height(6.dp))
                TextButton(onClick = onPlay) { Text("PLAY THIS TYPE  →", color = VegasLime) }
            }
        }
    }
}

@Composable
private fun VegasScreen(modifier: Modifier, onPlayVegas: () -> Unit) {
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        Text("VEGAS MODE", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black)
        Text("A little extra neon for your night.", color = MutedText, fontSize = 14.sp)
        Spacer(Modifier.height(16.dp))
        Card(colors = CardDefaults.cardColors(containerColor = CardInk), shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.padding(22.dp)) {
                Text("✦", color = VegasLime, fontSize = 36.sp)
                Text("WHAT HAPPENS IN VEGAS…", color = ElectricPink, fontSize = 12.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                Text("Your crew. Your dares. Your story.", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Black, lineHeight = 30.sp)
                Spacer(Modifier.height(8.dp))
                Text("Unlock the Vegas-themed prompt deck now. Venue discovery and night-out tools can be added here later—the game stays front and center.", color = MutedText, fontSize = 14.sp, lineHeight = 21.sp)
                Spacer(Modifier.height(18.dp))
                Button(onClick = onPlayVegas, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(containerColor = ElectricPink)) { Text("PLAY VEGAS DECK  →", fontWeight = FontWeight.Black) }
            }
        }
        Spacer(Modifier.height(16.dp))
        listOf("THE STRIP ENERGY" to "Big entrance, main-character moments, ridiculous headlines.", "DOWNTOWN CHAOS" to "Wild-card stories, group votes, and unexpected plot twists.", "CREW CLASSICS" to "Friendly dares and truths that work at any table.").forEach { (title, body) ->
            Card(colors = CardDefaults.cardColors(containerColor = CardInk), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(title, color = VegasLime, fontSize = 11.sp, letterSpacing = 1.2.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(5.dp))
                    Text(body, color = Color.White, fontSize = 14.sp, lineHeight = 20.sp)
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun EmptyState(symbol: String, title: String, subtitle: String) {
    Card(colors = CardDefaults.cardColors(containerColor = CardInk), shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(symbol, fontSize = 35.sp, color = ElectricPink)
            Spacer(Modifier.height(10.dp))
            Text(title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(subtitle, color = MutedText, textAlign = TextAlign.Center, fontSize = 14.sp)
        }
    }
}
