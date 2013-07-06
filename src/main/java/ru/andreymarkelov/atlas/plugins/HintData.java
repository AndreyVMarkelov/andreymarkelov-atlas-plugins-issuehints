package ru.andreymarkelov.atlas.plugins;

import com.atlassian.jira.issue.Issue;

public class HintData {
    private Long id;

    private Long projectId;

    private String issueTypeId;

    private String statusId;

    private String hintTitle;

    private String hintBody;

    private boolean isToggle;

    public HintData() {
    }

    public HintData(
            Long id,
            Long projectId,
            String issueTypeId,
            String statusId,
            String hintTitle,
            String hintBody,
            boolean isToogle) {
        this.id = id;
        this.projectId = projectId;
        this.issueTypeId = issueTypeId;
        this.statusId = statusId;
        this.hintTitle = hintTitle;
        this.hintBody = hintBody;
        this.isToggle = isToogle;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof HintData)) return false;

        HintData other = (HintData) obj;

        if (projectId == null) {
            if (other.projectId != null) return false;
        } else if (!projectId.equals(other.projectId)) {
             return false;
        }
        if (projectId == null) {
            if (other.projectId != null) return false;
        } else if (!projectId.equals(other.projectId)) {
             return false;
        }
        if (issueTypeId == null) {
            if (other.issueTypeId != null) return false;
        } else if (!issueTypeId.equals(other.issueTypeId)) {
             return false;
        }
        if (statusId == null) {
            if (other.statusId != null)
                return false;
        } else if (!statusId.equals(other.statusId)) {
            return false;
        }

        return true;
    }

    public String getHintBody() {
        return hintBody;
    }

    public String getHintTitle() {
        return hintTitle;
    }

    public Long getId() {
        return id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((issueTypeId == null) ? 0 : issueTypeId.hashCode());
        result = prime * result + (int) (projectId ^ (projectId >>> 32));
        result = prime * result + ((statusId == null) ? 0 : statusId.hashCode());
        return result;
    }

    public boolean isForIssue(Issue issue) {
        if (issue != null
                && issue.getProjectObject().getId().equals(projectId)
                && issue.getIssueTypeObject().getId().equals(issueTypeId)
                && issue.getStatusObject().getId().equals(statusId)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isToggle() {
        return isToggle;
    }

    public void setHintBody(String hintBody) {
        this.hintBody = hintBody;
    }

    public void setHintTitle(String hintTitle) {
        this.hintTitle = hintTitle;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setToggle(boolean isToggle) {
        this.isToggle = isToggle;
    }

    @Override
    public String toString() {
        return "HintData[id=" + id + ", projectId=" + projectId + ", issueTypeId="
                + issueTypeId + ", statusId=" + statusId + ", hintTitle="
                + hintTitle + ", hintBody=" + hintBody + ", isToggle=" + isToggle + "]";
    }
}
