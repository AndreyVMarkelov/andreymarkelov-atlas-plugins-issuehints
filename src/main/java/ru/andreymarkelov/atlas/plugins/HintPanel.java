package ru.andreymarkelov.atlas.plugins;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.renderer.wiki.AtlassianWikiRenderer;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.templaterenderer.TemplateRenderer;

public class HintPanel
    extends AbstractJiraContextProvider
{
    /**
     * Datastore.
     */
    private final HintDataStore hintDatastore;

    /**
     * Renderer manager.
     */
    private final RendererManager rendererManager;

    /**
    * Template renderer.
    */
    private final TemplateRenderer renderer;

    /**
     * Constructor.
     */
    public HintPanel(
            HintDataStore hintDatastore,
            RendererManager rendererManager,
            TemplateRenderer renderer) {
        this.hintDatastore = hintDatastore;
        this.rendererManager = rendererManager;
        this.renderer = renderer;
    }

    @Override
    public Map<String, Object> getContextMap(
        User user,
        JiraHelper helper)
    {
        Map<String, Object> contextMap = new HashMap<String, Object>();

        Issue currentIssue = (Issue) helper.getContextParams().get("issue");
        HintDataList list = hintDatastore.getHintDataList();
        HintData data = list.getHintForIssue(currentIssue);
        if (data != null) {
            contextMap.put("hinttitle", data.getHintTitle());
            contextMap.put("hintbody", rendererManager.getRenderedContent(AtlassianWikiRenderer.RENDERER_TYPE, data.getHintBody(), null));
            contextMap.put("hinttoggle", data.isToggle());

            StringWriter sw = new StringWriter();
            try {
                renderer.render("/templates/ru/andreymarkelov/atlas/plugins/hint-panel.vm", contextMap, sw);
                contextMap.put("hintscript", sw.toString());
            } catch (Exception e) {
                contextMap.put("hintbody", null);
            }
        }

        return contextMap;
    }
}
