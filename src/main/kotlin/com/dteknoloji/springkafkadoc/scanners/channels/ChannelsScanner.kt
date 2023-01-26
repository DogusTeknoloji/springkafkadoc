package com.dteknoloji.springkafkadoc.scanners.channels

import com.asyncapi.v2.model.channel.ChannelItem

interface ChannelsScanner {

    fun scan(): Map<String, ChannelItem>
}
