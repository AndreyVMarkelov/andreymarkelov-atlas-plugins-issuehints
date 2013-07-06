package ru.andreymarkelov.atlas.plugins;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.issue.status.Status;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.ApplicationProperties;

public class AdminHintsConfigEdit extends JiraWebActionSupport {
    /**
     * Unique ID.
     */
    private static final long serialVersionUID = -197610684816280856L;

    /**
     * Application properties.
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Datastore.
     */
    private final HintDataStore hintDatastore;

    /**
     * Hint ID.
     */
    private String hindId;

    private Long projectId;

    private String issueTypeId;

    private String statusId;

    private String hintTitle;

    private String hintBody;

    private String toggle;

    /**
     * Constructor.
     */
    public AdminHintsConfigEdit(
            ApplicationProperties applicationProperties,
            HintDataStore hintDatastore) {
        this.applicationProperties = applicationProperties;
        this.hintDatastore = hintDatastore;
    }

    @Override
    public String doDefault() throws Exception {
        if (hindId != null) {
            try {
                Long id = Long.parseLong(hindId);
                HintDataList list = hintDatastore.getHintDataList();
                HintData data = list.getHintById(id);
                if (data != null) {
                    this.projectId = data.getProjectId();
                    this.issueTypeId = data.getIssueTypeId();
                    this.statusId = data.getStatusId();
                    this.hintTitle = data.getHintTitle();
                    this.hintBody = data.getHintBody();
                    this.toggle = Boolean.valueOf(data.isToggle()).toString();
                }
            } catch (NumberFormatException nex) {
                addErrorMessage(getI18nHelper().getText("ru.andreymarkelov.atlas.plugins.hints.admin.errors.invalidhint"));
                return "editerror";
            }
        }

        return INPUT;
    }

    @Override
    @com.atlassian.jira.security.xsrf.RequiresXsrfCheck
    protected String doExecute() throws Exception {
        HintDataList list = hintDatastore.getHintDataList();
        HintData data = null;
        if (hindId != null) {
            data = list.getHintById(Long.parseLong(hindId));
        } else {
            data = list.getHintByInfo(projectId, issueTypeId, statusId);
        }

        if (data == null) {
            data = new HintData();
            data.setId(Counter.getValue());
            data.setProjectId(projectId);
            data.setIssueTypeId(issueTypeId);
            data.setStatusId(statusId);
            data.setHintTitle(hintTitle);
            data.setHintBody(hintBody);
            data.setToggle(Boolean.parseBoolean(toggle));
            list.addHintData(data);
        } else {
            data.setProjectId(projectId);
            data.setIssueTypeId(issueTypeId);
            data.setStatusId(statusId);
            data.setHintTitle(hintTitle);
            data.setHintBody(hintBody);
            data.setToggle(Boolean.parseBoolean(toggle));
        }

        hintDatastore.setHintDataList(list);

        return getRedirect("AdminHintsConfig!default.jspa");
    }

    @Override
    protected void doValidation() {
        super.doValidation();

        if (projectId == null) {
            addErrorMessage(getI18nHelper().getText("ru.andreymarkelov.atlas.plugins.hints.admin.form.project.error"));
        }

        if (issueTypeId == null || issueTypeId.length() == 0) {
            addErrorMessage(getI18nHelper().getText("ru.andreymarkelov.atlas.plugins.hints.admin.form.issuetype.error"));
        }

        if (statusId == null || statusId.length() == 0) {
            addErrorMessage(getI18nHelper().getText("ru.andreymarkelov.atlas.plugins.hints.admin.form.status.error"));
        }

        if (hintTitle == null || hintTitle.length() == 0) {
            addErrorMessage(getI18nHelper().getText("ru.andreymarkelov.atlas.plugins.hints.admin.form.hinttitle.error"));
        }

        if (hintBody == null || hintBody.length() == 0) {
            addErrorMessage(getI18nHelper().getText("ru.andreymarkelov.atlas.plugins.hints.admin.form.hintbody.error"));
        }
    }

    public Set<Pair<String, String>> getAllIssueTypes() {
        Collection<IssueType> types = ComponentAccessor.getConstantsManager().getAllIssueTypeObjects();
        Set<Pair<String, String>> res = new TreeSet<Pair<String, String>>();
        for (IssueType type : types) {
            res.add(new Pair<String, String>(type.getId(), type.getName()));
        }
        return res;
    }

    public Set<Pair<Long, String>> getAllProjects() {
        List<Project> projs = ComponentAccessor.getProjectManager().getProjectObjects();
        Set<Pair<Long, String>> res = new TreeSet<Pair<Long, String>>();
        for (Project proj : projs) {
            res.add(new Pair<Long, String>(proj.getId(), proj.getName()));
        }
        return res;
    }

    public Set<Pair<String, String>> getAllStatuses() {
        Collection<Status> statuses = ComponentAccessor.getConstantsManager().getStatusObjects();
        Set<Pair<String, String>> res = new TreeSet<Pair<String, String>>();
        for (Status status : statuses) {
            res.add(new Pair<String, String>(status.getId(), status.getName()));
        }
        return res;
    }

    /**
     * Get context path.
     */
    public String getBaseUrl() {
        return applicationProperties.getBaseUrl();
    }

    public String getHindId() {
        return hindId;
    }

    public String getHintBody() {
        return hintBody;
    }

    public String getHintTitle() {
        return hintTitle;
    }

    public String getIssueTypeId() {
        return issueTypeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getToggle() {
        return toggle;
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

    public void setHindId(String hindId) {
        this.hindId = hindId;
    }

    public void setHintBody(String hintBody) {
        this.hintBody = hintBody;
    }

    public void setHintTitle(String hintTitle) {
        this.hintTitle = hintTitle;
    }

    public void setIssueTypeId(String issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public void setToggle(String toggle) {
        this.toggle = toggle;
    }
}
