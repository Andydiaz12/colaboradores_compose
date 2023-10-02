package com.upaep.upaeppersonal.model.entities.features.library

data class BorrowedBook(
    var itemId: Int? = null,
    var libraryId: Int? = null,
    var title: String? = null,
    var author: String? = null,
    var loanDate: String? = null,
    var returnDate: String? = null,
    var renewals: Int? = null,
    var unformattedReturnedDate: String? = null
)
