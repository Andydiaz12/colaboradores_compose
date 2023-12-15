package com.upaep.upaeppersonal.model.repository

import android.util.Log
import com.upaep.upaeppersonal.model.api.features.MenuService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.database.menudao.PrivacyDAO
import com.upaep.upaeppersonal.model.entities.features.menu.PrivacyPolicy
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val menuService: MenuService,
    private val locksmithHelper: LocksmithHelper,
    private val privacyDAO: PrivacyDAO
) {
    suspend fun getPrivacyPolicy(): UpaepStandardResponse<String> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(error = true, message = locksmith.message)
        val privacyPolicy =
            menuService.getPrivacyPolicy(keyChain = locksmith.data!!.generalKeychain!!)
        return withContext(Dispatchers.IO) {
            if (!privacyPolicy.error) {
                privacyDAO.deletePrivacyPolicy()
                privacyDAO.setPrivacyPolicy(privacy = PrivacyPolicy(privacyPolicy = privacyPolicy.data!!))
            }
            privacyPolicy.copy(data = privacyDAO.getPrivacyPolicy()?.privacyPolicy ?: "")
        }
    }
}