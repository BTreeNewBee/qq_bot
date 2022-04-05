package com.iguigui.process.service.impl

import com.iguigui.common.service.MessageHandlerService
import org.apache.dubbo.config.annotation.DubboService

@DubboService
class MessageHandlerServiceImpl : MessageHandlerService {

    override fun getContent(message: String): String {
        println("handler message $message")
        return   message + "yes!"
    }

}