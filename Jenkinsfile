pipeline {
    agent any
    stages {
        stage('package') {
            steps {
                sh './mvnw clean package'
            }
        }
        stage('tests') {
            steps {
                sh './mvnw verify'
            }
        }
    }
}