package ru.andreymarkelov.atlas.plugins;

import java.io.IOException;
import java.io.Writer;
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

    @Override
    public void writeHtml(Writer writer, Map<String, Object> context) throws IOException {
        Object body = context.get("hintbody");
        if (body != null) {
            writer.write(body.toString() + context.get("hintscript").toString());
        } else {
            writer.write("");
        }
    }
}
