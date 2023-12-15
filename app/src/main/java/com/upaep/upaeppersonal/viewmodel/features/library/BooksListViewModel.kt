package com.upaep.upaeppersonal.viewmodel.features.library

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.entities.base.ErrorInfo
import com.upaep.upaeppersonal.model.entities.base.PeopleId
import com.upaep.upaeppersonal.model.entities.features.library.BorrowedBook
import com.upaep.upaeppersonal.model.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksListViewModel @Inject constructor(private val libraryRepository: LibraryRepository) :
    ViewModel() {

    val books = mutableListOf<BorrowedBook>()

    private val _loadingScreen = MutableLiveData(true)
    val loadingScreen: LiveData<Boolean> = _loadingScreen

    private val _error = MutableLiveData<ErrorInfo?>(null)
    val error: LiveData<ErrorInfo?> = _error

    init {
        getBookList()
        _loadingScreen.value = false
    }

    private fun getBookList() {
        viewModelScope.launch {
            val borrowedBooks = libraryRepository.getBorrowedBooks(peopleId = PeopleId(id = 94747))
            _error.value = ErrorInfo(visible = borrowedBooks.error, message = borrowedBooks.message)
            if (!borrowedBooks.error) {
                books.clear()
                books.addAll(borrowedBooks.data!!)
            }
        }
    }
}