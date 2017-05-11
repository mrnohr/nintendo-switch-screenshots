# Nintendo Switch Screenshot Downloader (V2)

I like being able to share screenshots from my Nintendo Switch to Twitter, but I'd also like to download a copy. This 
groovy script reads your timeline and downloads the screenshots to your computer.

![Alt text](images/readme-header.jpg?raw=true "Zelda")

## Version 1 vs. Version 2

The original (V1) script is a pretty basic groovy script that just downloads images from Twitter. The new version (v2) 
is more powerful and much more complicated. It still downloads images from Twitter, but it also can crop the pictures 
(useful for getting rid of the hearts in _Breath of the Wild_), automatically upload to dropbox, and optionally delete
the screenshots after processing.

**Version 1**: Git branch: [master](https://github.com/mrnohr/nintendo-switch-screenshots/tree/master)

**Version 2**: Git branch: [v2](https://github.com/mrnohr/nintendo-switch-screenshots/tree/v2)

## Downloading

Either clone this repository or download the latest 
[v2 release](https://github.com/mrnohr/nintendo-switch-screenshots/releases).

## Groovy

This runs with Groovy. If you need to install Groovy, [click here](http://groovy-lang.org/install.html).

## Config

All the configuration is done in `config/Config.groovy`. 

First, you will need to take the `Config.groovy.example` file and rename it to be just `Config.groovy`.

There are a few sections you can update, described below. You can turn off any of the actions by setting 
`enabled=false`

### download

This section is used to configure downloading images from Twitter. The biggest thing to change here is setting the 
`twitterUser` that you want to get images from.

### crop

This is specifically for _Breath of the Wild_. Most screenshots from that game have hearts at the top. This section, if 
enabled will configure automatically cropping out the hearts if the tweet has the #BreathoftheWild hashtag. It keeps 
both versions.

### dropbox

This section configures uploading to dropbox. You need to set up a dropbox app and add the access token to this file.

### delete

I added this section to clean up the files after they have been uploaded to dropbox.

### auditFile

This file is used to make sure the same image isn't downloaded multiple times.

## Twitter Auth Setup

To use this, you will need to create a new Twitter application at https://apps.twitter.com/. This is what will be used 
to lookup the tweets. Once you setup the application you will need to create yourself an access token, which is all done in the Twitter application setup.

In my case, I have a separate Twitter account that I marked private. My main twitter account is following that second 
account, and my main twitter account is the one I used to create the Twitter application. It is even easier if you just use one account.

### twitter4j.properties

Once you have the application keys and access tokens for your new app, create a `twitter4j.properties` file in the root 
directory (same as `screenshots.groovy`) in this format:

	debug=true
	oauth.consumerKey=*********************
	oauth.consumerSecret=******************************************
	oauth.accessToken=**************************************************
	oauth.accessTokenSecret=******************************************

There is an example `twitter4j.properties.example` file you can use to start with.

More information can be found here: http://twitter4j.org/en/configuration.html#fileconfiguration

## Running

Once you have the `Config.groovy` file set up, just run:

	groovy runApp.groovy
