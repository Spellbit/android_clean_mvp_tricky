package dev.stanislav.testtask.logging

import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.ISeedling

class ProductionSeedling(val delegate: ISeedling, logPath: String) : ISeedling by delegate {

    companion object {
        private const val LOGGER_NAME = "App"
    }

    val logger = LogbackFileLoggerCreator.createLogger(LOGGER_NAME, logPath)

    override fun log(level: Int, tag: String, msg: String, throwable: Throwable?, args: Array<out Any?>?) {
        delegate.log(level, tag, msg, throwable, args)

        when (level) {
            Arbor.DEBUG -> logger.debug(msg)
            Arbor.INFO -> logger.info(msg)
            Arbor.WARNING -> logger.warn(msg)
            Arbor.ERROR -> logger.error(msg)
        }
    }
}