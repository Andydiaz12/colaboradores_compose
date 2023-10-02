package com.upaep.upaeppersonal.view.base.defaultvalues

import com.google.gson.Gson
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.theme.ThemeSchema

private val gson = Gson()

private val defaultThemeStringify: String = gson.toJson(ThemeSchema.LIGHT)
val defaultTheme: ActiveTheme = gson.fromJson(defaultThemeStringify, ActiveTheme::class.java)