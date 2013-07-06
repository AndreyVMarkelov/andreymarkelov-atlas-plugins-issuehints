package ru.andreymarkelov.atlas.plugins;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class HintDataStoreImpl implements HintDataStore {
    /**
     * Plug-In Jira db key.
     */
    private final String PLUGIN_KEY = "HintDatastore";

    private final String HINTS_KEY = "hintDataList";

    /**
     * Plug-In settings.
     */
    private final PluginSettings pluginSettings;

    /**
     * Constructor.
     */
    public HintDataStoreImpl(PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettings = pluginSettingsFactory.createSettingsForKey(PLUGIN_KEY);;
    }

    @Override
    public HintDataList getHintDataList() {
        String xmlData = (String)getPluginSettings().get(HINTS_KEY);
        if (xmlData != null && xmlData.length() > 0) {
            return Serializer.hintDataListFromXml(xmlData);
        }

        return new HintDataList();
    }

    private synchronized PluginSettings getPluginSettings() {
        return pluginSettings;
    }

    @Override
    public void setHintDataList(HintDataList hintDataList) {
        String xmlData = "";
        if (hintDataList != null) {
            xmlData = Serializer.hintDataListToXml(hintDataList);
            getPluginSettings().put(HINTS_KEY, xmlData);
        }
    }
}