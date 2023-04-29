package org.example;

import org.example.dto.Movie;
import java.lang.reflect.Type;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

public class JSONDeserializerMovie implements JsonDeserializer<Movie>{
	public Movie deserialize(JsonElement json,
							  Type typeOfT,
							  JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();


		int movieID = jsonObject.get("movieID").getAsInt();
		String title = jsonObject.get("title").getAsString();
		String producer = jsonObject.get("producer").getAsString();
		String release_date = jsonObject.get("release_date").getAsString();
		String type = jsonObject.get("type").getAsString();
		String genre = jsonObject.get("genre").getAsString();
		String duration = jsonObject.get("duration").getAsString();

		double ratings=jsonObject.get("ratings").getAsDouble();


		Movie movie = new Movie(movieID, title,producer, release_date, type,genre,duration, ratings);

		return movie;
	}
}
