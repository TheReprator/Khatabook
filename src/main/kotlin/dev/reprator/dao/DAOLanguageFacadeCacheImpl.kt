package dev.reprator.dao

import dev.reprator.modals.LanguageModal
import org.ehcache.config.builders.*
import org.ehcache.config.units.*
import org.ehcache.impl.config.persistence.*
import java.io.*

class DAOLanguageFacadeCacheImpl(
    private val delegate: DAOLanguageFacade,
    storagePath: File
) : DAOLanguageFacade {
    private val cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .with(CacheManagerPersistenceConfiguration(storagePath))
        .withCache(
            "articlesCache",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Int::class.javaObjectType,
                LanguageModal::class.java,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(1000, EntryUnit.ENTRIES)
                    .offheap(10, MemoryUnit.MB)
                    .disk(100, MemoryUnit.MB, true)
            )
        )
        .build(true)

    private val articlesCache = cacheManager.getCache("articlesCache", Int::class.javaObjectType, LanguageModal::class.java)

    override suspend fun allLanguage(): List<LanguageModal> =
        delegate.allLanguage()

    override suspend fun language(id: Int): LanguageModal? =
        articlesCache[id]
            ?: delegate.language(id)
                .also { article -> articlesCache.put(id, article) }

    override suspend fun addNewLanguage(name: String): LanguageModal? =
        delegate.addNewLanguage(name)
            ?.also { article -> articlesCache.put(article.id, article) }

    override suspend fun editLanguage(id: Int, name: String): Boolean {
        articlesCache.put(id, LanguageModal(id, name))
        return delegate.editLanguage(id, name)
    }

    override suspend fun deleteLanguage(id: Int): Boolean {
        articlesCache.remove(id)
        return delegate.deleteLanguage(id)
    }

}