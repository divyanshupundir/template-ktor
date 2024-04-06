package com.divpundir.template.ktor.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val databaseModule = module {

    single<Database> {
        Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = "root",
            password = ""
        )
    }

    single<UserService> {
        UserService(
            database = get(),
            context = Dispatchers.IO
        )
    }
}
