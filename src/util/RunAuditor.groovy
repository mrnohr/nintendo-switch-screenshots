package src.util

import src.models.SwitchTweet

class RunAuditor {
	ConfigObject config
	File auditFile
	List<AuditLog> audits

	public RunAuditor(ConfigObject config) {
		this.config = config
		initializeAuditLog()
	}

	public writeAuditLog() {
		auditFile.withWriter { out ->
			audits.each{
				out.println it.toLogString()
			}
		}
	}

	public isDownloaded(SwitchTweet tweet) {
		AuditLog logEntry = audits.find{it.twitterId == tweet.twitterId}
		return logEntry ? logEntry.downloadedImage : false
	}

	public markDownloaded(SwitchTweet tweet) {
		AuditLog logEntry = createOrPopLog(tweet)
		logEntry.downloadedImage = true
		audits << logEntry
	}

	public isUploaded(SwitchTweet tweet) {
		AuditLog logEntry = audits.find{it.twitterId == tweet.twitterId}
		return logEntry ? logEntry.uploadedToDropbox : false
	}

	public markUploaded(SwitchTweet tweet) {
		AuditLog logEntry = createOrPopLog(tweet)
		logEntry.uploadedToDropbox = true
		audits << logEntry
	}

	public isDeleted(SwitchTweet tweet) {
		AuditLog logEntry = audits.find{it.twitterId == tweet.twitterId}
		return logEntry ? logEntry.deleted : false
	}

	public markDeleted(SwitchTweet tweet) {
		AuditLog logEntry = createOrPopLog(tweet)
		logEntry.deleted = true
		audits << logEntry
	}

	public isCropped(SwitchTweet tweet) {
		AuditLog logEntry = audits.find{it.twitterId == tweet.twitterId}
		return logEntry ? logEntry.cropped : false
	}

	public markCropped(SwitchTweet tweet) {
		AuditLog logEntry = createOrPopLog(tweet)
		logEntry.cropped = true
		audits << logEntry
	}

	private AuditLog createOrPopLog(SwitchTweet tweet) {
		AuditLog logEntry = audits.find{it.twitterId == tweet.twitterId}
		if(logEntry) {
			audits.remove(logEntry)
		} else {
			logEntry = new AuditLog()
			logEntry.twitterId = tweet.twitterId
		}
		return logEntry
	}


	private initializeAuditLog() {
		validateOrCreateDirectory()
		audits = []
		if(auditFile.exists()) {
			auditFile.eachLine {
				audits << new AuditLog(it)
			}
		}
	}

	private validateOrCreateDirectory(){
		auditFile = new File(config.auditFile)
		if(!auditFile.exists()) {
			File directory = auditFile.getParentFile()
			println "Creating directory ${directory.absolutePath}"
			directory.mkdirs()
		}
	}

	class AuditLog {
		String twitterId
		boolean downloadedImage = false
		boolean uploadedToDropbox = false
		boolean deleted = false
		boolean cropped = false

		public AuditLog() {}

		public AuditLog(String line) {
			List<String> parts = line.split(',')
			twitterId = parts[0]
			downloadedImage = parts[1].toBoolean()
			uploadedToDropbox = parts[2].toBoolean()
			deleted = parts[3]?.toBoolean() ?: false
			cropped = parts[4]?.toBoolean() ?: false
		}

		public toLogString() {
			return [twitterId, downloadedImage, uploadedToDropbox, deleted, cropped].join(',')
		}
	}
}