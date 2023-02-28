package com.example.postgresql.controllers;

import com.example.postgresql.entities.Administrators;
import com.example.postgresql.entities.CityGames;
import com.example.postgresql.entities.GameParticipants;
import com.example.postgresql.entities.PlacesOfGame;
import com.example.postgresql.idClasses.PlacesOfGameId;
import com.example.postgresql.services.CityGamesService;
import com.example.postgresql.services.PlacesOfGameService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.api.client.json.Json;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import com.google.gson.*;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class CityGamesController {

    private final CityGames cityGameCopy = new CityGames();
    @Autowired
    private CityGamesService cityGamesService;

    @Autowired
    private PlacesOfGameService placesOfGameService;

    /**
     *
     * @param jsonString
     * save cityGame into database
     */
    @PostMapping("/savegame")
//    @CrossOrigin(origins = "http://localhost:4200")
    void saveCityGame(@RequestBody String jsonString) throws IOException {
//        cityGameCopy.setNameOfGame(cityGame.getNameOfGame());
//        cityGameCopy.setCityForGame(cityGame.getCityForGame());
//        initFirebase();

        JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);

        CityGames cityGame = new CityGames();
        cityGame.setNameOfGame(data.get("nameOfGame").getAsString());
        cityGame.setDateForStartGame(data.get("dateForStartGame").getAsString());
        cityGame.setCityForGame(data.get("cityForGame").getAsString());
        cityGame.setCountryForGame(data.get("countryForGame").getAsString());
        cityGame.setAccessCode(Integer.parseInt(data.get("accessCode").getAsString()));


        JsonArray names = data.get("playing").getAsJsonArray();
        for(JsonElement element : names){
            JsonObject object = element.getAsJsonObject();

            Administrators admin = new Administrators();

            //add values to idParticipant and nickname to new admin
//            admin.setIdParticipant(Long.parseLong(object.get("idParticipant").getAsString()));
            admin.setNickname(object.get("nickname").getAsString());

            //add set of city games for this new admin
            Set<CityGames> setCityGames = new HashSet<>();
            setCityGames.add(cityGame);
            admin.setPlayingGames(setCityGames);

            //add to this new city game info about it's admin
            Set<GameParticipants> setParticipants = new HashSet<>();
            setParticipants.add(admin);
            cityGame.setPlaying(setParticipants);

        }

        cityGamesService.saveCityGame(cityGame);
    }

    /**
     *
     * @param cityGame
     * add new player to cityGame
     */
    @PostMapping("/addplayer")
    void addPlayerToGame(@RequestBody CityGames cityGame){

    }

    /**
     *
     * @param cityGame
     * add admin to cityGame
     */
    @PostMapping("/addadmin")
    void addAdminToGame(@RequestBody CityGames cityGame){

    }


//    /**
//     *
//     * @param id
//     * find cityGame by id
//     * @return cityGame from database
//     */
//    @GetMapping("/citygame/{id}")
//    public ResponseEntity<CityGames> findGameById(@PathVariable Long id){
//        Optional<CityGames> cityGame = cityGamesService.findGameById(id);
//
//        return cityGame.map(cityGames -> new ResponseEntity<>(cityGames, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(cityGame.get(), HttpStatus.NOT_FOUND));
//    }

    /**
     *
     * @param jsonString
     * @throws IOException
     */
    @PostMapping("/addplacetogame")
    public void addNewPlaceToGame(@RequestBody String jsonString) throws IOException{

        JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);

        PlacesOfGame newPlace = new PlacesOfGame();
        newPlace.setOrderId(data.get("orderId").getAsInt());
        newPlace.setAddress(data.get("address").getAsString());
        newPlace.setLatitudeCoord(data.get("latitudeCoord").getAsDouble());
        newPlace.setLongitudeCoord(data.get("longitudeCoord").getAsDouble());
        newPlace.setLegend(data.get("legend").getAsString());
        newPlace.setPhotoLink(data.get("photoLink").getAsString());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newPlace);
        System.out.println(json);

        JsonObject cityGameObject = data.get("cityGame").getAsJsonObject();

        Long gameId = cityGamesService.getCityGameId(
                cityGameObject.get("nameOfGame").getAsString(),
                cityGameObject.get("accessCode").getAsInt(),
                cityGameObject.get("cityForGame").getAsString(),
                cityGameObject.get("dateForStartGame").getAsString(),
                cityGameObject.get("countryForGame").getAsString());

        CityGames cityGameForUpdate = cityGamesService.findGameById(gameId);

        if(cityGameForUpdate == null){
            System.out.println("This is null!");
        }

        json = objectMapper.writeValueAsString(cityGameForUpdate);
        System.out.println(json);


        newPlace.setCityGame(cityGameForUpdate);
        json = objectMapper.writeValueAsString(newPlace);
        System.out.println(json);

        Set<PlacesOfGame> setPlaces = new HashSet<>();
        setPlaces.add(newPlace);

        newPlace.getCityGame().setPlacesOfGame(setPlaces);

        json = objectMapper.writeValueAsString(newPlace.getCityGame());
        System.out.println(json);


