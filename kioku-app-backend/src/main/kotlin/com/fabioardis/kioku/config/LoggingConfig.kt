package com.fabioardis.kioku.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration

@Configuration
class LoggingConfig

inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)