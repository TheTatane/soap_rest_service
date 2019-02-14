package app;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "infoSongs")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema InfoSongsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("InfoSongsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/SOAP/GetInfoSongs");
        wsdl11Definition.setSchema(InfoSongsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema InfoSongsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("songs.xsd"));
    }

    @Bean(name = "songs")
    public DefaultWsdl11Definition defaultWsdl11DefSongs(XsdSchema songsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("SongsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/SOAP/GetSongs");
        wsdl11Definition.setSchema(songsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema songsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("songs.xsd"));
    }

    @Bean(name = "albums")
    public DefaultWsdl11Definition defaultWsdl11DefinitionAlbums(XsdSchema albumsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AlbumsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/SOAP/GetAlbums");
        wsdl11Definition.setSchema(albumsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema albumsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("albums.xsd"));
    }
}