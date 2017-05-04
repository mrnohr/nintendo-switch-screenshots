package src.actions

import src.models.SwitchTweet

class DropboxUploader {
	ConfigObject config
	File downloadDirectory
	File cropDirectory
	File workingDirectory = new File(".")

	File uploaderScript


	public DropboxUploader(ConfigObject config) {
		this.config = config
		initializeDirectories()
		initializeScriptLocation()
	}

	public upload(SwitchTweet tweet, boolean cropped = false) {
		File downloadedFile = new File(tweet.downloadFilename, downloadDirectory)
		if(downloadedFile.exists()) {
			//println "Going to upload ${downloadedFile.absolutePath}"
			uploadFile(downloadedFile, false)
		} else {
			println "WARN: Could not find ${downloadedFile.absolutePath}"
		}

		if(cropped) {
			File croppedFile = new File(tweet.croppedFilename, cropDirectory)
			if(croppedFile.exists()) {
				//println "Going to upload ${croppedFile.absolutePath}"
				uploadFile(croppedFile, true)
			} else {
				println "WARN: Could not find ${croppedFile.absolutePath}"
			}
		}
	}

	private uploadFile(File file, boolean cropped) {
		String dropboxDestination = cropped ? "${config.dropbox.croppedDirectory}/${file.name}" : "${config.dropbox.mainDirectory}/${file.name}"
		String command = "${uploaderScript.absolutePath} upload ${file.absolutePath} ${dropboxDestination}"

		def process = new ProcessBuilder(addShellPrefix(command))
				.directory(workingDirectory)
				.redirectErrorStream(true)
				.start()
		process.inputStream.eachLine {println it}
		process.waitFor();
		def exitValue = process.exitValue()
	}

	private def addShellPrefix(String command) {
		def commandArray = new String[3]
		commandArray[0] = "sh"
		commandArray[1] = "-c"
		commandArray[2] = command
		return commandArray
	}

	private initializeDirectories(){
		downloadDirectory = new File(config.download.directory)
		cropDirectory = new File(config.crop.directory)

		if(!downloadDirectory.exists()) {
			throw new FileNotFoundException("What did you do? $downloadDirectory does not exist")
		}

		if(!cropDirectory.exists()) {
			throw new FileNotFoundException("What did you do? $cropDirectory does not exist")
		}
	}

	private initializeScriptLocation() {
		uploaderScript = new File(config.dropbox.uploaderScript)
		if(!uploaderScript.exists()) {
			throw new FileNotFoundException("Could not find dropbox uploader script ${uploaderScript.absolutePath}")
		}
	}

}