//        cityGameForUpdate.setPlacesOfGame(setPlaces);

//        cityGamesService.saveCityGame(newPlace.getCityGame());

//        cityGamesService.saveCityGame(newGame);
        placesOfGameService.saveNewPlace(newPlace);
    }


    @PostMapping("/addallplaces")
    public void addAllPlacesToGame(@RequestBody String jsonString) throws IOException{

        JsonArray jsonArray = new Gson().fromJson(jsonString, JsonArray.class);

        initFirebase();

        for (JsonElement elem: jsonArray) {

            JsonObject placeJson = elem.getAsJsonObject();

            PlacesOfGame newPlace = new PlacesOfGame();
            newPlace.setOrderId(placeJson.get("orderId").getAsInt());
            newPlace.setAddress(placeJson.get("address").getAsString());
            newPlace.setLatitudeCoord(placeJson.get("latitudeCoord").getAsDouble());
            newPlace.setLongitudeCoord(placeJson.get("longitudeCoord").getAsDouble());
            newPlace.setLegend(placeJson.get("legend").getAsString());
            newPlace.setPhotoLink(placeJson.get("photoLink").getAsString());

            JsonObject cityGameObject = placeJson.get("cityGame").getAsJsonObject();

            Long gameId = cityGamesService.getCityGameId(
                    cityGameObject.get("nameOfGame").getAsString(),
                    cityGameObject.get("accessCode").getAsInt(),
                    cityGameObject.get("cityForGame").getAsString(),
                    cityGameObject.get("dateForStartGame").getAsString(),
                    cityGameObject.get("countryForGame").getAsString());

            CityGames cityGameForUpdate = cityGamesService.findGameById(gameId);

            if(cityGameForUpdate == null){
                System.out.println("This is null!");
            }

            newPlace.setCityGame(cityGameForUpdate);

            Set<PlacesOfGame> setPlaces = new HashSet<>();
            setPlaces.add(newPlace);

            newPlace.getCityGame().setPlacesOfGame(setPlaces);

            placesOfGameService.saveNewPlace(newPlace);

            if (cityGameForUpdate != null) {
                addDataFirebase(newPlace, cityGameForUpdate.getNameOfGame());
            }
        }
    }

    public void initFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\remloc1-fbe72-firebase-adminsdk-h130v-9966196e2e.json");

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://remloc1-fbe72-default-rtdb.europe-west1.firebasedatabase.app/")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FirebaseApp.initializeApp(options);
    }

    private void addDataFirebase(PlacesOfGame newPlace, String nameOfGame) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> updates = new HashMap<>();

        String[] lang =new String[4];
        lang[0] = "pl";
        lang[1] = "en";
        lang[2] = "uk";
        lang[3] = "ru";

        for (String partLang: lang
             ) {
            updates.put("/Games/"+nameOfGame+"/"+partLang +"/" + newPlace.getOrderId() +"/hint", newPlace.getPhotoLink());
            updates.put("/Games/"+nameOfGame+"/"+partLang +"/" + newPlace.getOrderId() +"/latitude", newPlace.getLatitudeCoord());
            updates.put("/Games/"+nameOfGame+"/"+partLang +"/" + newPlace.getOrderId() +"/legend", newPlace.getLegend());
            updates.put("/Games/"+nameOfGame+"/"+partLang +"/" + newPlace.getOrderId() +"/longitude", newPlace.getLongitudeCoord());
            updates.put("/Games/"+nameOfGame+"/"+partLang +"/" + newPlace.getOrderId() +"/placeName", newPlace.getAddress());

        }



        ref.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    // An error occurred while updating the data
                } else {
                    // Data updated successfully!
                }
            }
        });
    }


    @PostMapping("/updatecitygame/{id}")
