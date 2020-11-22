package com.krasnov.monitoring.service.notification

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Component
class TelegramBot(
        @Value("\${tlg.token}")
        private val tlgToken: String,

        @Value("\${tlg.user-name}")
        private val tlgUserName: String,

        @Value("\${tlg.chat-id}")
        private val tlgChatId: String
) : TelegramLongPollingBot() {


    override fun getBotToken(): String {
        return tlgToken
    }

    override fun getBotUsername(): String {
        return tlgUserName
    }

    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            if (update.hasMessage()) {
                val response = SendMessage()
                response.chatId = update.message.chatId.toString()
                response.text = update.message.text
                try {
                    execute(response)
                    println("Sent message ${update.message.text} to ${update.message.chatId}")
                } catch (ex: TelegramApiException) {
                    println("Failed to send message ${update.message.text} to ${update.message.chatId} due to error: ${ex.message}")
                }
            }
        }
    }

    fun sendMsg(msg: String) {
        val message = SendMessage()
        message.chatId = tlgChatId
        message.text = msg
        try {
            execute(message)
            println("Sent message $msg to $tlgChatId")
        } catch (ex: TelegramApiException) {
            println("Failed to send message $msg to $tlgChatId due to error: ${ex.message}")
        }
    }

}