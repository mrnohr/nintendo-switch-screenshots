package src.actions

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata

import java.io.FileInputStream
import java.io.InputStream

import src.models.SwitchTweet

@Grab(group='com.dropbox.core', module='dropbox-core-sdk', version='3.0.3')
class DropboxUploader extends AbstractAction {
	DbxClientV2 client

	public DropboxUploader(ConfigObject config) {
		super(config)
		initializeDropbox()
	}

	public upload(SwitchTweet tweet, boolean cropped = false) {
		File downloadedFile = new File(tweet.downloadFilename, downloadDirectory)
		if(downloadedFile.exists()) {
			//println "Going to upload ${downloadedFile.absolutePath}"
			uploadFile(downloadedFile, false)
		} else {
			println "WARN: Could not find ${downloadedFile.absolutePath}"
		}

		if(config.crop.enabled && cropped) {
			File croppedFile = new File(tweet.croppedFilename, cropDirectory)
			if(croppedFile.exists()) {
				//println "Going to upload ${croppedFile.absolutePath}"
				uploadFile(croppedFile, true)
			} else {
				println "WARN: Could not find ${croppedFile.absolutePath}"
			}
		}
	}

	private void uploadFile(File file, boolean cropped) {
		String dropboxDestination = cropped ? "${config.dropbox.croppedDirectory}/${file.name}" : "${config.dropbox.mainDirectory}/${file.name}"

		InputStream inputStream = new FileInputStream(file)
		FileMetadata metadata = client.files().uploadBuilder(dropboxDestination).uploadAndFinish(inputStream);
		println "Uploaded to dropbox: ${metadata.getPathDisplay()}"
	}

	private void initializeDropbox() {
		DbxRequestConfig dropboxConfig = DbxRequestConfig.newBuilder("switch-screens").build()
		client = new DbxClientV2(dropboxConfig, config.dropbox.accessToken)
	}

}