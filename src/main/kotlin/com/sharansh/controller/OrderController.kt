package com.sharansh.controller

import com.sharansh.domain.Order
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import jakarta.inject.Inject
import org.kie.api.runtime.KieSession


@Controller
class OrderController(@Inject val session: KieSession) {

//    @Inject
//    val session: KieSession? = null

    @Post(value = "/order")
    @Produces(MediaType.APPLICATION_JSON)
    fun getOrder(@Body order: Order): Order {
        println(order.name)
//        val httpResponse = HttpResponse.status<Order?>(HttpStatus.ACCEPTED).body(order)
//        return httpResponse;

        session?.insert(order)
        session?.fireAllRules()
//        session.dispose();

        return order
    }
}