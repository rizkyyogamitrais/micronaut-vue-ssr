package au.com.clubconnect.controller

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Controller
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.views.ModelAndView
import io.micronaut.http.MediaType
import groovy.util.logging.Slf4j
import groovy.transform.CompileStatic
import jakarta.inject.Singleton
import au.com.clubconnect.service.WordService
import au.com.clubconnect.service.RenderService

@CompileStatic
@Slf4j
@Controller('/')
class IndexController {

    private final RenderService renderService

    private final WordService wordService

    IndexController(RenderService renderService, WordService wordService) {
        this.renderService = renderService
        this.wordService = wordService
    }

    @Get('/')
    HttpResponse index(HttpRequest request) {
        String html = renderService.renderPage("/");
        Map data = [
            rendered: html,
            state: wordService.getWord("firstPage")
        ]

        return HttpResponse.ok(new ModelAndView("index", data)).contentType(MediaType.TEXT_HTML)
    }

    @Get('/second')
    HttpResponse secondIndex(HttpRequest request) {
        String html = renderService.renderPage("/second");
        Map data = [
            rendered: html,
            state: wordService.getWord("secondPage")
        ]

        return HttpResponse.ok(new ModelAndView("index", data)).contentType(MediaType.TEXT_HTML)
    }
}