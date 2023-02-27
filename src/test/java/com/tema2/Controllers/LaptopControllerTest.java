package com.tema2.Controllers;

import com.tema2.Models.Laptop;
import com.tema2.Repositories.LaptopRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LaptopControllerTest {


    private TestRestTemplate http;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @Autowired
    LaptopRepository laptopRepository;

    @BeforeEach
    void setUp(){
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:"+port);
        http = new TestRestTemplate(restTemplateBuilder);

        laptopRepository.deleteAll();

        ArrayList<Laptop> laptops = new ArrayList<>();
        laptops.add(new Laptop("audi","notebook-15"));
        laptops.add(new Laptop("toyota","notebook-17"));
        laptops.add(new Laptop("ferrari","notebook-18"));

        laptops.forEach(laptop -> {
            laptopRepository.save(laptop);
        });
    }

    @AfterEach
    void unmounting(){
        laptopRepository.deleteAll();
    }


    @Test
    void getAll() {
        ResponseEntity<Laptop[]> laptops = http.getForEntity("/laptops",Laptop[].class);
        assertEquals(3,laptops.getBody().length);
    }

    @Test
    void getOne() {
        ResponseEntity<Laptop> laptop = http.getForEntity("/laptop/4",Laptop.class);
        assertEquals("audi",laptop.getBody().getMarca());
        assertEquals("notebook-15",laptop.getBody().getModelo());

    }

    @Test
    void add() {
        ResponseEntity<Laptop> response = http.postForEntity("/add",
                new Laptop("add","bytest")
                ,Laptop.class);
        Laptop laptop = response.getBody();
        int possible = (int) laptopRepository.findAll()
                .stream()
                .filter(laptop1 -> laptop1.getId().equals(laptop.getId()))
                .count();
        assertEquals(1,possible);
    }

    @Test
    void update() {
        Laptop laptopToUpdate = new Laptop("jack","sparrow");
        laptopToUpdate.setId(4L);
        HttpEntity<Laptop> lap = new HttpEntity<Laptop>(laptopToUpdate);
        http.exchange("/update",HttpMethod.PUT,lap,Laptop.class);
        assertEquals("jack",laptopRepository.findById(4L).get().getMarca());
        assertEquals("sparrow",laptopRepository.findById(4L).get().getModelo());
    }

    @Test
    void delete() {
        ResponseEntity<Laptop> laptop = http.exchange("/laptop/1",HttpMethod.DELETE,null,Laptop.class);
        assertTrue(laptopRepository.findById(1L).isEmpty());
    }

    @Test
    void deleteAll() {
        ResponseEntity<Laptop> laptop = http.exchange("/laptops",HttpMethod.DELETE,null,Laptop.class);
        assertEquals(0, laptopRepository.findAll().size());
    }
}