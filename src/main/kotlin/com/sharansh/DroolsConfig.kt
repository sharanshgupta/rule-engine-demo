package com.sharansh

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import org.kie.api.KieServices
import org.kie.api.builder.KieFileSystem
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.KieSession
import org.kie.internal.io.ResourceFactory
import java.io.IOException


@Factory
class DroolsConfig {

    private val kieServices = KieServices.Factory.get()

    @Throws(IOException::class)
    private fun getKieFileSystem(): KieFileSystem? {

        val kieFileSystem = kieServices.newKieFileSystem()
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules.drl"))
        return kieFileSystem
    }

    @Bean
    @Throws(IOException::class)
    fun getKieContainer(): KieContainer {
        println("Container created...")
        getKieRepository()
        val kb = kieServices.newKieBuilder(getKieFileSystem())
        kb.buildAll()
        val kieModule = kb.kieModule
        return kieServices.newKieContainer(kieModule.releaseId)
    }

    private fun getKieRepository() {
        val kieRepository = kieServices.repository
        kieRepository.addKieModule { kieRepository.defaultReleaseId }
    }

    @Bean
    @Throws(IOException::class)
    fun getKieSession(): KieSession? {
        println("session created...")
        return getKieContainer().newKieSession()
    }
}