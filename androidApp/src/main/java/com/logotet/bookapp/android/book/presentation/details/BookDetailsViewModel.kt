package com.logotet.bookapp.android.book.presentation.details

import androidx.lifecycle.viewModelScope
import com.logotet.bookapp.android.book.domain.BookRepository
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.domain.model.BookWithDetails
import com.logotet.bookapp.android.core.domain.result.DataResult
import com.logotet.bookapp.android.core.domain.result.onSuccess
import com.logotet.bookapp.android.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookRepository: BookRepository
) : BaseViewModel<BookWithDetails>() {

    sealed interface BookDetailsAction {
        data class SaveBook(val book: Book) : BookDetailsAction
        data class DeleteBook(val book: Book) : BookDetailsAction
    }

    private val _isSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSaved = _isSaved.asStateFlow()

    fun getBookDetails(bookId: String) {
        viewModelScope.launch {
            bookRepository.getBookDetails(bookId)
                .collectLatest { result ->
                    result.handleResult()
                }

            bookRepository.isFavoriteBook(bookId).collectLatest { isFavoriteResult ->
                if (isFavoriteResult is DataResult.Success) {
                    _isSaved.value = isFavoriteResult.data
                } else {
                    _isSaved.value = false
                }
            }
        }
    }

    fun onAction(action: BookDetailsAction) {
        when (action) {
            is BookDetailsAction.SaveBook -> {
                onSaveBookAction(action)
            }

            is BookDetailsAction.DeleteBook -> {
                onDeleteBookAction(action)
            }
        }
    }

    private fun onDeleteBookAction(action: BookDetailsAction.DeleteBook) {
        val book = action.book

        viewModelScope.launch {
            bookRepository.removeBookFromFavorites(bookId = book.id)
                .onSuccess {
                    _isSaved.value = false
                }
        }
    }

    private fun onSaveBookAction(action: BookDetailsAction.SaveBook) {
        viewModelScope.launch {
            val book = action.book

            bookRepository.insertFavoriteBook(book = book)
                .onSuccess {
                    _isSaved.value = true
                }
        }
    }
}