package ru.andreymarkelov.atlas.plugins;

import java.util.ArrayList;
import java.util.List;

import com.atlassian.jira.issue.Issue;

public class HintDataList {
    private List<HintData> list;

    public HintDataList() {
        this.list = new ArrayList<HintData>();
    }

    public void addHintData(HintData hintData) {
        this.list.add(hintData);
    }

    public HintData getHintById(Long id) {
        for (HintData hintData : list) {
            if (hintData.getId().equals(id)) {
                return hintData;
            }
        }
        return null;
    }

    public HintData getHintByInfo(
            Long projectId,
            String issueTypeId,
            String statusId) {
        for (HintData hintData : list) {
            if (hintData.getProjectId().equals(projectId)
                    && hintData.getIssueTypeId().equals(issueTypeId)
                    && hintData.getStatusId().equals(statusId)) {
                return hintData;
            }
        }
        return null;
    }

    public HintData getHintForIssue(Issue issue) {
        for (HintData hintData : list) {
            if (hintData.isForIssue(issue)) return hintData;
        }

        return null;
    }

    public List<HintData> getHints() {
        return list;
    }

    public int getSize() {
        return list.size();
    }

    public boolean isHintForIssue(Issue issue) {
        for (HintData hintData : list) {
            if (hintData.isForIssue(issue)) return true;
        }

        return false;
    }

    public void removeHintData(HintData hintData) {
        this.list.remove(hintData);
    }

    @Override
    public String toString() {
        return "HintDataList[list=" + list + "]";
    }
}
