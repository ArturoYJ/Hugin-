package com.hugin_munin

import com.hugin_munin.infrastructure.database.DatabaseFactory
import com.hugin_munin.infrastructure.plugins.configureHTTP
import com.hugin_munin.infrastructure.plugins.configureMonitoring
import com.hugin_munin.infrastructure.plugins.configureRouting
import com.hugin_munin.infrastructure.plugins.configureSecurity
import com.hugin_munin.infrastructure.plugins.configureSerialization
//import com.hugin_munin.infrastructure.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init(environment)

    configureSerialization()
    configureHTTP()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}