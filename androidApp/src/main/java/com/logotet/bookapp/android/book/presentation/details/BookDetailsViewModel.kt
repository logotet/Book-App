package com.logotet.bookapp.android.book.presentation.details

import androidx.lifecycle.viewModelScope
import com.logotet.bookapp.android.book.data.DefaultBookRepository
import com.logotet.bookapp.android.book.domain.model.BookDetails
import com.logotet.bookapp.android.book.domain.model.BookWithDetails
import com.logotet.bookapp.android.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookRepository: DefaultBookRepository
) : BaseViewModel<BookWithDetails>() {

    fun getBookDetails(bookId: String) {
        viewModelScope.launch {
            bookRepository.getBookDetails(bookId)
                .collectLatest { result ->
                   result.handleResult()
                }
        }
    }
}