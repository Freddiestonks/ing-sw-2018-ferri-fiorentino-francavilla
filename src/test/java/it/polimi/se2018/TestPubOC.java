//package it.polimi.se2018;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import it.polimi.se2018.model.*;
//import org.junit.Test;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.util.Objects;
//
//import static org.junit.Assert.fail;
//
//public class TestPubOC {
//    @Test
//    public void testCalculateScore() throws FileNotFoundException, InvalidPlaceException {
//        int[] result = new int[10];
//        result[0] = 12;
//        result[1] = 10;
//        result[2] = 10;
//        result[3] = 4;
//        result[4] = 6;
//        result[5] = 6;
//        result[6] = 4;
//        result[7] = 10;
//        result[8] = 15;
//        result[9] = 8;
//
//        Die[][] dice = new Die[4][5];
//        dice[0][0] = new Die(Color.PURPLE);
//        dice[0][0].setValue(4);
//        dice[0][1] = new Die(Color.BLUE);
//        dice[0][1].setValue(3);
//        dice[0][2] = new Die(Color.RED);
//        dice[0][2].setValue(2);
//        dice[0][3] = new Die(Color.YELLOW);
//        dice[0][3].setValue(5);
//        dice[0][4] = new Die(Color.GREEN);
//        dice[0][4].setValue(6);
//        dice[1][0] = new Die(Color.RED);
//        dice[1][0].setValue(2);
//        dice[1][1] = new Die(Color.YELLOW);
//        dice[1][1].setValue(5);
//        dice[1][2] = new Die(Color.BLUE);
//        dice[1][2].setValue(6);
//        dice[1][3] = new Die(Color.GREEN);
//        dice[1][3].setValue(3);
//        dice[1][4] = new Die(Color.PURPLE);
//        dice[1][4].setValue(2);
//        dice[2][0] = new Die(Color.YELLOW);
//        dice[2][0].setValue(1);
//        dice[2][1] = new Die(Color.BLUE);
//        dice[2][1].setValue(3);
//        dice[2][2] = new Die(Color.GREEN);
//        dice[2][2].setValue(2);
//        dice[2][3] = new Die(Color.YELLOW);
//        dice[2][3].setValue(4);
//        dice[2][4] = new Die(Color.GREEN);
//        dice[2][4].setValue(1);
//        dice[3][0] = new Die(Color.BLUE);
//        dice[3][0].setValue(5);
//        dice[3][1] = new Die(Color.GREEN);
//        dice[3][1].setValue(4);
//        dice[3][2] = new Die(Color.YELLOW);
//        dice[3][2].setValue(1);
//        dice[3][3] = new Die(Color.GREEN);
//        dice[3][3].setValue(3);
//        dice[3][4] = new Die(Color.YELLOW);
//        dice[3][4].setValue(2);
////
//        for (int a = 0; a<10;a++) {
//            int id = a;
//            WindowFrame wf = new WindowFrame(0, true);
//            String path = "src/main/json/publicCards.json";
//            JsonParser jsonParser = new JsonParser();
//            PubObjCard pubObjCard = null;
//
//            try {
//                Object object = jsonParser.parse(new FileReader(path));
//                JsonObject jsonObject = (JsonObject) object;
//                JsonArray patternCards = (JsonArray) jsonObject.get("publicCards");
//                jsonObject = (JsonObject) patternCards.get(id);
//                String type = jsonObject.get("type").getAsString();
//                String name = jsonObject.get("name").getAsString();
//                String description = jsonObject.get("description").getAsString();
//                int multiplier = jsonObject.get("multiplier").getAsInt();
//                String subtype = jsonObject.get("subtype").getAsString();
//                if (type.equals("color")) {
//                    switch (subtype) {
//                        case "row":
//                            pubObjCard = new PubOCColorDet(description, name, true, false, false, multiplier);
//                            break;
//                        case "col":
//                            pubObjCard = new PubOCColorDet(description, name, false, true, false, multiplier);
//                            break;
//                        case "diagonals":
//                            pubObjCard = new PubOCColorDet(description, name, false, false, true, multiplier);
//                            break;
//                        case "set":
//                            boolean[] colors = new boolean[5];
//                            JsonArray jsonArray = jsonObject.get("values").getAsJsonArray();
//                            for (int i = 0; i < 5; i++) {
//                                colors[i] = jsonArray.get(i).getAsBoolean();
//                            }
//                            pubObjCard = new PubOCColorSet(description, name, colors, multiplier);
//                            break;
//                        default:
//                            throw new IllegalArgumentException();
//                    }
//                } else if (type.equals("shade")) {
//                    switch (subtype) {
//                        case "row":
//                            pubObjCard = new PubOCShadeDet(description, name, true, true, multiplier);
//                            break;
//                        case "col":
//                            pubObjCard = new PubOCShadeDet(description, name, false, true, multiplier);
//                            break;
//                        case "set":
//                            boolean[] shades = new boolean[6];
//                            JsonArray jsonArray = jsonObject.get("values").getAsJsonArray();
//                            for (int i = 0; i < 6; i++) {
//                                shades[i] = jsonArray.get(i).getAsBoolean();
//                            }
//                            pubObjCard = new PubOCShadeSet(description, name, shades, multiplier);
//                            break;
//                    }
//                }
//
//            } catch (FileNotFoundException ignored) {
//            }
//
//
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < 5; j++) {
//                    wf.placeDie(dice[i][j], i, j);
//                }
//            }
//
//            if ((pubObjCard.calculateScore(wf) != result[a])) {
//
//                fail();
//            }
//        }
//
//
//
//
//    }
//}
