package ua.com.hiringservice.config;

import gui.ava.html.image.generator.HtmlImageGenerator;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.commonmark.Extension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration defining beans which used to generate images from the provided text. */
@Configuration
public class ImageGeneratorConfig {

  @Bean
  public HtmlImageGenerator htmlImageGenerator() {
    return new HtmlImageGenerator();
  }

  @Bean
  public ByteArrayOutputStream byteArrayOutputStream() {
    return new ByteArrayOutputStream();
  }

  @Bean
  public Parser parser() {
    return Parser.builder().extensions(getExtensions()).build();
  }

  @Bean
  public HtmlRenderer htmlRenderer() {
    return HtmlRenderer.builder().extensions(getExtensions()).build();
  }

  private static List<Extension> getExtensions() {
    return List.of(ImageAttributesExtension.create());
  }
}
