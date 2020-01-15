package edu.pse.beast.stringresource;

/**
 * Class that manages all StringResources for the Options.
 *
 * @author Niels Hanselmann
 */
public class OptionStringResProvider extends StringResourceProvider {

    private StringResourceLoader optionStringRes;

    /**
     *
     * @param languageId the languageId. Set to "de" for german
     */
    public OptionStringResProvider(final String languageId) {
        super(languageId);
        this.initialize();
    }

    /**
     *
     * @return OptionStringRes
     */
    public StringResourceLoader getOptionStringRes() {
        return optionStringRes;
    }

    /**
     * Initializes all attributes. Loads all StringResourceLoaders with the
     * filenames. The superclass provides the functions for this process
     */
    @Override
    protected final void initialize() {

        optionStringRes = this.getStringResourceLoaderFromModuleName("Option");
    }
}
