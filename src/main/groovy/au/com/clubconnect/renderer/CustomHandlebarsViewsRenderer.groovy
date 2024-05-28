package au.com.clubconnect.renderer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Helper
import com.github.jknack.handlebars.Options
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import com.github.jknack.handlebars.io.TemplateLoader
import io.micronaut.context.annotation.Replaces
import io.micronaut.core.io.scan.ClassPathResourceLoader
import io.micronaut.views.ViewsConfiguration
import io.micronaut.views.handlebars.HandlebarsViewsRenderer
import io.micronaut.views.handlebars.HandlebarsViewsRendererConfiguration
import org.apache.commons.lang3.RandomStringUtils
import jakarta.inject.Singleton
import groovy.transform.CompileStatic

@CompileStatic
@Singleton
@Replaces(HandlebarsViewsRenderer.class)
public final class CustomHandlebarsViewsRenderer extends HandlebarsViewsRenderer {

    private CharSequence hash = ""

    public CustomHandlebarsViewsRenderer(ViewsConfiguration viewsConfiguration, 
                                  ClassPathResourceLoader resourceLoader, 
                                  HandlebarsViewsRendererConfiguration handlebarsViewsRendererConfiguration,
                                  Handlebars handlebars) {
        super(viewsConfiguration, resourceLoader, handlebarsViewsRendererConfiguration, handlebars);
        this.hash = RandomStringUtils.random(15, true, true)
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setSuffix(".hbs");
        this.handlebars = new Handlebars(loader);
        this.handlebars.registerHelper("toJson", new Helper<Object>() {
            public CharSequence apply(Object obj, Options options) {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                return ow.writeValueAsString(obj)
            }
        });
        this.handlebars.registerHelper("hash", new Helper<Object>() {
            public CharSequence apply(Object obj, Options options) {
                return hash
            }
        });
        this.handlebars.registerHelper("when", new Helper<Object>() {
            public String apply(Object operand_1, Options options) {
                def result = false
                def operator = options.param(0)
                def operand_2 = options.param(1)
                switch(operator) {         
                    case 'eq': 
                        result = operand_1.equals(operand_2)
                        break;
                    case 'ne': 
                        result = !operand_1.equals(operand_2)
                        break;
                    default: 
                        break; 
                }
                if(result) return options.fn(this); 
                return options.inverse(this); 
            }
        });
    }
}