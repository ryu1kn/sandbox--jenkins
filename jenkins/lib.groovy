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

private def notifySuccess() { notify 'good', 'SUCCESSFUL' }
private def notifyFailure() { notify 'danger', 'FAILED' }

private def notify(colour, heading) {
    slackSend(color: colour, message: "${heading}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}

return this
