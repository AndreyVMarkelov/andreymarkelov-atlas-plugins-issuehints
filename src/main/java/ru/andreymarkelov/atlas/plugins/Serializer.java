package ru.andreymarkelov.atlas.plugins;

import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

public class Serializer {
    public static void main(String[] args) {
        HintDataList hintDataList = new HintDataList();
        HintData data = new HintData();
        data.setId(12L);
        data.setProjectId(1434L);
        data.setIssueTypeId("3");
        data.setStatusId("4");
        data.setHintTitle("ddddd");
        data.setHintBody("DDDDD");
        data.setToggle(true);
        hintDataList.addHintData(data);
        data = new HintData();
        data.setId(142L);
        data.setProjectId(14L);
        data.setIssueTypeId("5");
        data.setStatusId("7");
        data.setHintTitle("eeee");
        data.setHintBody("XXXXX");
        data.setToggle(false);
        hintDataList.addHintData(data);

        String xml = hintDataListToXml(hintDataList);
        System.out.println(xml);

        hintDataList = hintDataListFromXml(xml);
        System.out.println(hintDataList);
    }

    public static HintDataList hintDataListFromXml(String xml) {
        HintDataList hintDataList = new HintDataList();

        try {
            JSONArray arrs = new JSONArray(xml);
            for (int i = 0; i < arrs.length(); i++) {
                JSONObject obj = arrs.getJSONObject(i);
                HintData data = new HintData();
                data.setId(obj.getLong("id"));
                data.setProjectId(obj.getLong("projectId"));
                data.setIssueTypeId(obj.getString("issueTypeId"));
                data.setStatusId(obj.getString("statusId"));
                data.setHintTitle(obj.getString("hintTitle"));
                data.setHintBody(obj.getString("hintBody"));
                data.setToggle(obj.getBoolean("isToggle"));
                hintDataList.addHintData(data);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return hintDataList;
    }

    public static String hintDataListToXml(HintDataList hintDataList) {
        JSONArray arrs = new JSONArray();

        try {
            for (HintData data : hintDataList.getHints()) {
                JSONObject obj = new JSONObject();
                obj.put("id", data.getId());
                obj.put("projectId", data.getProjectId());
                obj.put("issueTypeId", data.getIssueTypeId());
                obj.put("statusId", data.getStatusId());
                obj.put("hintTitle", data.getHintTitle());
                obj.put("hintBody", data.getHintBody());
                obj.put("isToggle", data.isToggle());
                arrs.put(obj);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return arrs.toString();
    }

    private Serializer() {
    }
}
