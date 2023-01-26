package com.dteknoloji.springkafkadoc.service

import com.dteknoloji.springkafkadoc.configuration.AsyncApiDocket
import com.dteknoloji.springkafkadoc.types.AsyncAPI
import com.dteknoloji.springkafkadoc.types.Components
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class AsyncApiService(
    private val docket: AsyncApiDocket,
    private val channelsService: ChannelsService,
    private val schemasService: SchemasService
) {

    private var asyncAPI: AsyncAPI? = null

    @PostConstruct
    fun buildAsyncApi() {
        val components = Components.builder()
            .schemas(schemasService.getDefinitions())
            .build()

        asyncAPI = AsyncAPI.builder()
            .info(docket.info)
            .servers(docket.servers)
            .channels(channelsService.getChannels())
            .components(components)
            .build()
    }

    fun getAsyncAPI(): AsyncAPI = asyncAPI!!
}
