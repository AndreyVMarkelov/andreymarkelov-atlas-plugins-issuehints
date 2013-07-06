package ru.andreymarkelov.atlas.plugins;

import java.util.HashMap;
import java.util.Map;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;

public class HintPanel
    extends AbstractJiraContextProvider
{
    /**
     * Datastore.
     */
    private final HintDataStore hintDatastore;

    /**
     * Constructor.
     */
    public HintPanel(HintDataStore hintDatastore) {
        this.hintDatastore = hintDatastore;
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
            contextMap.put("hintbody", data.getHintBody());
            contextMap.put("hinttoggle", data.isToggle());
        }

        return contextMap;
    }
}
