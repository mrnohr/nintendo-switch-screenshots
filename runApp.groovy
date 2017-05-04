import src.SwitchScreenshots

String configFile="config/Config.groovy"
SwitchScreenshots app = new SwitchScreenshots(configFile)
app.takeActions()