//package org.example.tourplanner.frontend.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.awt.image.BufferedImage;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class OpenRouteServiceTests {
//    @BeforeAll
//    static void beforeAll() {
//        System.out.println("Starting with OpenRouteService-tests");
//    }
//
//    @AfterAll
//    static void afterAll() {
//        System.out.println("OpenRouteService-tests finished");
//    }
//
//    // just testing functionality --> this unit test is not representative and can be deleted afterward
//    @Test
//    void searchAddress_checkFunction() {
//        // arrange
//        String address = "Oskar-Morgenstern-Platz 1";
//        OpenRouteService service = new OpenRouteService();
//
//        // act
//        JsonNode response = service.searchAddress(address).block();
//        System.out.println(response);
//
//        // assert
//        assertTrue(true);
//    }
//
//    @Test
//    void fetchTile_checkFunction() {
//        // arrange
//        OpenRouteService service = new OpenRouteService();
//
//        // act
//        BufferedImage image = service.fetchTile(17, 71498, 45431).block();
//        if (image != null) {
//            System.out.println("image fetched successfully!");
//        } else {
//            System.out.println("error");
//        }
//
//        // assert
//        assertTrue(true);
//    }
//}
