package br.com.alura.screenmatch.api;

import br.com.alura.screenmatch.exceptions.ErrorOfConversionOfYearException;
import br.com.alura.screenmatch.models.Content;
import br.com.alura.screenmatch.models.OmdbContent;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindContent {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String find = "";
        List<Content> contents = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (!find.equalsIgnoreCase("exit")) {

            System.out.println("Write the title of the content you are looking for: ");
            find = sc.nextLine();

            if (find.equalsIgnoreCase("exit")){
                break;
            }

            String address = "https://omdbapi.com/?t=" + find.replace(" ", "+") + "&apikey=e841078d";
            System.out.println(address);
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(address))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();
                System.out.println(json);


                OmdbContent myMovieOmdb = gson.fromJson(json, OmdbContent.class);
                System.out.println(myMovieOmdb);

                Content myMovie = new Content(myMovieOmdb);
                System.out.println("Movie details already converted");
                System.out.println(myMovie);

                contents.add(myMovie);
            } catch (NumberFormatException e) {
                System.out.println("An error happened");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("An error happened while in the content title, please try again!");
            } catch (ErrorOfConversionOfYearException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(contents);

        FileWriter writer = new FileWriter("contents.json");
        writer.write(gson.toJson(contents));
        writer.close();
        System.out.println("The program finished correctly!");


    }
}
