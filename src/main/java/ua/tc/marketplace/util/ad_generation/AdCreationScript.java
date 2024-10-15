package ua.tc.marketplace.util.ad_generation;

import com.github.javafaker.Faker;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@Slf4j
public class AdCreationScript {

  private static final int[] authorIds = {4, 23, 18, 17, 5, 2, 26, 25, 20, 21};

  public static void main(String[] args) {
    log.info("Temporary files directory: {}", System.getProperty("java.io.tmpdir"));
    Faker faker = new Faker();
    Random random = new Random();

    for (int i = 0; i < 150; i++) {
      // Generate attributes JSON
      String adAttributes =
          String.format(
              """
                [
                    { "attributeId": 1, "value": "%s" },
                    { "attributeId": 2, "value": "%s" },
                    { "attributeId": 3, "value": "%s" },
                    { "attributeId": 4, "value": "%s" },
                    { "attributeId": 5, "value": "%s" },
                    { "attributeId": 6, "value": "%s" },
                    { "attributeId": 7, "value": "%s" },
                    { "attributeId": 8, "value": "%s" }
                ]
                """,
              faker.animal().name(),
              faker.number().digit() + " years",
              faker.options().option("Small", "Medium", "Big"),
              faker.options().option("Male", "Female"),
              faker.options().option("Small", "Medium", "Large"),
              faker.color().name(),
              faker.medical().symptoms(),
              faker.name().firstName());

      // Generate a random number of photos (between 1 to 3)
      int photoCount = random.nextInt(3) + 1;

      try (CloseableHttpClient client = HttpClients.createDefault()) {
        HttpPost post =
            new HttpPost("https://marketplace-production-c9c4.up.railway.app/api/v1/ad");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        // Add form fields
        builder.addTextBody(
            "authorId",
            String.valueOf(authorIds[random.nextInt(authorIds.length)]),
            ContentType.TEXT_PLAIN);
        builder.addTextBody("title", faker.animal().name(), ContentType.TEXT_PLAIN);
        builder.addTextBody("description", faker.lorem().sentence(), ContentType.TEXT_PLAIN);
        builder.addTextBody(
            "price", String.valueOf(random.nextDouble() * 100), ContentType.TEXT_PLAIN);
        builder.addTextBody("adAttributes", adAttributes, ContentType.APPLICATION_JSON);
        builder.addTextBody(
            "categoryId", String.valueOf(random.nextLong(1, 7)), ContentType.TEXT_PLAIN);

        // Add photo files
        for (int j = 0; j < photoCount; j++) {
          Path tempFile = Files.createTempFile("ad_image_" + j, ".jpg");
          Files.write(
              tempFile,
              HttpClient.newBuilder()
                  .followRedirects(HttpClient.Redirect.ALWAYS)
                  .build()
                  .send(
                      HttpRequest.newBuilder()
                          .uri(
                              URI.create(
                                  "https://picsum.photos/300/300?random=" + random.nextInt(1000)))
                          .GET()
                          .build(),
                      HttpResponse.BodyHandlers.ofByteArray())
                  .body());

          builder.addBinaryBody(
              "photoFiles",
              tempFile.toFile(),
              ContentType.IMAGE_JPEG,
              tempFile.getFileName().toString());
        }

        HttpEntity entity = builder.build();
        post.setEntity(entity);
        post.setHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXJhc0BzaGV2Y2hlbmtvLnVhIiwiZmlyc3ROYW1lIjoiVGFyYXMiLCJleHAiOjE3MzAxODc3NzB9.vZkJocIgFgrz6g26QYD7ZyrbhCeg3ITmvuMWv1Hg7lQ");

        log.info("Sending request to create ad #{}", i + 1);
        var response = client.execute(post);
        log.info("Response status: {}", response.getStatusLine().getStatusCode());
        log.info("Response body: {}", new String(response.getEntity().getContent().readAllBytes()));
        //        log.info("Ad #{} created successfully", i + 1);
      } catch (Exception e) {
        log.error("Failed to create ad #{}", i + 1, e);
      }
    }
  }
}
