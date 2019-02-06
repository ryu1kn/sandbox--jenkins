
final Map<String, Map<String, String>> statusSettings = [
        SUCCESS: [color: 'good', mark: ':ok_hand:'],
        FAILURE: [color: 'danger', mark: ':-1:']
]

def regressionSuite(Map<String, String> params) {
    Closure<String> notifyStatus = { String status -> notify(params.owner, status) }
    try {
        withEnv(['TARGET_ENV=tst']) {
            stageWithTask params.task
        }
        notifyStatus('SUCCESS')
    } catch (e) {
        notifyStatus('FAILURE')
        throw e
    }
}

def stageWithTask(String taskName) {
    stage('Build') {
        println taskName
    }
}

private String notify(String owner, String status) {
    statusSetting = statusSettings[status]
    slackSend(
        color: statusSetting.color,
        message: "${statusSetting.heading} ${env.JOB_NAME} #${env.BUILD_NUMBER}, took ${duration()}. Owner is $owner. <${env.BUILD_URL}|View Build>"
    )
}

private String duration() {
    // https://github.com/jenkinsci/slack-plugin/issues/327
    currentBuild.durationString.replace(' and counting', '')
}

return this
