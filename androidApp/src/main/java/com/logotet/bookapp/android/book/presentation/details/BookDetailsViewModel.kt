package com.logotet.bookapp.android.book.presentation.details

import androidx.lifecycle.viewModelScope
import com.logotet.bookapp.android.book.data.DefaultBookRepository
import com.logotet.bookapp.android.book.domain.model.BookDetails
import com.logotet.bookapp.android.core.domain.result.DataResult
import com.logotet.bookapp.android.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookRepository: DefaultBookRepository
) : BaseViewModel<BookDetails>() {

    fun getBookDetails(bookId: String) {
        viewModelScope.launch {
            bookRepository.getBookDetails(bookId)
                .collectLatest { result ->
                    when (result) {
                        is DataResult.Loading -> {
                            _state.value = ScreenState.Loading
                        }

                        is DataResult.Success -> {
                            _state.value = ScreenState.Success(result.data)
                        }

                        is DataResult.Error -> {
                            _state.value = ScreenState.Error(result.error)
                        }
                    }
                }
        }
    }
}