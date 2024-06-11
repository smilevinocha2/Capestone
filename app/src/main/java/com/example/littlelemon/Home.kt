package com.example.littlelemon

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import coil.compose.rememberImagePainter
import com.example.littlelemon.components.Header
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

val KarlaRegular = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal)
)
val MarkazyRegular = FontFamily(
    Font(R.font.markazi_regular, FontWeight.Normal)
)

@Composable
fun Home(navController: NavHostController){

    val activity = LocalContext.current as Activity
    val database: AppDatabase by lazy {
        Room.databaseBuilder(activity.baseContext, AppDatabase::class.java, "database.db").build()
    }
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(initial = emptyList())
    var menuItems = databaseMenuItems

    //Search variables
    var searchPhrase by remember { mutableStateOf( "") }
    if( searchPhrase.isNotEmpty() ){
        menuItems = menuItems.filter { it.title.lowercase().contains( searchPhrase.lowercase() ) }
    }

    //Group variables categories
    val mains = "mains"
    val desserts = "desserts"
    val starters = "starters"
    var selectedCategory by remember { mutableStateOf( "") }
    if( selectedCategory.isNotEmpty() ){
        menuItems = menuItems.filter { it.category.lowercase() == selectedCategory.lowercase() }
    }

    Scaffold(
        topBar = {
            HeaderHome(navController = navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            HeroSection(searchPhrase) { searchPhrase = it }
            Column (
                modifier = Modifier.padding(top = 20.dp)
            )
            {
                Text(
                    text = ("Order For Delivery!").uppercase(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = KarlaRegular,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 20.dp)
                )
                Row (
                    Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    FilledTonalButton(
                        onClick = {
                            selectedCategory = if( selectedCategory == mains) "" else mains
                                  },
                        colors = ButtonDefaults
                            .buttonColors(
                                if(selectedCategory == mains ) colorResource(R.color.secondary1) else colorResource(R.color.secondary3)),
                    ) {
                        Text(
                            mains,
                            color = colorResource(id = R.color.primary1),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    FilledTonalButton(
                        onClick = { selectedCategory = if( selectedCategory == desserts) "" else desserts },
                        colors = ButtonDefaults
                            .buttonColors(
                                containerColor = if(selectedCategory == desserts ) colorResource(R.color.secondary1) else colorResource(R.color.secondary3)),
                    ) {
                        Text(
                            desserts,
                            color = colorResource(id = R.color.primary1 ),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    FilledTonalButton(
                        onClick = { selectedCategory = if( selectedCategory == starters) "" else starters },
                        colors = ButtonDefaults
                            .buttonColors(if(selectedCategory == starters ) colorResource(R.color.secondary1) else colorResource(R.color.secondary3)),
                    ) {
                        Text(
                            starters,
                            color = colorResource(id = R.color.primary1),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Divider(
                    color = colorResource(R.color.primary1),
                    thickness = 1.dp,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
            }
            MenuItems(menuItems)
        }
    }

}

@Composable
fun MenuItem(item: MenuItemRoom){
    Row (
        Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Column (
            Modifier.fillMaxWidth(0.7f)
        ){
            Text(
                text = item.title,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                fontFamily = KarlaRegular,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Text(
                text = if(item.description.length>45) item.description.substring(0, 45).plus("...") else item.description,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontFamily = KarlaRegular,
                color = colorResource(R.color.secondary4),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Text(
                text = "$"+item.price,
                fontSize = 22.sp,
                textAlign = TextAlign.Start,
                fontFamily = MarkazyRegular,
                color = colorResource(R.color.primary1),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
        Image(
            //painter = painterResource(id = R.drawable.hero_image),
            painter = rememberImagePainter(data = item.image),
            contentDescription = "hero image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(10.dp)

        )
    }
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    )
}

@Composable
fun MenuItems(items: List<MenuItemRoom>){
    Column {
        items.forEach {
            MenuItem(it)
        }
    }
}

@Composable
fun HeaderHome(navController: NavHostController){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(top=15.dp, bottom = 30.dp)
    )
    {
        Image(
            painter = painterResource(id = R.drawable.logo_header),
            contentDescription = "Logo Image",
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(top = 10.dp)
        )

        IconButton( onClick = {
            navController.navigate(Profile.route)
        })
        {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null
            )
        }
    }
}

@Composable
fun HeroSection(search: String, onSearchChanged: ((String) -> Unit) = {}){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.primary1))
            .padding(top = 20.dp)
        ,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = "Little Lemon",
            fontSize = 56.sp,
            textAlign = TextAlign.Start,
            fontFamily = MarkazyRegular,
            color = colorResource(R.color.primary2),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(start = 20.dp)
        )
        Row (
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column (
                modifier = Modifier.fillMaxWidth(0.65f)
            ){
                Text(
                    text = "Chicago",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = MarkazyRegular,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 20.dp)
                )
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = MarkazyRegular,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 20.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "hero image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

        }

        TextField(
            value = search,
            onValueChange = onSearchChanged,
            label = { Text("Enter Search Phrase") },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    Home(rememberNavController())
}