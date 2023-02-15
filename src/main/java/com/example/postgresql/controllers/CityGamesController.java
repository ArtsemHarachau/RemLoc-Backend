package com.example.postgresql.controllers;

import com.example.postgresql.entities.Administrators;
import com.example.postgresql.entities.CityGames;
import com.example.postgresql.entities.GameParticipants;
import com.example.postgresql.entities.PlacesOfGame;
import com.example.postgresql.services.CityGamesService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

//    @PostMapping("/saveCityGame")
//    public String saveCityGame(@ModelAttribute("cityGame") CityGames cityGame) {
//        cityGamesService.saveCityGame(cityGame);
//        return "redirect:/";//
//    }


    /**
     *
     * @param jsonString
     * save cityGame into database
     */
//    @PostMapping("/savegame")
////    @CrossOrigin(origins = "http://localhost:4200")
//    void saveCityGame(@RequestBody CityGames cityGame) throws JsonProcessingException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(cityGame);
//        System.out.println(json);
//
////        for(GameParticipants elem: cityGame.getPlaying()){
////            System.out.println(elem.nickname);
////        }
////
////        cityGamesService.saveCityGame(cityGame);
//    }
    @PostMapping("/savegame")
//    @CrossOrigin(origins = "http://localhost:4200")
    void saveCityGame(@RequestBody String jsonString) throws IOException {
//        cityGameCopy.setNameOfGame(cityGame.getNameOfGame());
//        cityGameCopy.setCityForGame(cityGame.getCityForGame());
//        initFirebase();

//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        CityGames cityGame = objectMapper.readValue(jsonString, CityGames.class);

        JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);

        CityGames cityGame = new CityGames();
        cityGame.setNameOfGame(data.get("nameOfGame").getAsString());
        cityGame.setDateForStartGame(data.get("dateForStartGame").getAsString());
        cityGame.setCityForGame(data.get("cityForGame").getAsString());
        cityGame.setCountryForGame(data.get("countryForGame").getAsString());
        cityGame.setAccessCode(Integer.parseInt(data.get("accessCode").getAsString()));
//        System.out.println(data);
//        System.out.println(cityGame.getNameOfGame());
//        System.out.println(cityGame.getDateForStartGame());
//        System.out.println(cityGame.getCityForGame());
//        System.out.println(cityGame.getCountryForGame());
//        System.out.println(cityGame.getAccessCode());




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



//            System.out.println(object);
        }
//        System.out.println(cityGame.getIdGame());

