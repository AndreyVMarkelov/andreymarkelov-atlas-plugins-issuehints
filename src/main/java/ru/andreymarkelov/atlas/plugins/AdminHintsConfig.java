package ru.andreymarkelov.atlas.plugins;

import java.util.List;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.renderer.wiki.AtlassianWikiRenderer;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.issue.status.Status;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.ApplicationProperties;

public class AdminHintsConfig extends JiraWebActionSupport {
    /**
     * Unique ID.
     */
    private static final long serialVersionUID = 5138492582079389714L;

    /**
     * Application properties.
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Datastore.
     */
    private final HintDataStore hintDatastore;

    /**
     * Renderer manager.
     */
    private final RendererManager rendererManager;

    private HintDataList hints;

    private String remId;

    /**
     * Constructor.
     */
    public AdminHintsConfig(
        ApplicationProperties applicationProperties,
        HintDataStore hintDatastore,
        RendererManager rendererManager) {
        this.applicationProperties = applicationProperties;
        this.hintDatastore = hintDatastore;
        this.rendererManager = rendererManager;
    }

    @Override
    public String doDefault() throws Exception {
        hints = hintDatastore.getHintDataList();
        return INPUT;
    }

    @Override
    protected String doExecute() throws Exception {
        if (remId != null) {
            try {
                Long id = Long.parseLong(remId);
                HintDataList list = hintDatastore.getHintDataList();
                HintData data = list.getHintById(id);
                if (data != null) {
                    list.getHints().remove(data);
                    hintDatastore.setHintDataList(list);
                }
            } catch (NumberFormatException nex) {
                // nothing
            }
        }

        return getRedirect("AdminHintsConfig!default.jspa");
    }

    /**
     * Get context path.
     */
    public String getBaseUrl() {
        return applicationProperties.getBaseUrl();
    }

    public List<HintData> getHints() {
        return hints.getHints();
    }

    public String getIssueType(String id) {
        IssueType it = ComponentAccessor.getConstantsManager().getIssueTypeObject(id);
        if (it != null) {
            return it.getName();
        } else {
            return "issueTypeId=".concat(id.toString());
        }
    }

    public String getProject(Long id) {
        Project p = ComponentAccessor.getProjectManager().getProjectObj(id);
        if (p != null) {
            return p.getName();
        } else {
            return "projectId=".concat(id.toString());
        }
    }

    public String getRemId() {
        return remId;
    }

    public String getStatus(String id) {
        Status s = ComponentAccessor.getConstantsManager().getStatusObject(id);
        if (s != null) {
            return s.getName();
        } else {
            return "statusId=".concat(id.toString());
        }
    }

    public String getWiki(String str) {
        return rendererManager.getRenderedContent(AtlassianWikiRenderer.RENDERER_TYPE, str, null);
    }

    /**
     * Check administration permissions.
     */
    public boolean hasAdminPermission() {
        User user = getLoggedInUser();
        if (user == null) {
            return false;
        }

        return getPermissionManager().hasPermission(Permissions.ADMINISTER, getLoggedInUser());
    }

    public void setRemId(String remId) {
        this.remId = remId;
    }
}