//    public ResponseEntity<CityGames> updateGame(@RequestBody CityGames cityGame, @PathVariable Long id){
////        CityGames copiedCityGame = cityGamesService.findGameById(id);
//
//        Optional<CityGames> cityGameForUpdate = cityGamesService.findGameById(id);
//        return cityGameForUpdate
//                .map(updatedCityGame -> {
//                    updatedCityGame.setNameOfGame(cityGame.getNameOfGame());
//                    updatedCityGame.setCityForGame(cityGame.getCityForGame());
//                    updatedCityGame.setNameOfGame(cityGame.getNameOfGame());
//                    updatedCityGame.setAccessCode(cityGame.getAccessCode());
//                    updatedCityGame.setCountryForGame(cityGame.getCountryForGame());
//                    updatedCityGame.setDateForStartGame(cityGame.getDateForStartGame());
//                    updatedCityGame.setDateForEndGame(cityGame.getDateForEndGame());
//                    updatedCityGame.setPlacesOfGame(cityGame.getPlacesOfGame());
//                    updatedCityGame.setPlaying(cityGame.getPlaying());
//                    cityGamesService.saveCityGame(updatedCityGame);
//                    return new ResponseEntity<>(updatedCityGame, HttpStatus.OK);
//                })
//                .orElseGet(() -> {
//                    cityGamesService.saveCityGame(cityGame);
//                    return new ResponseEntity<>(cityGame, HttpStatus.NOT_FOUND);
//                });
//
//    }


    /**
     *
     * @param id
     * delete citGame by id
     */
    @DeleteMapping("/citygame/{id}")
    public void deleteGameById(@PathVariable Long id){
        cityGamesService.deleteGameById(id);
    }


    /**
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/allcitygames")
    public ResponseEntity<JsonArray> getAllCityGames() throws IOException{

        List<CityGames> listOfGames = cityGamesService.getAllCityGames();

        JsonArray gamesJsonArray = new JsonArray();
        ObjectMapper objectMapper = new ObjectMapper();

        for (CityGames gameObject:listOfGames) {

            JsonObject gameJson = new Gson().fromJson(objectMapper.writeValueAsString(gameObject), JsonObject.class);
            gamesJsonArray.add(gameJson);

        }

        System.out.println(gamesJsonArray);

        return ResponseEntity.ok(gamesJsonArray);
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @GetMapping("/only/allcitygames")
    public ResponseEntity<JsonArray> getCityGamesOnly() throws IOException, IllegalStateException{

        List<CityGames> listOfGames = cityGamesService.getCityGamesOnly();
        List<JsonObject> listOfJsonGames = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonArray gamesJsonArray = new JsonArray();

        for (CityGames gameObject: listOfGames) {

            String json = objectMapper.writeValueAsString(gameObject);

            JsonObject gameJson = new Gson().fromJson(json, JsonObject.class);
            gameJson.remove("playing");
            gameJson.remove("placesOfGame");

            System.out.println(gameJson);

            listOfJsonGames.add(gameJson);
            gamesJsonArray.add(gameJson);
        }

        System.out.println(gamesJsonArray);

        return ResponseEntity.ok(gamesJsonArray);
    }

    /**
     *
     * @param cityGames
     * @return
     */
    @PostMapping("/citygame")
    public ResponseEntity<Long> getCityGameId(@RequestBody CityGames cityGames){

        Long testIdGame = cityGamesService.getCityGameId(cityGames.getNameOfGame(), cityGames.getAccessCode(),
                cityGames.getCityForGame(), cityGames.getDateForStartGame(), cityGames.getCountryForGame());

        System.out.println("IDGAME: -> " + testIdGame);
        Set<GameParticipants> listParticipants = new HashSet<>();
        Administrators part1 = new Administrators();
        part1.nickname = "Lala12";

        Set<CityGames> listGames = new HashSet<>();
        listGames.add(cityGames);

        part1.setPlayingGames(listGames);

        listParticipants.add(part1);

        cityGames.setPlaying(listParticipants);

        cityGamesService.saveCityGame(cityGames);

        return new ResponseEntity<>(testIdGame, HttpStatus.OK);
    }

    /*public void initFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\remloc1-fbe72-firebase-adminsdk-h130v-9966196e2e.json");

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://remloc1-fbe72-default-rtdb.europe-west1.firebasedatabase.app/")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FirebaseApp.initializeApp(options);

        addDataToFirebase();
    }

    private void addDataToFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> updates = new HashMap<>();
        updates.put("/Games/"+cityGameCopy.getCityForGame()+"/pl/1/hint", "1");
        updates.put("/Games/"+cityGameCopy.getCityForGame()+"/pl/1/latitude", "1");
        updates.put("/Games/"+cityGameCopy.getCityForGame()+"/pl/1/legend", "1");
        updates.put("/Games/"+cityGameCopy.getCityForGame()+"/pl/1/longitude", "1");
        updates.put("/Games/"+cityGameCopy.getCityForGame()+"/pl/1/placeName", cityGameCopy.getCityForGame());

        ref.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    // An error occurred while updating the data
                } else {
                    // Data updated successfully!
                }
            }
        });
    }*/
}
