package com.upaep.upaeppersonal.model.entities.features.benefits

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BenefitDetail(
    @PrimaryKey
    var benefitId: Int? = null,
    var categoryId: Int? = null,
    var companyId: Int? = null,
    var managerName: String? = null,
    var name: String? = null,
    var categoryName: String? = null,
    var addres: String? = null,
    var mail: String? = null,
    var phone: String? = null,
    var phoneAlternative: String? = null,
    var mobile: String? = null,
    var icon: String? = null,
    var restrictions: String? = null,
    var discountDescription: String? = null,
    var validity: String? = null
)
