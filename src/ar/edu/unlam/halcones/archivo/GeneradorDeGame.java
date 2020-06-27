package ar.edu.unlam.halcones.archivo;

import ar.edu.unlam.halcones.entities.Character;
import ar.edu.unlam.halcones.entities.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.unlam.halcones.archivo.JsonKey.*;

public class GeneradorDeGame {

    public Game generarEntornoDeJuego(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode gameTree = objectMapper.readTree(new FileReader(new File(fileName)));

        JsonNode settings = gameTree.get(SETTINGS_KEY);
        String characterName = settings.get(CHARACTER_KEY).asText();
        String welcomeMessage = settings.get(WELCOOME_KEY).asText();
        
        // Proceso items
        JsonNode itemsNode = gameTree.get(ITEMS_KEY);
        List<Item> gameItems = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<Item>>() {
        });

        // Proceso npcs
        JsonNode npcsNode = gameTree.get(NPCS_KEY);
        List<Npc> gameNpcs = objectMapper.readValue(npcsNode.toString(), new TypeReference<List<Npc>>() {
        });

        // Proceso endgames
        JsonNode endgamesNode = gameTree.get(ENDGAMES_KEY);
        List<EndGame> gameEndGames = objectMapper.readValue(endgamesNode.toString(), new TypeReference<List<EndGame>>() {
        });

        // Proceso inventario
        Inventory inventory = new Inventory();
        List<Item> itemsForInventory = new LinkedList<>();
        JsonNode inventoryNode = gameTree.get(INVENTORY_KEY);

        findAndAddElements(objectMapper, gameItems, itemsForInventory, inventoryNode);

        inventory.add(itemsForInventory);

        // Proceso Location por primera vez para generar places
        List<Location> gameLocations = new LinkedList<>();
        JsonNode locationsNode = gameTree.get(LOCATIONS_KEY);
        JsonNode[] locationsArrayNode = objectMapper.readValue(locationsNode.toString(), JsonNode[].class);
        for (JsonNode aLocation : locationsArrayNode) {
            List<Place> placesInLocation = new LinkedList<>();
            JsonNode placesNode = aLocation.get(PLACES_KEY);
            if (placesNode != null) {
                JsonNode[] placesNodes = objectMapper.readValue(placesNode.toString(), JsonNode[].class);
                for (JsonNode placeNode : placesNodes) {
                    Place place = new Place();
                    String placeName = placeNode.get(NAME_KEY).asText();
                    String placeGender = placeNode.get(GENDER_KEY).asText();
                    String placeNumber = placeNode.get(NUMBER_KEY).asText();

                    List<Item> itemsInPlace = new LinkedList<>();
                    JsonNode itemsNodeInPlace = placeNode.get(ITEMS_KEY);
                    findAndAddElements(objectMapper, gameItems, itemsInPlace, itemsNodeInPlace);

                    place.setName(placeName);
                    place.setGender(placeGender);
                    place.setNumber(placeNumber);
                    place.setItems(itemsInPlace);
                    placesInLocation.add(place);
                }
            }

            List<Npc> npcsInLocation = new LinkedList<>();
            JsonNode npcsInLocationNode = aLocation.get(NPCS_KEY);
            if (npcsInLocationNode != null) {
                findAndAddElements(objectMapper, gameNpcs, npcsInLocation, npcsInLocationNode);
            }

            String locationName = objectMapper.readValue(aLocation.get(NAME_KEY).toString(), String.class);
            String locationGender = aLocation.get(GENDER_KEY).asText();
            String locationNumber = aLocation.get(NUMBER_KEY).asText();
            String locationDescription = aLocation.get(DESCRIPTION_KEY).asText();

            Location location = new Location(locationName, locationGender, locationNumber,
                    locationDescription, placesInLocation, npcsInLocation);
            gameLocations.add(location);
        }

        // Proceso locations por segunda vez para agregar connections
        for (JsonNode aLocation : locationsArrayNode) {
            JsonNode connectionsNode = aLocation.get(CONNECTIONS_KEY);
            if (connectionsNode != null) {
                JsonNode[] connectionsNodes = objectMapper.readValue(connectionsNode.toString(), JsonNode[].class);
                for (JsonNode connectionNode : connectionsNodes) {
                    String connnectionDirection = connectionNode.get(DIRECTION_KEY).asText();
                    String locationInConnection = connectionNode.get(LOCATION_KEY).asText();
                    Location connectionLocation = gameLocations.stream().filter(location -> location.getName().equals(locationInConnection))
                            .findAny()
                            .orElse(null);

                    JsonNode obstaclesNode = connectionNode.get(OBSTACLES_KEY);
                    Npc connectionObstacle = null;
                    if (obstaclesNode != null) {
                        connectionObstacle = gameNpcs.stream().filter(npc -> npc.getName().equals(obstaclesNode.asText()))
                                .findAny()
                                .orElse(null);
                    }

                    Connection connection = new Connection(connnectionDirection, connectionLocation, connectionObstacle);

                    // Busco a que location pertence esta connection y se la agrego
                    String locationName = aLocation.get(NAME_KEY).asText();
                    List<Location> collect = gameLocations.stream().filter(location -> location.getName().equals(locationName))
                            .collect(Collectors.toList());
                    collect.forEach(location -> location.addConnection(connection));
                }
            }
        }

        Character character = new Character(gameLocations.get(0), inventory, characterName);
        return new Game(welcomeMessage, character, gameLocations, gameNpcs, gameItems, gameEndGames);
    }

    private <T extends GameEntity> void findAndAddElements(ObjectMapper objectMapper, Collection<T> listFrom, Collection<T> listTo, JsonNode searchNode) throws IOException {
        String[] itemsNameInInvetory = objectMapper.readValue(searchNode.toString(), String[].class);
        for (String itemName : itemsNameInInvetory) {
            listFrom.stream().filter(item -> item.getName().equals(itemName))
                    .findAny()
                    .ifPresent(listTo::add);
        }
    }
}
