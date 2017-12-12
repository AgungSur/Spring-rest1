package hello;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private Greeting x;
	
	@Autowired
	private EntityManagerFactory em;
	
	@RequestMapping("/actors")
	public List<Actor> allActor(){
		return em.createEntityManager()
				.createQuery("form Actor")
				.getResultList();
		
	}
	
	@RequestMapping("/film")
	public List<Film> allFilms(){
		return em.createEntityManager()
				.createQuery("form Films")
				.getResultList();
		
	}
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
	 return x;
		//	return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping("/data")
	public List<String> dataNegara(@RequestParam("pre") String prefix) {
		List<String> data = new ArrayList();
		data.add("Indonesia");
		data.add("Malaysia");
		data.add("Brunnei");
		data.add("Timor Leste");

		return data.stream().filter(line ->
			// line.startsWith(prefix))
			line.contains(prefix))
			.collect(Collectors.toList());

	}
	@RequestMapping("/countries")
	public String getCountries() throws IOException{
	URL url = new URL ("http://www.webservicex.net/country.asmx/GetCountries");
	URLConnection connection = url.openConnection();
	InputStream stream = url.openConnection().getInputStream();
	
	//atau bisa juga koneksinya spt dibawah ini:
	connection.setDoOutput(true);
	connection.setRequestProperty("Content-Type", "application/x-www-Form-urlencoded");
	connection.setRequestProperty("Conten-Length", "0");
	
	InputStreamReader reader = new InputStreamReader (stream);
	BufferedReader buffer = new BufferedReader(reader);
	
	String line;
	StringBuilder builder = new StringBuilder();
	while((line = buffer.readLine()) !=null) {
		builder.append(line);
	}
	return builder.toString();
	/*
	return new BufferedReader(new InputStreamReader(stream))
			.lines()
			.collect(Collectors.joining("\n"));
			*/
	}
			
}
