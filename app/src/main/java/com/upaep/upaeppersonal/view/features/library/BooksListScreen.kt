package com.upaep.upaeppersonal.view.features.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.library.BorrowedBook
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.library.BooksListViewModel

@Preview
@Composable
fun BooksListScreen(booksListViewModel: BooksListViewModel = hiltViewModel()) {
    val activeTheme by
    UserPreferences(LocalContext.current).activeTheme.collectAsState(initial = defaultTheme)
    val books = booksListViewModel.books
    val loadingScreen by booksListViewModel.loadingScreen.observeAsState()
    val errorInfo by booksListViewModel.error.observeAsState()
    BaseView(screenName = "BIBLIOTECA", loadingScreen = loadingScreen!!, error = errorInfo, noData = books.isEmpty()) {
        CardDescription(textColor = activeTheme!!.BASE_TEXT_COLOR, books = books)
    }
}

@Composable
fun CardDescription(textColor: Color, books: List<BorrowedBook>) {
    Column(modifier = Modifier.padding(vertical = 15.dp)) {
        Text(
            text = "LIBROS PRESTADOS", color = textColor,
            fontFamily = roboto_bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "La opción de renovación se activará tres días hábiles antes de tu fecha de devolución y recuerda que solo puedes renovar una vez cada libro de forma digital",
            color = textColor,
            fontFamily = roboto_regular,
            fontSize = 14.sp,
            lineHeight = 14.sp
        )
        Spacer(modifier = Modifier.size(15.dp))
        Divider()
        books.forEachIndexed { index, book ->
            SingleBook(book = book, last = (index + 1 != books.size), textColor = textColor)
        }
    }
}

@Composable
fun SingleBook(book: BorrowedBook, last: Boolean, textColor: Color) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(text = book.title ?: "", fontFamily = roboto_bold, color = textColor, fontSize = 14.sp)
        Spacer(modifier = Modifier.size(15.dp))
        Text(text = "Autor:", color = textColor, fontSize = 14.sp, fontFamily = roboto_regular)
        Text(
            text = book.author ?: "",
            color = textColor,
            fontSize = 14.sp,
            fontFamily = roboto_bold
        )
        Spacer(modifier = Modifier.size(15.dp))
        BookRenewalRow(
            label = "Día del prestamo:",
            date = book.loanDate ?: "",
            icon = R.drawable.icono_cronometro_verde,
            textColor = textColor
        )
        Spacer(modifier = Modifier.size(15.dp))
        BookRenewalRow(
            label = "Fecha de devolución:",
            date = book.returnDate ?: "",
            icon = R.drawable.icono_cronometro_rojo,
            textColor = textColor
        )
        Spacer(modifier = Modifier.size(10.dp))
        if ((book.renewals ?: 1) < 1) {
            Button(
                onClick = {}, colors = ButtonDefaults.buttonColors(
                    containerColor = Upaep_yellow
                )
            ) {
                Text(text = "RENOVAR", fontFamily = roboto_bold)
            }
        }
        if (last) Divider(modifier = Modifier.padding(vertical = 5.dp))
    }
}

@Composable
fun BookRenewalRow(label: String, date: String, icon: Int, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(13.dp)
        )
        Text(text = label, color = textColor, fontSize = 14.sp, fontFamily = roboto_regular)
    }
    Row {
        Spacer(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(13.dp)
        )
        Text(text = date, color = textColor, fontSize = 14.sp, fontFamily = roboto_regular)
    }
}

fun getBooks(): List<BorrowedBook> {
    return listOf(
        BorrowedBook(
            itemId = 1,
            libraryId = 1,
            title = "Enfermería pediátrica",
            author = null,
            loanDate = "18 de agosto del 2023 02:39:22",
            returnDate = "01 de septiembre del 2023 11:59:00",
            renewals = 1
        ),
        BorrowedBook(
            itemId = 2,
            libraryId = 2,
            title = "Libro de los poderosos",
            author = "El poderosisimo autor",
            loanDate = "15 de agosto del 2023",
            returnDate = "31 de agosto del 2023",
            renewals = 0
        ),
        BorrowedBook(
            itemId = 3,
            libraryId = 3,
            title = "El segundo libro más poderoso",
            author = "El crack",
            loanDate = "10 de agosto del 2023",
            returnDate = "18 de agosto del 2023",
            renewals = 3,
        ),
    )
}