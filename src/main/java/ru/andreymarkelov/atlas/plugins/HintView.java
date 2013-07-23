package ru.andreymarkelov.atlas.plugins;

import java.util.Map;
import com.atlassian.plugin.web.model.WebPanel;

public class HintView implements WebPanel {
    @Override
    public String getHtml(Map<String, Object> context) {
        Object body = context.get("hintbody");
        if (body != null) {
            return (body.toString() + context.get("hintscript").toString());
        } else {
            return "";
        }
    }
}
