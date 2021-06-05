package com.example.teamproject_1;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    public static String toJson(Note note) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isMajor", note.getIsMajor());
            jsonObject.put("subject", note.getSubject());
            jsonObject.put("number", note.getFirstToLast());
            jsonObject.put("professor", note.getProfessor());
            jsonObject.put("book", note.getBook());
            jsonObject.put("building", note.getBuilding());
            jsonObject.put("room", note.getRoom());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
