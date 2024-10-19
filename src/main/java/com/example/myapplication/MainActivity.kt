import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.background
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.TextButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import coil.compose.rememberImagePainter
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Scaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationApp()
        }
    }
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") { SkillCinemaScreen(navController) }
        composable("next_screen") { NextScreen(navController) }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SkillCinemaScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SkillCinema",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )

            ClickableText(
                text = AnnotatedString("Пропустить"),
                onClick = {
                    navController.navigate("next_screen")
                },
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        val pagerState = rememberPagerState()

        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(top = 50.dp)

        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(
                        id = when (page) {
                            0 -> R.drawable.film1
                            1 -> R.drawable.film2
                            else -> R.drawable.film3
                        }
                    ),
                    contentDescription = "Картинка $page",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                )

                Text(
                    text = when (page) {
                        0 -> "Узнавай о премьерах"
                        1 -> "Создавай коллекции"
                        else -> "Делись с друзьями"
                    },
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(top = 46.dp, start = 16.dp)
                        .align(Alignment.Start)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .padding(4.dp)
                        .background(
                            if (pagerState.currentPage == index) Color.Black else Color.Gray,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )
            }
        }
    }
}

data class MovieItem(
    val imageUrl: String,
    val rating: Double,
    val title: String,
    val genre: String
)

@Composable
fun NextScreen(navController: NavHostController) {
    val premieres = listOf(
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма"),
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма"),
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма"),
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма"),
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма")
    )

    val popular = listOf(
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма"),
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма"),
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма"),
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма"),
        MovieItem("https://via.placeholder.com/150", 7.8, "Близкие", "драма")
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } 
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) 
        ) {
            item {
                Text(
                    text = "SkillCinema",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }

            item {
                SectionHeader(title = "Премьеры")
            }
            item {
                MovieSection(movies = premieres) { movie ->
                    println("Clicked on: ${movie.title}")
                }
            }

            item {
                SectionHeader(title = "Популярные")
            }
            item {
                MovieSection(movies = popular) { movie ->
                    println("Clicked on: ${movie.title}")
                }
            }

            item {
                SectionHeader(title = "Боевики США")
            }
            item {
                MovieSection(movies = popular) { movie ->
                    println("Clicked on: ${movie.title}")
                }
            }

            item {
                SectionHeader(title = "ТОП-250")
            }
            item {
                MovieSection(movies = popular) { movie ->
                    println("Clicked on: ${movie.title}")
                }
            }

            item {
                SectionHeader(title = "Драма Франции")
            }
            item {
                MovieSection(movies = popular) { movie ->
                    println("Clicked on: ${movie.title}")
                }
            }

            item {
                SectionHeader(title = "Сериалы")
            }
            item {
                MovieSection(movies = popular) { movie ->
                    println("Clicked on: ${movie.title}")
                }
            }
        }
    }
}
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val items = listOf(
            BottomNavigationItem("Home","main_screen", Icons.Rounded.Home),
            BottomNavigationItem("Search",  "search_screen", Icons.Rounded.Search),
            BottomNavigationItem("Profile", "profile_screen", Icons.Rounded.Person)
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector = Icons.Rounded.Home
)


@Composable
            fun SectionHeader(title: String) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(
                        onClick = { },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(text = "ВСЕ", color = Color.Blue, fontSize = 16.sp)
                    }
                }
            }


            @Composable
            fun MovieSection(
                movies: List<MovieItem>,
                onMovieClick: (MovieItem) -> Unit
            ) {


                Column(modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth()) {
                    LazyRow(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(movies) { movie: MovieItem ->
                            MovieCard(movie = movie) {
                                onMovieClick(movie)
                            }
                        }
                    }
                }
            }


            @Composable
            fun MovieCard(movie: MovieItem, onClick: () -> Unit) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onClick() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    androidx.compose.material3.Card(
                        modifier = Modifier
                            .width(111.dp)
                            .height(156.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = movie.rating.toString(),
                                color = Color.White,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .background(Color(0xFF3D3BFF), shape = RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                                fontSize = 10.sp
                            )
                        }
                    }

                    Text(
                        text = movie.title,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = movie.genre,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }


