package com.example.graduationproject.language

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import java.util.*

object LanguageConfig {
    fun changeLanguage(context: Context, languageCode: String): ContextWrapper {
        var context = context
        val resources = context.resources
        val configuration = resources.configuration
        val systemLocale: Locale
        systemLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales[0]
        } else {
            configuration.locale
        }
        if (languageCode != "" && systemLocale.language != languageCode) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(locale)
            } else {
                configuration.locale = locale
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(configuration)
            } else {
                context.resources.updateConfiguration(
                    configuration,
                    context.resources.displayMetrics
                )
            }
        }
        return ContextWrapper(context)
    }
}