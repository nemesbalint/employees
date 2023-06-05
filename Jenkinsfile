pipeline {
    agent any
    stages {
        stage('package') {
            steps {
                git 'https://github.com/nemesbalint/employees.git';
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