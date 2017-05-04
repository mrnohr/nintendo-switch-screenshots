package src.actions

public abstract class AbstractAction {
	ConfigObject config
	File downloadDirectory
	File cropDirectory

	public AbstractAction(ConfigObject config, boolean createDirs = false) {
		this.config = config
		initializeDirectories(createDirs)
	}

	private initializeDirectories(boolean createDirs){
		downloadDirectory = new File(config.download.directory)
		cropDirectory = new File(config.crop.directory)

		if(config.download.enabled && !downloadDirectory.exists()) {
			println "Creating directory ${downloadDirectory.absolutePath}"
			downloadDirectory.mkdirs()
		}

		if(config.crop.enabled && !cropDirectory.exists()) {
			println "Creating directory ${cropDirectory.absolutePath}"
			cropDirectory.mkdirs()
		}
	}
}