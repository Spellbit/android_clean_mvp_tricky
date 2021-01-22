package dev.stanislav.testtask.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.html.HTMLLayout
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy
import org.slf4j.LoggerFactory
import java.io.File

object LogbackFileLoggerCreator {

    fun createLogger(name: String, logPath : String) : Logger {
        val context = LoggerFactory.getILoggerFactory() as LoggerContext
        context.reset()

        val rollingFileAppender = RollingFileAppender<ILoggingEvent>()
        rollingFileAppender.isAppend = true
        rollingFileAppender.context = context
        rollingFileAppender.lazy = true
        rollingFileAppender.file = logPath + File.separator + "log.html"

        val trigPolicy = SizeBasedTriggeringPolicy<ILoggingEvent>()
        trigPolicy.maxFileSize = "5MB"
        trigPolicy.context = context
        trigPolicy.start()

        val rollingPolicy = FixedWindowRollingPolicy()
        rollingPolicy.context = context
        rollingPolicy.setParent(rollingFileAppender)
        rollingPolicy.fileNamePattern = logPath + File.separator + "log_backup.%i.txt"
        rollingPolicy.minIndex = 1
        rollingPolicy.maxIndex = 1
        rollingPolicy.start()

        rollingFileAppender.triggeringPolicy = trigPolicy
        rollingFileAppender.rollingPolicy = rollingPolicy

        val htmlLayout = HTMLLayout()
        htmlLayout.context = context
        htmlLayout.pattern = "%d{HH:mm:ss.SSS}%thread%level%msg"
        htmlLayout.start()

        val encoder = LayoutWrappingEncoder<ILoggingEvent>()
        encoder.layout = htmlLayout
        encoder.context = context
        encoder.start()

        rollingFileAppender.encoder = encoder
        rollingFileAppender.start()

        val logger = LoggerFactory.getLogger(name) as Logger
        logger.level = Level.DEBUG
        logger.addAppender(rollingFileAppender)

        return logger
    }
}