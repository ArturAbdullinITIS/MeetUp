package ru.tbank.petcare.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull
import ru.tbank.petcare.data.remote.network.deepl.DeeplApiService
import ru.tbank.petcare.domain.model.Language
import ru.tbank.petcare.domain.repository.TranslationRepository
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val deeplApiService: DeeplApiService,
) : TranslationRepository {

    companion object {
        private const val RU_LANGUAGE = "RU"
        private const val EN_LANGUAGE = "EN"
    }

    private fun getDeeplLanguage(language: Language): String {
        return when (language) {
            Language.ENGLISH -> EN_LANGUAGE
            Language.RUSSIAN -> RU_LANGUAGE
        }
    }

    override fun translate(text: String, sourceLanguage: Language, targetLanguage: Language): Flow<String> = flow {
        if (sourceLanguage == targetLanguage) {
            emit(text)
            return@flow
        }
        val response = withTimeoutOrNull(7000L) {
            deeplApiService.translate(
                text = text,
                sourceLang = getDeeplLanguage(sourceLanguage),
                targetLang = getDeeplLanguage(targetLanguage)
            )
        }
        val translatedText = response?.translations?.firstOrNull()?.text ?: text
        emit(translatedText)
    }.catch {
        emit(text)
    }
}
