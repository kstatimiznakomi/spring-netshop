package com.newshop.shopnetnew.config;

import com.newshop.shopnetnew.endpoint.ProductsEndpoint;
import com.newshop.shopnetnew.endpoint.UsersEndpoint;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }
    @Bean(name = "products")
    public DefaultWsdl11Definition productsWsdlDefinition() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(ProductsEndpoint.NAMESPACE_URL);
        wsdl11Definition.setSchema(xsdProductsSchema());
        return wsdl11Definition;
    }

    @Bean(name = "users")
    public DefaultWsdl11Definition usersWsdlDefinition() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("UsersPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(UsersEndpoint.NAMESPACE_URL);
        wsdl11Definition.setSchema(xsdUsersSchema());
        return wsdl11Definition;
    }

    @Bean("productsSchema")
    public XsdSchema xsdProductsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ws/products.xsd"));
    }
    @Bean("usersSchema")
    public XsdSchema xsdUsersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ws/users.xsd"));
    }

}
