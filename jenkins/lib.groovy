def regressionSuite(Map params) {
    try {
        withEnv(['TARGET_ENV=tst']) {
            stageWithTask params.task
        }
        notifySuccess()
    } catch (e) {
        notifyFailure()
        throw e
    }
}

def stageWithTask(String taskName) {
    stage('Build') {
        println taskName
    }
}

private def notifySuccess() { notify 'good', '👌' }
private def notifyFailure() { notify 'danger', '👎' }

private def notify(String colour, String heading) {
    slackSend(color: colour, message: "${heading} ${env.JOB_NAME} #${env.BUILD_NUMBER} took ${currentBuild.durationString} <${env.BUILD_URL}|View Build>")
}

return this
