package dev.reprator.language.domain

import dev.reprator.language.data.LanguageRepository
import dev.reprator.language.modal.LanguageEntity
import dev.reprator.language.modal.LanguageId
import dev.reprator.language.modal.LanguageModal
import dev.reprator.language.modal.LanguageName

class LanguageFacadeImpl(private val repository: LanguageRepository): LanguageFacade {

    override suspend fun getAllLanguage(): Iterable<LanguageModal> {
        return repository.allLanguage().ifEmpty {
            throw LanguageEmptyException()
        }
    }

    override suspend fun getLanguage(id: LanguageId): LanguageModal {
        return repository.language(id) ?: throw LanguageNotFoundException()
    }

    override suspend fun addNewLanguage(languageInfo: LanguageName): LanguageModal {
        return repository.addNewLanguage(languageInfo) ?: throw IllegalLanguageException("Language already exist")
    }

    override suspend fun editLanguage(languageInfo: LanguageEntity): Boolean {
        return repository.editLanguage(languageInfo.id, languageInfo.name)
    }

    override suspend fun deleteLanguage(id: LanguageId): Boolean {
        return repository.deleteLanguage(id)
    }
}