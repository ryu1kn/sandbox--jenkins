def regressionSuite(Map params) {
    try {
        withEnv(['TARGET_ENV=tst']) {
            stageWithTask params.task
        }
    } finally {
        def decoration = getMessageDecoration()
        if (decoration.notify) {
            slackSend(color: decoration.color, message: "${decoration.heading}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
}

def stageWithTask(String taskName) {
    stage('Build') {
        println taskName
    }
}

private Map getMessageDecoration() {
    switch (currentBuild.result) {
        case 'SUCCESS':
            [color: 'good', heading: 'SUCCESSFUL', notify: true]
            break
        case 'FAILURE':
            [color: 'danger', heading: 'FAILED', notify: true]
            break
        case 'UNSTABLE':
        default:
            [notify: false];
    }
}

return this
