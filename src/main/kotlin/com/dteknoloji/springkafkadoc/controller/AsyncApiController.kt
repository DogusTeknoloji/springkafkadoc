package com.dteknoloji.springkafkadoc.controller

import com.dteknoloji.springkafkadoc.service.AsyncApiSerializerService
import com.dteknoloji.springkafkadoc.service.AsyncApiService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/springkafkadoc")
class AsyncApiController(
    private val asyncApiService: AsyncApiService,
    private val serializer: AsyncApiSerializerService
) {

    @GetMapping("/json", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun asyncApiAsJSON(): String {
        val asyncAPI = asyncApiService.getAsyncAPI()
        return serializer.toJsonString(asyncAPI)
    }

    @GetMapping("/yaml")
    fun asyncApiAsYAML(): String {
        val asyncAPI = asyncApiService.getAsyncAPI()
        return serializer.toYamlString(asyncAPI)
    }
}
