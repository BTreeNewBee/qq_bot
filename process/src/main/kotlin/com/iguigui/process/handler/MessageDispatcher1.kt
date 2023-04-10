package com.iguigui.process.handler


import com.iguigui.common.interfaces.DTO
import com.iguigui.process.annotations.SubscribeBotMessage
import com.iguigui.process.entity.mongo.GroupPermission
import com.iguigui.process.qqbot.IMessageDispatcher
import com.iguigui.process.qqbot.dto.GroupMessagePacketDTO
import com.iguigui.process.qqbot.dto.GroupRecallEventDTO
import com.iguigui.process.qqbot.dto.MemberCardChangeEventDTO
import com.iguigui.process.service.GroupPermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import javax.annotation.PostConstruct
import kotlin.reflect.KClass

@Component
class MessageDispatcher1 : ApplicationContextAware, IMessageDispatcher {

    @Autowired
    private lateinit var groupPermissionService: GroupPermissionService


    private lateinit var applicationContext: ApplicationContext

    private val messageHandlers = mutableMapOf<KClass<out DTO>, MutableList<Method>>()

    val handlerBeans = mutableMapOf<Method, Any>()

    //Inject the application context
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }


    //Register the message handler
    @PostConstruct
    fun init() {
        //Get all the beans
        val beans = applicationContext.getBeansOfType(Object::class.java)
        beans.forEach { entry ->
            entry.value.`class`.methods.forEach { method ->
                registerHandler(entry.value, method)
            }
        }
    }


    private fun registerHandler(bean: Any, method: Method) {
        val findAnnotations = method.getAnnotationsByType(SubscribeBotMessage::class.java)
        if (findAnnotations.isEmpty()) {
            return
        }
        val subscribeBotMessage = findAnnotations.first()
//        if (!subscribeBotMessage.export) {
//            return
//        }
        val parameters = method.parameters
        if (parameters.size > 1) {
            return
        }
        val parameter = parameters[0]
        val assignableFrom = DTO::class.java.isAssignableFrom(parameter.type)
        if (assignableFrom) {
            messageHandlers.getOrPut(parameter.type.kotlin as KClass<out DTO>) { ArrayList() }.add(method)
            handlerBeans[method] = bean
        }
    }

    //Dispatch the message
    override fun handler(message: DTO) {

        messageHandlers[message::class]?.forEach { method ->
//            method(handlerBeans[method], message)
            val annotation = method.getAnnotation(SubscribeBotMessage::class.java)
            if (!annotation.export) {
                method.invoke(handlerBeans[method], message)
                return@forEach
            }
            when (message) {
                is GroupMessagePacketDTO -> {
                    val id = message.sender.group.id
                    if (!groupPermissionService.checkGroupPermission(id, method.name)) {
                        return@forEach
                    }
                }

                is GroupRecallEventDTO -> {
                    val id = message.group.id
                    if (!groupPermissionService.checkGroupPermission(id, method.name)) {
                        return@forEach
                    }
                }

                is MemberCardChangeEventDTO -> {
                    val id = message.member.group.id
                    if (!groupPermissionService.checkGroupPermission(id, method.name)) {
                        return@forEach
                    }
                }
            }
            method.invoke(handlerBeans[method], message)
        }
    }


}