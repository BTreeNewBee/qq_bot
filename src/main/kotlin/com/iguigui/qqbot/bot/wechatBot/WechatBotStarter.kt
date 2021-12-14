package com.iguigui.qqbot.bot.wechatBot


import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
open class WechatBotStarter {

    @Bean
    open fun wechatBot() : WechatBot {
        val wechatBot = WechatBot()
        wechatBot.login()
        GlobalScope.launch {
            delay(1000*10)
            wechatBot.loadInfo()
        }
        println("wechat login success")
        return wechatBot
    }

}