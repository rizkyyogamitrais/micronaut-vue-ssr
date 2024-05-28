package au.com.clubconnect.service

import groovy.util.logging.Slf4j
import groovy.transform.CompileStatic
import jakarta.inject.Singleton
import au.com.clubconnect.renderer.ServerSideRenderer

@Slf4j
@Singleton
@CompileStatic
class RenderService {

    private final ServerSideRenderer renderer

    RenderService(ServerSideRenderer renderer) {
        this.renderer = renderer
    }

    String renderPage(String route) {
        String html = ""
        try {
            html = renderer.render(route)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return html;
    }
}