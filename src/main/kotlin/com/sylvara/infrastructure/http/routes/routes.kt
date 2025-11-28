package com.sylvara.com.sylvara.infrastructure.http.routes



import com.sylvara.infrastructure.http.controllers.
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.homeRoutes() {
    val homeController by inject<HomeController>()

    route("/api/home") {
        get {
            homeController.getHomeData(call)
        }
    }
}
class routes {
}