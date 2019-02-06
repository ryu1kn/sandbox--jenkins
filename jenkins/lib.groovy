evaluate(new File("${pwd()}/jenkins/lib/Test.groovy"))

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
        println(new Test('awesome').greet)
    }
}

private def notifySuccess() { notify 'good', '👌' }
private def notifyFailure() { notify 'danger', '👎' }

private def notify(String colour, String heading) {
    slackSend(color: colour, message: "${heading} ${env.JOB_NAME} #${env.BUILD_NUMBER}, took ${duration()}. <${env.BUILD_URL}|View Build>")
}

private String duration() {
    // https://github.com/jenkinsci/slack-plugin/issues/327
    currentBuild.durationString.replace(' and counting', '')
}

return this
