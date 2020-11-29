package uz.tafakkur.sport.telegram;

import java.util.HashMap;

public class MessageConstants {
    static final HashMap<String, String> MESSAGES_CYRILL = new HashMap<String, String>() {

        {
            put("search", "\uD83D\uDD0D Излаш");
            put("currentLocation", "\uD83D\uDCCD Яшаш жойи");
            put("about", "\uD83E\uDD16 Бот ҳақида");
            put(
                "aboutMsg",
                "Бу бот орқали сиз Тошкент шаҳридан мини-футбол ўйнаш учун залларни ёки сунъий чим қопламали (газон) майдонларни излашингиз мумкин. Келажакда бот орқали нафақат Тошкент бўйлаб, балки Ўзбекистоннинг исталган ҳудудидан турли спорт иншоотларини излаш имконияти яратилади."
            );
        }
    };
}
