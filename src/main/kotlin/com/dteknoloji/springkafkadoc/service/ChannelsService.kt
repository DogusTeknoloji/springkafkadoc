package com.dteknoloji.springkafkadoc.service

import com.asyncapi.v2.model.channel.ChannelItem
import com.dteknoloji.springkafkadoc.scanners.channels.ChannelsScanner
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ChannelsService(private val channelsScanners: List<ChannelsScanner>) {

    private val channels = mutableMapOf<String, ChannelItem>()

    @PostConstruct
    fun findChannels() {
        for (scanner in channelsScanners) {
            val channels = scanner.scan()

            if (channels.isNotEmpty()) processFoundChannels(channels)
        }
    }

    private fun processFoundChannels(foundChannels: Map<String, ChannelItem>) {
        for ((key, value) in foundChannels) {
            channels[key]?.let { channel ->
                value.subscribe?.let { channel.subscribe = it }
                value.publish?.let { channel.publish = it }
            } ?: run {
                channels[key] = value
            }
        }
    }

    fun getChannels(): Map<String, ChannelItem> {
        return channels
    }
}