//        GameParticipants gameParticipants = new Administrators();
//        gameParticipants.set;

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


    /**
     *
     * @param id
     * find cityGame by id
     * @return cityGame from database
     */
    @GetMapping("/citygame/{id}")
    public ResponseEntity<CityGames> findGameById(@PathVariable Long id){
        Optional<CityGames> cityGame = cityGamesService.findGameById(id);

        return cityGame.map(cityGames -> new ResponseEntity<>(cityGames, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(cityGame.get(), HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addplacetogame")
    public void addNewPlaceToGame(@RequestBody CityGames cityGame){

        Set<PlacesOfGame> setPlacesOfGame = new HashSet<>();
        PlacesOfGame placeOfGame = new PlacesOfGame();
        placeOfGame.setOrderId(1);
        placeOfGame.setAddress("Serbska 17");
        placeOfGame.setLegend("Lalalalalaa");
        placeOfGame.setPhotoLink("Blablablabla");
        placeOfGame.setLatitudeCoord(1.1);
        placeOfGame.setLongitudeCoord(1.1);

        setPlacesOfGame.add(placeOfGame);

        cityGame.setPlacesOfGame(setPlacesOfGame);

        cityGamesService.saveCityGame(cityGame);
    }

    @PostMapping("/updatecitygame/{id}")
    public ResponseEntity<CityGames> updateGame(@RequestBody CityGames cityGame, @PathVariable Long id){
//        CityGames copiedCityGame = cityGamesService.findGameById(id);

        Optional<CityGames> cityGameForUpdate = cityGamesService.findGameById(id);
        return cityGameForUpdate
                .map(updatedCityGame -> {
                    updatedCityGame.setNameOfGame(cityGame.getNameOfGame());
                    updatedCityGame.setCityForGame(cityGame.getCityForGame());
                    updatedCityGame.setNameOfGame(cityGame.getNameOfGame());
                    updatedCityGame.setAccessCode(cityGame.getAccessCode());
                    updatedCityGame.setCountryForGame(cityGame.getCountryForGame());
                    updatedCityGame.setDateForStartGame(cityGame.getDateForStartGame());
                    updatedCityGame.setDateForEndGame(cityGame.getDateForEndGame());
                    updatedCityGame.setPlacesOfGame(cityGame.getPlacesOfGame());
                    updatedCityGame.setPlaying(cityGame.getPlaying());
                    cityGamesService.saveCityGame(updatedCityGame);
                    return new ResponseEntity<>(updatedCityGame, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    cityGamesService.saveCityGame(cityGame);
                    return new ResponseEntity<>(cityGame, HttpStatus.NOT_FOUND);
                });

    }


    /**
     *
     * @param id
     * delete citGame by id
     */
    @DeleteMapping("/citygame/{id}")
    public void deleteGameById(@PathVariable Long id){
        cityGamesService.deleteGameById(id);
    }




//    @GetMapping("users/{id}")
//    public ResponseEntity<User> getById(@PathVariable long id) {
//
//        Optional<User> user = userService.getById(id);
//        if (user.isPresent()) {
//            return new ResponseEntity<>(user.get(), HttpStatus.OK);
//        } else {
//            throw new RecordNotFoundException();
//        }
//    }

//    @Param("nameOfGame") String nameOfGame,
//    @Param("accessCode") Integer accessCode,
//    @Param("cityForGame") String cityForGame,
//    @Param("dateForStartGame") String dateForStartGame,
//    @Param("countryForGame") String countryForGame


//    @GetMapping("/citygame")
//    @ResponseBody
//    public ResponseEntity<Long> getCityGameId(@RequestParam("nameOfGame") String nameOfGame,
//                                              @RequestParam("accessCode") Integer accessCode,
//                                              @RequestParam("cityForGame") String cityForGame,
//                                              @RequestParam("dateForStartGame") String dateForStartGame,
//                                              @RequestParam("countryForGame") String countryForGame){
//        Long idGame = cityGamesService.getCityGameId(nameOfGame, accessCode, cityForGame, dateForStartGame,
//                countryForGame);
//
//        System.out.println("IDGAME: -> " + idGame);
//
//        return new ResponseEntity<>(idGame, HttpStatus.OK);
//    }

    @PostMapping("/citygame")
//    @ResponseBody
    public ResponseEntity<Long> getCityGameId(@RequestBody CityGames cityGames){
//        Long testIdGame = cityGamesService.getCityGameId(cityGames.getNameOfGame(), cityGames.getAccessCode(), cityGames.getCityForGame(), cityGames.getDateForStartGame(),
//                cityGames.getCountryForGame());

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
    }


//    @PostMapping("/createNewGame")
//    public String createNewCityGame(Model model){
//        CityGames cityGame = new CityGames();
//        model.addAttribute("cityGame", cityGame);
//
//    }


//    /**
//     * Save city game to database.
//     *
//     */
//    @RequestMapping(value = "/city/game", method = RequestMethod.POST)
//    public ResponseEntity<CityGames> createCityGame(@RequestBody @NotNull CityGames cityGame) {
//        cityGamesService.saveCityGame(cityGame);
//        return ResponseEntity.ok().body(cityGame);
//    }
//
//    /**
//     * List all games.
//     *
//     */
//    @RequestMapping(value = "/city/games", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Iterable<CityGames> listGames(Model model) {
//        return cityGamesService.listAllGames();
//    }
}
